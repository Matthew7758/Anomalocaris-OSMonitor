const ctx = document.getElementById('cpuChart').getContext('2d');
const style = getComputedStyle(document.documentElement);

const chart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: [],
        datasets: [
            { label: 'CPU %', data: [], borderColor: style.getPropertyValue('--neon-green').trim(), fill: true, tension: 0.4 },
            { label: 'RAM %', data: [], borderColor: style.getPropertyValue('--neon-blue').trim(), fill: true, tension: 0.4 },
            { label: 'GPU %', data: [], borderColor: style.getPropertyValue('--neon-purple').trim(), fill: true, tension: 0.4 }
        ]
    },
    options: { scales: { y: { min: 0, max: 100 } } }
});

const sse = new EventSource('/api/stream');
sse.onmessage = (e) => {
    const d = JSON.parse(e.data);
    document.getElementById('os-banner').innerText = `SYSTEM: ${d.osName}`;

    if (chart.data.labels.length > 20) {
        chart.data.labels.shift();
        chart.data.datasets.forEach(ds => ds.data.shift());
    }

    chart.data.labels.push(new Date().toLocaleTimeString());
    chart.data.datasets[0].data.push(d.cpuUsage.toFixed(1));
    chart.data.datasets[1].data.push(((d.ramUsed / d.ramTotal) * 100).toFixed(1));
    chart.data.datasets[2].data.push(d.gpuUsage.toFixed(1));
    chart.update();
};