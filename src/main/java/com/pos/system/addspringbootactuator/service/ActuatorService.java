package com.pos.system.addspringbootactuator.service;

import com.pos.system.addspringbootactuator.dto.HealthStatusResponse;

public interface ActuatorService {
    HealthStatusResponse getCustomHealthStatus();
}