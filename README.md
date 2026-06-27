# Distributed Rate-Limiting with Spring Cloud Gateway

>  **Project Focus & Context:** This repository was built as a hands-on architectural exercise to learn reactive microservices, distributed rate-limiting patterns, and cross-service security filters. The primary goal was achieving functional completion, wiring up a distributed system, and learning the underlying network mechanics rather than enterprise-grade production optimization.

A scalable, reactive microservices backend demonstrating user authentication, centralized API routing, and distributed rate limiting using Redis.

## The Architecture Flow

The system processes client requests through a distinct authentication and gateway validation pipeline:

1. **Authentication & Token Generation (`auth_service`):** The client first talks directly to the authentication service to log in. Upon successful authentication, this service generates and returns a signed JSON Web Token (JWT).
2. **API Gateway Entry (`gateway_service` on Port 8080):** For all subsequent business requests (like accessing job listings), the client passes the JWT in the headers and hits the Gateway.
3. **Distributed Rate Limiting:** The Gateway immediately checks a **Redis** instance using a Token-Bucket algorithm. If the client has exceeded their request limit, the Gateway drops the request right there with an HTTP 429 (Too Many Requests).
4. **JWT Validation Filter:** Once traffic constraints are satisfied, a custom global gateway filter intercepts the request, parses the Bearer token, and validates its signature and claims.
5. **Downstream Routing (`JobListing`):** Validated, rate-limited requests are securely forwarded to the core business services.

```mermaid
sequenceDiagram
    autonumber
    actor Client
    participant Auth as auth_service
    participant GW as gateway_service 
    participant Redis as Redis Cache
    participant Job as JobListing Service

    %% Step 1: Authentication
    Client->>Auth: Post Login / Register
    Auth-->>Client: Return Signed JWT

    %% Step 2: Gateway Request
    Client->>GW: API Request (with JWT Header)
    
    %% Step 3 & 4: Rate Limiting & Validation
    GW->>Redis: Check Token Bucket (Rate Limit)
    Redis-->>GW: Limit Allowed
    Note over GW: Execute Custom JWT Filter<br/>(Validate Signature & Claims)
    
    %% Step 5: Downstream Forwarding
    GW->>Job: Forward Request
    Job-->>Client: Return Data
