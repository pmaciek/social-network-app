package com.mpecherzewski.socialnetworkapp.posts.facade;

import com.mpecherzewski.socialnetworkapp.posts.domain.Post;
import com.mpecherzewski.socialnetworkapp.posts.repository.PostsRepository;
import com.mpecherzewski.socialnetworkapp.users.domain.User;
import com.mpecherzewski.socialnetworkapp.users.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostFacade {
    private final PostsRepository postsRepository;
    private final UserFacade userFacade;

    public Post addNewPost(String userId, String message) {
        User user = addUserIfMissing(userId);
        Post newPost = createPost(user.getUserId(), message);
        return postsRepository.addPost(newPost);
    }

    public List<Post> getPostsByUserId(String userId) {
        User user = userFacade.loadUser(userId);
        return sortReverseOrder(postsRepository.getPostsByUserIds(Collections.singletonList(user.getUserId())));
    }

    private User addUserIfMissing(String userId) {
        return userFacade.addUser(userId);
    }

    public List<Post> getTrackedPostsByUserId(String userId) {
        User user = userFacade.loadUser(userId);
        return sortReverseOrder(postsRepository.getPostsByUserIds(user.getTrackedUsers()));
    }

    private Post createPost(String userId, String message) {
        return Post.builder()
                .userId(userId)
                .message(message)
                .build();
    }

    private List<Post> sortReverseOrder(List<Post> posts) {
        return posts.stream()
                .sorted(Comparator.comparing(Post::getPostDate).reversed())
                .collect(Collectors.toList());
    }
}
