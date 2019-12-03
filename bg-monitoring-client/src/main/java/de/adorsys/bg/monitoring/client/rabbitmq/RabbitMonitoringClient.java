package de.adorsys.bg.monitoring.client.rabbitmq;

import de.adorsys.bg.monitoring.api.MonitoringMessage;
import de.adorsys.bg.monitoring.client.MonitoringClient;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.IOException;

public class RabbitMonitoringClient implements MonitoringClient {

    private final RabbitTemplate template;

    public RabbitMonitoringClient(RabbitTemplate template) {
        this.template = template;
    }

    @Override
    public void send(MonitoringMessage message) throws IOException {
        try {
            template.convertAndSend(message);
        } catch (AmqpException e) {
            throw new IOException(e);
        }
    }
}
