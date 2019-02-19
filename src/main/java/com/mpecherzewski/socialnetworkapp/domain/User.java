package com.mpecherzewski.socialnetworkapp.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;


@Builder
@Value
public class User {
    String userId;
    LocalDateTime creationDate;
    List<String> trackedUsers;
}
