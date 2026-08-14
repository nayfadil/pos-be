package com.pos.system.addspringbootactuator.service;

import com.pos.system.addspringbootactuator.dto.ActuatorStatusResponse;

public interface AddSpringBootActuatorService {
    ActuatorStatusResponse getApplicationHealthStatus();
}
