package com.twitter.users.domain.follow;

import com.twitter.users.application.PageDomain;
import com.twitter.users.domain.user.User;

public interface FollowRepository {
    Follow save(User follower, User followee);

    PageDomain<Follow> findFolloweesByFollowerName(String followerName, long offset);

    PageDomain<Follow> findFollowersByFolloweeName(String followerName, long offset);
}
