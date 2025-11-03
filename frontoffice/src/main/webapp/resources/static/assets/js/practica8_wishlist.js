document.addEventListener('DOMContentLoaded', () => {
    // Seleccionar todos los botones de añadir a favoritos
    const wishlistButtons = document.querySelectorAll('.wishlist-add');

    wishlistButtons.forEach(button => {
        button.addEventListener('click', () => {
            // Obtener el código del artículo desde el atributo id
            const articleCode = button.id;

            // Obtener la lista de favoritos desde localStorage o inicializar un Set vacío
            let wishlist = new Set(JSON.parse(localStorage.getItem('wishlist')) || []);

            // Añadir el nuevo código si no existe
            if (!wishlist.has(articleCode)) {
                wishlist.add(articleCode);
                localStorage.setItem('wishlist', JSON.stringify([...wishlist]));
                console.log(`Artículo ${articleCode} añadido a favoritos`);
                // Opcional: Actualizar el contador en la interfaz
                updateWishlistCount();
            } else {
                console.log(`El artículo ${articleCode} ya está en favoritos`);
            }
        });
    });

    // Función para actualizar el contador de favoritos en la interfaz
    function updateWishlistCount() {
        const wishlist = JSON.parse(localStorage.getItem('wishlist')) || [];
        const countElement = document.getElementById('wishlist-count');
        if (countElement) {
            countElement.textContent = wishlist.length;
        }
    }

    // Inicializar el contador al cargar la página
    updateWishlistCount();
});