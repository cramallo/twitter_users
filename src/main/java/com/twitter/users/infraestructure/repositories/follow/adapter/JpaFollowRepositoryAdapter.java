package com.twitter.users.infraestructure.repositories.follow.adapter;

import com.twitter.users.domain.follow.Follow;
import com.twitter.users.domain.follow.FollowRepository;
import com.twitter.users.infraestructure.entities.FollowEntity;
import com.twitter.users.infraestructure.repositories.follow.JpaFollowRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class JpaFollowRepositoryAdapter implements FollowRepository {

    private JpaFollowRepository followRepository;

    @Override
    public Follow save(final Long followerId, final Long followeeId) {
        final var follow = FollowEntity.builder()
                .follower(followerId)
                .followee(followeeId)
                .build();

        final var savedFollow = followRepository.save(follow);

        return FollowMapper.buildFollowFromEntity(savedFollow);
    }

}
