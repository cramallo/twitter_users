package com.twitter.users.application;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface NotificationGateway<T> {
    void send(T payload) throws JsonProcessingException;
}
