package com.jenius.test.cloudgateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
    @Override
    public GatewayFilter apply(CustomFilter.Config config) {
        return (exchange, chain) -> {
            return Mono.empty();
        };
    }

    public static class Config {
        // Put the configuration properties for your filter here
    }
}
