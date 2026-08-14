package com.pos.monitoring.addspringbootactuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DatabaseCustomHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    public DatabaseCustomHealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(2)) {
                return Health.up().withDetail("database", "Reachable and Valid").build();
            }
            return Health.down().withDetail("database", "Connection Invalid").build();
        } catch (Exception e) {
            return Health.down(e).withDetail("database", "Unreachable").build();
        }
    }
}