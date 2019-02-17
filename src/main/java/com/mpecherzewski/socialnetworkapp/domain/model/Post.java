package com.mpecherzewski.socialnetworkapp.domain.model;


import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class Post {

    String user;
    String message;
    LocalDateTime postDate;
}

