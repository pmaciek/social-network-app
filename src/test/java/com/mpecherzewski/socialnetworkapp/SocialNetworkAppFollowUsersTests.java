package com.mpecherzewski.socialnetworkapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.MessageFormat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SocialNetworkAppFollowUsersTests extends SocialNetworkAppBaseTests {

    @Test
    public void testShouldReturnNotFoundForNotExistingUser() {
        //given
        String userId = "notExistingUser";
        String userIdToFollow = "notExistingUserToFollow";

        //when
        ResponseEntity<String> result = callPostForEntity(MessageFormat.format(API_FOLLOW_USER, userId, userIdToFollow), null);

        //then
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(result.getBody(), is(MessageFormat.format(MSG_USER_DOES_NOT_EXISTS, userId)));
    }

    @Test
    public void testShouldReturnNotFoundForNotExistingUserToFollow() {
        //given
        String userId = "user";
        String userIdToFollow = "notExistingUserToFollow";
        callAddPost(userId, createPostRq("m1"));

        //when
        ResponseEntity<String> result = callPostForEntity(MessageFormat.format(API_FOLLOW_USER, userId, userIdToFollow), null);

        //then
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(result.getBody(), is(MessageFormat.format(MSG_USER_DOES_NOT_EXISTS, userIdToFollow)));
    }


    @Test
    public void testShouldFollowUser() {
        //given
        String userId = "user1";
        String userId2 = "user2";
        String userId3 = "user3";
        callAddPost(userId, createPostRq("m1"));
        callAddPost(userId2, createPostRq("m2"));
        callAddPost(userId3, createPostRq("m2"));

        //when
        ResponseEntity followedUser1 = callFollowUser(userId, userId2);
        ResponseEntity followedUser2 = callFollowUser(userId, userId3);

        //then
        assertThat(followedUser1.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(followedUser2.getStatusCode(), is(HttpStatus.CREATED));

    }
}

