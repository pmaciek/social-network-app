package com.mpecherzewski.socialnetworkapp.repository.impl;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class TrackEntity extends Entity {
    private String userId;
}
