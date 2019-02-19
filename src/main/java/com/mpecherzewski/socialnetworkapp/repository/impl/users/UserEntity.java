package com.mpecherzewski.socialnetworkapp.repository.impl.users;

import com.mpecherzewski.socialnetworkapp.repository.impl.Entity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;


@Builder
@Value
@EqualsAndHashCode(callSuper = true)
class UserEntity extends Entity {
    String userId;
    LocalDateTime creationDate;

}
