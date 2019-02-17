package com.mpecherzewski.socialnetworkapp.domain.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;

@Value
@Builder
public class User {

    String userId;
    LocalDateTime creationDate;
    Set<String> followedUsers;
}
