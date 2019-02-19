package com.mpecherzewski.socialnetworkapp;

import com.mpecherzewski.socialnetworkapp.api.model.AddPostDto;
import com.mpecherzewski.socialnetworkapp.domain.Post;
import com.mpecherzewski.socialnetworkapp.repository.impl.LocalDateTimeProvider;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;

class SocialNetworkAppBaseTests {
    static final String API_POSTS = "/api/v1/users/{0}/posts";
    static final String API_GET_FOLLOWED_POSTS = "/api/v1/users/{0}/tracks";
    static final String API_FOLLOW_USER = "/api/v1/users/{0}/tracks/{1}";
    static final String MSG_USER_DOES_NOT_EXISTS = "User {0} does not exist";

    @Mock
    protected LocalDateTimeProvider localDateTimeProvider;

    @Before
    public void setUp() {
        when(localDateTimeProvider.getLocalDateNow()).thenReturn(LocalDateTime.now());
    }

    @Autowired
    private TestRestTemplate restTemplate;

    ResponseEntity callAddPost(String userID, AddPostDto rq) {
        return this.restTemplate.postForEntity(MessageFormat.format(API_POSTS, userID), new HttpEntity(rq), ResponseEntity.class);
    }

    ResponseEntity callFollowUser(String userID, String userIdToFollow) {
        return this.restTemplate.postForEntity(MessageFormat.format(API_FOLLOW_USER, userID, userIdToFollow), null, ResponseEntity.class);
    }

    List<Post> callGetPosts(String userID) {
        return this.restTemplate.exchange(MessageFormat.format(API_POSTS, userID), HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {
        }).getBody();
    }

    List<Post> callGetFollowedPosts(String userID) {
        return this.restTemplate.exchange(MessageFormat.format(API_GET_FOLLOWED_POSTS, userID), HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {
        }).getBody();
    }


    AddPostDto createPostRq(String m1) {
        return new AddPostDto(m1);
    }

    ResponseEntity<String> callPostForEntity(String api, Object request) {
        return this.restTemplate.postForEntity(api, request, String.class);
    }

    ResponseEntity<String> callGetForEntity(String api) {
        return this.restTemplate.getForEntity(api, String.class);
    }

    class PostMatcher extends BaseMatcher<Post> {
        private String user;
        private String message;

        PostMatcher(String user, String message) {
            this.user = user;
            this.message = message;
        }

        @Override
        public boolean matches(Object o) {
            return user.equalsIgnoreCase(((Post) o).getUser())
                    && message.equalsIgnoreCase(((Post) o).getMessage())
                    && ((Post) o).getPostDate() != null;
        }

        @Override
        public void describeTo(Description description) {
        }
    }
}
