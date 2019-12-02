package de.adorsys.bg.monitoring.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.adorsys.bg.monitoring.api.MonitoringMessage;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.IOException;

public class RabbitMonitoringClient implements MonitoringClient {

    private final RabbitTemplate template;
    private final String routingKey;
    private final ObjectMapper objectMapper = new ObjectMapper();
    {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public RabbitMonitoringClient(RabbitTemplate template, String routingKey) {
        this.template = template;
        this.routingKey = routingKey;
    }

    @Override
    public void send(MonitoringMessage message) throws IOException {
        try {
            MessageBuilder messageBuilder = MessageBuilder.withBody(serialize(message));
            template.send(routingKey, messageBuilder.build());
        } catch (AmqpException e) {
            throw new IOException(e);
        }
    }

    byte[] serialize(MonitoringMessage message) throws IOException {
        return objectMapper.writeValueAsBytes(message);
    }
}
