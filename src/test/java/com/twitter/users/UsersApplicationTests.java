package com.twitter.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.users.application.NotificationGateway;
import com.twitter.users.domain.follow.Follow;
import com.twitter.users.domain.follow.FollowRepository;
import com.twitter.users.domain.user.UserRepository;
import com.twitter.users.infraestructure.controllers.follow.requests.FollowRequest;
import com.twitter.users.infraestructure.repositories.follow.JpaFollowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UsersApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private JpaFollowRepository jpaFollowRepository;

    @Autowired
    private NotificationGateway<Follow> notificationGateway;

    @MockBean
    private RabbitTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void reset() {
        jpaFollowRepository.deleteAll();
    }

    @Test
    @DisplayName("When execute new follow then success")
    void testSuccessNewFollow() throws Exception {
        // GIVEN
        final var followRequest = new FollowRequest("followee_name");

        // WHEN
        ResultActions response = mockMvc.perform(put("/follows/follow")
                .contentType(MediaType.APPLICATION_JSON)
                .header("user_name", "follower_name")
                .content(objectMapper.writeValueAsString(followRequest)));

        // THEN
        response.andExpect(status().isOk());
        response.andExpect(content().string("Follow Processed Successfully"));
    }

    @Test
    @DisplayName("When execute new follow then success")
    void testSuccessGetFollowersByFollowee() throws Exception {
        // GIVEN
        final var followRequest = new FollowRequest("followee_name");

        mockMvc.perform(put("/follows/follow")
                .contentType(MediaType.APPLICATION_JSON)
                .header("user_name", "follower_name")
                .content(objectMapper.writeValueAsString(followRequest)));

        // WHEN
        ResultActions response = mockMvc.perform(get("/follows/followers/followee_name")
                .param("offset", "0"));

        // THEN
        response.andExpect(status().isOk());
        response.andExpect(
                        jsonPath(
                                "$.data[0].follower",
                                is("follower_name")
                        )
                )
                .andExpect(
                        jsonPath(
                                "$.data[0].followee",
                                is("followee_name")
                        )
                )
                .andExpect(jsonPath("$.offset", is(0)))
                .andExpect(jsonPath("$.total", is(1)))
                .andExpect(jsonPath("$.limit", is(100)));
    }

    @Test
    @DisplayName("When execute new follow and not found user then fail with 404")
    void testFailNewFollow() throws Exception {
        // GIVEN
        final var followRequest = new FollowRequest("invalid_follower_name");

        // WHEN
        ResultActions response = mockMvc.perform(put("/follows/follow")
                .contentType(MediaType.APPLICATION_JSON)
                .header("user_name", "follower_name")
                .content(objectMapper.writeValueAsString(followRequest)));

        // THEN
        response.andExpect(status().isNotFound());
        response.andExpect(jsonPath(
                "$.message",
                is("Entity user not found in class JpaUserRepositoryAdapter and method findByName")
        ));
    }

}
