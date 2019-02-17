package com.mpecherzewski.socialnetworkapp;

import com.mpecherzewski.socialnetworkapp.api.model.AddPostRequest;
import com.mpecherzewski.socialnetworkapp.domain.model.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.MessageFormat;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SocialNetworkAppApplicationGetFollowedPostsTests extends SocialNetworkAppApplicationBaseTests {

    @Test
    public void testShouldReturnFollowedPostsInReverseOrder() throws InterruptedException {
        //given
        String userId = "callingUser";
        String userId1 = "userFollowed1";
        String userId2 = "userFollowed2";
        String message1 = "new post 1";
        String message2 = "new post 2";
        String message3 = "new post 3";
        callAddPost(userId, createPostRq(message1));
        callAddPost(userId1, createPostRq(message2));
        Thread.sleep(100);
        callAddPost(userId2, createPostRq(message3));
        callFollowUser(userId, userId1);
        callFollowUser(userId, userId2);

        //when
        List<Post> posts = callGetFollowedPosts(userId);

        //then
        assertThat(posts).isNotEmpty();
        assertThat(posts).hasSize(2);
        assertThat(posts.get(0).getUser()).isEqualTo(userId2);
        assertThat(posts.get(0).getMessage()).isEqualTo(message3);
        assertThat(posts.get(0).getPostDate()).isNotNull();
        assertThat(posts.get(1).getUser()).isEqualTo(userId1);
        assertThat(posts.get(1).getMessage()).isEqualTo(message2);
        assertThat(posts.get(1).getPostDate()).isNotNull();
    }

    @Test
    public void testShouldReturnBadRequestForNotExistingUser() {
        //given
        String userID = "notExistingUser";

        //when
        ResponseEntity<String> result = callGetForEntity(MessageFormat.format(API_GET_FOLLOWED_POSTS, userID));

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(result.getBody()).isEqualTo(MessageFormat.format(MSG_USER_DOES_NOT_EXISTS,userID));
    }


}

