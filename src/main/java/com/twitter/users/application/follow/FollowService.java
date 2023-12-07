package com.twitter.users.application.follow;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.twitter.users.application.user.UserService;
import com.twitter.users.domain.follow.Follow;
import com.twitter.users.domain.follow.FollowRepository;
import com.twitter.users.domain.shared.NotificationEventsService;
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
    private NotificationEventsService<Follow> notificationEventsService;

    public Follow follow(final String followerName, final String followeeName) {

        final var follower = userService.findByName(followerName);
        final var followee = userService.findByName(followeeName);

        final var createdFollow = createFollow(follower, followee);

        try {
            notificationEventsService.send(createdFollow);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return createdFollow;
    }

    private Follow createFollow(final User follower, final User followee) {
        return followRepository.save(follower.getId(), followee.getId());
    }
}
