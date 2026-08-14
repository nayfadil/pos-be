package com.pos.system.addspringbootactuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomDatabaseHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        boolean databaseHealthy = checkDatabaseConnection();
        if (databaseHealthy) {
            return Health.up()
                    .withDetail("database", "POS Database Connection Available")
                    .withDetail("status", "UP")
                    .build();
        }
        return Health.down()
                .withDetail("database", "POS Database Connection Failed")
                .withDetail("status", "DOWN")
                .build();
    }

    private boolean checkDatabaseConnection() {
        return true;
    }
}