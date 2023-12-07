package com.twitter.users.application.user;

import com.twitter.users.domain.user.User;
import com.twitter.users.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    public User findByName(final String name) {
        return userRepository.findByName(name);
    }
}
