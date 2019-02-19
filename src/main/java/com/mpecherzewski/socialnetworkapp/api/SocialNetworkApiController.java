package com.mpecherzewski.socialnetworkapp.api;

import com.mpecherzewski.socialnetworkapp.api.model.AddPostDto;
import com.mpecherzewski.socialnetworkapp.domain.model.Post;
import com.mpecherzewski.socialnetworkapp.service.SocialNetworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SocialNetworkApiController {
    private final SocialNetworkService socialNetworkService;

    @GetMapping(path = "/users/{userId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Post> getUserPosts(@PathVariable String userId) {
        return socialNetworkService.getUserPosts(userId);
    }

    @GetMapping(path = "/users/{userId}/tracks", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Post> getTrackedPosts(@PathVariable String userId) {
        return socialNetworkService.getTrackedPosts(userId);
    }

    @PostMapping(path = "/users/{userId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity post(@PathVariable String userId, @Valid @RequestBody AddPostDto addPostRequest) {
        socialNetworkService.addNewPost(userId, addPostRequest.getMessage());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping(path = "/users/{userId}/tracks/{userIdToTrack}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity track(@PathVariable String userId, @PathVariable String userIdToTrack) {
        socialNetworkService.trackUser(userId, userIdToTrack);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
