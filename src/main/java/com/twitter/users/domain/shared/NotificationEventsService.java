package com.twitter.users.domain.shared;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface NotificationEventsService<T> {
    void send(T payload) throws JsonProcessingException;
}
