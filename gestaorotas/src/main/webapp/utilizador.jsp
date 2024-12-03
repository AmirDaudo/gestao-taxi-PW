<%@ page import="com.gestaorotas.model.Usuarios" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Verificar se o usuário está logado
    Usuarios usuario = (Usuarios) session.getAttribute("usuario");
    if (usuario == null) {
        // Se não estiver logado, redireciona para a página de login
        response.sendRedirect("index.jsp");
        return; // Impede que o restante do código da página seja executado
    }
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Painel de Controle do Cliente - Taxi</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://unpkg.com/leaflet/dist/leaflet.css" />
    <link rel="stylesheet" href="css/utilizador.css">
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-warning">
        <div class="container-fluid">
            <a class="navbar-brand text-white" href="#">
                <img src="img/Vetor.png" alt="Taxi Logo" width="90">
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="#historico-corridas">Historico de Corridas</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#cancelar-corrida">Cancelar Corrida</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#denunciar">Denunciar</a>
                    </li>
                    <!-- Avatar do usuário -->
                    <li class="nav-item">
                        <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAAAXNSR0IArs4c6QAAAO5JREFUSEvVldENwjAMRK+bwCTAJjAJMAlsApsAkwCHEtS6jm0ligSWqvaj9bPPznVA5xg650cUsACwB7B9X3cAVwDH9GzWGAGsAVyULATtEqwIiQBuANiBFoQsrRY8ACU5OXPaWF14gEPS3mJwFnxPDQ9Q0n+cjHM41wKoPQfcbQYszOrC1J8fexLlzvM5IIyRV5T35nPg5fhtANdvleYgK82W8ahZU2rNA1baHg2m2oY25Mjul3SfbZUGsLzHG/jMmyQgYg0eZGIdEkDdaXAtwX8FpfqEBLTIk3NOZJKAZ0vpo2+/eaNWUc39f8ALCDsoGfKFTMgAAAAASUVORK5CYII=" alt="Avatar" class="avatar" data-bs-toggle="modal" data-bs-target="#userProfileModal">
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Modal do Perfil do Usuário -->
    <div class="modal fade" id="userProfileModal" tabindex="-1" aria-labelledby="userProfileModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="userProfileModalLabel">Perfil do Usuário</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="text-center mb-3">
                        <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAAAXNSR0IArs4c6QAAAO5JREFUSEvVldENwjAMRK+bwCTAJjAJMAlsApsAkwCHEtS6jm0ligSWqvaj9bPPznVA5xg650cUsACwB7B9X3cAVwDH9GzWGAGsAVyULATtEqwIiQBuANiBFoQsrRY8ACU5OXPaWF14gEPS3mJwFnxPDQ9Q0n+cjHM41wKoPQfcbQYszOrC1J8fexLlzvM5IIyRV5T35nPg5fhtANdvleYgK82W8ahZU2rNA1baHg2m2oY25Mjul3SfbZUGsLzHG/jMmyQgYg0eZGIdEkDdaXAtwX8FpfqEBLTIk3NOZJKAZ0vpo2+/eaNWUc39f8ALCDsoGfKFTMgAAAAASUVORK5CYII=" alt="Avatar" class="avatar" style="width: 100px; height: 100px;">
                        <p class="mt-2">Clique na imagem para alterar a foto de perfil.</p>
                    </div>

                    <!-- Formulário para alterar a foto de perfil -->
                    <form id="profilePhotoForm">
                        <input type="file" class="form-control" id="profilePhoto" accept="image/*">
                        <button type="submit" class="btn btn-primary w-100 mt-3">Alterar Foto de Perfil</button>
                    </form>

                    <!-- Dados do usuário -->
                    <div class="mt-4">
                        <p><strong>Nome:</strong> <%= usuario.getNome() %></p>
                        <p><strong>Telefone:</strong> <%= usuario.getTelefone() %></p>
                        <p><strong>Email:</strong> <%= usuario.getEmail() %></p>

                        <!-- Botão de logout -->
                        <form id="logoutForm" action="LogoutServlet" method="post" class="mt-3">
                            <button type="submit" class="btn btn-danger w-100">Logout</button>
                        </form>

                        <!-- Botão para abrir o modal de exclusão de conta -->
                        <button type="button" class="btn btn-danger w-100 mt-3" data-bs-toggle="modal" data-bs-target="#deleteAccountModal">
                            Deletar Conta
                        </button>

                        <!-- Botão para editar perfil -->
                        <a href="perfil.jsp" class="btn btn-warning w-100 mt-3"><i class="fas fa-taxi"></i> Editar</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

                    <!-- Modal de Confirmação -->
