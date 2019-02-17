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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SocialNetworkAppApplicationAddPostsTests extends SocialNetworkAppApplicationBaseTests {

    @Test
    public void testShouldAddNewPostForNotExistingUser() {
        //given
        String userID = "userTest1";
        String message = "new post";

        //when
        List<Post> posts = callAddPost(userID, createPostRq(message));

        //then
        assertThat(posts, hasSize(1));
        assertThat(posts, hasItems(new PostMatcher(userID, message)));
    }

    @Test
    public void testShouldAddNewPostForExistingUser() {
        //given
        String userID = "userTest2";
        String message1 = "new post";
        String message2 = "new post2";
        callAddPost(userID, createPostRq(message1));

        //when
        List<Post> posts = callAddPost(userID, createPostRq(message2));

        //then
        assertThat(posts, hasSize(2));
        assertThat(posts, hasItems(new PostMatcher(userID, message1), new PostMatcher(userID, message2)));
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

