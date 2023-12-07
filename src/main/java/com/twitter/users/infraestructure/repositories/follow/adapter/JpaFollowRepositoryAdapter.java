package com.twitter.users.infraestructure.repositories.follow.adapter;

import com.twitter.users.domain.follow.Follow;
import com.twitter.users.domain.follow.FollowRepository;
import com.twitter.users.domain.shared.PageDomain;
import com.twitter.users.domain.user.User;
import com.twitter.users.infraestructure.entities.FollowEntity;
import com.twitter.users.infraestructure.repositories.follow.JpaFollowRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static com.twitter.users.infraestructure.repositories.follow.adapter.FollowAdapter.buildFollowFromEntities;
import static com.twitter.users.infraestructure.repositories.follow.adapter.UserEntityAdapter.buildFollowFromEntity;


@Service
@AllArgsConstructor
public class JpaFollowRepositoryAdapter implements FollowRepository {

    private static final int DEFAULT_OFFSET = 100;

    private JpaFollowRepository followRepository;

    @Override
    public Follow save(final User follower, final User followee) {

        final var follow = FollowEntity.builder()
                .follower(buildFollowFromEntity(follower))
                .followee(buildFollowFromEntity(followee))
                .build();

        final var savedFollow = followRepository.save(follow);

        return FollowAdapter.buildFollowFromEntity(savedFollow);
    }

    @Override
    public PageDomain<Follow> findFolloweesByFollowerName(final String followerName,
                                                          final long offset) {

        final var pageFoundFollowees = followRepository.findByFollowerName(
                followerName,
                PageRequest.of((int) offset, DEFAULT_OFFSET)
        );

        return PageDomain.<Follow>builder()
                .total(pageFoundFollowees.getTotalPages())
                .limit(pageFoundFollowees.getSize())
                .data(buildFollowFromEntities(pageFoundFollowees.getContent()))
                .offset(pageFoundFollowees.getPageable().getOffset())
                .build();
    }

}
