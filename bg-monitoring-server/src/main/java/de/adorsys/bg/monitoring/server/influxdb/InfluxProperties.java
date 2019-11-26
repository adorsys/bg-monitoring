package de.adorsys.bg.monitoring.server.influxdb;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bg.monitoring.server.influxdb")
public class InfluxProperties {

    private String url = "http://localhost:8086";
    private String database = "bg_monitoring";
    private String username;
    private String password;

    public String getUrl() {
        return url;
    }

    public InfluxProperties setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getDatabase() {
        return database;
    }

    public InfluxProperties setDatabase(String database) {
        this.database = database;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public InfluxProperties setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public InfluxProperties setPassword(String password) {
        this.password = password;
        return this;
    }
}
