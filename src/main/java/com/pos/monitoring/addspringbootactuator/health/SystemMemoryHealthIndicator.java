package com.pos.monitoring.addspringbootactuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("systemMemoryHealthIndicator")
public class SystemMemoryHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();

        double freePercentage = ((double) freeMemory / totalMemory) * 100;

        if (freePercentage < 10.0) {
            return Health.down()
                    .withDetail("freeMemoryBytes", freeMemory)
                    .withDetail("totalMemoryBytes", totalMemory)
                    .withDetail("maxMemoryBytes", maxMemory)
                    .withDetail("freeMemoryPercentage", String.format("%.2f%%", freePercentage))
                    .withDetail("status", "Low Memory Warning")
                    .build();
        }

        return Health.up()
                .withDetail("freeMemoryBytes", freeMemory)
                .withDetail("totalMemoryBytes", totalMemory)
                .withDetail("maxMemoryBytes", maxMemory)
                .withDetail("freeMemoryPercentage", String.format("%.2f%%", freePercentage))
                .build();
    }
}