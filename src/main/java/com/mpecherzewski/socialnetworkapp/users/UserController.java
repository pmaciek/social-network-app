package com.mpecherzewski.socialnetworkapp.users;

import com.mpecherzewski.socialnetworkapp.users.domain.UserFacade;
import com.mpecherzewski.socialnetworkapp.users.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
class UserController {
    private final UserFacade userFacade;

    @PostMapping(path = "/users/{userId}/tracks/{userIdToTrack}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<User> trackUser(@PathVariable String userId, @PathVariable String userIdToTrack) {
        User user = userFacade.trackUser(userId, userIdToTrack);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }
}
