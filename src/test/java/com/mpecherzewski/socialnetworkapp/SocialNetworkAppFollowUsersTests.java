package com.mpecherzewski.socialnetworkapp;

import com.mpecherzewski.socialnetworkapp.users.dto.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.MessageFormat;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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
        String userId = "users";
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
        ResponseEntity<User> responseEntity = callFollowUser(userId, userId2);

        //then
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody().getId(), is(notNullValue()));
        assertThat(responseEntity.getBody().getUserId(), is(userId));
        assertThat(responseEntity.getBody().getCreationDate(), is(notNullValue()));
        assertThat(responseEntity.getBody().getTrackedUsers().size(), is(1));
        assertThat(responseEntity.getBody().getTrackedUsers(), equalTo(Collections.singletonList(userId2)));
    }
}

