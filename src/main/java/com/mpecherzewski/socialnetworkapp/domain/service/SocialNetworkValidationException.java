package com.mpecherzewski.socialnetworkapp.domain.service;


import lombok.Getter;

@Getter
public class SocialNetworkValidationException extends RuntimeException {
    private final String errorCode;

    public SocialNetworkValidationException(String errorCode) {
        this.errorCode = errorCode;
    }

    public SocialNetworkValidationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
