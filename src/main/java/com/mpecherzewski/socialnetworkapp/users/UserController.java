package com.mpecherzewski.socialnetworkapp.users;

import com.mpecherzewski.socialnetworkapp.users.facade.UserFacade;
import com.mpecherzewski.socialnetworkapp.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
class UserController {
    private final UserFacade userFacade;

    @PostMapping(path = "/users/{userId}/tracks/{userIdToTrack}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    User trackUser(@PathVariable String userId, @PathVariable String userIdToTrack) {
        return userFacade.trackUser(userId, userIdToTrack);
    }
}
