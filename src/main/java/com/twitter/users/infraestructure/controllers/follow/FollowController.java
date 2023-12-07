package com.twitter.users.infraestructure.controllers.follow;

import com.twitter.users.application.follow.FollowService;
import com.twitter.users.infraestructure.controllers.follow.requests.FollowRequest;
import com.twitter.users.infraestructure.controllers.follow.responses.FollowResponse;
import com.twitter.users.infraestructure.controllers.shared.PageResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.twitter.users.infraestructure.controllers.follow.responses.adapter.FollowResponseAdapter.buildFollowResponsesFromDomains;

@RestController
@RequestMapping("follows")
@AllArgsConstructor
public class FollowController {

    private static final String FOLLOW_PROCESSED = "Follow Processed Successfully";

    private FollowService followService;

    @PutMapping("/follow")
    public ResponseEntity<String> follow(@RequestHeader("user_name") final String userName,
                                         @RequestBody FollowRequest followRequest) {

        followService.follow(userName, followRequest.getFollowee());

        return ResponseEntity.ok(FOLLOW_PROCESSED);
    }

    @GetMapping("followees")
    public PageResponse<FollowResponse> getFollowees(@RequestHeader("user_name") final String userName,
                                                     @RequestParam long offset) {

        final var pageDomain = followService.getFolloweesByFollowerName(userName, offset);

        return PageResponse.<FollowResponse>builder()
                .data(buildFollowResponsesFromDomains(pageDomain.getData()))
                .limit(pageDomain.getLimit())
                .total(pageDomain.getTotal())
                .offset(pageDomain.getOffset())
                .build();
    }
}
