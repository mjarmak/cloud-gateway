package com.jenius.test.cloudgateway.predicates;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.jenius.test.cloudgateway.predicates.CustomPredicateFactory.Config;

@Configuration
public class CustomPredicatesConfig {


    @Bean
    public CustomPredicateFactory goldenCustomer(CustomPredicateService goldenCustomerService) {
        return new CustomPredicateFactory(goldenCustomerService);
    }


    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder, CustomPredicateFactory gf ) {

        return builder.routes()
                .route("admin_route", r ->
                        r.predicate(gf.apply(new Config(true, "customerId")))
                                .and()
                                .path("/bank/**")
                                .filters(f -> f.redirect(300, "https://www.facebook.com"))
                                .uri("http://localhost:8080")
                )
                .route("user_route", r ->
                        r.predicate(gf.apply(new Config(false, "customerId")))
                                .and()
                                .path("/bank/**")
                                .filters(f -> f.redirect(300, "https://www.linkedin.com"))
                                .uri("http://localhost:8080")
                )
                .build();
    }
}