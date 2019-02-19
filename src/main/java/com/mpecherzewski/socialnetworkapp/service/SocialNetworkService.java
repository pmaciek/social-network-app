package com.mpecherzewski.socialnetworkapp.service;

import com.mpecherzewski.socialnetworkapp.domain.Post;
import com.mpecherzewski.socialnetworkapp.domain.User;
import com.mpecherzewski.socialnetworkapp.repository.PostsRepository;
import com.mpecherzewski.socialnetworkapp.repository.TracksRepository;
import com.mpecherzewski.socialnetworkapp.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SocialNetworkService {
    private final UsersRepository userRepository;
    private final PostsRepository postsRepository;
    private final TracksRepository tracksRepository;

    public void addNewPost(String userId, String message) {
        if (!userRepository.getUser(userId).isPresent()) {
            userRepository.addUser(userId);
        }
        Post newPost = Post.builder().user(userId).message(message).build();
        postsRepository.addPost(newPost);
    }

    public List<Post> getUserPosts(String userId) {
        User user = userRepository.getUser(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return sortReverseOrder(postsRepository.getPostsByUserIds(Collections.singletonList(user.getUserId())));
    }

    public void trackUser(String userId, String userIdToTrack) {
        User user = userRepository.getUser(userId).orElseThrow(() -> new UserNotFoundException(userId));
        User userToTrack = userRepository.getUser(userIdToTrack).orElseThrow(() -> new UserNotFoundException(userIdToTrack));
        tracksRepository.trackUser(user.getUserId(), userToTrack.getUserId());
    }

    public List<Post> getTrackedPosts(String userId) {
        User user = userRepository.getUser(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return sortReverseOrder(postsRepository.getPostsByUserIds(user.getTrackedUsers()));
    }

    private List<Post> sortReverseOrder(List<Post> posts) {
        return posts.stream()
                .sorted(Comparator.comparing(Post::getPostDate).reversed())
                .collect(Collectors.toList());
    }
}