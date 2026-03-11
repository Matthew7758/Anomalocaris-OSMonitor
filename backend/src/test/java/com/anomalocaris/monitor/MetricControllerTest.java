package com.anomalocaris.monitor;

import com.anomalocaris.monitor.controller.MetricController;
import com.anomalocaris.monitor.model.HardwareMetrics;
import com.anomalocaris.monitor.service.MetricService;

import reactor.core.publisher.Flux;

import com.anomalocaris.monitor.repository.MetricRepository;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(controllers = MetricController.class)
class MetricControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private MetricService metricService;

    @MockitoBean
    private MetricRepository metricRepository;

    @Test
    void testRecentEndpoint() {
        // STUBBING: This prevents the 500 error by ensuring repo.findAll() isn't null
        HardwareMetrics mockMetric = new HardwareMetrics(1L, LocalDateTime.now(), 10.0, 1024L, 2048L, 5.0, "Linux");

        Mockito.when(metricRepository.findAll()).thenReturn(Flux.just(mockMetric));

        webTestClient.get()
                .uri("/api/recent")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBody()
                .jsonPath("$[0].osName").isEqualTo("Linux");
    }
}