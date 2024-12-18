<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contato - Taxi Service</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-warning p-3">
        <div class="container-fluid">
            <a class="navbar-brand" href="index.jsp">
                <img src="img/Vetor.png" alt="Taxi Logo" width="90">
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="index.jsp">In�cio</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="sobre.jsp">Sobre</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="servico.jsp">Servi�os</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="#">Contato</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Contato Section -->
    <section class="py-5 bg-light">
        <div class="container text-center">
            <h1>Fale Conosco</h1>
            <p class="lead">Entre em contato conosco para mais informa��es ou para agendar um servi�o.</p>
        </div>
    </section>

    <!-- Formul�rio de Contato -->
    <section class="py-5">
        <div class="container">
            <form action="ContatoServlet" method="post">
                <div class="mb-3">
                    <label for="name" class="form-label">Nome Completo</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="Seu nome" required>
                </div>
                <div class="mb-3">
                    <label for="phone" class="form-label">Telefone</label>
                    <input type="tel" class="form-control" id="phone" name="phone" placeholder="Seu n�mero de telefone" required>
                </div>
                <div class="mb-3">
                    <label for="message" class="form-label">Mensagem</label>
                    <textarea class="form-control" id="message" name="message" rows="4" placeholder="Sua mensagem" required></textarea>
                </div>
                <button type="submit" class="btn btn-warning">Enviar</button>
            </form>
        </div>
    </section>

    <!-- Footer -->
    <footer class="bg-warning text-center py-4">
        <p>&copy; 2024 Taxi Service. Todos os direitos reservados.</p>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
