package com.mpecherzewski.socialnetworkapp.posts;

import com.mpecherzewski.socialnetworkapp.posts.facade.PostFacade;
import com.mpecherzewski.socialnetworkapp.posts.dto.AddPostDto;
import com.mpecherzewski.socialnetworkapp.posts.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
class PostController {
    private final PostFacade postFacade;

    @GetMapping(path = "/users/{userId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Post> getPosts(@PathVariable String userId) {
        return postFacade.getPostsByUserId(userId);
    }

    @PostMapping(path = "/users/{userId}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Post addPost(@PathVariable String userId, @Valid @RequestBody AddPostDto addPostRequest) {
        return postFacade.addNewPost(userId, addPostRequest.getMessage());
    }

    @GetMapping(path = "/users/{userId}/trackedPosts", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Post> getTrackedPosts(@PathVariable String userId) {
        return postFacade.getTrackedPostsByUserId(userId);
    }

}
