package com.twitter.users.domain.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Follow {
    private Long id;
    private String follower;
    private String followee;
}
