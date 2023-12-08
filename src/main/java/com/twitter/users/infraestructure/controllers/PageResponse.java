package com.twitter.users.infraestructure.controllers;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PageResponse<T> {
    List<T> data;
    long offset;
    int total;
    int limit;
}
