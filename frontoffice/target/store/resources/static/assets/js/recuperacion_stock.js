document.addEventListener('DOMContentLoaded', () => {
    // Obtener el código del artículo desde el input oculto
    const articleCodeInput = document.querySelector('input[name="codigoArticulo"]');
    if (!articleCodeInput) {
        console.error('No se encontró el input con name="codigoArticulo"');
        document.getElementById('soldToday').textContent = 'Error: Input no encontrado';
        return;
    }
    const articleCode = articleCodeInput.value.trim();
    if (!articleCode) {
        console.error('Código del artículo no encontrado o vacío');
        document.getElementById('soldToday').textContent = 'Error: Código no encontrado';
        return;
    }

    // Determinar la URL base y el endpoint según el contexto
    const baseUrl = window.location.origin;
    let apiUrl;

        apiUrl = `${baseUrl}/api/articulo/${encodeURIComponent(articleCode)}/vendido/hoy`;
    

    // Depuración: Verifica la URL en la consola
    console.log('URL de la APIJS:', apiUrl);

    // Función para actualizar las ventas diarias
    function updateSoldToday() {
        fetch(apiUrl, {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            }
        })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`Error en la petición: ${response.status} ${response.statusText}`);
                    }
                    return response.text();
                })
                .then(data => {
                    document.getElementById('soldToday').textContent = data;
                })
                .catch(error => {
                    console.error('Error:', error);
                    document.getElementById('soldToday').textContent = 'Error al cargar';
                });
    }

    // Llamar a la función al cargar la página
    updateSoldToday();
});