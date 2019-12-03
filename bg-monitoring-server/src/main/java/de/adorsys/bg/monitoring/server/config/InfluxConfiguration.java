package de.adorsys.bg.monitoring.server.config;

import de.adorsys.bg.monitoring.server.influxdb.InfluxProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(InfluxProperties.class)
class InfluxConfiguration {
}
