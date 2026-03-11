package com.anomalocaris.monitor;

import com.anomalocaris.monitor.repository.MetricRepository;
import com.anomalocaris.monitor.service.MetricService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class MetricServiceTest {

    @Mock
    private MetricRepository repository;

    private MetricService service;

    @BeforeEach
    void setUp() {
        service = new MetricService(repository);
    }

    @Test
    void testSnapshotGeneration() {
        assertNotNull(service.getSnapshot());
    }
}