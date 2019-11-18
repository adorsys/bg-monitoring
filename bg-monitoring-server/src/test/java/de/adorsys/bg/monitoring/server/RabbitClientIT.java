package de.adorsys.bg.monitoring.server;

import de.adorsys.bg.monitoring.api.MonitoringMessage;
import de.adorsys.bg.monitoring.client.rabbitmq.RabbitMonitoringClient;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.io.IOException;

import static org.mockito.Mockito.*;

// requires an instance of rabbitmq running
// $docker-compose run rabbitmq
@SpringBootTest
class RabbitClientIT {
    private static MonitoringMessageHandler mockMessageHandler = mock(MonitoringMessageHandler.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @TestConfiguration
    static class Configuration {
        @Bean
        @Primary
        MonitoringMessageHandler monitoringMessageHandler() {
            return mockMessageHandler;
        }
    }

    @Test
    void sentMessageIsReceived() throws IOException {
        RabbitMonitoringClient monitoringClient = new RabbitMonitoringClient(rabbitTemplate);
        MonitoringMessage message = MonitoringMessage.builder()
            .setStatus(200)
            .build();

        monitoringClient.send(message);

        verify(mockMessageHandler, timeout(2000)).handle(message);
    }
}