<div class="modal fade" id="logoutModal" tabindex="-1" aria-labelledby="logoutModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="logoutModalLabel">Confirmar Logout</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Você realmente deseja sair?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                <button type="button" class="btn btn-danger" id="confirmLogoutButton">Logout</button>
            </div>
        </div>
    </div>
</div>

          <div class="container mt-3">
        <h1 class="text-center">Solicitação de Táxi</h1>
        
        <!-- Mapa da localização -->
        <div id="map" style="height: 400px;"></div>
        
        <!-- Exibir mensagens de sucesso ou erro -->
        <%
            String message = (String) request.getAttribute("message");
            String status = (String) request.getAttribute("status");
            if (message != null && status != null) {
        %>
            <div class="alert <%= status.equals("success") ? "alert-success" : "alert-danger" %>">
                <%= message %>
            </div>
        <%
            }
        %>

        <!-- Formulário de Solicitação de Táxi -->
        <form id="taxi-form" class="mt-4" action="SolicitarTaxiServlet" method="post">
            <div class="mb-3">
                <label for="pickup" class="form-label">Local de Partida</label>
                <input type="text" class="form-control" id="pickup" name="pickup" readonly>
            </div>
            <div class="mb-3">
                <label for="destination" class="form-label">Destino</label>
                <input type="text" class="form-control" id="destination" name="destination" placeholder="Digite o destino">
            </div>
            <div class="mb-3">
                <label for="distance" class="form-label">Distância (km)</label>
                <input type="text" class="form-control" id="distance" name="distance" readonly>
            </div>
            <div class="mb-3">
                <label for="fare" class="form-label">Valor Estimado (MZN)</label>
                <input type="text" class="form-control" id="fare" name="fare" readonly>
            </div>
            <button type="button" class="btn btn-primary w-100" onclick="generateRandomValues()">Gerar Valores</button>
            <button type="submit" class="btn btn-success w-100 mt-2">Solicitar Táxi</button>
        </form>
    </div>


    <!-- Seção de Histórico de Corridas -->
    <div class="container mt-5" id="historico-corridas">
        <div class="section-header">
            <h3>Histórico de Corridas</h3>
            <ul id="corridas-list" class="list-group">
                <!-- Corridas anteriores serão listadas aqui -->
            </ul>
        </div>
    </div>
</div> 
    <!-- Outras Funcionalidades: Cancelar Corrida, Denunciar, etc. -->
    <div class="container mt-5">
        <!-- Seção de Cancelar Corrida -->
        <div class="section-header" id="cancelar-corrida">
            <h3>Cancelar Corrida</h3>
            <p>Deseja cancelar a corrida? Se sim, clique no botão abaixo:</p>
            <button class="btn btn-custom-cancel w-100" id="submit-cancel">Cancelar Corrida</button>
        </div>


   <!-- Seção de Denúncia -->
    <section class="py-5 bg-light">
        <div class="container">
            <h3>Denunciar</h3>
            <p>Em caso de problemas ou comportamentos inadequados, denuncie a corrida aqui.</p>
            <form action="DenunciaServlet" method="post">
                <div class="mb-3">
                    <label for="denuncia" class="form-label">Descreva o problema</label>
                    <textarea class="form-control" id="denuncia" name="denuncia" rows="3" placeholder="Escreva a sua denúncia" required></textarea>
                </div>
                <button type="submit" class="btn btn-custom-report w-100">Enviar Denúncia</button>
            </form>
        </div>
    </section>

    <!-- Modal de Sucesso -->
    <div class="modal fade" id="successModal" tabindex="-1" aria-labelledby="successModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="successModalLabel">Sucesso</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>Denúncia enviada com sucesso!</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                </div>
            </div>
        </div>
    </div>


       <!-- Chat com o Cliente -->
            <div id="chat" class="tab-content mt-5">
                <h3 class="mb-4">Chat</h3>
                <div class="chat-box" id="chatBox" style="height: 300px; overflow-y: auto; border: 1px solid #ccc; padding: 10px;">
        <!-- Mensagens dinâmicas serão exibidas aqui -->
             </div>
                <div class="input-group mt-3">
                <input type="text" class="form-control" id="chatInput" placeholder="Escreva uma mensagem">
                    <button class="btn btn-warning" id="sendButton">Enviar</button>
                      </div>
                        </div>
      
      <!-- Modal de Exclusão de Conta -->
<div class="modal fade" id="deleteAccountModal" tabindex="-1" aria-labelledby="deleteAccountModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteAccountModalLabel">Deletar Conta</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Tem certeza de que deseja deletar sua conta? Esta ação não pode ser desfeita.
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                <form action="DeleteAccountServlet" method="post">
                    <button type="submit" class="btn btn-danger">Deletar Conta</button>
                </form>
            </div>
        </div>
    </div>
