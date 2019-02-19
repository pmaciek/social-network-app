package com.mpecherzewski.socialnetworkapp.users.domain;

import com.mpecherzewski.socialnetworkapp.infastructure.LocalDateTimeProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserConfiguration {

    @Bean
    UsersRepository usersRepository(LocalDateTimeProvider localDateTimeProvider) {
        return new InMemoryUsersRepository(localDateTimeProvider);
    }

    @Bean
    UserFacade userFacade(UsersRepository usersRepository) {
        return new UserFacade(usersRepository);
    }
}
