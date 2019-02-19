package com.mpecherzewski.socialnetworkapp.users.domain;

import com.mpecherzewski.socialnetworkapp.users.dto.User;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class UserFacade {
    private final UsersRepository userRepository;

    public Optional<User> getUserById(String userId) {
        return userRepository.getUser(userId).map(mapUserEntityToUser());
    }

    public void addUser(String userId) {
        userRepository.addUser(userId);
    }

    public User trackUser(String userId, String userIdToTrack) {
        return mapUserEntityToUser().apply(userRepository.trackUser(userId, userIdToTrack));
    }

    private Function<UserEntity, User> mapUserEntityToUser() {
        return user -> User.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .creationDate(user.getCreationDate())
                .trackedUsers(Collections.unmodifiableList(new ArrayList<>(user.getTrackedUsers())))
                .build();
    }
}
