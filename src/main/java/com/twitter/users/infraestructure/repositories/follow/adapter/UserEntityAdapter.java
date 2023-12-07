package com.twitter.users.infraestructure.repositories.follow.adapter;

import com.twitter.users.domain.user.User;
import com.twitter.users.infraestructure.entities.UserEntity;

public class UserEntityAdapter {
    public static UserEntity buildFollowFromEntity(final User user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
