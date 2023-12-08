package com.twitter.users.infraestructure.controllers;

import com.twitter.users.application.follow.FollowService;
import com.twitter.users.infraestructure.controllers.follow.FollowController;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.twitter.users.utils.TestUtils.FOLLOWEE_USER_NAME;
import static com.twitter.users.utils.TestUtils.FOLLOWER_USER_NAME;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FollowController.class)
public class FollowControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FollowService followService;

    @Test
    @DisplayName("When send new follow and all params sent and execute successfully then success")
    void testSuccessNewFollow() throws Exception {
        // GIVEN
        final var requestJsonBody = new JSONObject().put("followee", FOLLOWEE_USER_NAME);

        // WHEN
        final var result = mvc.perform(
                MockMvcRequestBuilders
                        .put("/follows/follow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("user_name", FOLLOWER_USER_NAME)
                        .content(requestJsonBody.toString())
        );

        // THEN
        result.andExpect(status().isOk());
        result.andExpect(content().string("Follow Processed Successfully"));
    }

    @Test
    @DisplayName("When send new follow and header is not sent then throw bad request")
    void testFailNewFollow() throws Exception {
        // GIVEN
        final var requestJsonBody = new JSONObject().put("followee", FOLLOWEE_USER_NAME);

        // WHEN
        final var result = mvc.perform(
                MockMvcRequestBuilders
                        .put("/follows/follow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJsonBody.toString())
        );

        // THEN
        result.andExpect(status().isBadRequest());
    }
}
