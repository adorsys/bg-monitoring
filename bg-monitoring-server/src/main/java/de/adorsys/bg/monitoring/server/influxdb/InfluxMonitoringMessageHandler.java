package de.adorsys.bg.monitoring.server.influxdb;

import de.adorsys.bg.monitoring.api.MonitoringMessage;
import de.adorsys.bg.monitoring.server.MonitoringMessageHandler;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBException;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class InfluxMonitoringMessageHandler implements MonitoringMessageHandler {

    private final String url;
    private final String database;
    private final String username;
    private final String password;

    public InfluxMonitoringMessageHandler(InfluxProperties influxProperties) {
        this.url = influxProperties.getUrl();
        this.database = influxProperties.getDatabase();
        this.username = influxProperties.getUsername();
        this.password = influxProperties.getPassword();
    }

    @Override
    public void handle(MonitoringMessage message) {
        Point monitoringDataPoint = createPoint(message);
        save(monitoringDataPoint);
    }

    private Point createPoint(MonitoringMessage message) {
        Point.Builder pointBuilder = Point.measurement("monitoring_data");
        if (message.getTimestamp() != null) {
            pointBuilder.addField("timestamp", message.getTimestamp().toString());
        }
        if (message.getCorrelationId() != null) {
            pointBuilder.addField("correlation_id", message.getCorrelationId());
        }
        if (message.getBankCode() != null) {
            pointBuilder.tag("bank_code", message.getBankCode());
        }
        if (message.getIban() != null) {
            pointBuilder.addField("iban", message.getIban());
        }
        if (message.getSource() != null) {
            pointBuilder.addField("source", message.getSource());
        }
        if (message.getFunctionName() != null) {
            pointBuilder.addField("function_name", message.getFunctionName());
        }
        if (message.getFields() != null && !message.getFields().isEmpty()) {
            for (Map.Entry<String, String> field : message.getFields().entrySet()) {
                if (field.getKey() != null && field.getValue() != null) {
                    pointBuilder.addField(field.getKey(), field.getValue());
                }
            }
        }
        pointBuilder.addField("status", message.getStatus());
        if (message.getErrorMessage() != null) {
            pointBuilder.addField("error_message", message.getErrorMessage());
        }
        return pointBuilder.build();
    }

    /**
     * @throws InfluxDBException if save fails, allow to propagate for sake of
     * {@link SimpleMessageListenerContainer} auto ack behavior
     * @see org.springframework.amqp.core.AcknowledgeMode#AUTO
     */
    private void save(Point monitoringDataPoint) {
        try (InfluxDB connect = createConnection()) {
            connect.setDatabase(database);
            connect.write(monitoringDataPoint);
        }
    }

    private InfluxDB createConnection() {
        if (username != null && password != null) {
            return InfluxDBFactory.connect(url, username, password);
        }
        return InfluxDBFactory.connect(url);
    }
}
