package com.mpecherzewski.socialnetworkapp.users.domain;

import com.mpecherzewski.socialnetworkapp.infastructure.LocalDateTimeProvider;
import com.mpecherzewski.socialnetworkapp.users.dto.UserNotFoundException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
class InMemoryUsersRepository implements UsersRepository {
    private final ConcurrentHashMap<String, UserEntity> usersByUserId = new ConcurrentHashMap<>();

    private final LocalDateTimeProvider localDateTimeProvider;

    @Override
    public Optional<UserEntity> getUser(String userId) {
        return Optional.ofNullable(usersByUserId.get(userId));
    }

    @Override
    public void addUser(String userId) {
        usersByUserId.computeIfAbsent(userId, (user) -> createNewUserEntity(userId));
    }

    @Override
    public UserEntity trackUser(String userId, String userIdToTrack) {
        UserEntity user = getUser(userId).orElseThrow(() -> new UserNotFoundException(userId));
        UserEntity userToTrack = getUser(userIdToTrack).orElseThrow(() -> new UserNotFoundException(userIdToTrack));
        UserEntity updatedUser = createNewUserEntityWithNewTrack(user, userToTrack.getUserId());
        usersByUserId.put(user.getUserId(), updatedUser);
        return updatedUser;
    }

    private UserEntity createNewUserEntity(String userId) {
        return UserEntity.builder().userId(userId).creationDate(getLocalDateNow()).trackedUsers(new HashSet<>()).build();
    }

    private UserEntity createNewUserEntityWithNewTrack(UserEntity userEntity, String userToTrack) {
        HashSet<String> tracks = new HashSet<>(userEntity.getTrackedUsers());
        tracks.add(userToTrack);
        return UserEntity.builder().userId(userEntity.getUserId()).creationDate(getLocalDateNow()).trackedUsers(tracks).build();
    }

    private LocalDateTime getLocalDateNow() {
        return localDateTimeProvider.getLocalDateNow();
    }
}
