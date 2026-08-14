package com.pos.monitoring.addspringbootactuator.service.impl;

import com.pos.monitoring.addspringbootactuator.dto.HealthStatusResponse;
import com.pos.monitoring.addspringbootactuator.service.MonitoringService;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.time.LocalDateTime;

@Service
public class MonitoringServiceImpl implements MonitoringService {

    private final DataSource dataSource;

    public MonitoringServiceImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public HealthStatusResponse getSystemHealthSummary() {
        String dbStatus = "UP";
        try (Connection connection = dataSource.getConnection()) {
            if (!connection.isValid(2)) {
                dbStatus = "DOWN";
            }
        } catch (Exception e) {
            dbStatus = "DOWN";
        }

        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        File root = new File(".");
        long freeDiskSpace = root.getFreeSpace();

        String overallStatus = "UP".equals(dbStatus) ? "UP" : "DOWN";

        return new HealthStatusResponse(
                overallStatus,
                dbStatus,
                freeMemory,
                totalMemory,
                freeDiskSpace,
                LocalDateTime.now()
        );
    }
}
