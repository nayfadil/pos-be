package com.pos.monitoring.addspringbootactuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Component("customDatabaseHealthIndicator")
public class DatabaseHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    public DatabaseHealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            boolean valid = statement.execute("SELECT 1");
            if (valid) {
                return Health.up()
                        .withDetail("database", "Operational")
                        .withDetail("validationQuery", "SELECT 1")
                        .build();
            } else {
                return Health.down()
                        .withDetail("database", "Query execution failed")
                        .build();
            }
        } catch (Exception e) {
            return Health.down(e)
                    .withDetail("database", "Connection failed")
                    .build();
        }
    }
}