package com.twitter.users.infraestructure.controllers.follow.responses.adapter;

import com.twitter.users.domain.follow.Follow;
import com.twitter.users.infraestructure.controllers.follow.responses.FollowResponse;

import java.util.List;

public class FollowResponseAdapter {

    public static List<FollowResponse> buildFollowResponsesFromDomains(final List<Follow> follows) {
        return follows.stream()
                .map(follow -> new FollowResponse(follow.getFollower(), follow.getFollowee()))
                .toList();
    }
}
