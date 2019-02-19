package com.mpecherzewski.socialnetworkapp.users.domain;

import com.mpecherzewski.socialnetworkapp.users.dto.User;
import com.mpecherzewski.socialnetworkapp.users.dto.UserNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
public class UserFacade {
    private final UsersRepository userRepository;

    public Optional<User> getUserById(String userId) {
        return userRepository.getUser(userId).map(this::mapUserEntityToUser);
    }

    public void addUser(String userId) {
        userRepository.addUser(userId);
    }

    public User trackUser(String userId, String userIdToTrack) {
        return mapUserEntityToUser(userRepository.trackUser(userId, userIdToTrack));
    }

    public User loadUser(String userId) {
        return getUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    private User mapUserEntityToUser(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .userId(userEntity.getUserId())
                .creationDate(userEntity.getCreationDate())
                .trackedUsers(Collections.unmodifiableList(new ArrayList<>(userEntity.getTrackedUsers())))
                .build();
    }
}
