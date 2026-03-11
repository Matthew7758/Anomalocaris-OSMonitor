package com.anomalocaris.monitor.controller;

import com.anomalocaris.monitor.model.HardwareMetrics;
import com.anomalocaris.monitor.service.MetricService;
import com.anomalocaris.monitor.repository.MetricRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import java.time.Duration;

/**
 * REST controller for streaming hardware metrics to clients.
 * Provides a Server-Sent Events (SSE) endpoint that emits new metrics every
 * second.
 * Utilizes reactive programming to ensure non-blocking I/O and efficient
 * resource usage.
 */
@RestController
@RequestMapping("/api")
public class MetricController {
    private final MetricService service;
    private final MetricRepository repo;

    public MetricController(MetricService s, MetricRepository r) {
        this.service = s;
        this.repo = r;
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE) // SSE endpoint for real-time metrics streaming
    public Flux<HardwareMetrics> stream() {
        return Flux.interval(Duration.ofSeconds(1)).map(i -> service.getSnapshot()).flatMap(repo::save);
    }

    @GetMapping("/recent") // New endpoint to fetch recent metrics for dashboard
    public Flux<HardwareMetrics> getRecentMetrics() {
        return repo.findAll().take(10);
    }
}