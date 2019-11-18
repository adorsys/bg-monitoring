package de.adorsys.bg.monitoring.server.rabbitmq;

import de.adorsys.bg.monitoring.api.MonitoringMessage;
import de.adorsys.bg.monitoring.server.MonitoringMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMonitoringMessageListener {

    private final MonitoringMessageHandler messageHandler;

    public RabbitMonitoringMessageListener(MonitoringMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Component
    static class LoggingMonitoringMessageHandler implements MonitoringMessageHandler {
        private static final Logger log = LoggerFactory.getLogger(LoggingMonitoringMessageHandler.class);

        @Override
        public void handle(MonitoringMessage message) {
            log.warn(message.toString());
        }
    }

    @RabbitListener(queues = "${bg.monitoring.server.rabbitmq.queue:bg.monitoring}")
    public void handle(MonitoringMessage message) {
        messageHandler.handle(message);
    }
}
