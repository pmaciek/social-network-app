package com.mpecherzewski.socialnetworkapp;

import com.mpecherzewski.socialnetworkapp.posts.dto.Post;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SocialNetworkAppGetPostsTests extends SocialNetworkAppBaseTests {

    @Before
    public void setUp() {
        LocalDateTime now = LocalDateTime.now();
        when(localDateTimeProvider.getLocalDateNow()).thenReturn(now, now.plusSeconds(100), now.plusSeconds(200));
    }

    @Test
    public void testShouldReturnPostsForUser() {
        //given
        String userID = "userTestAddPost1";
        String userID2 = "userTestAddPost2";
        String message1 = "new posts";
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
    public void testShouldReturnPostsInReverseOrder() {
        //given
        String userID = "userTestAddPost";
        String message1 = "new posts";
        String message2 = "new post2";
        String message3 = "new post3";
        callAddPost(userID, createPostRq(message1));
        callAddPost(userID, createPostRq(message2));
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
    public void testShouldReturnNotFoundForNotExistingUser() {
        //given
        String userID = "invalidUser";

        //when
        ResponseEntity<String> result = callGetForEntity(MessageFormat.format(API_POSTS, userID));

        //then
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(result.getBody(), is(MessageFormat.format(MSG_USER_DOES_NOT_EXISTS, userID)));
    }

}

