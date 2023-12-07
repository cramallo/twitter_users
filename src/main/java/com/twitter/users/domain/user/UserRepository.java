package com.twitter.users.domain.user;

public interface UserRepository {
    User findByName(String name);
}
