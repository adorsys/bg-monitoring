package de.adorsys.bg.monitoring.client;

import de.adorsys.bg.monitoring.api.MonitoringMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

public class MonitoringHandlerInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(MonitoringHandlerInterceptor.class);

    private final MonitoringClient monitoringClient;
    private final String serviceName;

    public MonitoringHandlerInterceptor(MonitoringClient monitoringClient, String serviceName) {
        this.monitoringClient = monitoringClient;
        this.serviceName = serviceName;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex)  {
        if (!(handler instanceof HandlerMethod)) {
            return;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if ("springfox.documentation.swagger.web".equals(handlerMethod.getBeanType().getPackage().getName())) {
            return;
        }
        MonitoringMessage.Builder messageBuilder = MonitoringMessage.builder()
            .setTimestamp(Instant.now())
            .setCorrelationId(getCorrelationId(request))
            .setBankCode(MonitoringContext.getBankCode())
            .setIban(MonitoringContext.getIban())
            .setSource(serviceName)
            .setFunctionName(handlerMethod.getMethod().getName())
            .setStatus(response.getStatus())
            .setErrorMessage(ex != null ? ex.getMessage() : null);
        try {
            monitoringClient.send(messageBuilder.build());
        } catch (IOException e) {
            log.warn("Failed to send data to monitoring system", e);
        } finally {
            MonitoringContext.clear();
        }
    }

    private String getCorrelationId(HttpServletRequest request) {
        String header = request.getHeader("Correlation-ID");
        if (header != null) {
            return header;
        }
        return MonitoringContext.getCorrelationId();
    }
}
