package de.adorsys.bg.monitoring.client;

import de.adorsys.bg.monitoring.api.MonitoringMessage;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class RabbitMonitoringClientTest {

    private final RabbitMonitoringClient rabbitMonitoringClient = new RabbitMonitoringClient(null, null);

    @Test
    void nullPropertiesAreNotSerialized() throws Exception {
        MonitoringMessage emptyMessage = MonitoringMessage.builder().build();
        assertArrayEquals("{}".getBytes(), rabbitMonitoringClient.serialize(emptyMessage));
    }

    @Test
    void timestampSerializedInIso8601Format() throws Exception {
        MonitoringMessage message = MonitoringMessage.builder().setTimestamp(Instant.EPOCH).build();
        byte[] actual = rabbitMonitoringClient.serialize(message);
        assertArrayEquals("{\"timestamp\":\"1970-01-01T00:00:00Z\"}".getBytes(), actual);
    }
}
