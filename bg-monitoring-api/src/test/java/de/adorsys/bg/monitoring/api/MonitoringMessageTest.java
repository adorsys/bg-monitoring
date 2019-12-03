package de.adorsys.bg.monitoring.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonitoringMessageTest {

    @Test
    void fieldsAreImmutable() {
        MonitoringMessage message = MonitoringMessage.builder()
            .setField("field1", "...")
            .build();

        assertThrows(UnsupportedOperationException.class, () -> message.getFields().remove("field1"));
    }

    @Test
    @DisplayName("up to 5 fields are allowed")
    void fieldsMaxSizeValidation() {
        MonitoringMessage.Builder builder = MonitoringMessage.builder()
            .setField("field1", "")
            .setField("field2", "")
            .setField("field3", "")
            .setField("field4", "")
            .setField("field5", "")
            .setField("field6", "");
        assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void nullReturnedForUnknownField() {
        assertNull(MonitoringMessage.builder().getField("unknown field"));
    }
}
