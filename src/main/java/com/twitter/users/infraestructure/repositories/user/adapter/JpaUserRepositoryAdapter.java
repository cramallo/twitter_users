package com.twitter.users.infraestructure.repositories.user.adapter;

import com.twitter.users.domain.user.User;
import com.twitter.users.domain.user.UserRepository;
import com.twitter.users.infraestructure.repositories.exceptions.EntityNotFoundException;
import com.twitter.users.infraestructure.repositories.user.JpaUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JpaUserRepositoryAdapter implements UserRepository {

    private JpaUserRepository userRepository;

    @Override
    public User findByName(final String name) {
        final var foundUser = userRepository.findByName(name)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "user",
                                "JpaUserRepositoryAdapter",
                                "findByName"
                        )
                );
        return new User(foundUser.getId(), foundUser.getName());
    }
}
