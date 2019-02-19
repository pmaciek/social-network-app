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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SocialNetworkAppGetFollowedPostsTests extends SocialNetworkAppBaseTests {

    @Before
    public void setUp()  {
        LocalDateTime now = LocalDateTime.now();
        when(localDateTimeProvider.getLocalDateNow()).thenReturn(now, now.plusSeconds(100), now.plusSeconds(200));
    }

    @Test
    public void testShouldReturnFollowedPostsInReverseOrder() {
        //given
        String userId = "callingUser";
        String userId1 = "userFollowed1";
        String userId2 = "userFollowed2";
        String message1 = "new posts 1";
        String message2 = "new posts 2";
        String message3 = "new posts 3";
        callAddPost(userId, createPostRq(message1));
        callAddPost(userId1, createPostRq(message2));
        callAddPost(userId2, createPostRq(message3));
        callFollowUser(userId, userId1);
        callFollowUser(userId, userId2);

        //when
        List<Post> posts = callGetFollowedPosts(userId);

        //then
        assertThat(posts, hasSize(2));
        assertThat(posts, IsIterableContainingInOrder.contains(
                new PostMatcher(userId2, message3), new PostMatcher(userId1, message2)));
    }

    @Test
    public void testShouldReturnNotFoundForNotExistingUser() {
        //given
        String userID = "notExistingUser";

        //when
        ResponseEntity<String> result = callGetForEntity(MessageFormat.format(API_GET_FOLLOWED_POSTS, userID));

        //then
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(result.getBody(), is(MessageFormat.format(MSG_USER_DOES_NOT_EXISTS, userID)));
    }
}

