package com.pos.monitoring.addspringbootactuator.service.impl;

import com.pos.monitoring.addspringbootactuator.dto.HealthStatusResponse;
import com.pos.monitoring.addspringbootactuator.service.MonitoringService;
import org.springframework.stereotype.Service;

@Service
public class MonitoringServiceImpl implements MonitoringService {

    @Override
    public HealthStatusResponse getCustomHealthStatus() {
        return new HealthStatusResponse(
                "UP",
                "POS Backend Application Monitoring Active",
                System.currentTimeMillis()
        );
    }
}