<%@ page import="java.util.List" %>
<%@ page import="com.gestaorotas.model.Denuncias" %>
<%@ page import="jakarta.persistence.EntityManager" %>
<%@ page import="jakarta.persistence.EntityManagerFactory" %>
<%@ page import="com.gestaorotas.JpaUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gestaorotas.model.Motoristas" %>

<%@ page import="com.gestaorotas.model.Usuarios" %>
<%
    Usuarios usuario = (Usuarios) session.getAttribute("usuario");
    String adminName = (String) session.getAttribute("adminName");
    if (usuario == null && adminName == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Painel do Administrador - Gestão de Táxi</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/Admin.css">
</head>
<body>

    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-warning p-3">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">
                <img src="img/Vetor.png" alt="Taxi Logo" width="90">
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="#taxista-requests">Dashboard</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#atividades-recentes">Atividades</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#feedback">Feedback</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#graficos">Relatórios</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
      <h1>Bem-vindo, <%= adminName != null ? adminName : usuario.getNome() %>!</h1>

    <header class="bg-dark text-light text-center py-4">
        <h1>Painel do Administrador</h1>
    </header>
            <!-- Seção de Denúncias -->
        <section class="mt-5">
            <h2>Denúncias</h2>
            <div class="card">
                <div class="card-body">
                    <ul class="list-group">
                        <%
                            EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
                            EntityManager em = emf.createEntityManager();
                            try {
                                List<Denuncias> denuncias = em.createQuery("SELECT d FROM Denuncias d", Denuncias.class).getResultList();
                                if (denuncias != null && !denuncias.isEmpty()) {
                                    for (Denuncias denuncia : denuncias) {
                        %>
                                        <li class="list-group-item">
                                            <strong>Data:</strong> <%= denuncia.getData() %><br>
                                            <strong>Nome do Usuário:</strong> <%= denuncia.getUsuarioNome() %><br>
                                            <strong>Email do Usuário:</strong> <%= denuncia.getUsuarioEmail() %><br>
                                            <strong>Mensagem:</strong> <%= denuncia.getMensagem() %><br>
                                            <strong>Status:</strong> <%= denuncia.getStatus() %><br>
                                            <form action="AdminDenunciasServlet" method="post" style="display:inline;">
                                                <input type="hidden" name="id" value="<%= denuncia.getId() %>">
                                                <input type="hidden" name="action" value="apagar">
                                                <button type="submit" class="btn btn-danger mt-2">Apagar</button>
                                            </form>
                                            <form action="AdminDenunciasServlet" method="post" style="display:inline;">
                                                <input type="hidden" name="id" value="<%= denuncia.getId() %>">
                                                <input type="hidden" name="action" value="aprovar">
                                                <button type="submit" class="btn btn-success mt-2">Aprovar</button>
                                            </form>
                                            <form action="AdminDenunciasServlet" method="post" style="display:inline;">
                                                <input type="hidden" name="id" value="<%= denuncia.getId() %>">
                                                <input type="hidden" name="action" value="rejeitar">
                                                <button type="submit" class="btn btn-warning mt-2">Rejeitar</button>
                                            </form>
                                        </li>
                        <%
                                    }
                                } else {
                        %>
                                    <li class="list-group-item">Nenhuma denúncia recebida.</li>
                        <%
                                }
                            } finally {
                                em.close();
                            }
                        %>
                    </ul>
                </div>
            </div>
        </section>
                    
                         <!-- Motoristas Cadastrados -->
        
             <div class="container mt-5">
        <h3>Motoristas Cadastrados</h3>
        <form action="MotoristasCadastradosServlet" method="get">
            <button type="submit" class="btn btn-primary">Carregar Motoristas</button>
        </form>

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

        <div class="table-responsive mt-3">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>Telefone</th>
                        <th>Status</th>
                        <th>Disponibilidade</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Motoristas> motoristasCadastrados = (List<Motoristas>) request.getAttribute("motoristasCadastrados");
                        if (motoristasCadastrados != null && !motoristasCadastrados.isEmpty()) {
                            for (Motoristas motorista : motoristasCadastrados) {
                    %>
                    <tr>
                        <td><%= motorista.getId() %></td>
                        <td><a href="detalhes_motorista.jsp?id=<%= motorista.getId() %>"><%= motorista.getNome() %></a></td>
                        <td><%= motorista.getEmail() %></td>
                        <td><%= motorista.getTelefone() %></td>
                        <td><%= motorista.getStatus() %></td>
                        <td><%= motorista.getDisponibilidade() %></td>
                        <td>
                            <% if (Boolean.TRUE.equals(motorista.getBloqueado())) { %>
                                <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#confirmUnblockModal" data-id="<%= motorista.getId() %>">
                                    Desbloquear
                                </button>
                            <% } else { %>
                                <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#confirmBlockModal" data-id="<%= motorista.getId() %>">
                                    Bloquear
                                </button>
                            <% } %>
                            <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#confirmDeleteModal" data-id="<%= motorista.getId() %>">
                                Apagar
                            </button>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="7">Nenhum motorista cadastrado encontrado. Carregue os motoristas cadastrados.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Modal de Confirmação de Bloqueio -->
    <div class="modal fade" id="confirmBlockModal" tabindex="-1" aria-labelledby="confirmBlockModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmBlockModalLabel">Confirmar Bloqueio</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    Você tem certeza que deseja bloquear este motorista?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <form id="blockForm" action="MotoristasCadastradosServlet" method="post">
                        <input type="hidden" name="id" id="motoristaIdBloquear" value="">
                        <input type="hidden" name="action" value="bloquear">
                        <button type="submit" class="btn btn-warning">Bloquear</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal de Confirmação de Desbloqueio -->
    <div class="modal fade" id="confirmUnblockModal" tabindex="-1" aria-labelledby="confirmUnblockModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmUnblockModalLabel">Confirmar Desbloqueio</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    Você tem certeza que deseja desbloquear este motorista?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <form id="unblockForm" action="MotoristasCadastradosServlet" method="post">
                        <input type="hidden" name="id" id="motoristaIdDesbloquear" value="">
                        <input type="hidden" name="action" value="desbloquear">
                        <button type="submit" class="btn btn-success">Desbloquear</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal de Confirmação de Exclusão -->
    <div class="modal fade" id="confirmDeleteModal" tabindex="-1" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmDeleteModalLabel">Confirmar Exclusão</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    Você tem certeza que deseja apagar este motorista?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <form id="deleteForm" action="ApagarMotoristaServlet" method="post">
                        <input type="hidden" name="id" id="motoristaIdApagar" value="">
                        <button type="submit" class="btn btn-danger">Apagar</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
     
                
      <!-- Exibir lista de motoristas online -->
        <section class="dashboard mb-4" id="atividades-recentes">
            <div class="section-title">
                <h2>Atividades Recentes & Status dos Motoristas</h2>
            </div>
            <div class="activity-list">
                <ul>
                    <%
                        List<Motoristas> motoristasOnline = (List<Motoristas>) request.getAttribute("motoristasOnline");
                        if (motoristasOnline != null && !motoristasOnline.isEmpty()) {
                            for (Motoristas motorista : motoristasOnline) {
                    %>
                        <li>
                            <strong><%= motorista.getNome() %></strong> está online.
                        </li>
                    <%
                            }
                        } else {
                    %>
                    <li>Nenhum motorista online no momento.</li>
                    <%
                        }
                    %>
                </ul>
            </div>
        </section>


        <!-- Feedback Resumido -->
        <section class="dashboard mb-4" id="feedback">
            <div class="section-title">
                <h2>Média de Feedback</h2>
            </div>
            <div class="feedback-summary bg-light p-3 border rounded">
                <p><strong>Média Avaliações de Clientes:</strong> ????? (4.2)</p>
                <p><strong>Média Avaliações de Motoristas:</strong> ????? (4.8)</p>
            </div>
        </section>

        <!-- Gráficos de Relatório Financeiro e Corridas -->
        <section class="dashboard mb-4" id="graficos">
            <div class="section-title">
                <h2>Gráficos Financeiros e de Corridas</h2>
            </div>
            <div class="row">
                <!-- Gráfico de Corridas -->
                <div class="col-md-6 mb-4">
                    <h3>Gráfico de Corridas</h3>
                    <div class="history-chart bg-light p-3 border rounded">
                        <canvas id="corridasChart"></canvas>
                    </div>
                </div>
                <!-- Gráfico Financeiro -->
                <div class="col-md-6 mb-4">
                    <h3>Gráfico Financeiro</h3>
                    <div class="financial-report bg-light p-3 border rounded">
                        <canvas id="financeChart"></canvas>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <!-- Chart.js Script para Gráficos -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <!-- Bootstrap 5 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/admin.js"></script>
    
 
    <script>
        // Script para definir o ID do motorista nos modais de confirmação
        var confirmBlockModal = document.getElementById('confirmBlockModal');
        confirmBlockModal.addEventListener('show.bs.modal', function (event) {
            var button = event.relatedTarget;
            var motoristaId = button.getAttribute('data-id');
            var inputMotoristaIdBloquear = document.getElementById('motoristaIdBloquear');
            inputMotoristaIdBloquear.value = motoristaId;
        });

        var confirmUnblockModal = document.getElementById('confirmUnblockModal');
        confirmUnblockModal.addEventListener('show.bs.modal', function (event) {
            var button = event.relatedTarget;
            var motoristaId = button.getAttribute('data-id');
            var inputMotoristaIdDesbloquear = document.getElementById('motoristaIdDesbloquear');
            inputMotoristaIdDesbloquear.value = motoristaId;
        });

        var confirmDeleteModal = document.getElementById('confirmDeleteModal');
        confirmDeleteModal.addEventListener('show.bs.modal', function (event) {
            var button = event.relatedTarget;
            var motoristaId = button.getAttribute('data-id');
            var inputMotoristaIdApagar = document.getElementById('motoristaIdApagar');
            inputMotoristaIdApagar.value = motoristaId;
        });
    </script>
    
    
</body>
</html>
