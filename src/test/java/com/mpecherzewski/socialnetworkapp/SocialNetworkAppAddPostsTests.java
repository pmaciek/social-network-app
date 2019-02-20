package com.mpecherzewski.socialnetworkapp;

import com.mpecherzewski.socialnetworkapp.posts.domain.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.MessageFormat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SocialNetworkAppAddPostsTests extends SocialNetworkAppBaseTests {

    @Test
    public void testShouldAddNewPostForNotExistingUser() {
        //given
        String userID = "userTest1";
        String message = "new posts";

        //when
        ResponseEntity<Post> responseEntity = callAddPost(userID, createPostRq(message));

        //then
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(responseEntity.getBody().getId(), is(notNullValue()));
        assertThat(responseEntity.getBody().getUserId(), is(userID));
        assertThat(responseEntity.getBody().getMessage(), is(message));
        assertThat(responseEntity.getBody().getPostDate(), is(notNullValue()));
    }

    @Test
    public void testShouldAddNewPostForExistingUser() {
        //given
        String userID = "userTest2";
        String message1 = "new posts";
        String message2 = "new post2";
        callAddPost(userID, createPostRq(message1));

        //when
        ResponseEntity<Post> responseEntity = callAddPost(userID, createPostRq(message2));

        //then
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(responseEntity.getBody().getId(), is(notNullValue()));
        assertThat(responseEntity.getBody().getUserId(), is(userID));
        assertThat(responseEntity.getBody().getMessage(), is(message2));
        assertThat(responseEntity.getBody().getPostDate(), is(notNullValue()));
    }

    @Test
    public void testShouldReturnBadRequestForMessageLongerThan140Chars() {
        //given
        String userID = "userTest1";
        String message = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        //when
        ResponseEntity<String> result = callPostForEntity(MessageFormat.format(API_POSTS, userID), createPostRq(message));

        //then
        assertThat(result.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(result.getBody(), containsString("size must be between 0 and 140"));
    }
}

