document.addEventListener("DOMContentLoaded", function () {
    // Função para validação do telefone
    function validatePhone(inputId, errorId) {
        const phoneInput = document.getElementById(inputId);
        const errorElement = document.getElementById(errorId);
        const phoneRegex = /^[0-9]{9}$/; // Aceita somente 9 dígitos numéricos

        phoneInput.addEventListener('input', function () {
            if (!phoneRegex.test(phoneInput.value)) {
                errorElement.style.display = 'block';
                errorElement.textContent = 'Número de telefone inválido. Deve conter exatamente 9 dígitos.';
            } else {
                errorElement.style.display = 'none';
            }
        });
    }

    // Função para validação de correspondência de senha
    function validatePasswords(formId, passwordId, confirmPasswordId, errorId) {
        const form = document.getElementById(formId);
        const passwordInput = document.getElementById(passwordId);
        const confirmPasswordInput = document.getElementById(confirmPasswordId);
        const passwordMatchError = document.getElementById(errorId);

        function checkPasswordMatch() {
            if (passwordInput.value !== confirmPasswordInput.value) {
                passwordMatchError.style.display = 'block';
                passwordMatchError.textContent = 'As senhas não coincidem. Por favor, verifique novamente.';
            } else {
                passwordMatchError.style.display = 'none';
            }
        }

        form.addEventListener('submit', function (e) {
            if (passwordInput.value !== confirmPasswordInput.value) {
                e.preventDefault();
                checkPasswordMatch();
            }
        });

        passwordInput.addEventListener('input', checkPasswordMatch);
        confirmPasswordInput.addEventListener('input', checkPasswordMatch);
    }

    // Ativar validação nos inputs
    validatePhone('driverPhone', 'driverPhoneError');
    validatePasswords('driverForm', 'driverPassword', 'confirmDriverPassword', 'driverPasswordMatchError');
});
