package com.twitter.users.domain.shared;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PageDomain <T> {
    List<T> data;
    long offset;
    int total;
    int limit;
}
