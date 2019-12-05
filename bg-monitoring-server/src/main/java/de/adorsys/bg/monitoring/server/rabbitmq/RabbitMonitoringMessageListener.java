package de.adorsys.bg.monitoring.server.rabbitmq;

import de.adorsys.bg.monitoring.api.MonitoringMessage;
import de.adorsys.bg.monitoring.server.MonitoringMessageHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMonitoringMessageListener {

    private final MonitoringMessageHandler messageHandler;

    public RabbitMonitoringMessageListener(MonitoringMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @RabbitListener(queues = "${bg.monitoring.server.rabbitmq.queue:bg.monitoring}")
    public void handle(MonitoringMessage message) {
        messageHandler.handle(message);
    }
}
