<%-- 
    Document   : confMotorista
    Created on : 17/11/2024, 23:10:15
    Author     : asus
--%>

<%@ page import="com.gestaorotas.model.Motoristas" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Perfil do Motorista</title>
    <!-- Incluir CSS do Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <<link rel="stylesheet" href="css/conf.css"/>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-warning p-3 sticky-top">
        <div class="container-fluid">
            <a class="navbar-brand" href="index.jsp">
                <img src="img/Vetor.png" alt="Taxi Logo" width="90">
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" 
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <button class="btn btn-default" data-bs-toggle="modal" data-bs-target="#updatePhoneModal">Alterar Número</button>
                    </li>
                    <li class="nav-item">
                        <button class="btn btn-default" data-bs-toggle="modal" data-bs-target="#updateAvailabilityModal">Alterar Disponibilidade</button>
                    </li>
                    <li class="nav-item">
                        <button class="btn btn-default" data-bs-toggle="modal" data-bs-target="#updatePasswordModal">Alterar Senha</button>
                    </li>
                    <li class="nav-item">
                        <button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteAccountModal">Deletar Conta</button>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-5">
        <h1>Perfil do Motorista</h1>
        <div class="card">
            <div class="card-body">
                <% 
                    // Recuperar o motorista da sessão
                    Motoristas motorista = (Motoristas) session.getAttribute("motorista");
                    if (motorista != null) {
                %>
                <!-- Exibir Dados do Motorista -->
                <div class="mb-3">
                    <label class="form-label">Nome Completo</label>
                    <p><%= motorista.getNome() %></p>
                </div>
                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <p><%= motorista.getEmail() %></p>
                </div>
                <div class="mb-3">
                    <label class="form-label">Número de Telefone</label>
                    <p><%= motorista.getTelefone() %></p>
                </div>
                <div class="mb-3">
                    <label class="form-label">Disponibilidade</label>
                    <p><%= motorista.getDisponibilidade() %></p>
                  
                </div>
                <% 
                    } else { 
                        // Se o motorista não estiver logado, redirecionar para a página de login
                        response.sendRedirect("index.jsp");
                    } 
                %>
            </div>
        </div>
    </div>

    <!-- Modal para Alterar Número de Telefone -->
    <div class="modal fade" id="updatePhoneModal" tabindex="-1" aria-labelledby="updatePhoneModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="updatePhoneModalLabel">Alterar Número de Telefone</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="updatePhoneForm" action="UpdateDriverProfileServlet" method="post">
                        <div class="mb-3">
                            <label for="newPhone" class="form-label">Novo Número de Telefone</label>
                            <input type="tel" class="form-control" id="newPhone" name="telefone" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Salvar Alterações</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal para Alterar Disponibilidade -->
    <div class="modal fade" id="updateAvailabilityModal" tabindex="-1" aria-labelledby="updateAvailabilityModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="updateAvailabilityModalLabel">Alterar Disponibilidade</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="updateAvailabilityForm" action="UpdateDriverProfileServlet" method="post">
                        <div class="mb-3">
                            <label for="newAvailability" class="form-label">Nova Disponibilidade</label>
                            <select class="form-select" id="newAvailability" name="disponibilidade" required>
                                <option value="24h">24h</option>
                                <option value="12h">12h</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Salvar Alterações</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal para Alterar Senha -->
    <div class="modal fade" id="updatePasswordModal" tabindex="-1" aria-labelledby="updatePasswordModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="updatePasswordModalLabel">Alterar Senha</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="updatePasswordForm" action="UpdateDriverProfileServlet" method="post">
                        <div class="mb-3">
                            <label for="newPassword" class="form-label">Nova Senha</label>
                            <input type="password" class="form-control" id="newPassword" name="senha" required>
                        </div>
                        <div class="mb-3">
                            <label for="confirmPassword" class="form-label">Confirmar Nova Senha</label>
                            <input type="password" class="form-control" id="confirmPassword" name="confirmarSenha" required>
                        </div>
                        <button type="submit" class="btn btn-primary"> Salvar Alterações</button>
                        
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal de Sucesso -->
    <div class="modal fade" id="successModal" tabindex="-1" aria-labelledby="successModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="successModalLabel">Sucesso</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    Perfil atualizado com sucesso.
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal de Erro -->
    <div class="modal fade" id="errorModal" tabindex="-1" aria-labelledby="errorModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="errorModalLabel">Erro</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p id="errorMessage"></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Incluir JavaScript do Bootstrap -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript">
        window.onload = function() {
            const params = new URLSearchParams(window.location.search);
            if (params.has('success')) {
                var myModal = new bootstrap.Modal(document.getElementById('successModal'), {
                    keyboard: false
                });
                myModal.show();
            }
        };
    </script>
</body>
</html>
