package com.twitter.users.application.follow;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.twitter.users.application.user.UserService;
import com.twitter.users.domain.follow.Follow;
import com.twitter.users.domain.follow.FollowRepository;
import com.twitter.users.application.NotificationGateway;
import com.twitter.users.application.PageDomain;
import com.twitter.users.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class FollowService {

    private FollowRepository followRepository;
    private UserService userService;
    private NotificationGateway<Follow> notificationGateway;

    public Follow follow(final String followerName, final String followeeName) {

        final var follower = userService.findByName(followerName);
        final var followee = userService.findByName(followeeName);

        final var createdFollow = createFollow(follower, followee);

        try {
            notificationGateway.send(createdFollow);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return createdFollow;
    }

    public PageDomain<Follow> getFolloweesByFollowerName(final String followerName,
                                                         final long offset) {

        return followRepository.findFolloweesByFollowerName(followerName, offset);
    }

    public PageDomain<Follow> getFollowersByFolloweeName(final String followerName,
                                                         final long offset) {

        return followRepository.findFollowersByFolloweeName(followerName, offset);
    }

    private Follow createFollow(final User follower, final User followee) {
        return followRepository.save(follower, followee);
    }

}
