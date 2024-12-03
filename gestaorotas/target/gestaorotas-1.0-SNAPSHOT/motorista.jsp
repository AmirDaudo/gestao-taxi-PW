  <%-- 
    Document   : motorista
    Created on : 09/11/2024, 10:18:30
    Author     : asus
--%>
<%@page import="com.gestaorotas.model.Corridas"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.gestaorotas.model.Motoristas" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // Verificar se o usuário está logado
    Motoristas motorista = (Motoristas) session.getAttribute("motorista");
    if (motorista == null) {
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
    <title>Área do Motorista - Taxi Service</title>

    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome Icons -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <!-- Estilo Personalizado -->
    <link rel="stylesheet" href="motorista.css">

  <script>
        function aceitarPedido(corridaId) {
            document.getElementById('form-' + corridaId).action = 'MotoristasServlet?action=aceitar';
            document.getElementById('form-' + corridaId).submit();
        }

        function rejeitarPedido(corridaId) {
            document.getElementById('form-' + corridaId).action = 'MotoristasServlet?action=rejeitar';
            document.getElementById('form-' + corridaId).submit();
        }
    </script>
   
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-warning p-3">
        <div class="container-fluid">
            <a class="navbar-brand" href="index.html">
                <img src="img/Vetor.png" alt="Taxi Logo" width="90">
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
  <div class="container mt-3">
        <h1 class="text-center">Pedidos de Corrida</h1>
        
        <%
            List<Corridas> corridasPendentes = (List<Corridas>) request.getAttribute("corridasPendentes");
            if (corridasPendentes != null && !corridasPendentes.isEmpty()) {
        %>
            <div class="list-group mt-5">
                <%
                    for (Corridas corrida : corridasPendentes) {
                %>
                <div class="list-group-item d-flex justify-content-between align-items-center">
                    <div>
                        <h5>Cliente: <%= corrida.getClienteNome() %></h5>
                        <p>Destino: <%= corrida.getDestino() %></p>
                        <p>Distância: <%= corrida.getDistancia() %> km | Valor Estimado: MZN <%= corrida.getValorEstimado() %></p>
                    </div>
                    <div>
                        <form id="form-<%= corrida.getId() %>" method="post">
                            <input type="hidden" name="corridaId" value="<%= corrida.getId() %>">
                            <button type="button" class="btn btn-success me-2" onclick="aceitarPedido(<%= corrida.getId() %>)">Aceitar</button>
                            <button type="button" class="btn btn-danger" onclick="rejeitarPedido(<%= corrida.getId() %>)">Rejeitar</button>
                        </form>
                    </div>
                </div>
                <%
                    }
                %>
            </div>
        <%
            } else {
        %>
            <div class="alert alert-info">Nenhuma requisição de táxi pendente.</div>
        <%
            }
        %>
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
        function aceitarPedido(corridaId) {
            document.getElementById('form-' + corridaId).action = 'MotoristasServlet?action=aceitar';
            document.getElementById('form-' + corridaId).submit();
        }

        function rejeitarPedido(corridaId) {
            document.getElementById('form-' + corridaId).action = 'MotoristasServlet?action=rejeitar';
            document.getElementById('form-' + corridaId).submit();
        }
    </script>
    
</body>
</html>
