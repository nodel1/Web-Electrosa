// Procesar datos para el gráfico
const processDeliveryStats = () => {
    const monthlyAverages = {
        PB: new Array(12).fill(0), // Península/Baleares
        IB: new Array(12).fill(0), // Islas Canarias
        IN: new Array(12).fill(0)  // Internacional
    };

    // Agrupar y calcular medias por mes y región (si hay datos)
    pedidoStats.forEach(stat => {
        if (stat.month >= 1 && stat.month <= 12) {
            monthlyAverages[stat.shippingRegion][stat.month - 1] = stat.transitTimeAsDays || 0;
        }
    });

    return {
        categories: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
        series: [
            { name: 'Península/Baleares', data: monthlyAverages.PB },
            { name: 'Islas Canarias', data: monthlyAverages.IB },
            { name: 'Internacional', data: monthlyAverages.IN }
        ]
    };
};


document.addEventListener('DOMContentLoaded', () => {
    if (pedidoStats && pedidoStats.length > 0) {
        const chartData = processDeliveryStats();
        const chart = toastui.Chart.lineChart('#deliveryChart', {
            data: chartData,
            options: {
                chart: {
                    width: 800,
                    height: 400,
                    title: 'Tiempo medio de entrega por mes (días)'
                },
                yAxis: {
                    title: 'Días',
                    min: 0,
                    max: 15
                },
                xAxis: {
                    title: 'Mes'
                },
                series: {
                    showDot: true,
                    lineWidth: 2
                }
            }
        });
        console.log('Gráfico renderizado con éxito');
    } else {
        console.error('No hay datos en pedidoStats para renderizar el gráfico.');
    }
});