</div>


       <!-- Contatos de Emergência -->
        <div class="section-header" id="contatos-emergencia">
            <h3>Contatos de Emergência</h3>
            <p>Em caso de emergência, entre em contato com as autoridades:</p>
            <ul>
                <li><strong>Polícia:</strong> 112</li>
                <li><strong>Bombeiros:</strong> 193</li>
                <li><strong>Central de Táxi:</strong> +258 844 272 434</li>
            </ul>
        </div>
    </div>

    <!-- Script JavaScript para GeolocalizaÃ§Ã£o, Pagamento e HistÃ³rico -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>
    <script src="js/util.js"></script>
    <!-- Scripts JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>
    <script>
        function confirmLogout() {
    var logoutModal = new bootstrap.Modal(document.getElementById('logoutModal'), {
        keyboard: false
    });
    logoutModal.show();
}

document.getElementById('confirmLogoutButton').addEventListener('click', function() {
    document.getElementById('logoutForm').submit();
});

    </script>
    
    <!-- Script para verificar a sessão -->
    <script>
        // Se não estiver logado, redireciona para o login
        if (!<%= session.getAttribute("usuario") != null %>) {
            window.location.href = "login.jsp";
        }
    </script>
    
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            var denunciaEnviada = <%= session.getAttribute("denunciaEnviada") != null %>;
            if (denunciaEnviada) {
                var successModal = new bootstrap.Modal(document.getElementById('successModal'));
                successModal.show();
                // Remover a flag da sessão após exibir o modal
                <% session.removeAttribute("denunciaEnviada"); %>
            }
        });
    </script>
    
    <script>
    const chatBox = document.getElementById('chatBox');
    const chatInput = document.getElementById('chatInput');
    const sendButton = document.getElementById('sendButton');
    const userId = '<%= session.getAttribute("userId") %>'; // Exemplo: ID do motorista ou cliente
    const targetId = '<%= request.getParameter("targetId") %>'; // ID do destinatário

    const ws = new WebSocket(`ws://${location.host}/nome-do-seu-projeto/chat?${userId}`);

    ws.onmessage = (event) => {
        const message = document.createElement('div');
        message.classList.add('message', 'client'); // Customize classe com base no remetente
        message.innerHTML = `<p>${event.data}</p>`;
        chatBox.appendChild(message);
        chatBox.scrollTop = chatBox.scrollHeight;
    };

    sendButton.onclick = () => {
        const message = chatInput.value.trim();
        if (message) {
            ws.send(`${targetId}|${message}`); // Envia para o destinatário
            const messageDiv = document.createElement('div');
            messageDiv.classList.add('message', 'driver'); // Customize classe com base no remetente
            messageDiv.innerHTML = `<p>${message}</p>`;
            chatBox.appendChild(messageDiv);
            chatInput.value = '';
            chatBox.scrollTop = chatBox.scrollHeight;
        }
    };
</script>


             <script>
        var map, pickupMarker;

        function initMap() {
            map = new google.maps.Map(document.getElementById('map'), {
                center: {lat: -12.9747, lng: 40.5178}, // Coordenadas iniciais para Pemba
                zoom: 15
            });

            var pickupInput = document.getElementById('pickup');
            var destinationInput = document.getElementById('destination');
            var distanceInput = document.getElementById('distance');
            var fareInput = document.getElementById('fare');

            var destinationAutocomplete = new google.maps.places.Autocomplete(destinationInput);
            destinationAutocomplete.bindTo('bounds', map);

            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function (position) {
                    var pos = {
                        lat: position.coords.latitude,
                        lng: position.coords.longitude
                    };
                    map.setCenter(pos);

                    pickupMarker = new google.maps.Marker({
                        position: pos,
                        map: map,
                        title: 'Você está aqui!'
                    });

                    pickupInput.value = pos.lat + "," + pos.lng;
                }, function () {
                    handleLocationError(true, map.getCenter());
                });
            } else {
                handleLocationError(false, map.getCenter());
            }
        }

        function generateRandomValues() {
            var distance = (Math.random() * 10 + 1).toFixed(2); // Distância entre 1 e 10 km
            var fare = (distance * 50).toFixed(2); // Exemplo: MZN 50 por km

            document.getElementById('distance').value = distance;
            document.getElementById('fare').value = fare;
        }

        function handleLocationError(browserHasGeolocation, pos) {
            var infoWindow = new google.maps.InfoWindow({
                map: map,
                position: pos,
                content: browserHasGeolocation ?
                    'Erro: Falha ao buscar a localização.' :
                    'Erro: Seu navegador não suporta geolocalização.'
            });
            infoWindow.open(map);
        }

        $(document).ready(function() {
            initMap();
        });
    </script>


    </body>
</html>
