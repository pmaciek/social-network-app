package com.mpecherzewski.socialnetworkapp.repository;

import com.mpecherzewski.socialnetworkapp.domain.model.Post;
import com.mpecherzewski.socialnetworkapp.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostsRepository {

    Optional<User> getUser(String userName);

    List<Post> addPost(Post post);

    List<Post> getPostsByUserName(String userId);

    Set<String> followUser(String userId, String userIdToFollow);

    List<Post> getFollowedPostsByUserName(String userId);
}
