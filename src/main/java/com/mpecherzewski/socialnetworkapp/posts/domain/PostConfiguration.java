package com.mpecherzewski.socialnetworkapp.posts.domain;

import com.mpecherzewski.socialnetworkapp.infastructure.LocalDateTimeProvider;
import com.mpecherzewski.socialnetworkapp.users.domain.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PostConfiguration {

    @Bean
    PostsRepository postsRepository(LocalDateTimeProvider localDateTimeProvider) {
        return new InMemoryPostsRepository(new LocalDateTimeProvider());
    }

    @Bean
    PostFacade postFacade(PostsRepository postsRepository, UserFacade userFacade) {
        return new PostFacade(postsRepository, userFacade);
    }

}
