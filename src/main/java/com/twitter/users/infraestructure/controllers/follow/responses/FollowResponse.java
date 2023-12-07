package com.twitter.users.infraestructure.controllers.follow.responses;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class FollowResponse {
    String follower;
    String followee;
}
