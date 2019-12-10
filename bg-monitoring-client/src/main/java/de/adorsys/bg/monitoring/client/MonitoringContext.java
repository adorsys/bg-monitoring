package de.adorsys.bg.monitoring.client;

public final class MonitoringContext {
    private static final ThreadLocal<String> correlationId = new ThreadLocal<>();
    private static final ThreadLocal<String> bankCode = new ThreadLocal<>();
    private static final ThreadLocal<String> iban = new ThreadLocal<>();
    private static final ThreadLocal<String> errorMessage = new ThreadLocal<>();

    public static String getCorrelationId() {
        return correlationId.get();
    }

    public static void setCorrelationId(String correlationId) {
        MonitoringContext.correlationId.set(correlationId);
    }

    public static String getBankCode() {
        return bankCode.get();
    }

    public static void setBankCode(String bankCode) {
        MonitoringContext.bankCode.set(bankCode);
    }

    public static String getIban() {
        return iban.get();
    }

    public static void setIban(String iban) {
        MonitoringContext.iban.set(iban);
    }

    public static String getErrorMessage() {
        return errorMessage.get();
    }

    public static void setErrorMessage(String errorMessage) {
        MonitoringContext.errorMessage.set(errorMessage);
    }

    public static void clear() {
        correlationId.remove();
        bankCode.remove();
        iban.remove();
        errorMessage.remove();
    }
}
