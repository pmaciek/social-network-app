package com.mpecherzewski.socialnetworkapp;

import com.mpecherzewski.socialnetworkapp.api.model.AddPostRequest;
import com.mpecherzewski.socialnetworkapp.domain.model.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SocialNetworkAppApplicationAddPostsTests extends SocialNetworkAppApplicationBaseTests {

    @Test
    public void testShouldAddNewPostForNotExistingUser() {
        //given
        String userID = "userTest1";
        String new_post = "new post";
        AddPostRequest rq = AddPostRequest.builder().message(new_post).build();

        //when
        List<Post> posts = callAddPost(userID, rq);

        //then
        assertThat(posts).isNotEmpty();
        assertThat(posts).hasSize(1);
        assertThat(posts.get(0).getUser()).isEqualTo(userID);
        assertThat(posts.get(0).getMessage()).isEqualTo(new_post);
        assertThat(posts.get(0).getPostDate()).isNotNull();
    }

    @Test
    public void testShouldAddNewPostForExistingUser() {
        //given
        String userID = "userTest2";
        String new_post = "new post";
        AddPostRequest rq = AddPostRequest.builder().message(new_post).build();
        callAddPost(userID, rq);

        //when
        List<Post> posts = callAddPost(userID, rq);

        //then
        assertThat(posts).isNotEmpty();
        assertThat(posts).hasSize(2);
        assertThat(posts.get(0).getUser()).isEqualTo(userID);
        assertThat(posts.get(0).getMessage()).isEqualTo(new_post);
        assertThat(posts.get(0).getPostDate()).isNotNull();
        assertThat(posts.get(1).getUser()).isEqualTo(userID);
        assertThat(posts.get(1).getMessage()).isEqualTo(new_post);
        assertThat(posts.get(1).getPostDate()).isNotNull();
    }
}

