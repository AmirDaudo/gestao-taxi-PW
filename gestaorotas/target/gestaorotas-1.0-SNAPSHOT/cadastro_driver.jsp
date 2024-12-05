<%-- 
    Document   : cadastro_driver
    Created on : 05/12/2024, 21:50:31
    Author     : asus
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Motorista</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1>Cadastro de Motorista</h1>

    <!-- Exibição de mensagens de erro ou sucesso -->
    <c:if test="${not empty param.error}">
        <div class="alert alert-danger">${param.error}</div>
    </c:if>
    <c:if test="${not empty param.success}">
        <div class="alert alert-success">${param.success}</div>
    </c:if>

    <!-- Formulário de Cadastro -->
    <form id="driverForm" action="CadastroDriver" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
        <!-- Nome do Motorista -->
        <div class="mb-3">
            <label for="driverName" class="form-label">Nome do Motorista</label>
            <input type="text" class="form-control" id="driverName" placeholder="Nome completo" name="nome" required>
        </div>
        <!-- Número de Telefone -->
        <div class="mb-3">
            <label for="driverPhone" class="form-label">Número de Telefone</label>
            <div class="input-group">
                <span class="input-group-text">+258</span>
                <input type="tel" class="form-control" id="driverPhone" placeholder="Número de telefone" name="telefone" required>
            </div>
        </div>
        <!-- E-mail -->
        <div class="mb-3">
            <label for="driverEmail" class="form-label">E-mail</label>
            <input type="email" class="form-control" id="driverEmail" placeholder="email@example.com" name="email" required>
        </div>
        <!-- Senha -->
        <div class="mb-3">
            <label for="driverPassword" class="form-label">Senha</label>
            <input type="password" class="form-control" id="driverPassword" placeholder="Senha" name="senha" required>
        </div>
        <!-- Confirmação de Senha -->
        <div class="mb-3">
            <label for="confirmDriverPassword" class="form-label">Confirmar Senha</label>
            <input type="password" class="form-control" id="confirmDriverPassword" placeholder="Confirmar Senha" name="confirmar_senha" required>
        </div>
        <!-- Fotos do Carro -->
        <div class="mb-3">
            <label for="carPhotos" class="form-label">Fotos do Carro (4 fotos)</label>
            <input type="file" class="form-control" id="carPhotos" name="fotos_carro" accept="image/*" multiple required>
            <small class="form-text text-muted">Carregue exatamente 4 fotos do carro.</small>
        </div>
        <!-- Marca do Carro -->
        <div class="mb-3">
            <label for="carBrand" class="form-label">Marca do Carro</label>
            <input type="text" class="form-control" id="carBrand" placeholder="Ex: Toyota" name="marca_carro" required>
        </div>
        <!-- Modelo do Carro -->
        <div class="mb-3">
            <label for="carModel" class="form-label">Modelo do Carro</label>
            <input type="text" class="form-control" id="carModel" placeholder="Ex: Corolla" name="modelo_carro" required>
        </div>
        <!-- Matrícula do Carro -->
        <div class="mb-3">
            <label for="carPlate" class="form-label">Matrícula</label>
            <input type="text" class="form-control" id="carPlate" placeholder="Ex: ABC-1234" name="matricula" required>
        </div>
        <!-- Disponibilidade -->
        <div class="mb-3">
            <label for="driverAvailability" class="form-label">Disponibilidade</label>
            <select class="form-select" id="driverAvailability" name="disponibilidade" required>
                <option value="">Selecione...</option>
                <option value="24h">24h</option>
                <option value="12h">12h</option>
            </select>
        </div>
        <!-- Botão de Cadastro -->
        <button type="submit" class="btn btn-warning w-100">Cadastrar Motorista</button>
    </form>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
