<%-- 
    Document   : detalhes_motorista
    Created on : 23/11/2024, 22:25:35
    Author     : asus
--%>

<%@page import="com.gestaorotas.JpaUtil"%>
<%@ page import="com.gestaorotas.model.Motoristas" %>
<%@ page import="jakarta.persistence.EntityManager" %>
<%@ page import="jakarta.persistence.EntityManagerFactory" %>
<%@ page import="jakarta.persistence.Persistence" %>
<%@ page import="java.util.Base64" %>
<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Motorista</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    
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


    <div class="container my-5">
        <%
            int motoristaId = Integer.parseInt(request.getParameter("id"));
            EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
            EntityManager em = emf.createEntityManager();
            Motoristas motorista = em.find(Motoristas.class, motoristaId);
            if (motorista == null) {
        %>
            <div class="alert alert-danger" role="alert">
                Motorista não encontrado!
            </div>
        <% } else { %>
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h1>Detalhes do Motorista</h1>
                </div>
                <div class="card-body">
                    <p><strong>Nome:</strong> <%= motorista.getNome() %></p>
                    <p><strong>Email:</strong> <%= motorista.getEmail() %></p>
                    <p><strong>Telefone:</strong> <%= motorista.getTelefone() %></p>
                    <p><strong>Status:</strong> <%= motorista.getStatus() %><p>
                    <p><strong>Disponibilidade:</strong> 
                        <span class="badge <%= "bloqueado".equals(motorista.getDisponibilidade()) ? "bg-danger" : "bg-success" %>">
                            <%= motorista.getDisponibilidade() %>
                        </span>
                    </p>

                    <h2 class="mt-4">Fotos do Carro</h2>
                    <div class="row">
                        <% if (motorista.getFoto1() != null) { %>
                            <div class="col-md-3 mb-3">
                                <img src="<%= request.getContextPath() + "/" + motorista.getFoto1() %>" class="img-thumbnail" alt="Foto 1" data-bs-toggle="modal" data-bs-target="#fotoModal1">
                            </div>
                        <% } %>
                        <% if (motorista.getFoto2() != null) { %>
                            <div class="col-md-3 mb-3">
                                <img src="<%= request.getContextPath() + "/" + motorista.getFoto2() %>" class="img-thumbnail" alt="Foto 2" data-bs-toggle="modal" data-bs-target="#fotoModal2">
                            </div>
                        <% } %>
                        <% if (motorista.getFoto3() != null) { %>
                            <div class="col-md-3 mb-3">
                                <img src="<%= request.getContextPath() + "/" + motorista.getFoto3() %>" class="img-thumbnail" alt="Foto 3" data-bs-toggle="modal" data-bs-target="#fotoModal3">
                            </div>
                        <% } %>
                        <% if (motorista.getFoto4() != null) { %>
                            <div class="col-md-3 mb-3">
                                <img src="<%= request.getContextPath() + "/" + motorista.getFoto4() %>" class="img-thumbnail" alt="Foto 4" data-bs-toggle="modal" data-bs-target="#fotoModal4">
                            </div>
                        <% } %>
                    </div>

                    <h2 class="mt-4">Ações</h2>
                    <form action="ApagarMotoristaServlet" method="post" class="d-inline">
                        <input type="hidden" name="id" value="<%= motorista.getId() %>">
                        <button type="submit" class="btn btn-danger">Apagar Motorista</button>
                    </form>
                    <form action="BloquearMotoristaServlet" method="post" class="d-inline">
                        <input type="hidden" name="id" value="<%= motorista.getId() %>">
                        <button type="submit" class="btn btn-warning">
                            <%= "bloqueado".equals(motorista.getDisponibilidade()) ? "Desbloquear Motorista" : "Bloquear Motorista" %>
                        </button>
                    </form>
                </div>
            </div>

            <!-- Modais para exibir fotos ampliadas -->
            <% if (motorista.getFoto1() != null) { %>
                <div class="modal fade" id="fotoModal1" tabindex="-1" aria-labelledby="fotoModalLabel1" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-body">
                                <img src="<%= request.getContextPath() + "/" + motorista.getFoto1() %>" class="img-fluid" alt="Foto 1">
                            </div>
                        </div>
                    </div>
                </div>
            <% } %>
            <% if (motorista.getFoto2() != null) { %>
                <div class="modal fade" id="fotoModal2" tabindex="-1" aria-labelledby="fotoModalLabel2" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-body">
                                <img src="<%= request.getContextPath() + "/" + motorista.getFoto2() %>" class="img-fluid" alt="Foto 2">
                            </div>
                        </div>
                    </div>
                </div>
            <% } %>
            <% if (motorista.getFoto3() != null) { %>
                <div class="modal fade" id="fotoModal3" tabindex="-1" aria-labelledby="fotoModalLabel3" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-body">
                                <img src="<%= request.getContextPath() + "/" + motorista.getFoto3() %>" class="img-fluid" alt="Foto 3">
                            </div>
                        </div>
                    </div>
                </div>
            <% } %>
            <% if (motorista.getFoto4() != null) { %>
                <div class="modal fade" id="fotoModal4" tabindex="-1" aria-labelledby="fotoModalLabel4" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-body">
                                <img src="<%= request.getContextPath() + "/" + motorista.getFoto4() %>" class="img-fluid" alt="Foto 4">
                            </div>
                        </div>
                    </div>
                </div>
            <% } %>
        <% }
           em.close();
        %>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
