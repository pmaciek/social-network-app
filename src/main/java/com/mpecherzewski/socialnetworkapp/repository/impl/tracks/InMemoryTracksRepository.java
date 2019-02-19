package com.mpecherzewski.socialnetworkapp.repository.impl.tracks;

import com.mpecherzewski.socialnetworkapp.repository.TracksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;

@Repository
@RequiredArgsConstructor
class InMemoryTracksRepository implements TracksRepository {

    private final Map<String, Set<TrackEntity>> tacksByUserId = new ConcurrentHashMap<>();

    @Override
    public List<String> getTracksByUserId(String userId) {
        return tacksByUserId.getOrDefault(userId, Collections.emptySet()).stream().map(TrackEntity::getUserId).collect(toList());
    }

    @Override
    public void trackUser(String userId, String userIdToTrack) {
        addTrack(userId, userIdToTrack);
    }

    private void addTrack(String userId, String userIdToTrack) {
        tacksByUserId.computeIfAbsent(userId, k -> new HashSet<>()).add(new TrackEntity(userIdToTrack));
    }
}
