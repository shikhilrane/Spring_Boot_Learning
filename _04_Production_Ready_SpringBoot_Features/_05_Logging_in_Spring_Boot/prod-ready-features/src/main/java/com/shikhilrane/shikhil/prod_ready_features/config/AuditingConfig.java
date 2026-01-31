package com.shikhilrane.shikhil.prod_ready_features.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditingConfig {
}

// This class turns ON the auditing feature in Spring Boot so automatic tracking can work