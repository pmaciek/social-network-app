package com.mpecherzewski.socialnetworkapp.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class User {
    private String id;
    private String userId;
    private LocalDateTime creationDate;
    private List<String> trackedUsers;
}
