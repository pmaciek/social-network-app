package com.mpecherzewski.socialnetworkapp.repository.impl.users;

import com.mpecherzewski.socialnetworkapp.domain.User;
import com.mpecherzewski.socialnetworkapp.repository.TracksRepository;
import com.mpecherzewski.socialnetworkapp.repository.UsersRepository;
import com.mpecherzewski.socialnetworkapp.repository.impl.LocalDateTimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
class InMemoryUsersRepository implements UsersRepository {

    private final Map<String, UserEntity> usersByUserId = new ConcurrentHashMap<>();

    private final LocalDateTimeProvider localDateTimeProvider;

    private final TracksRepository tracksRepository;

    @Override
    public Optional<User> getUser(String userId) {
        return Optional.ofNullable(usersByUserId.get(userId)).map(mapUserEntityToUser());
    }

    @Override
    public void addUser(String userId) {
        usersByUserId.computeIfAbsent(userId, (user) -> createNewUserEntity(userId));
    }

    private Function<UserEntity, User> mapUserEntityToUser() {
        return user -> User.builder()
                .userId(user.getUserId())
                .creationDate(user.getCreationDate())
                .trackedUsers(getTracksByUserId(user))
                .build();
    }

    private List<String> getTracksByUserId(UserEntity user) {
        return tracksRepository.getTracksByUserId(user.getUserId());
    }

    private UserEntity createNewUserEntity(String userId) {
        return UserEntity.builder().userId(userId).creationDate(getLocalDateNow()).build();
    }

    private LocalDateTime getLocalDateNow() {
        return localDateTimeProvider.getLocalDateNow();
    }
}
