package com.anomalocaris.monitor.service;

import com.anomalocaris.monitor.model.HardwareMetrics;
import com.anomalocaris.monitor.repository.MetricRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

@Service
public class MetricService {
    // SystemInfo is provided by OSHI for hardware abstraction
    private final SystemInfo si = new SystemInfo();
    private final MetricRepository repo;

    public MetricService(MetricRepository repo) {
        this.repo = repo;
    }

    /**
     * Captures current CPU, RAM, and GPU metrics as a HardwareMetrics snapshot.
     */
    public HardwareMetrics getSnapshot() {
        var hw = si.getHardware();
        var mem = hw.getMemory();
        return new HardwareMetrics(
            null, 
            LocalDateTime.now(), 
            hw.getProcessor().getSystemCpuLoad(500) * 100, // CPU load over 500ms
            mem.getTotal() - mem.getAvailable(),           // RAM Used
            mem.getTotal(),                                // RAM Total
            fetchGpuUsage(),                               // GPU Utilization via nvidia-smi
            si.getOperatingSystem().toString()             // Host OS details
        );
    }

    /**
     * Executes nvidia-smi command to query GPU utilization percentage.
     */
    private double fetchGpuUsage() {
        try {
            Process p = new ProcessBuilder("nvidia-smi", "--query-gpu=utilization.gpu", "--format=csv,noheader,nounits").start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line = reader.readLine();
                return line != null ? Double.parseDouble(line.trim()) : 0.0;
            }
        } catch (Exception e) { 
            return 0.0; // Fallback to 0 if nvidia-smi is unavailable or fails
        }
    }

    /**
     * Scheduled cleanup task: Runs every 5 minutes to delete records older than 1 hour.
     * This keeps the table size constrained and dashboard queries performant.
     */
    @Scheduled(fixedRate = 300000) 
    public void pruneOldData() {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        repo.deleteByTimestampBefore(oneHourAgo)
            .subscribe(); // Executed reactively without blocking the main application thread
    }
}