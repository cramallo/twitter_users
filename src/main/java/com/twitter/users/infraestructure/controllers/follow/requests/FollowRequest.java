package com.twitter.users.infraestructure.controllers.follow.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowRequest {
    @NonNull
    private String followee;
}
