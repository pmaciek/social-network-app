package com.mpecherzewski.socialnetworkapp.repository.impl;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public abstract class Entity {
    private final String resourceId = UUID.randomUUID().toString();
}
