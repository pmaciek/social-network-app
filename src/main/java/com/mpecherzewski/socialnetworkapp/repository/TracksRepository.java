package com.mpecherzewski.socialnetworkapp.repository;

import java.util.List;

public interface TracksRepository {

    void trackUser(String userId, String userIdToTrack);

    List<String> getTracksByUserId(String userId);
}
