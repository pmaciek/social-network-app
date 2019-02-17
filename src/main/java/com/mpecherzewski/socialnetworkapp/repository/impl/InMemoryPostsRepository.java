package com.mpecherzewski.socialnetworkapp.repository.impl;

import com.mpecherzewski.socialnetworkapp.domain.model.Post;
import com.mpecherzewski.socialnetworkapp.domain.model.User;
import com.mpecherzewski.socialnetworkapp.repository.PostsRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
class InMemoryPostsRepository implements PostsRepository {

    private final Map<String, UserEntity> users = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> followedUsers = new ConcurrentHashMap<>();
    private final Map<String, List<PostEntity>> posts = new ConcurrentHashMap<>();

    @Override
    public List<Post> addPost(Post post) {
        String userId = post.getUser();
        addUserIfAbsent(userId);
        addNewPost(post, userId);
        return retrievePostsByUserId(userId);
    }

    @Override
    public Optional<User> getUser(String userName) {
        return Optional.ofNullable(users.get(userName)).map(mapUserEntityToUser());
    }

    @Override
    public List<Post> getPostsByUserName(String userId) {
        return retrievePostsByUserId(userId);
    }

    @Override
    public List<Post> getFollowedPostsByUserName(String userId) {
        if (isUserPresent(userId)) {
            return getFollowedUsersByUserId(userId)
                    .stream()
                    .map(user -> retrievePostsByUserId(user))
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public Set<String> followUser(String userId, String userIdToFollow) {
        if (isUserPresent(userId) && isUserPresent(userIdToFollow)) {
            addNewFollowedUser(userId, userIdToFollow);
            return retrieveFollowedUsersByUserId(userId);
        }
        return Collections.emptySet();
    }

    private Function<UserEntity, User> mapUserEntityToUser() {
        return user -> User.builder()
                .userId(user.getUserId())
                .creationDate(user.getCreationDate())
                .followedUsers(getFollowedUsersByUserId(user.getUserId()))
                .build();
    }

    private Set<String> retrieveFollowedUsersByUserId(String userId) {
        return followedUsers.get(userId);
    }

    private Set<String> getFollowedUsersByUserId(String userId) {
        return retrieveFollowedUsersByUserId(userId);
    }

    private List<Post> retrievePostsByUserId(String userId) {
        return posts.get(userId).stream().map(p -> createPost(userId, p)).collect(Collectors.toList());
    }

    private boolean isUserPresent(String userId) {
        return Optional.ofNullable(users.get(userId)).isPresent();
    }

    private void addUserIfAbsent(String userId) {
        users.computeIfAbsent(userId, (user) -> createNewUserEntity(userId));
    }

    private void addNewPost(Post post, String userID) {
        posts.computeIfAbsent(userID, k -> new ArrayList<>()).add(createNewPostEntity(post));
    }

    private void addNewFollowedUser(String userId, String userIdToFollow) {
        followedUsers.computeIfAbsent(userId, k -> new HashSet<>()).add(userIdToFollow);
    }

    private Post createPost(String userName, PostEntity p) {
        return Post.builder().user(userName).message(p.getMessage()).postDate(p.getPostDate()).build();
    }

    private PostEntity createNewPostEntity(Post post) {
        return PostEntity.builder().postDate(LocalDateTime.now()).message(post.getMessage()).build();
    }

    private UserEntity createNewUserEntity(String userId) {
        return UserEntity.builder().userId(userId).creationDate(LocalDateTime.now()).build();
    }

}
