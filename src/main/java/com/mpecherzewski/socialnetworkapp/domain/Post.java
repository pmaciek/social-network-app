package com.mpecherzewski.socialnetworkapp.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class Post {

    private String user;
    private String message;
    private LocalDate postDate;

}

