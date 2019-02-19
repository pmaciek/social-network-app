package com.mpecherzewski.socialnetworkapp.users.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class User {
    String id;
    String userId;
    LocalDateTime creationDate;
    List<String> trackedUsers;
}
