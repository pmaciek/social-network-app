package com.mpecherzewski.socialnetworkapp.repository.impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Builder
class PostEntity {
    private String message;
    private LocalDate postDate;
}
