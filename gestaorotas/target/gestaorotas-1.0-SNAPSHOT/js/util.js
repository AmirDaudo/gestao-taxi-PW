/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


  // Inicializa o mapa centrado em Pemba, MoÃ§ambique
        var map = L.map('map').setView([-12.973041, 40.517801], 13);

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: 'Â© OpenStreetMap'
        }).addTo(map);

        var currentMarker;

        // ObtenÃ§Ã£o da localizaÃ§Ã£o automaticamente ao carregar a pÃ¡gina
        window.onload = function() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function(position) {
                    var lat = position.coords.latitude;
                    var lng = position.coords.longitude;

                    // Atualiza o campo de local de partida
                    document.getElementById('pickup').value = `Latitude: ${lat}, Longitude: ${lng}`;

                    // Adiciona um marcador no mapa
                    if (currentMarker) {
                        map.removeLayer(currentMarker);
                    }
                    currentMarker = L.marker([lat, lng]).addTo(map)
                        .bindPopup("Sua localizaÃ§Ã£o")
                        .openPopup();

                    // Centraliza o mapa
                    map.setView([lat, lng], 13);
                }, function(error) {
                    alert("Erro ao obter localizaÃ§Ã£o.");
                });
            } else {
                alert('Seu navegador nÃ£o suporta Geolocation.');
            }
        }

        // Evento de mudanÃ§a no mÃ©todo de pagamento
        document.getElementById('payment-method').addEventListener('change', function() {
            var selectedMethod = this.value;
            var phoneNumberGroup = document.getElementById('phone-number-group');

            // Se Emola ou Mpesa for selecionado, exibir campo de nÃºmero de telefone
            if (selectedMethod === 'emola' || selectedMethod === 'mpesa') {
                phoneNumberGroup.style.display = 'block';
            } else {
                phoneNumberGroup.style.display = 'none';
            }
        });

        // Evento de confirmaÃ§Ã£o de pagamento
        document.getElementById('confirm-payment').addEventListener('click', function() {
            var selectedMethod = document.getElementById('payment-method').value;
            var phoneNumber = document.getElementById('phone-number').value;

            if (selectedMethod === 'emola' || selectedMethod === 'mpesa') {
                if (phoneNumber === '') {
                    alert('Por favor, insira o nÃºmero de telefone.');
                    return;
                }
                alert('Pagamento efetuado via ' + selectedMethod.toUpperCase() + ' com o nÃºmero ' + phoneNumber + '.');
            } else if (selectedMethod === 'dinheiro') {
                alert('Pagamento efetuado em dinheiro.');
            } else {
                alert('Por favor, selecione um mÃ©todo de pagamento.');
            }

            // Fechar o modal apÃ³s o pagamento
            var paymentModal = new bootstrap.Modal(document.getElementById('paymentModal'));
            paymentModal.hide();
        });

        // Evento de envio de pedido de tÃ¡xi e gravaÃ§Ã£o do histÃ³rico
        document.getElementById('taxi-form').addEventListener('submit', function(e) {
            e.preventDefault();
            var destination = document.getElementById('destination').value;
            if (destination === '') {
                alert('Por favor, insira o destino.');
                return;
            }
            var listItem = document.createElement('li');
            listItem.classList.add('list-group-item');
            listItem.textContent = `Corrida solicitada para o destino: ${destination}`;
            document.getElementById('corridas-list').appendChild(listItem);
            alert('TÃ¡xi solicitado com sucesso.');
        });

        // Evento de cancelamento de corrida
        document.getElementById('submit-cancel').addEventListener('click', function() {
            alert('Corrida cancelada com sucesso.');
        });

        // Evento de envio de denÃºncia
        document.getElementById('enviarDenuncia').addEventListener('click', function() {
            var denunciaText = document.getElementById('denuncia').value;
            if (denunciaText === '') {
                alert('Por favor, descreva o problema.');
            } else {
                alert('DenÃºncia enviada com sucesso.');
            }
        });

        // Evento de envio de mensagem para a central
        document.getElementById('enviarCentral').addEventListener('click', function() {
            var mensagemText = document.getElementById('mensagem').value;
            if (mensagemText === '') {
                alert('Por favor, escreva sua mensagem.');
            } else {
                alert('Mensagem enviada para a Central.');
            }
        });
        
           // Script para manipulaÃ§Ã£o do mapa
        var map = L.map('map').setView([-12.973041, 40.517801], 13);

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: 'Â© OpenStreetMap'
        }).addTo(map);

        // Script para atualizar a foto de perfil
        document.getElementById('profilePhotoForm').addEventListener('submit', function(e) {
            e.preventDefault();
            var photoInput = document.getElementById('profilePhoto');
            if (photoInput.files.length > 0) {
                alert('Foto de perfil alterada com sucesso.');
                // LÃ³gica de atualizaÃ§Ã£o da foto de perfil aqui
            } else {
                alert('Por favor, selecione uma foto.');
            }
        });
        
            // Função para enviar mensagem no chat
        const chatBox = document.getElementById('chatBox');
        const chatInput = document.getElementById('chatInput');
        const sendButton = document.getElementById('sendButton');

        sendButton.addEventListener('click', function() {
            const message = chatInput.value;
            if (message.trim() !== '') {
                const newMessage = document.createElement('div');
                newMessage.classList.add('message', 'driver');
                newMessage.innerHTML = `<p>${message}</p>`;
                chatBox.appendChild(newMessage);
                chatInput.value = '';
                chatBox.scrollTop = chatBox.scrollHeight; // Rolagem automática para a última mensagem
            }
        });

