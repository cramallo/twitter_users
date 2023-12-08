package com.twitter.users.infraestructure.notifications;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.users.application.NotificationGateway;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NotificationRabbitMQAdapter<T> implements NotificationGateway {

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
