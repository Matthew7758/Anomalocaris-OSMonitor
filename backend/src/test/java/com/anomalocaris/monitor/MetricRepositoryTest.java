package com.anomalocaris.monitor;

import com.anomalocaris.monitor.model.HardwareMetrics;
import com.anomalocaris.monitor.repository.MetricRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

@DataR2dbcTest
class MetricRepositoryTest {

    @Autowired
    private MetricRepository repository;

    @Test
    void testSaveMetric() {
        HardwareMetrics metrics = new HardwareMetrics(
                null,
                LocalDateTime.now(),
                15.5, 2048L, 4096L, 2.5, "Arch Linux");

        repository.save(metrics)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }
}