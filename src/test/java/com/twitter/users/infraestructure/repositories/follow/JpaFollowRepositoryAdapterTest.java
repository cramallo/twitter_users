package com.twitter.users.infraestructure.repositories.follow;

import com.twitter.users.domain.follow.Follow;
import com.twitter.users.domain.user.User;
import com.twitter.users.infraestructure.entities.FollowEntity;
import com.twitter.users.infraestructure.entities.UserEntity;
import com.twitter.users.infraestructure.repositories.follow.adapter.JpaFollowRepositoryAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.twitter.users.utils.TestUtils.FOLLOWEE_USER_NAME;
import static com.twitter.users.utils.TestUtils.FOLLOWER_USER_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JpaFollowRepositoryAdapterTest {

    private static final Long FOLLOWER_USER_ID = 1L;
    private static final Long FOLLOWEE_USER_ID = 2L;

    @Mock
    private JpaFollowRepository followRepository;

    @InjectMocks
    private JpaFollowRepositoryAdapter followRepositoryAdapter;

    @Test
    void testSuccessSave() {

        final var followEntity = FollowEntity.builder()
                .follower(new UserEntity(FOLLOWER_USER_ID, FOLLOWER_USER_NAME))
                .followee(new UserEntity(FOLLOWEE_USER_ID, FOLLOWEE_USER_NAME))
                .build();

        when(followRepository.save(followEntity)).thenReturn(followEntity);

        final var follower = new User(FOLLOWER_USER_ID, FOLLOWER_USER_NAME);
        final var followee = new User(FOLLOWEE_USER_ID, FOLLOWEE_USER_NAME);

        // WHEN
        final var savedFollowEntity = followRepositoryAdapter.save(follower, followee);

        // THEN
        final var expectedFollow = Follow.builder()
                .follower(FOLLOWER_USER_NAME)
                .followee(FOLLOWEE_USER_NAME)
                .build();
        assertEquals(expectedFollow, savedFollowEntity);
    }
}
