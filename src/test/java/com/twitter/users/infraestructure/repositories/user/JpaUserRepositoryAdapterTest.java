package com.twitter.users.infraestructure.repositories.user;

import com.twitter.users.domain.user.User;
import com.twitter.users.infraestructure.entities.UserEntity;
import com.twitter.users.infraestructure.repositories.exceptions.EntityNotFoundException;
import com.twitter.users.infraestructure.repositories.user.adapter.JpaUserRepositoryAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.twitter.users.utils.TestUtils.FOLLOWEE_USER_ID;
import static com.twitter.users.utils.TestUtils.FOLLOWEE_USER_NAME;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JpaUserRepositoryAdapterTest {
    @Mock
    private JpaUserRepository userRepository;

    @InjectMocks
    private JpaUserRepositoryAdapter userRepositoryAdapter;

    @Test
    @DisplayName("when find user the success")
    void testSuccessFindByName() {
        // GIVEN
        final var userEntity = new UserEntity(FOLLOWEE_USER_ID, FOLLOWEE_USER_NAME);
        when(userRepository.findByName(FOLLOWEE_USER_NAME))
                .thenReturn(Optional.of(userEntity));

        // WHEN
        final var foundUser = userRepositoryAdapter.findByName(FOLLOWEE_USER_NAME);

        // THEN
        final var expectedUser = new User(FOLLOWEE_USER_ID, FOLLOWEE_USER_NAME);
        assertEquals(expectedUser, foundUser);
    }

    @Test
    @DisplayName("When found user is empty then throw EntityNotFoundException")
    void testFailFindByName() {
        // GIVEN
        when(userRepository.findByName(FOLLOWEE_USER_NAME))
                .thenReturn(empty());

        // WHEN
        final Executable execution = () -> userRepositoryAdapter.findByName(FOLLOWEE_USER_NAME);

        // THEN
        assertThrows(EntityNotFoundException.class, execution);
    }
}
