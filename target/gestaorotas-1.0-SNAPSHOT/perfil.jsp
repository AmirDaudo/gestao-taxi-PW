<%-- 
    Document   : perfil
    Created on : 16/11/2024, 13:05:20
    Author     : asus
--%>

<%@ page import="com.gestaorotas.model.Usuarios" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CodingDung | Template de Perfil</title>
    <link rel="stylesheet" href="css/perfil.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css" rel="stylesheet">
  
</head>

<body>
    <div class="container light-style flex-grow-1 container-p-y">
        <h4 class="font-weight-bold py-3 mb-4">
            Configurações da Conta
        </h4>
         <% 
             // Recuperar o usuário da sessão
           Usuarios usuario = (Usuarios) session.getAttribute("usuario");
           if (usuario != null) {
        %>
        
        <!-- Exibe uma mensagem de boas-vindas com o nome do usuário -->
        <h1>Bem-vindo, <%= usuario.getNome() %></h1>
        
        <div class="card overflow-hidden">
            <div class="row no-gutters row-bordered row-border-light">
                <div class="col-md-3 pt-0">
                    <div class="list-group list-group-flush account-settings-links">
                        <a class="list-group-item list-group-item-action active" data-toggle="list" href="#account-general">Geral</a>
                        <a class="list-group-item list-group-item-action" data-toggle="modal" data-target="#changePasswordModal">Alterar Senha</a>
                    </div>
                </div>
                <div class="col-md-9">
                    <div class="tab-content">
                        <div class="tab-pane fade active show" id="account-general">
                            <div class="card-body">
                                <!-- Exibe os dados do usuário -->
                                <div class="form-group">
                                    <label>Nome:</label>
                                    <input type="text" class="form-control" value="<%= usuario.getNome() %>" disabled>
                                </div>

                                <div class="form-group">
                                    <label>E-mail:</label>
                                    <input type="email" class="form-control" value="<%= usuario.getEmail() %>" disabled>
                                </div>

                                <div class="form-group">
                                    <label>Telefone:</label>
                                    <input type="text" class="form-control" value="<%= usuario.getTelefone() %>" disabled>
                                </div>

                                <div class="form-group">
                                    <label>Senha:</label>
                                    <input type="password" class="form-control" value="<%= usuario.getSenha() %>" disabled>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
                    <% 
                        } else { 
                            // Se o usuário não estiver logado, redirecionar para a página de login
                            response.sendRedirect("index.jsp");
                        } 
                    %>

    <!-- Modal para Alteração de Senha -->
<div class="modal fade" id="changePasswordModal" tabindex="-1" role="dialog" aria-labelledby="changePasswordModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="changePasswordModalLabel">Alterar Senha</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Fechar">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form id="changePasswordForm" action="AlterarSenhaServlet" method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="current-password">Senha Atual</label>
                        <div class="password-container">
                            <input type="password" class="form-control" id="current-password" name="currentPassword" placeholder="Digite sua senha atual" required>
                            <span class="password-toggle" onclick="togglePasswordModal('current-password')">
                                <!-- Ícone de alternância de senha -->
                            </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="new-password">Nova Senha</label>
                        <div class="password-container">
                            <input type="password" class="form-control" id="new-password" name="newPassword" placeholder="Digite sua nova senha" required>
                            <span class="password-toggle" onclick="togglePasswordModal('new-password')">
                                <!-- Ícone de alternância de senha -->
                            </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="repeat-new-password">Repetir Nova Senha</label>
                        <div class="password-container">
                            <input type="password" class="form-control" id="repeat-new-password" name="confirmNewPassword" placeholder="Repita sua nova senha" required>
                            <span class="password-toggle" onclick="togglePasswordModal('repeat-new-password')">
                                <!-- Ícone de alternância de senha -->
                            </span>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                    <button type="submit" class="btn btn-primary">Salvar Alterações</button>
                </div>
            </form>
        </div>
    </div>
</div>

    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/js/bootstrap.bundle.min.js"></script>
     <script src="js/perfil.js"></script>
    
</body>

</html>
