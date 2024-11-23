<%@ page import="java.util.List" %>
<%@ page import="com.gestaorotas.model.Denuncias" %>
<%@ page import="jakarta.persistence.EntityManager" %>
<%@ page import="jakarta.persistence.EntityManagerFactory" %>
<%@ page import="com.gestaorotas.JpaUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gestaorotas.model.Motoristas" %>

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
           
          <div class="container mt-5">
        <h1>Motoristas Logados</h1>
        <div class="table-responsive">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>Telefone</th>
                        <th>Disponibilidade</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Motoristas> motoristasLogados = (List<Motoristas>) request.getAttribute("motoristasLogados");
                        if (motoristasLogados != null) {
                            for (Motoristas motorista : motoristasLogados) {
                    %>
                    <tr>
                        <td><%= motorista.getId() %></td>
                        <td><%= motorista.getNome() %></td>
                        <td><%= motorista.getEmail() %></td>
                        <td><%= motorista.getTelefone() %></td>
                        <td><%= motorista.getDisponibilidade() %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="5">Nenhum motorista logado.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>

  
        <!-- Atividades e Status dos Motoristas -->
        <section class="dashboard mb-4" id="atividades-recentes">
            <div class="section-title">
                <h2>Atividades Recentes & Status dos Motoristas</h2>
            </div>
            <div class="activity-list">
                <!-- Aqui aparecerão as notificações de atividade -->
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
    <!-- Adicionar o script WebSocket -->
    <script>
    document.addEventListener("DOMContentLoaded", function () {
        const atividadeList = document.querySelector("#atividades-recentes .activity-list");
        const ws = new WebSocket("ws://localhost:8080/gestaorotas-1.0-SNAPSHOT/admin");

        ws.onmessage = function (event) {
            const mensagem = event.data;
            const atividadeElement = document.createElement("div");
            atividadeElement.classList.add("activity-item", "mb-3", "p-3", "border", "rounded", "bg-light");
            atividadeElement.innerHTML = `<p><strong>${mensagem}</strong></p>`;
            atividadeList.appendChild(atividadeElement);
        };
    });
    </script>
</body>
</html>
