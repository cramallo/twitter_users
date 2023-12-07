package com.twitter.users.infraestructure.notifications.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.users.domain.shared.NotificationEventsService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NotificationEventsRabbitMQAdapter<T> implements NotificationEventsService {

    private AmqpTemplate amqpTemplate;
    private ObjectMapper objectMapper;

    @Override
    public void send(final Object payload) throws JsonProcessingException {
        amqpTemplate.convertAndSend(
                "follow-events-exchange",
                "follow-events-routing-key",
                objectMapper.writeValueAsString(payload)
        );
    }
}
