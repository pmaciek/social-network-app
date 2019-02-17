package com.mpecherzewski.socialnetworkapp.repository.impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
class PostEntity {
    private String message;
    private LocalDateTime postDate;
}
