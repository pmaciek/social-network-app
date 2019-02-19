package com.mpecherzewski.socialnetworkapp.posts.domain;

import com.mpecherzewski.socialnetworkapp.posts.dto.Post;

import java.util.List;

public interface PostsRepository {

    PostEntity addPost(Post post);

    List<PostEntity> getPostsByUserIds(List<String> userIds);

}
