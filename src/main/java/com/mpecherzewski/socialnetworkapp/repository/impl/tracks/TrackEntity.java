package com.mpecherzewski.socialnetworkapp.repository.impl.tracks;

import com.mpecherzewski.socialnetworkapp.repository.impl.Entity;
import lombok.EqualsAndHashCode;
import lombok.Value;


@EqualsAndHashCode(callSuper = false)
@Value
class TrackEntity extends Entity {
    private String userId;
}
