<%-- 
    Document   : motorista
    Created on : 09/11/2024, 10:18:30
    Author     : asus
--%>
<%@ page import="com.gestaorotas.model.Motoristas" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Área do Motorista - Taxi Service</title>
    
    <script type="text/javascript">
        function redirectToLogin(message, url) 
        {
            alert(message); window.location.href = url;
        }
        </script>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome Icons -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <!-- Estilo Personalizado -->
    <link rel="stylesheet" href="motorista.css">
   
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-warning p-3">
        <div class="container-fluid">
            <a class="navbar-brand" href="index.html">
                <img src="Vetor.png" alt="Taxi Logo" width="90">
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" 
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="index.jsp">Início</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="contacto.jsp">Contato</a>
                    </li>
                    <li class="nav-item">
                         <!-- Botão de logout -->
                      <!-- Botão para abrir o modal de logout -->
                    <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#logoutModal">
                         Logout
                    </button>

                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Área do Motorista -->
    <section class="container my-5">
        <div class="row">
            <div class="col-lg-3">
                <!-- Menu Lateral -->
                <div class="list-group">
                    <a href="#dashboard" class="list-group-item list-group-item-action active">
                        <i class="fas fa-tachometer-alt"></i> Dashboard
                    </a>
                    <a href="#pedidos" class="list-group-item list-group-item-action">
                        <i class="fas fa-taxi"></i> Pedidos de Corrida
                    </a>
                    <a href="#pagamentos" class="list-group-item list-group-item-action">
                        <i class="fas fa-wallet"></i> Pagamentos
                    </a>
                    <a href="#chat" class="list-group-item list-group-item-action">
                        <i class="fas fa-comments"></i> Chat com Cliente
                    </a>
                    <a href="#configuracoes" class="list-group-item list-group-item-action">
                        <i class="fas fa-cog"></i> Configurações
                    </a>
                </div>
            </div>

            <div class="col-lg-9">
                <!-- Conteúdo Principal -->
                <div id="dashboard" class="tab-content">
                      <% 
                        // Recuperar o usuário da sessão
                        Motoristas motorista = (Motoristas) session.getAttribute("motorista");
                        if (motorista != null) {
                    %>
                    <!-- Dashboard -->
                    <h3 class="mb-4">Bem-vindo, <%= motorista.getNome() %> </h3> 
                    <div class="row text-center">
                        <div class="col-md-4">
                            <div class="card border-0 shadow">
                                <div class="card-body">
                                    <i class="fas fa-taxi fa-3x text-warning mb-3"></i>
                                    <h5 class="card-title">Corridas Hoje</h5>
                                    <p class="card-text" id="corridasHoje">0</p>
                                </div>
                            </div>
                        </div>
                          <% 
                        } else { 
                      %> 
                            // Se o usuário não estiver logado, redirecionar para a página de login
                           <script type="text/javascript">
                             redirectToLogin("Você não fez login. Redirecionando para a página inicial.", "index.jsp");

                        
                           </script>
                           <%       
                        } 
                    %>
                        <div class="col-md-4">
                            <div class="card border-0 shadow">
                                <div class="card-body">
                                    <i class="fas fa-wallet fa-3x text-warning mb-3"></i>
                                    <h5 class="card-title">Ganhos Hoje</h5>
                                    <p class="card-text" id="ganhosHoje">MZN 0,00</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card border-0 shadow">
                                <div class="card-body">
                                    <i class="fas fa-star fa-3x text-warning mb-3"></i>
                                    <h5 class="card-title">Avaliações</h5>
                                    <p class="card-text" id="avaliacoesMotorista">4.8</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Pedidos de Corrida -->
                <div id="pedidos" class="tab-content mt-5">
                    <h3 class="mb-4">Pedidos de Corrida</h3>
                    <div class="list-group">
                        <div class="list-group-item d-flex justify-content-between align-items-center">
                            <div>
                                <h5>Cliente: João Silva</h5>
                                <p>Destino: Centro de Pemba</p>
                                <p>Distância: 5 km | Valor Estimado: MZN 200,00</p>
                            </div>
                            <div>
                                <button class="btn btn-success me-2" onclick="aceitarPedido()">Aceitar</button>
                                <button class="btn btn-danger" onclick="rejeitarPedido()">Rejeitar</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Chat com o Cliente -->
                <div id="chat" class="tab-content mt-5">
                    <h3 class="mb-4">Chat com Cliente</h3>
                    <div class="chat-box" id="chatBox">
                        <div class="message client">
                            <p>Olá, você está disponível para essa corrida?</p>
                        </div>
                        <div class="message driver">
                            <p>Sim, estou disponível. Podemos negociar o preço.</p>
                        </div>
                        <!-- Novas mensagens aparecerão aqui -->
                    </div>
                    <div class="input-group">
                        <input type="text" class="form-control" id="chatInput" placeholder="Escreva uma mensagem">
                        <button class="btn btn-warning" id="sendButton">Enviar</button>
                    </div>
                </div>

                <!-- Pagamentos -->
                <div id="pagamentos" class="tab-content mt-5">
                    <h3 class="mb-4">Informações de Pagamento</h3>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Data</th>
                                <th>Corridas</th>
                                <th>Valor Total (MZN)</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>20/10/2024</td>
                                <td>4</td>
                                <td>MZN 800,00</td>
                            </tr>
                            <tr>
                                <td>19/10/2024</td>
                                <td>3</td>
                                <td>MZN 600,00</td>
                            </tr>
                        </tbody>
                    </table>
                </div>                                                                                                
                <!-- Configurações -->
                <div id="configuracoes" class="tab-content mt-5">
                    <h3 class="mb-4">Configurações da Conta</h3>
                    <form>
                        <div class="mb-3">
                            <label for="nomeMotorista" class="form-label">Nome Completo</label>
                            <p> <%= motorista.getNome() %></p>
                        </div>
                        <div class="mb-3">
                            <label for="emailMotorista" class="form-label">E-mail</label>
                           <p> <%= motorista.getTelefone() %></p>
                        </div>
                        <div class="mb-3">
                            <label for="telefoneMotorista" class="form-label">Número de Telefone</label>
                             <p> <%= motorista.getEmail() %></p>
                        </div>
                        <a href="confMotorista.jsp" class="btn btn-warning"> Editar dados </a> 
                    </form>
                </div>
            </div>
        </div>
    </section>


<!-- Modal de Logout -->
<div class="modal fade" id="logoutModal" tabindex="-1" aria-labelledby="logoutModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="logoutModalLabel">Logout</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Tem certeza de que deseja sair?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                <form id="logoutForm" action="LogoutServlet" method="post">
                    <button type="submit" class="btn btn-danger">Logout</button>
                </form>
            </div>
        </div>
    </div>
</div>

    <!-- Bootstrap 5 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <!-- Funções JavaScript -->
    <script>
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


    function confirmLogout() {
         var logoutModal = new bootstrap.Modal(document.getElementById('logoutModal'), {
        keyboard: false
    });
    logoutModal.show();
}

         document.getElementById('confirmLogoutButton').addEventListener('click', function() {
    document.getElementById('logoutForm').submit();
});

        }
    </script>
</body>
</html>