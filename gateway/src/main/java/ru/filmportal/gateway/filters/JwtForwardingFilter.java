package ru.filmportal.gateway.filters;

import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtForwardingFilter implements GatewayFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (token != null && token.startsWith("Bearer ")) {
            exchange.getRequest().mutate().header(HttpHeaders.AUTHORIZATION, token);
        }

        return chain.filter(exchange);
    }
}