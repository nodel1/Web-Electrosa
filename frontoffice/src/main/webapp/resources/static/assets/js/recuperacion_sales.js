document.addEventListener('DOMContentLoaded', () => {
    // Función para añadir el distintivo de oferta flash
    function addFlashOfferBadges() {
        const showcases = document.querySelectorAll('.showcase');
        showcases.forEach(showcase => {
            const articleName = showcase.querySelector('.showcase-title').textContent.toLowerCase();
            if (articleName.includes('fagor')) {
                const badge = document.createElement('span');
                badge.className = 'badge badge-green';
                badge.innerHTML = '<ion-icon name="gift-outline" role="img" aria-label="sale"></ion-icon> OFERTA FLASH';
                showcase.querySelector('.showcase-content').appendChild(badge);
            }
        });
    }

    setTimeout(addFlashOfferBadges, 1000);
});