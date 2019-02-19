package com.mpecherzewski.socialnetworkapp.repository.impl.posts;

import com.mpecherzewski.socialnetworkapp.repository.impl.Entity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;



@Builder
@EqualsAndHashCode(callSuper = true)
@Value
class PostEntity extends Entity {
    String userId;
    String message;
    LocalDateTime postDate;

}
