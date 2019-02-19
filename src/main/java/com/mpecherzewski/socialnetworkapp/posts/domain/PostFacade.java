package com.mpecherzewski.socialnetworkapp.posts.domain;

import com.mpecherzewski.socialnetworkapp.posts.dto.Post;
import com.mpecherzewski.socialnetworkapp.users.domain.UserFacade;
import com.mpecherzewski.socialnetworkapp.users.dto.User;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PostFacade {
    private final PostsRepository postsRepository;
    private final UserFacade userFacade;

    public Post addNewPost(String userId, String message) {
        if (!userFacade.getUserById(userId).isPresent()) {
            userFacade.addUser(userId);
        }
        Post newPost = createPost(userId, message);
        return mapToPost(postsRepository.addPost(newPost));
    }

    public List<Post> getPostsByUserId(String userId) {
        User user = userFacade.loadUser(userId);
        return sortReverseOrder(
                postsRepository.getPostsByUserIds(
                        Collections.singletonList(user.getUserId())).stream()
                        .map(this::mapToPost)
                        .collect(Collectors.toList()));
    }

    public List<Post> getTrackedPostsByUserId(String userId) {
        User user = userFacade.loadUser(userId);
        return sortReverseOrder(postsRepository.getPostsByUserIds(user.getTrackedUsers()).stream().map(this::mapToPost)
                .collect(Collectors.toList()));
    }

    private Post createPost(String userId, String message) {
        return Post.builder().userId(userId).message(message).build();
    }

    private List<Post> sortReverseOrder(List<Post> posts) {
        return posts.stream()
                .sorted(Comparator.comparing(Post::getPostDate).reversed())
                .collect(Collectors.toList());
    }

    private Post mapToPost(PostEntity post) {
        return Post.builder().id(post.getId()).userId(post.getUserId()).message(post.getMessage()).postDate(post.getPostDate()).build();
    }
}
