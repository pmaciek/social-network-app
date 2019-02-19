package com.mpecherzewski.socialnetworkapp.repository;

import com.mpecherzewski.socialnetworkapp.domain.model.User;

import java.util.Optional;

public interface UsersRepository {

    Optional<User> getUser(String userId);
    void addUser(String userId);
}
