package com.twitter.users.infraestructure.repositories.follow.adapter;

import com.twitter.users.domain.follow.Follow;
import com.twitter.users.infraestructure.entities.FollowEntity;

import java.util.List;

public class FollowAdapter {

    public static Follow buildFollowFromEntity(final FollowEntity followEntity) {
        return Follow.builder()
                .id(followEntity.getId())
                .follower(followEntity.getFollower().getName())
                .followee(followEntity.getFollowee().getName())
                .build();
    }

    public static List<Follow> buildFollowFromEntities(final List<FollowEntity> followEntities) {
        return followEntities.stream()
                .map(FollowAdapter::buildFollowFromEntity)
                .toList();
    }
}
