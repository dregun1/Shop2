package com.example.shop_17.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditConfig {

    @Bean
    public AuditorAwareImpl<String> auditorProvide(){
        return  new AuditorAwareImpl();
    }
}
