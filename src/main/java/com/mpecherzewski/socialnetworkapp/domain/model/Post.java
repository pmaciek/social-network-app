package com.mpecherzewski.socialnetworkapp.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class Post {

    private String user;
    private String message;
    private LocalDateTime postDate;
}

