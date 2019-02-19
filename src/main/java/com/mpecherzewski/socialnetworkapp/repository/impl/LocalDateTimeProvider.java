package com.mpecherzewski.socialnetworkapp.repository.impl;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LocalDateTimeProvider {
    public LocalDateTime getLocalDateNow() {
        return LocalDateTime.now();
    }
}
