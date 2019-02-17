package com.mpecherzewski.socialnetworkapp.repository.impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
class UserEntity {

    private String userId;
    private LocalDateTime creationDate;
}
