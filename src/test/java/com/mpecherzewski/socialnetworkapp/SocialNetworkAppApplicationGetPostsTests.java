package com.mpecherzewski.socialnetworkapp;

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
public class SocialNetworkAppApplicationGetPostsTests extends SocialNetworkAppApplicationBaseTests {

    @Test
    public void testShouldReturnPostsInReverseOrder() throws InterruptedException {
        //given
        String userID = "userTestAddPost";
        String message1 = "new post";
        String message2 = "new post2";
        callAddPost(userID, createPostRq(message1));
        Thread.sleep(100);
        callAddPost(userID, createPostRq(message2));

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
    public void testShouldReturnBadRequestForNotExistingUser() {
        //given
        String userID = "invalidUser";

        //when
        ResponseEntity<String> result = callGetForEntity(MessageFormat.format(API_POSTS, userID));

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(result.getBody()).isEqualTo(MessageFormat.format(MSG_USER_DOES_NOT_EXISTS, userID));
    }

}

