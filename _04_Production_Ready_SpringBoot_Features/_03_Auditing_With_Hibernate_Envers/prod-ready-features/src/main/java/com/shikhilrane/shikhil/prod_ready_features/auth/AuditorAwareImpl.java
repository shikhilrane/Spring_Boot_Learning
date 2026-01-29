package com.shikhilrane.shikhil.prod_ready_features.auth;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorProvider")
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // later this will come from Spring Security
//        get security context
//        get Authentication
//        get the principle
//        get the username from spring security
        return Optional.of("Shikhil Rane"); // This is just hard coded value we have provided here for understanding otherwise we get it by spring security
    }
}