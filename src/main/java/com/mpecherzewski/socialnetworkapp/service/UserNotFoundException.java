package com.mpecherzewski.socialnetworkapp.service;


import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private static final String MSG_USER_DOES_NOT_EXIST = "User %s does not exist";
    private final String errorCode;

    UserNotFoundException(String userId) {
        this.errorCode = String.format(MSG_USER_DOES_NOT_EXIST, userId);
    }
}
