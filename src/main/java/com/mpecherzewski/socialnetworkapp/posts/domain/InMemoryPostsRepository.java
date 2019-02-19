package com.mpecherzewski.socialnetworkapp.posts.domain;

import com.mpecherzewski.socialnetworkapp.infastructure.LocalDateTimeProvider;
import com.mpecherzewski.socialnetworkapp.posts.dto.Post;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class InMemoryPostsRepository implements PostsRepository {
    private final ConcurrentHashMap<String, List<PostEntity>> postsByUserId = new ConcurrentHashMap<>();

    private final LocalDateTimeProvider localDateTimeProvider;

    @Override
    public PostEntity addPost(Post post) {
        PostEntity newPostEntity = createNewPostEntity(post);
        addNewPost(newPostEntity);
        return newPostEntity;
    }

    @Override
    public List<PostEntity> getPostsByUserIds(List<String> userIds) {
        return userIds.stream()
                .map(this::retrievePostsByUserId)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<PostEntity> retrievePostsByUserId(String userId) {
        return Optional.ofNullable(postsByUserId.get(userId)).orElse(Collections.emptyList());
    }

    private void addNewPost(PostEntity post) {
        postsByUserId.computeIfAbsent(post.getUserId(), k -> new ArrayList<>()).add(post);
    }

    private PostEntity createNewPostEntity(Post post) {
        return PostEntity.builder().userId(post.getUserId()).postDate(getLocalDateNow()).message(post.getMessage()).build();
    }

    private LocalDateTime getLocalDateNow() {
        return localDateTimeProvider.getLocalDateNow();
    }
}
