package com.mpecherzewski.socialnetworkapp.posts.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Post {

    private String id;
    private String userId;
    private String message;
    private LocalDateTime postDate;
}

