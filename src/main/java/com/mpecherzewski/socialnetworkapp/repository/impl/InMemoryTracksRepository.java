package com.mpecherzewski.socialnetworkapp.repository.impl;

import com.mpecherzewski.socialnetworkapp.repository.TracksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
class InMemoryTracksRepository implements TracksRepository {

    private final Map<String, Set<TrackEntity>> tacksByUserId = new ConcurrentHashMap<>();

    @Override
    public Set<TrackEntity> getTracksByUserId(String userId) {
        return tacksByUserId.getOrDefault(userId, Collections.emptySet());
    }

    @Override
    public void trackUser(String userId, String userIdToTrack) {
        addTrack(userId, userIdToTrack);
    }

    private void addTrack(String userId, String userIdToTrack) {
        tacksByUserId.computeIfAbsent(userId, k -> new HashSet<>()).add(new TrackEntity(userIdToTrack));
    }
}
