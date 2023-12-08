package com.twitter.users.application;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
// CAMBIAR A DTO MOVER A APPLICATION
public class PageDomain <T> {
    List<T> data;
    long offset;
    int total;
    int limit;
}
