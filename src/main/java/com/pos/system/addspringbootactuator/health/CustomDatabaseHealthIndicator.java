package com.pos.system.addspringbootactuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Component
public class CustomDatabaseHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    public CustomDatabaseHealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            boolean valid = connection.isValid(2);
            if (valid) {
                return Health.up()
                        .withDetail("database", "MySQL/PostgreSQL")
                        .withDetail("status", "Active")
                        .build();
            } else {
                return Health.down()
                        .withDetail("database", "Connection Invalid")
                        .build();
            }
        } catch (Exception e) {
            return Health.down(e)
                    .withDetail("database", "Connection Exception: " + e.getMessage())
                    .build();
        }
    }
}
