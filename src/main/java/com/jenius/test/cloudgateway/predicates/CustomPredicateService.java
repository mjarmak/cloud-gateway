package com.jenius.test.cloudgateway.predicates;

import org.springframework.stereotype.Component;

/**
 * @author Philippe
 *
 */
@Component
public class CustomPredicateService {

    public boolean isGoldenCustomer(String customerId) {

        // TODO: Add some AI logic to check is this customer deserves a "golden" status ;^)
        return "baeldung".equalsIgnoreCase(customerId);
    }

}