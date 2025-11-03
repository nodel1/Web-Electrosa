document.addEventListener('DOMContentLoaded', function () {
  const toggleLinks = document.querySelectorAll('.toggle-password');

  toggleLinks.forEach(link => {
    link.addEventListener('click', function (event) {
      event.preventDefault();

      const inputId = this.dataset.target;
      const input = document.getElementById(inputId);
      const icon = this.querySelector('ion-icon');

      // Alternar tipo
      if (input.type === 'password') {
        input.type = 'text';
        this.innerHTML = '<ion-icon name="eye-off"></ion-icon> Ocultar';
      } else {
        input.type = 'password';
        this.innerHTML = '<ion-icon name="eye"></ion-icon> Mostrar';
      }

      validateInput(input); // revalida al cambiar tipo
    });
  });

  // Validar al escribir
  const inputs = document.querySelectorAll('#password, #repetirPassword');
  inputs.forEach(input => {
    input.addEventListener('input', () => validateInput(input));
  });

  function validateInput(input) {
    if (input.value.length >= 6) {
      input.classList.add('input-valid');
      input.classList.remove('input-invalid');
    } else {
      input.classList.add('input-invalid');
      input.classList.remove('input-valid');
    }
  }
});