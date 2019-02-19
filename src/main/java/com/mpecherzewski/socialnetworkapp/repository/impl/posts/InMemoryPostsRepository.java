package com.mpecherzewski.socialnetworkapp.repository.impl.posts;

import com.mpecherzewski.socialnetworkapp.domain.Post;
import com.mpecherzewski.socialnetworkapp.repository.PostsRepository;
import com.mpecherzewski.socialnetworkapp.repository.impl.LocalDateTimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
class InMemoryPostsRepository implements PostsRepository {

    private final Map<String, List<PostEntity>> postsByUserId = new ConcurrentHashMap<>();

    private final LocalDateTimeProvider localDateTimeProvider;

    @Override
    public void addPost(Post post) {
        String userId = post.getUser();
        addNewPost(post, userId);
    }

    @Override
    public List<Post> getPostsByUserIds(List<String> userIds) {
        return userIds.stream()
                .map(this::retrievePostsByUserId)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<Post> retrievePostsByUserId(String userId) {
        return postsByUserId.get(userId).stream()
                .map(p -> createPost(userId, p))
                .collect(Collectors.toList());
    }


    private void addNewPost(Post post, String userID) {
        postsByUserId.computeIfAbsent(userID, k -> new ArrayList<>()).add(createNewPostEntity(post));
    }

    private Post createPost(String userName, PostEntity p) {
        return Post.builder().user(userName).message(p.getMessage()).postDate(p.getPostDate()).build();
    }

    private PostEntity createNewPostEntity(Post post) {
        return PostEntity.builder().userId(post.getUser()).postDate(getLocalDateNow()).message(post.getMessage()).build();
    }

    private LocalDateTime getLocalDateNow() {
        return localDateTimeProvider.getLocalDateNow();
    }
}
