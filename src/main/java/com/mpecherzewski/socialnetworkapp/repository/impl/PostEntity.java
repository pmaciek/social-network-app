package com.mpecherzewski.socialnetworkapp.repository.impl;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;


@Builder
@Value
@EqualsAndHashCode(callSuper = true)
class PostEntity extends Entity {
    String userId;
    String message;
    LocalDateTime postDate;

}
