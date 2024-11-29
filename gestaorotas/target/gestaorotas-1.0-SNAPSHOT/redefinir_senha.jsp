<%-- 
    Document   : redefinir_senha
    Created on : 23/11/2024, 23:53:11
    Author     : asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <title>Redefinir Senha</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1>Redefinir Senha</h1>
        <form action="RedefinirSenhaServlet" method="post">
            <input type="hidden" name="token" value="<%= request.getParameter("token") %>">
            <div class="form-group">
                <label for="novaSenha">Nova Senha:</label>
                <input type="password" class="form-control" id="novaSenha" name="novaSenha" required>
            </div>
            <button type="submit" class="btn btn-primary">Redefinir Senha</button>
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
