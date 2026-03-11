package com.anomalocaris.monitor.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Table("hardware_metrics")
public record HardwareMetrics(@Id Long id, LocalDateTime timestamp, double cpuUsage, long ramUsed, long ramTotal,
        double gpuUsage, String osName) {
}