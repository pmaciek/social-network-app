package com.mpecherzewski.socialnetworkapp.posts.repository;

import com.mpecherzewski.socialnetworkapp.posts.domain.Post;

import java.util.List;

public interface PostsRepository {

    Post addPost(Post post);

    List<Post> getPostsByUserIds(List<String> userIds);

}
