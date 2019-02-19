package com.mpecherzewski.socialnetworkapp.domain.model;


import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;


@Builder
@Value
public class Post {

    private String user;
    private String message;
    private LocalDateTime postDate;
}

