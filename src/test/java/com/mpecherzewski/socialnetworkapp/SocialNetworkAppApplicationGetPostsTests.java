package com.mpecherzewski.socialnetworkapp;

import com.mpecherzewski.socialnetworkapp.api.model.AddPostRequest;
import com.mpecherzewski.socialnetworkapp.domain.model.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SocialNetworkAppApplicationGetPostsTests {

    private static final String API_POSTS = "/api/v1/users/{0}/posts";
    private static final String API_FOLLOW_USER = "/api/v1/users/{1}/tracks/{2}";
    private static final String API_GET_FOLLOWED_POSTS = "/api/v1/users/{1}/tracks";


    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void testShouldReturnPosts() {
        //given
        String userID = "userTest1";
        String message1 = "new post";
        String message2 = "new post2";
        callAddPost(userID, AddPostRequest.builder().message(message1).build());
        callAddPost(userID, AddPostRequest.builder().message(message2).build());

        //when
        List<Post> posts = callGetPosts(userID);

        //then
        assertThat(posts).isNotEmpty();
        assertThat(posts).hasSize(2);
        assertThat(posts.get(0).getUser()).isEqualTo(userID);
        assertThat(posts.get(0).getMessage()).isEqualTo(message2);
        assertThat(posts.get(0).getPostDate()).isNotNull();
        assertThat(posts.get(1).getUser()).isEqualTo(userID);
        assertThat(posts.get(1).getMessage()).isEqualTo(message1);
        assertThat(posts.get(1).getPostDate()).isNotNull();
    }

    @Test
    public void testShouldReturnExceptionForNotExistsUSer() {
        //given
        String userID = "userTest2";

        //when
        ResponseEntity<String> result = this.restTemplate.getForEntity(MessageFormat.format(API_POSTS, userID), String.class);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(result.getBody()).isEqualTo("User '" + userID + "' does not exist");
    }


    @Test
    public void testShouldFollowUser() {
        //given
        String userID = "user1";
        String userID2 = "user2";
        callAddPost(userID, AddPostRequest.builder().message("m1").build());
        callAddPost(userID2, AddPostRequest.builder().message("m2").build());

        //when
        Set<String> followedUser = callFollowUser(userID, userID2);


        //then
        assertThat(followedUser).isNotEmpty();
        assertThat(followedUser).containsExactlyInAnyOrder(userID2);

    }

    private List<Post> callGetPosts(String userID) {
        return this.restTemplate.exchange(MessageFormat.format(API_POSTS, userID), HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {
        }).getBody();
    }

    private List<Post> callAddPost(String userID, AddPostRequest rq) {
        return this.restTemplate.exchange(MessageFormat.format(API_POSTS, userID), HttpMethod.POST, new HttpEntity(rq), new ParameterizedTypeReference<List<Post>>() {
        }).getBody();
    }

    private Set<String> callFollowUser(String userID, String userIdToFollow) {
        return this.restTemplate.exchange(MessageFormat.format(API_FOLLOW_USER, userID, userIdToFollow), HttpMethod.POST, null, new ParameterizedTypeReference<Set<String>>() {
        }).getBody();
    }


}

