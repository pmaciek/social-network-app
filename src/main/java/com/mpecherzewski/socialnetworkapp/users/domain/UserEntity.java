package com.mpecherzewski.socialnetworkapp.users.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@Builder
@Value
class UserEntity {
    String id = UUID.randomUUID().toString();
    String userId;
    LocalDateTime creationDate;
    Set<String> trackedUsers;

    void addTrackedUser(String userId) {
        trackedUsers.add(userId);
    }
}
