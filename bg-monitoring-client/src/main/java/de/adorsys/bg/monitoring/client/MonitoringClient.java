package de.adorsys.bg.monitoring.client;

import de.adorsys.bg.monitoring.api.MonitoringMessage;

import java.io.IOException;

public interface MonitoringClient {
    void send(MonitoringMessage message) throws IOException;
}
