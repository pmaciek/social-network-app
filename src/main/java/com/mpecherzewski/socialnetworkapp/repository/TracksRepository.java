package com.mpecherzewski.socialnetworkapp.repository;

import com.mpecherzewski.socialnetworkapp.repository.impl.TrackEntity;

import java.util.Set;

public interface TracksRepository {

    void trackUser(String userId, String userIdToTrack);

    Set<TrackEntity> getTracksByUserId(String userId);
}
