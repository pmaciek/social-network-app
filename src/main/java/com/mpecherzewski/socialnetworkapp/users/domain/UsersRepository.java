package com.mpecherzewski.socialnetworkapp.users.domain;

import java.util.Optional;

public interface UsersRepository {

    Optional<UserEntity> getUser(String userId);

    void addUser(String userId);

    UserEntity trackUser(String userId, String userIdToTrack);
}
