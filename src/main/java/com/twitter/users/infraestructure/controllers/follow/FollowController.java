package com.twitter.users.infraestructure.controllers.follow;

import com.twitter.users.application.follow.FollowService;
import com.twitter.users.infraestructure.controllers.follow.requests.FollowRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow")
@AllArgsConstructor
public class FollowController {

    private static final String FOLLOW_PROCESSED = "Follow Processed Successfully";

    private FollowService followService;

    @PutMapping
    public ResponseEntity<String> follow(@RequestHeader("user_name") final String userName,
                                         @RequestBody FollowRequest followRequest) {

        followService.follow(userName, followRequest.getFollowee());

        return ResponseEntity.ok(FOLLOW_PROCESSED);
    }
}
