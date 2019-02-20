package com.mpecherzewski.socialnetworkapp.users.repository;

import com.mpecherzewski.socialnetworkapp.users.domain.User;

import java.util.Optional;

public interface UsersRepository {

    Optional<User> getUser(String userId);

    User addUser(String userId);

    User trackUser(String userId, String userIdToTrack);
}
