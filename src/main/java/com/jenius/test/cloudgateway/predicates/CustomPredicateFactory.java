package com.jenius.test.cloudgateway.predicates;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.http.HttpCookie;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.function.Predicate;

public class CustomPredicateFactory extends
        AbstractRoutePredicateFactory<CustomPredicateFactory.Config> {

    private final CustomPredicateService customPredicateService;

    public CustomPredicateFactory(CustomPredicateService customPredicateService) {
        super(Config.class);
        this.customPredicateService = customPredicateService;
    }

    // ... constructor omitted

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return (ServerWebExchange t) -> {
            List<String> headers = t.getRequest()
                    .getHeaders()
                    .get(config.getCustomerIdHeader());

            boolean isGolden;
            if ( headers == null || headers.isEmpty()) {
                isGolden = false;
            } else {
                String customerId = headers.get(0);
                isGolden = customPredicateService.isGoldenCustomer(customerId);
            }
            return config.isGolden() ? isGolden : !isGolden;
        };
    }

    @Validated
    public static class Config {
        boolean isGolden = true;
        @NotEmpty
        String customerIdHeader = "customerId";

        public Config( boolean isGolden, String customerIdHeader) {
            this.isGolden = isGolden;
            this.customerIdHeader = customerIdHeader;
        }

        public boolean isGolden() {
            return isGolden;
        }

        public void setGolden(boolean value) {
            this.isGolden = value;
        }

        /**
         * @return the customerIdHeader
         */
        public String getCustomerIdHeader() {
            return customerIdHeader;
        }

        /**
         * @param customerIdCookie the customerIdCookie to set
         */
        public void setCustomerIdHeader(String customerIdCookie) {
            this.customerIdHeader = customerIdCookie;
        }

    }
}

