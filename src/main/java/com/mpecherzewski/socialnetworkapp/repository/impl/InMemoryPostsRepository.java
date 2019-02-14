package com.mpecherzewski.socialnetworkapp.repository.impl;

import com.mpecherzewski.socialnetworkapp.domain.Post;
import com.mpecherzewski.socialnetworkapp.repository.PostsRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class InMemoryPostsRepository implements PostsRepository {

    private final Map<String, String> users = new ConcurrentHashMap<>();
    private final Map<String, List<String>> followedUsers = new ConcurrentHashMap<>();
    private final Map<String, List<PostEntity>> posts = new ConcurrentHashMap<>();

    @Override
    public List<Post> addPost(Post post) {
        String userID = addUserIfAbsent(post.getUser());
        addNewPost(post, userID);
        return getPosts(userID, post.getUser());
    }

    @Override
    public Optional<List<Post>> getPostsByUserName(String userName) {
        String userID = users.get(userName);
        if (Optional.ofNullable(userID).isPresent()) {
            return Optional.of(getPosts(userID, userName));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Post>> getFollowedPostsByUserName(String userName) {
        String userID = users.get(userName);
        if (Optional.ofNullable(userID).isPresent()) {
            List<PostEntity> collect = followedUsers.get(userID)
                    .stream()
                    .map(user -> posts.get(user))
                    .flatMap(List::stream)
                    .collect(Collectors.toList());

            return Optional.of();
        }
        return Optional.empty();
    }

    private List<Post> getPosts(String userID, String userName) {
        return posts.get(userID).stream().map(p -> createPost(userName, p)).collect(Collectors.toList());
    }

    private String addUserIfAbsent(String user) {
        return users.computeIfAbsent(user, u -> calculateUuid());
    }

    private void addNewPost(Post post, String userID) {
        posts.computeIfAbsent(userID, k -> new ArrayList<>()).add(createPostEntity(post));
    }

    private Post createPost(String userName, PostEntity p) {
        return Post.builder().user(userName).message(p.getMessage()).postDate(p.getPostDate()).build();
    }

    private PostEntity createPostEntity(Post post) {
        return PostEntity.builder().message(post.getMessage()).message(post.getMessage()).build();
    }

    private String calculateUuid() {
        return UUID.randomUUID().toString();
    }

}
