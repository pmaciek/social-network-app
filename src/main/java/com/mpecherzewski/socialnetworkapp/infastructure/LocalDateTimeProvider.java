package com.mpecherzewski.socialnetworkapp.infastructure;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LocalDateTimeProvider {
    public LocalDateTime getLocalDateNow() {
        return LocalDateTime.now();
    }
}
