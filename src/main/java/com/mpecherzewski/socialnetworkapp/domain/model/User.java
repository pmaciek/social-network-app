package com.mpecherzewski.socialnetworkapp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@Builder
@Getter
public class User {

    private String userId;
    private LocalDateTime creationDate;
    private Set<String> followedUsers;
}
