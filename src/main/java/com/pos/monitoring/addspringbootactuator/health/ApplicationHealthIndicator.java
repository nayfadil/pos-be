package com.pos.monitoring.addspringbootactuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.io.File;

@Component("customApplicationHealthIndicator")
public class ApplicationHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        
        File root = new File(".");
        long freeDiskSpace = root.getFreeSpace();
        long totalDiskSpace = root.getTotalSpace();

        boolean isMemoryHealthy = freeMemory > 10 * 1024 * 1024;
        boolean isDiskHealthy = freeDiskSpace > 100 * 1024 * 1024;

        if (isMemoryHealthy && isDiskHealthy) {
            return Health.up()
                    .withDetail("freeMemoryBytes", freeMemory)
                    .withDetail("totalMemoryBytes", totalMemory)
                    .withDetail("maxMemoryBytes", maxMemory)
                    .withDetail("freeDiskSpaceBytes", freeDiskSpace)
                    .withDetail("totalDiskSpaceBytes", totalDiskSpace)
                    .build();
        } else {
            return Health.down()
                    .withDetail("reason", "System resources are running critically low")
                    .withDetail("freeMemoryBytes", freeMemory)
                    .withDetail("freeDiskSpaceBytes", freeDiskSpace)
                    .build();
        }
    }
}
