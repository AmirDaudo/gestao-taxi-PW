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
