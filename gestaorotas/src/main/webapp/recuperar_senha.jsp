<%-- 
    Document   : recuperar_senha
    Created on : 23/11/2024, 23:45:15
    Author     : asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <title>Recuperar Senha</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1>Recuperar Senha</h1>
        <form action="RecuperarSenhaServlet" method="post">
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" class="form-control" id="email" name="email" required>
            </div>
            <button type="submit" class="btn btn-primary">Enviar Link de Redefinição</button>
        </form>
        <div>
            <c:if test="${not empty param.message}">
                <div class="alert alert-success mt-3">${param.message}</div>
            </c:if>
            <c:if test="${not empty param.error}">
                <div class="alert alert-danger mt-3">${param.error}</div>
            </c:if>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
