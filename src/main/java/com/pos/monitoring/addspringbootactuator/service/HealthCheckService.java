package com.pos.monitoring.addspringbootactuator.service;

import com.pos.monitoring.addspringbootactuator.dto.HealthStatusResponse;

public interface HealthCheckService {
    HealthStatusResponse getApplicationHealthDetails();
}