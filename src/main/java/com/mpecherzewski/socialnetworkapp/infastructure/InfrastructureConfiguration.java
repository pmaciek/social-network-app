package com.mpecherzewski.socialnetworkapp.infastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InfrastructureConfiguration {

    @Bean
    LocalDateTimeProvider localDateTimeProvider() {
        return new LocalDateTimeProvider();
    }
}
