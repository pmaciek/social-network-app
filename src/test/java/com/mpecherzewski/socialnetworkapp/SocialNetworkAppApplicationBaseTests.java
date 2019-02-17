package com.mpecherzewski.socialnetworkapp;

import com.mpecherzewski.socialnetworkapp.api.model.AddPostRequest;
import com.mpecherzewski.socialnetworkapp.domain.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

abstract class SocialNetworkAppApplicationBaseTests {
    static final String API_POSTS = "/api/v1/users/{0}/posts";
    static final String API_GET_FOLLOWED_POSTS = "/api/v1/users/{0}/tracks";
    static final String API_FOLLOW_USER = "/api/v1/users/{0}/tracks/{1}";
    static final String MSG_USER_DOES_NOT_EXISTS = "User {0} does not exist";

    @Autowired
    private TestRestTemplate restTemplate;

    List<Post> callAddPost(String userID, AddPostRequest rq) {
        return this.restTemplate.exchange(MessageFormat.format(API_POSTS, userID), HttpMethod.POST, new HttpEntity(rq), new ParameterizedTypeReference<List<Post>>() {
        }).getBody();
    }

    Set<String> callFollowUser(String userID, String userIdToFollow) {
        return this.restTemplate.exchange(MessageFormat.format(API_FOLLOW_USER, userID, userIdToFollow), HttpMethod.POST, null, new ParameterizedTypeReference<Set<String>>() {
        }).getBody();
    }

    List<Post> callGetPosts(String userID) {
        return this.restTemplate.exchange(MessageFormat.format(API_POSTS, userID), HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {
        }).getBody();
    }

    List<Post> callGetFollowedPosts(String userID) {
        return this.restTemplate.exchange(MessageFormat.format(API_GET_FOLLOWED_POSTS, userID), HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {
        }).getBody();
    }


    AddPostRequest createPostRq(String m1) {
        return AddPostRequest.builder().message(m1).build();
    }

    ResponseEntity<String> callPostForEntity(String api) {
        return this.restTemplate.postForEntity(api, null, String.class);
    }

    ResponseEntity<String> callGetForEntity(String api) {
        return this.restTemplate.getForEntity(api, String.class);
    }
}
