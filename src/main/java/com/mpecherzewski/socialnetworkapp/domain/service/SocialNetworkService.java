package com.mpecherzewski.socialnetworkapp.domain.service;

import com.mpecherzewski.socialnetworkapp.domain.model.Post;
import com.mpecherzewski.socialnetworkapp.domain.model.User;
import com.mpecherzewski.socialnetworkapp.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SocialNetworkService {
    private final PostsRepository postsRepository;

    public List<Post> addNewPost(String userId, String messsage) {
        Post newPost = Post.builder().user(userId).message(messsage).build();
        return sortReverseOrder(postsRepository.addPost(newPost));
    }

    public List<Post> getUserPosts(String userId) {
        User user = postsRepository.getUser(userId).orElseThrow(getSocialNetworkValidationExceptionSupplier(userId));
        return sortReverseOrder(postsRepository.getPostsByUserName(user.getUserId()));
    }

    public Set<String> followUser(String userId, String userIdToFollow) {
        User user = postsRepository.getUser(userId).orElseThrow(getSocialNetworkValidationExceptionSupplier(userId));
        User userToFollow = postsRepository.getUser(userIdToFollow).orElseThrow(getSocialNetworkValidationExceptionSupplier(userIdToFollow));
        return postsRepository.followUser(user.getUserId(), userToFollow.getUserId());
    }

    public List<Post> getFollowedPosts(String userId) {
        User user = postsRepository.getUser(userId).orElseThrow(getSocialNetworkValidationExceptionSupplier(userId));
        return sortReverseOrder(postsRepository.getFollowedPostsByUserName(user.getUserId()));
    }

    private List<Post> sortReverseOrder(List<Post> posts) {
        return posts.stream().sorted(Comparator.comparing(Post::getPostDate).reversed()).collect(Collectors.toList());
    }

    private Supplier<SocialNetworkValidationException> getSocialNetworkValidationExceptionSupplier(String userId) {
        return () -> new SocialNetworkValidationException(String.format("User \'%s\' does not exist", userId));
    }
}
