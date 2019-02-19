package com.mpecherzewski.socialnetworkapp.repository;

import com.mpecherzewski.socialnetworkapp.domain.model.Post;

import java.util.List;

public interface PostsRepository {

    void addPost(Post post);

    List<Post> getPostsByUserIds(List<String> userIds);

}
