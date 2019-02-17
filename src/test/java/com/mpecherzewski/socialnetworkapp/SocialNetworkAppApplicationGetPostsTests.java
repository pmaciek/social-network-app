package com.mpecherzewski.socialnetworkapp;

import com.mpecherzewski.socialnetworkapp.domain.model.Post;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.MessageFormat;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SocialNetworkAppApplicationGetPostsTests extends SocialNetworkAppApplicationBaseTests {

    @Test
    public void testShouldReturnPostsForUser()  {
        //given
        String userID = "userTestAddPost1";
        String userID2 = "userTestAddPost2";
        String message1 = "new post";
        String message2 = "new post2";
        callAddPost(userID, createPostRq(message1));
        callAddPost(userID, createPostRq(message2));
        callAddPost(userID2, createPostRq(message1));

        //when
        List<Post> posts = callGetPosts(userID);

        //then
        assertThat(posts, hasSize(2));
        assertThat(posts, hasItems(new PostMatcher(userID, message1), new PostMatcher(userID, message2)));
    }


    @Test
    public void testShouldReturnPostsInReverseOrder() throws InterruptedException {
        //given
        String userID = "userTestAddPost";
        String message1 = "new post";
        String message2 = "new post2";
        String message3 = "new post3";
        callAddPost(userID, createPostRq(message1));
        Thread.sleep(100);
        callAddPost(userID, createPostRq(message2));
        Thread.sleep(100);
        callAddPost(userID, createPostRq(message3));

        //when
        List<Post> posts = callGetPosts(userID);

        //then
        assertThat(posts, hasSize(3));
        assertThat(posts, IsIterableContainingInOrder.contains(
                new PostMatcher(userID, message3),
                new PostMatcher(userID, message2),
                new PostMatcher(userID, message1)));
    }

    @Test
    public void testShouldReturnBadRequestForNotExistingUser() {
        //given
        String userID = "invalidUser";

        //when
        ResponseEntity<String> result = callGetForEntity(MessageFormat.format(API_POSTS, userID));

        //then
        assertThat(result.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(result.getBody(), is(MessageFormat.format(MSG_USER_DOES_NOT_EXISTS, userID)));
    }

}

