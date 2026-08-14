package com.pos.monitoring.addspringbootactuator.service.impl;

import com.pos.monitoring.addspringbootactuator.dto.HealthStatusResponse;
import com.pos.monitoring.addspringbootactuator.service.HealthCheckService;
import org.springframework.boot.actuate.health.HealthContributorRegistry;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class HealthCheckServiceImpl implements HealthCheckService {

    private final HealthContributorRegistry healthContributorRegistry;

    public HealthCheckServiceImpl(HealthContributorRegistry healthContributorRegistry) {
        this.healthContributorRegistry = healthContributorRegistry;
    }

    @Override
    public HealthStatusResponse getApplicationHealthDetails() {
        Map<String, Object> details = new HashMap<>();
        boolean isOverallUp = true;

        for (var entry : healthContributorRegistry) {
            String name = entry.getName();
            Object contributor = entry.getContributor();
            if (contributor instanceof HealthIndicator indicator) {
                var health = indicator.health();
                details.put(name, health.getDetails());
                if (!"UP".equalsIgnoreCase(health.getStatus().getCode())) {
                    isOverallUp = false;
                }
            }
        }

        return new HealthStatusResponse(
                isOverallUp ? "UP" : "DOWN",
                LocalDateTime.now(),
                details
        );
    }
}