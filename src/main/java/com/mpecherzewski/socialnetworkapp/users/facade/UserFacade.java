package com.mpecherzewski.socialnetworkapp.users.facade;

import com.mpecherzewski.socialnetworkapp.users.domain.User;
import com.mpecherzewski.socialnetworkapp.users.dto.UserNotFoundException;
import com.mpecherzewski.socialnetworkapp.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserFacade {
    private final UsersRepository userRepository;

    public Optional<User> getUserById(String userId) {
        return userRepository.getUser(userId);
    }

    public User addUser(String userId) {
        return getUserById(userId).orElse(userRepository.addUser(userId));
    }

    public User trackUser(String userId, String userIdToTrack) {
        return userRepository.trackUser(userId, userIdToTrack);
    }

    public User loadUser(String userId) {
        return getUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
}
