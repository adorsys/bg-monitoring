package de.adorsys.bg.monitoring.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import static java.util.Collections.singletonMap;

public class MonitoringClientEnvironmentPostProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        environment.getPropertySources()
            .addLast(new MapPropertySource("bg-monitoring",
                singletonMap("spring.autoconfigure.exclude",
                    "org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration")));
    }
}
