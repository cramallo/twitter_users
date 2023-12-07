package com.twitter.users.infraestructure.repositories.follow;

import com.twitter.users.domain.follow.Follow;
import com.twitter.users.infraestructure.entities.FollowEntity;
import com.twitter.users.infraestructure.repositories.follow.adapter.JpaFollowRepositoryAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


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
        // GIVEN
        final var followEntity = FollowEntity.builder()
                .follower(FOLLOWER_USER_ID)
                .followee(FOLLOWEE_USER_ID)
                .build();
        when(followRepository.save(followEntity)).thenReturn(followEntity);

        // WHEN
        final var savedFollowEntity = followRepositoryAdapter.save(FOLLOWER_USER_ID, FOLLOWEE_USER_ID);

        // THEN
        final var expectedFollow = Follow.builder()
                .followerId(FOLLOWER_USER_ID)
                .followeeId(FOLLOWEE_USER_ID)
                .build();
        assertEquals(expectedFollow, savedFollowEntity);
    }
}
