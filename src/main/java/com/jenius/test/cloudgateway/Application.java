package com.jenius.test.cloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.URI;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	 either do it in the yml file, or do it here in java
	@Bean
	public RouteLocator dynamicZipCodeRoute(RouteLocatorBuilder builder) {
		return builder.routes()

//				forwards /baeldung to http://baeldung.com
				.route("dynamicRewrite", r ->
						r.path("/baeldung")
								.filters(f -> f.filter((exchange, chain) -> {
									ServerHttpRequest req = exchange.getRequest();
									addOriginalRequestUrl(exchange, req.getURI());

									ServerHttpRequest request = req.mutate().uri(URI.create("https://baeldung.com")).build();
									exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, request.getURI());
									return chain.filter(exchange.mutate().request(request).build());
								}))
								.uri("https://baeldung.com"))

//				.route("r1", r -> r.host("**.baeldung.com")
//						.and()
//						.path("/baeldung")
//						.uri("http://baeldung.com"))

				.build();
	}
}
