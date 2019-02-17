package com.mpecherzewski.socialnetworkapp.api;

import com.mpecherzewski.socialnetworkapp.api.model.AddPostRequest;
import com.mpecherzewski.socialnetworkapp.domain.model.Post;
import com.mpecherzewski.socialnetworkapp.domain.service.SocialNetworkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SocialNetworkApiController {
    private final SocialNetworkService socialNetworkService;

    @GetMapping(path = "/users/{userId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Post> getUserPosts(@PathVariable String userId) {
        return socialNetworkService.getUserPosts(userId);
    }

    @PostMapping(path = "/users/{userId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Post> addNewPost(@PathVariable String userId, @Valid @RequestBody AddPostRequest addPostRequest) {
        return socialNetworkService.addNewPost(userId, addPostRequest.getMessage());
    }

    @PostMapping(path = "/users/{userId}/tracks/{userIdToFollow}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<String> followUser(@PathVariable String userId, @PathVariable String userIdToFollow) {
        return socialNetworkService.followUser(userId, userIdToFollow);
    }

    @GetMapping(path = "/users/{userId}/tracks", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Post> getFollowedPosts(@PathVariable String userId) {
        return socialNetworkService.getFollowedPosts(userId);
    }
}
