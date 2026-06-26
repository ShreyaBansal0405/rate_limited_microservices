package com.shreya.gateway_service.filter;


import com.shreya.gateway_service.service.jwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtValidationFilter implements GlobalFilter, Ordered {


    private final jwtService jwtServiceob;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 1. Bypass authentication completely for the /login endpoint
        if (request.getURI().getPath().contains("/login")) {
            return chain.filter(exchange);
        }

        // 2. Check if the Authorization Header exists
        if (!request.getHeaders().containsHeader(HttpHeaders.AUTHORIZATION)) {
            return onError(exchange, "Missing Authorization Header", HttpStatus.UNAUTHORIZED);
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // 3. Check if the header matches standard 'Bearer <token>' format
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return onError(exchange, "Invalid Authorization Header Format", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);

        try {
            // 4. Validate the token using your jwtService logic
            boolean isValid = jwtServiceob.isValid(token);


            if (!isValid) {
                return onError(exchange, "Unauthorized Token", HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            return onError(exchange, "Token Verification Failed", HttpStatus.UNAUTHORIZED);
        }

        // 5. Token is valid! Let the request proceed through to /jobs (port 8085)
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        // Run this filter at the absolute highest priority before everything else
        return Ordered.HIGHEST_PRECEDENCE;
    }
}