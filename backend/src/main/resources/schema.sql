CREATE TABLE IF NOT EXISTS hardware_metrics (
    id SERIAL PRIMARY KEY,
    timestamp TIMESTAMP,
    cpu_usage DOUBLE PRECISION,
    ram_used BIGINT,
    ram_total BIGINT,
    gpu_usage DOUBLE PRECISION,
    os_name TEXT
);