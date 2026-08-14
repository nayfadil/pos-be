package com.pos.monitoring.addspringbootactuator.dto;

import java.time.LocalDateTime;

public record HealthStatusResponse(
    String status,
    String databaseStatus,
    long freeMemory,
    long totalMemory,
    long freeDiskSpace,
    LocalDateTime timestamp
) {}
