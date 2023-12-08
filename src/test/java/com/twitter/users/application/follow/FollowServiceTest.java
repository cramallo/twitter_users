package com.twitter.users.application.follow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.twitter.users.application.NotificationGateway;
import com.twitter.users.application.user.UserService;
import com.twitter.users.domain.follow.Follow;
import com.twitter.users.domain.follow.FollowRepository;
import com.twitter.users.domain.user.User;
import com.twitter.users.infraestructure.repositories.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.twitter.users.utils.TestUtils.FOLLOWEE_USER_ID;
import static com.twitter.users.utils.TestUtils.FOLLOWEE_USER_NAME;
import static com.twitter.users.utils.TestUtils.FOLLOWER_USER_ID;
import static com.twitter.users.utils.TestUtils.FOLLOWER_USER_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FollowServiceTest {

    @Mock
    private FollowRepository followRepository;

    @Mock
    private UserService userService;

    @Mock
    private NotificationGateway<Follow> notificationGateway;

    @InjectMocks
    private FollowService followService;


    @Test
    @DisplayName("When user found and success save then success")
    void testSuccessCreationFollow() throws JsonProcessingException {
        // GIVEN
        when(userService.findByName(FOLLOWER_USER_NAME)).thenReturn(
                new User(FOLLOWER_USER_ID, FOLLOWER_USER_NAME)
        );

        when(userService.findByName(FOLLOWEE_USER_NAME)).thenReturn(
                new User(FOLLOWEE_USER_ID, FOLLOWEE_USER_NAME)
        );

        final var follow = buildFollow();

        when(followRepository.save(
                new User(FOLLOWER_USER_ID, FOLLOWER_USER_NAME),
                new User(FOLLOWEE_USER_ID, FOLLOWEE_USER_NAME)
        )).thenReturn(follow);

        // WHEN
        final var followCreated = followService.follow(FOLLOWER_USER_NAME, FOLLOWEE_USER_NAME);

        // THEN
        assertEquals(follow, followCreated);
        verify(notificationGateway).send(followCreated);
    }

    @Test
    @DisplayName("When user not found then throw EntityNotFoundException")
    void testFailCreationFollow() {
        // GIVEN
        when(userService.findByName(FOLLOWER_USER_NAME)).thenReturn(
                new User(FOLLOWER_USER_ID, FOLLOWER_USER_NAME)
        );

        when(userService.findByName(FOLLOWER_USER_NAME)).thenThrow(
                new EntityNotFoundException("user", "className", "methodName")
        );

        // WHEN
        final Executable execution = () -> followService.follow(FOLLOWER_USER_NAME, FOLLOWEE_USER_NAME);

        // THEN
        assertThrows(EntityNotFoundException.class, execution);
        verifyNoInteractions(followRepository);
        verifyNoInteractions(notificationGateway);
    }

    private Follow buildFollow() {
        return Follow.builder()
                .follower(FOLLOWER_USER_NAME)
                .followee(FOLLOWEE_USER_NAME)
                .build();
    }

}
