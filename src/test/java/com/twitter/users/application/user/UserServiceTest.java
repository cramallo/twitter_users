package com.twitter.users.application.user;

import com.twitter.users.domain.user.User;
import com.twitter.users.domain.user.UserRepository;
import com.twitter.users.infraestructure.repositories.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @Test
    @DisplayName("When user found then success")
    void testSuccessFindUser() {
        // GIVEN
        when(userRepository.findByName("name")).thenReturn(new User());

        // WHEN
        final var foundUser = userService.findByName("name");

        // THEN
        final var expectedUser = new User();
        assertEquals(expectedUser, foundUser);
    }

    @Test
    @DisplayName("When user not found in repository then throw EntityNotFoundException")
    void testFailFindUser() {
        // GIVEN
        when(userRepository.findByName("name")).thenThrow(EntityNotFoundException.class);

        // WHEN
        final Executable execution = () -> userService.findByName("name");

        // THEN
        assertThrows(EntityNotFoundException.class, execution);
    }
}
