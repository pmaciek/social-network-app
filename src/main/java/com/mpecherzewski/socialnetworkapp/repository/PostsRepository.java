package com.mpecherzewski.socialnetworkapp.repository;

import com.mpecherzewski.socialnetworkapp.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostsRepository {

    List<Post> addPost(Post post);

    Optional<List<Post>> getPostsByUserName(String userName);

    Optional<List<Post>> getFollowedPostsByUserName(String userName);
}
