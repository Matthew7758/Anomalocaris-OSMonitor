package com.anomalocaris.monitor.repository;

import com.anomalocaris.monitor.model.HardwareMetrics;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

/**
 * Reactive repository for hardware metrics.
 * Provides non-blocking persistence and specialized time-series query methods.
 */
@Repository
public interface MetricRepository extends ReactiveCrudRepository<HardwareMetrics, Long> {

    /**
     * Fetches the 50 most recent metrics for dashboard rendering.
     * Uses the 'timestamp' index to ensure O(log n) retrieval speed.
     */
    Flux<HardwareMetrics> findTop50ByOrderByTimestampDesc();

    /**
     * Removes records older than the specified time.
     * This keeps the database table size constant and performance optimal.
     */
    Mono<Void> deleteByTimestampBefore(LocalDateTime timestamp);
}