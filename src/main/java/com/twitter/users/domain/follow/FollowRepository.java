package com.twitter.users.domain.follow;

import com.twitter.users.domain.shared.PageDomain;
import com.twitter.users.domain.user.User;

import java.util.List;

public interface FollowRepository {
    Follow save(User follower, User followee);

    PageDomain<Follow> findFolloweesByFollowerName(String followerName, long offset);
}
