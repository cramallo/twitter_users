package com.twitter.users.domain.follow;

public interface FollowRepository {
    Follow save(Long followerId, Long followeeId);
}
