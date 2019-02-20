package com.mpecherzewski.socialnetworkapp.users.repository;

import com.mpecherzewski.socialnetworkapp.infastructure.LocalDateTimeProvider;
import com.mpecherzewski.socialnetworkapp.users.domain.User;
import com.mpecherzewski.socialnetworkapp.users.dto.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
class InMemoryUsersRepository implements UsersRepository {
    private final ConcurrentHashMap<String, UserEntity> usersByUserId = new ConcurrentHashMap<>();

    private final LocalDateTimeProvider localDateTimeProvider;

    @Override
    public Optional<User> getUser(String userId) {
        return Optional.ofNullable(usersByUserId.get(userId)).map(this::mapUserEntityToUser);
    }

    @Override
    public User addUser(String userId) {
        return mapUserEntityToUser(usersByUserId.computeIfAbsent(userId, (user) -> createNewUserEntity(userId, new HashSet<>())));
    }

    @Override
    public User trackUser(String userId, String userIdToTrack) {
        User user = getUser(userId).orElseThrow(() -> new UserNotFoundException(userId));
        User userToTrack = getUser(userIdToTrack).orElseThrow(() -> new UserNotFoundException(userIdToTrack));
        UserEntity updatedUser = createNewUserEntityWithNewTrack(user, userToTrack.getUserId());
        usersByUserId.put(user.getUserId(), updatedUser);
        return mapUserEntityToUser(updatedUser);
    }

    private UserEntity createNewUserEntityWithNewTrack(User user, String userToTrack) {
        HashSet<String> tracks = new HashSet<>(user.getTrackedUsers());
        tracks.add(userToTrack);
        return createNewUserEntity(user.getUserId(), tracks);
    }

    private UserEntity createNewUserEntity(String userId, HashSet<String> trackedUsers) {
        return UserEntity.builder()
                .userId(userId)
                .creationDate(getLocalDateNow())
                .trackedUsers(trackedUsers)
                .build();
    }

    private User mapUserEntityToUser(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .userId(userEntity.getUserId())
                .creationDate(userEntity.getCreationDate())
                .trackedUsers(Collections.unmodifiableList(new ArrayList<>(userEntity.getTrackedUsers())))
                .build();
    }

    private LocalDateTime getLocalDateNow() {
        return localDateTimeProvider.getLocalDateNow();
    }
}
