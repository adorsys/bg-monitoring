package de.adorsys.bg.monitoring.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bg.monitoring.client")
public class MonitoringClientProperties {
    private boolean enabled;
    private Rabbit rabbitmq = new Rabbit();

    public static class Rabbit {
        private String routingKey = "bg.monitoring";

        public String getRoutingKey() {
            return routingKey;
        }

        public void setRoutingKey(String routingKey) {
            this.routingKey = routingKey;
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Rabbit getRabbitmq() {
        return rabbitmq;
    }

    public void setRabbitmq(Rabbit rabbitmq) {
        this.rabbitmq = rabbitmq;
    }
}
