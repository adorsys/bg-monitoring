package de.adorsys.bg.monitoring.api;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents a message sent to the monitoring system from a component
 * of the Banking Gateway as described in https://wiki.adorsys.de/display/BG/Monitoring
 *
 * <p> Sending party should identify itself using the {@code source} property.
 */
public final class MonitoringMessage {
    private final Instant timestamp;
    private final String correlationId;
    private final String bankCode;
    private final String iban;
    private final String source; // e.g. MBS, XS2A
    private final String functionName; // e.g. get account transactions
    // up to 5 text fields which could be filled differently for each function name
    private final Map<String, String> fields;
    private final Integer status;
    private final String errorMessage;

    public static Builder builder() {
        return new Builder();
    }

    public MonitoringMessage(Builder builder) {
        this.timestamp = builder.getTimestamp();
        this.correlationId = builder.getCorrelationId();
        this.bankCode = builder.getBankCode();
        this.iban = builder.getIban();
        this.source = builder.getSource();
        this.functionName = builder.getFunctionName();
        Map<String, String> fields = builder.getFields();
        if (fields == null) {
            this.fields = null;
        } else {
            if (fields.size() > 5) {
                throw new IllegalArgumentException("Up to 5 fields are allowed");
            }
            this.fields = Collections.unmodifiableMap(fields);
        }
        this.status = builder.getStatus();
        this.errorMessage = builder.getErrorMessage();
    }

    public static final class Builder {
        private Instant timestamp;
        private String correlationId;
        private String bankCode;
        private String iban;
        private String source;
        private String functionName;
        private Map<String, String> fields;
        private Integer status;
        private String errorMessage;

        private Builder() {
        }

        public MonitoringMessage build() {
            return new MonitoringMessage(this);
        }

        public Instant getTimestamp() {
            return timestamp;
        }

        public Builder setTimestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public String getCorrelationId() {
            return correlationId;
        }

        public Builder setCorrelationId(String correlationId) {
            this.correlationId = correlationId;
            return this;
        }

        public String getBankCode() {
            return bankCode;
        }

        public Builder setBankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        public String getIban() {
            return iban;
        }

        public Builder setIban(String iban) {
            this.iban = iban;
            return this;
        }

        public String getSource() {
            return source;
        }

        public Builder setSource(String source) {
            this.source = source;
            return this;
        }

        public String getFunctionName() {
            return functionName;
        }

        public Builder setFunctionName(String functionName) {
            this.functionName = functionName;
            return this;
        }

        public Map<String, String> getFields() {
            return fields;
        }

        public Builder setFields(Map<String, String> fields) {
            this.fields = fields;
            return this;
        }

        public String getField(String name) {
            if (fields == null) {
                return null;
            }
            return fields.get(name);
        }

        public Builder setField(String name, String value) {
            if (fields == null) {
                fields = new LinkedHashMap<>();
            }
            fields.put(name, value);
            return this;
        }

        public Integer getStatus() {
            return status;
        }

        public Builder setStatus(Integer status) {
            this.status = status;
            return this;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public Builder setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public String getIban() {
        return iban;
    }

    public String getSource() {
        return source;
    }

    public String getFunctionName() {
        return functionName;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public Integer getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonitoringMessage that = (MonitoringMessage) o;
        return Objects.equals(timestamp, that.timestamp) &&
            Objects.equals(correlationId, that.correlationId) &&
            Objects.equals(bankCode, that.bankCode) &&
            Objects.equals(iban, that.iban) &&
            Objects.equals(source, that.source) &&
            Objects.equals(functionName, that.functionName) &&
            Objects.equals(fields, that.fields) &&
            Objects.equals(status, that.status) &&
            Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, correlationId, bankCode, iban, source,
            functionName, fields, status, errorMessage);
    }

    @Override
    public String toString() {
        return "MonitoringMessage{" +
            "timestamp=" + timestamp +
            ", correlationId='" + correlationId + '\'' +
            ", bankCode='" + bankCode + '\'' +
            ", iban='" + iban + '\'' +
            ", source='" + source + '\'' +
            ", functionName='" + functionName + '\'' +
            ", fields=" + fields +
            ", status=" + status +
            ", errorMessage='" + errorMessage + '\'' +
            '}';
    }
}
