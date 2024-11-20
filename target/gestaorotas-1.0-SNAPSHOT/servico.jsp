<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Serviços - Taxi Service</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-warning p-3">
        <div class="container-fluid">
            <a class="navbar-brand" href="index.jsp">
                <img src="Vetor.png" alt="Taxi Logo" width="90">
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="index.jsp">Início</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="sobre.jsp">Sobre</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="servico.jsp">Serviços</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="contacto.jsp">Contato</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Serviços Section -->
    <section class="py-5 bg-light">
        <div class="container text-center">
            <h1>Nossos Serviços</h1>
            <p class="lead">Oferecemos uma gama de serviços de transporte para atender todas as suas necessidades.</p>
        </div>
    </section>

    <section class="py-5">
        <div class="container text-center">
            <div class="row">
                <div class="col-md-4">
                    <div class="card border-0 shadow">
                        <div class="card-body">
                            <i class="fas fa-car fa-3x mb-3 text-warning"></i>
                            <h5 class="card-title">Táxi Rápido</h5>
                            <p class="card-text">Chegue ao seu destino rapidamente com nossos motoristas experientes.</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card border-0 shadow">
                        <div class="card-body">
                            <i class="fas fa-couch fa-3x mb-3 text-warning"></i>
                            <h5 class="card-title">Táxi Confortável</h5>
                            <p class="card-text">Desfrute de conforto e segurança nos nossos veículos bem equipados.</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card border-0 shadow">
                        <div class="card-body">
                            <i class="fas fa-user-tie fa-3x mb-3 text-warning"></i>
                            <h5 class="card-title">Motoristas Profissionais</h5>
                            <p class="card-text">Nossos motoristas são profissionais e capacitados para lhe atender.</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Footer -->
    <footer class="bg-warning text-center py-4">
        <p>&copy; 2024 Taxi Service. Todos os direitos reservados.</p>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
