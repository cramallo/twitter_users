package com.twitter.users.infraestructure.repositories.follow.adapter;

import com.twitter.users.domain.follow.Follow;
import com.twitter.users.infraestructure.entities.FollowEntity;

public class FollowMapper {

    public static Follow buildFollowFromEntity(final FollowEntity followEntity) {
        return Follow.builder()
                .id(followEntity.getId())
                .followerId(followEntity.getFollower())
                .followeeId(followEntity.getFollowee())
                .build();
    }
}
