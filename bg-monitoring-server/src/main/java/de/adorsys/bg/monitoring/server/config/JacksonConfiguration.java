package de.adorsys.bg.monitoring.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.adorsys.bg.monitoring.api.MonitoringMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JacksonConfiguration {

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.addMixIn(MonitoringMessage.class, JacksonMonitoringMessageMixin.class);
        return objectMapper;
    }

    @JsonDeserialize(builder = MonitoringMessage.Builder.class)
    private static class JacksonMonitoringMessageMixin {
    }
}
