package com.pos.monitoring.addspringbootactuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component("customDatabaseHealthIndicator")
public class DatabaseHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    public DatabaseHealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(2)) {
                return Health.up()
                        .withDetail("database", "Relational Database Service")
                        .withDetail("status", "Reachable")
                        .withDetail("validationTimeoutSeconds", 2)
                        .build();
            } else {
                return Health.down()
                        .withDetail("database", "Connection validation failed")
                        .build();
            }
        } catch (Exception e) {
            return Health.down(e)
                    .withDetail("database", "Unable to connect to database")
                    .build();
        }
    }
}
