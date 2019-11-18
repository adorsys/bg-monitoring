package de.adorsys.bg.monitoring.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.bg.monitoring.api.MonitoringMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MonitoringApplicationTests {

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void monitoringMessageDeserialization() throws Exception {

        String json = "{\n" +
            "    \"timestamp\": \"2019-11-20T14:18:53.312015Z\",\n" +
            "    \"correlationId\": \"correlation_id\",\n" +
            "    \"bankCode\": \"83094444\",\n" +
            "    \"iban\": \"DE91 1000 0000 0123 4567 89\",\n" +
            "    \"source\": \"XS2A\",\n" +
            "    \"functionName\": \"get account transactions\",\n" +
            "    \"fields\": {\n" +
            "        \"field_1\": \"value_1\",\n" +
            "        \"field_2\": \"value_2\",\n" +
            "        \"field_3\": \"value_3\"\n" +
            "    },\n" +
            "    \"status\": 403,\n" +
            "    \"errorMessage\": \"CONSENT_UNKNOWN\"\n" +
            "}";

        MonitoringMessage expectedMessage = MonitoringMessage.builder()
            .setTimestamp(Instant.parse("2019-11-20T14:18:53.312015Z"))
            .setCorrelationId("correlation_id")
            .setBankCode("83094444")
            .setIban("DE91 1000 0000 0123 4567 89")
            .setSource("XS2A")
            .setFunctionName("get account transactions")
            .setField("field_1", "value_1")
            .setField("field_2", "value_2")
            .setField("field_3", "value_3")
            .setStatus(403)
            .setErrorMessage("CONSENT_UNKNOWN")
            .build();

        MonitoringMessage actualMessage = objectMapper.readValue(json, MonitoringMessage.class);
        assertEquals(expectedMessage, actualMessage);
    }

}
