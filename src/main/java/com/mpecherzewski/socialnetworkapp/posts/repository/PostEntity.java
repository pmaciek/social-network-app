package com.mpecherzewski.socialnetworkapp.posts.repository;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;


@Builder
@Value
class PostEntity {
    String id = UUID.randomUUID().toString();
    String userId;
    String message;
    LocalDateTime postDate;
}
