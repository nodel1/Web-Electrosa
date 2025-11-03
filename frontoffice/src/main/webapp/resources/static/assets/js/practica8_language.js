window.addEventListener("load", init);

// Función de inicialización
function init() {
    console.log("Inicializando cambio de idioma...");
    let languageSelector = document.getElementById('language-selector');
    if (languageSelector) {
        console.log("Elemento language-selector encontrado");
        languageSelector.addEventListener('change', handleLanguageChange);
    } else {
        console.error("Elemento language-selector no encontrado");
    }
}

// Manejador del evento change
function handleLanguageChange(e) {
    console.log("Cambiando idioma a: " + e.target.value);
    let form = document.getElementById('language-form');
    if (form) {
        console.log("Formulario language-form encontrado, enviando...");
        form.submit();
    } else {
        console.error("Formulario language-form no encontrado");
    }
}