package com.shreya.gateway_service;

import com.shreya.gateway_service.service.jwtService;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimiterConfig {

    @Bean
    @Primary
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        // Forcefully builds the connection directly to your running Docker Redis
        return new LettuceConnectionFactory("127.0.0.1", 6379);
    }

    @Bean
    public KeyResolver userKeyResolver() {
        // Keeps tracking independent per user token
        return exchange -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                return Mono.just(authHeader.substring(7));
            }
            return Mono.just("anonymous-user");
        };
    }
}