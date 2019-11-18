package de.adorsys.bg.monitoring.server;

import de.adorsys.bg.monitoring.api.MonitoringMessage;

public interface MonitoringMessageHandler {
    void handle(MonitoringMessage message);
}
