package com.test.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Component
public class TimeoutFilter implements GlobalFilter, Ordered {

    // 设置超时时间为60秒
    @Value("${gateway.expiry:60}") // 过期时间，默认 60 秒
    private long gatewayExpiry;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        log.info("==========TimeoutFilter==========");
        return chain.filter(exchange)
                .timeout(Duration.ofSeconds(gatewayExpiry))
                .onErrorResume(throwable -> {
                    if (throwable instanceof java.util.concurrent.TimeoutException) {
                        ServerHttpResponse response = exchange.getResponse();
                        response.setStatusCode(HttpStatus.GATEWAY_TIMEOUT);
                        return response.setComplete();
                    }
                    return Mono.error(throwable);
                });
    }

    @Override
    public int getOrder() {
        return 1; // 设置过滤器的执行顺序
    }

}