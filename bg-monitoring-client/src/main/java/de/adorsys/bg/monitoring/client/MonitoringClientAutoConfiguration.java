package de.adorsys.bg.monitoring.client;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(MonitoringClientProperties.class)
@ConditionalOnProperty(name = "bg.monitoring.client.enabled", havingValue = "true")
public class MonitoringClientAutoConfiguration extends RabbitAutoConfiguration implements WebMvcConfigurer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    MonitoringClientProperties properties;

    @Value("${spring.application.name}")
    String serviceName;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String routingKey = properties.getRabbitmq().getRoutingKey();
        RabbitMonitoringClient monitoringClient = new RabbitMonitoringClient(rabbitTemplate, routingKey);
        registry.addInterceptor(new MonitoringHandlerInterceptor(monitoringClient, serviceName));
    }
}
