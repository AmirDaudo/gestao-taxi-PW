<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <!-- Metadados e links de CSS -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Taxi Service - Página Inicial</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome Icons -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <!-- Estilo Personalizado -->
    <link rel="stylesheet" href="css/style-home.css">
</head>
<body>
    <!-- Navbar Fixa -->
    <nav class="navbar navbar-expand-lg navbar-light bg-warning p-3 sticky-top">
        <div class="container-fluid">
            <a class="navbar-brand" href="index.jsp">
                <img src="img/Vetor.png" alt="Taxi Logo" width="90">
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" 
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
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
                        <a class="nav-link" href="servico_1.jsp">Serviços</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="contacto.jsp">Contato</a>
                    </li>
                    <li class="nav-item">
                        <!-- Botão para abrir o modal de login -->
                        <button type="button" class="btn btn-light" data-bs-toggle="modal" data-bs-target="#registerModal">
                            <i class="fas fa-sign-in-alt"></i> cadastre-se
                        </button>
                    </li>
                    <button type="button" class="btn btn-light" data-bs-toggle="modal" data-bs-target="#driverModal">
                        <i class="fas fa-user-plus"></i> Cadastrar Motorista
                    </button>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Hero Section -->
    <section class="bg-dark text-light text-center py-5" style="background-image: url('img/taxi-hero.jpg'); background-size: cover; background-position: center;">
        <div class="container">
            <h1 class="display-4">Táxi 24 Horas, Segurança e Conforto</h1>
            <p class="lead">Peça um táxi a qualquer hora, em qualquer lugar.</p>
            <!-- Exibir mensagens de erro -->
        <%
            String error = request.getParameter("error");
            if (error != null) {
        %>
            <div class="alert alert-danger">
                <%= error %>
            </div>
        <%
            }
        %>
            <a href="#" class="btn btn-warning btn-lg" data-bs-toggle="modal" data-bs-target="#loginModal"><i class="fas fa-taxi"></i> Fazer Login</a>
        </div>
    </section>

    <!-- Services Section -->
    <section class="py-5">
        <div class="container text-center">
            <h2 class="mb-5">Nossos Serviços</h2>
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

    <!-- Testimonials Section -->
    <section class="bg-light py-5">
        <div class="container text-center">
            <h2 class="mb-5">Comentários</h2>
            <div class="row">
                <div class="col-md-4">
                    <div class="card border-0">
                        <div class="card-body">
                            <i class="fas fa-quote-left fa-2x mb-3"></i>
                            <p class="card-text">"Melhor serviço de táxi que já usei! Muito rápido e seguro."</p>
                            <h6 class="card-subtitle mb-2 text-muted">- Amir Daudo</h6>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card border-0">
                        <div class="card-body">
                            <i class="fas fa-quote-left fa-2x mb-3"></i>
                            <p class="card-text">"Sempre que preciso de um táxi, confio nesta empresa. Muito profissional."</p>
                            <h6 class="card-subtitle mb-2 text-muted">- Almir Almorim</h6>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card border-0">
                        <div class="card-body">
                            <i class="fas fa-quote-left fa-2x mb-3"></i>
                            <p class="card-text">"Os motoristas são muito educados e os carros sempre estão em ótimas condições."</p>
                            <h6 class="card-subtitle mb-2 text-muted">- Quimba Maera</h6>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Modal de Login -->
<div class="modal fade" id="loginModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="loginModalLabel">Login</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Exibição de mensagens de erro ou sucesso -->
                <c:if test="${not empty param.error}">
                    <div style="color:red;">${param.error}</div>
                </c:if>
                <c:if test="${not empty param.success}">
                    <div style="color:green;">${param.success}</div>
                </c:if>

                <form id="loginForm" action="LoginServlet" method="post">
                    <div class="mb-3">
                        <label for="phoneLogin" class="form-label">Número de Telefone</label>
                        <div class="input-group">
                            <span class="input-group-text">+258</span>
                            <input type="tel" class="form-control" id="numero" placeholder="Número de telefone" name="numero" required>
                        </div>
                        <div class="error" id="phoneLoginError" style="display: none;">Por favor, insira apenas números.</div>
                    </div>
                    <div class="mb-3">
                        <label for="passwordLogin" class="form-label">Senha</label>
                        <input type="password" class="form-control" id="passwordLogin" placeholder="Digite sua senha" name="senha" required>
                    </div>
                    <button type="submit" class="btn btn-warning w-100">Entrar</button>
                </form>

                <div class="mt-3 text-center">
                    <p><a href="recuperar_senha.jsp" class="link-primary">Esqueci minha senha</a></p>
                </div>

                <!-- Seção para login com redes sociais -->
                <div class="text-center mt-4">
                    <p>Ou faça login com:</p>
                    <button class="btn btn-danger w-100 mb-2"><i class="fab fa-google"></i> Entrar com Google</button>
                    <button class="btn btn-dark w-100"><i class="fab fa-apple"></i> Entrar com iCloud</button>
                </div>

                <div class="mt-3 text-center">
                    <p>Não tem uma conta? <a href="#" data-bs-toggle="modal" data-bs-target="#registerModal" data-bs-dismiss="modal">Cadastre-se</a></p>
                </div>
            </div>
        </div>
    </div>
</div>

   <!-- Modal de Cadastro -->
<div class="modal fade" id="registerModal" tabindex="-1" aria-labelledby="registerModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="registerModalLabel">Cadastro</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="registerForm" action="CadastroUser" method="post">
                    <div class="mb-3">
                        <label for="usernameRegister" class="form-label">Nome de Usuário</label>
                        <input type="text" class="form-control" name="nome" id="usernameRegister" placeholder="Username" required>
                    </div>
                    <div class="mb-3">
                        <label for="emailRegister" class="form-label">E-mail</label>
                        <input type="email" class="form-control" id="emailRegister" placeholder="seuemail@gmail.com" required name="email">
                    </div>
                    <div class="mb-3">
                        <label for="phoneRegister" class="form-label">Número de Celular</label>
                        <div class="input-group">
                            <span class="input-group-text">+258</span>
                            <input type="tel" class="form-control" name="numero" id="phoneRegister" placeholder="Número de celular" required>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="passwordRegister" class="form-label">Senha</label>
                        <input type="password" class="form-control" id="passwordRegister" placeholder="Digite sua senha" required name="senha">
                    </div>
                    <div class="mb-3">
                        <label for="confirmPasswordRegister" class="form-label">Confirme sua Senha</label>
                        <input type="password" class="form-control" id="confirmPasswordRegister" placeholder="Confirme sua senha" required>
                    </div>
                    <!-- Exibição de mensagem de erro -->
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger" role="alert">${errorMessage}</div>
                    </c:if>
                     <!-- Seção para cadastro com redes sociais -->
                <div class="text-center mt-4">
                    <p>Ou cadastre-se com:</p>
                    <button class="btn btn-danger w-100 mb-2"><i class="fab fa-google"></i> Cadastre-se com Google</button>
                    <button class="btn btn-dark w-100"><i class="fab fa-apple"></i> Cadastre-se com iCloud</button>
                </div>
                    <button type="submit" class="btn btn-warning w-100">Cadastrar</button>
                </form>
            </div>
        </div>
    </div>
</div>

   
<!-- Modal de Cadastro do Motorista -->
<div class="modal fade" id="driverModal" tabindex="-1" aria-labelledby="driverModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="driverModalLabel">Cadastro de Motorista</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                    <!-- Exibição de mensagens de erro ou sucesso -->
                    <c:if test="${not empty param.error}">
                         <div style="color:red;">${param.error}</div>
                      </c:if>
                    <c:if test="${not empty param.success}">
                          <div style="color:green;">${param.success}</div>
                      </c:if>

                <form id="driverForm" action="CadastroDriver" method="post" enctype="multipart/form-data">
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
                        <div class="error" id="driverPhoneError" style="display: none;">Por favor, insira apenas números.</div>
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
                        <div class="error" id="driverPasswordMatchError" style="display: none;">As senhas não coincidem.</div>
                    </div>
                   
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
                    <!-- Disponibilidade (Select Dropdown) -->
                    <div class="mb-3">
                        <label for="driverAvailability" class="form-label">Disponibilidade</label>
                        <select class="form-select" id="driverAvailability" name="disponibilidade" required>
                            <option value="">Selecione...</option>
                            <option value="24h">24h</option>
                            <option value="12h">12h</option>
                        </select>
                    </div>
                    <!-- Termos e Condições -->
                    <div class="form-group mb-3">
                        <input type="checkbox" id="termos" name="termos" required>
                        <label for="termos">
                            Eu li e concordo com os <a href="#" data-bs-toggle="modal" data-bs-target="#termosModal">Termos e Condições</a>
                        </label>
                    </div>
                    <!-- Botão de Cadastro -->
                    <button type="submit" class="btn btn-warning w-100">Cadastrar Motorista</button>
                </form>
            </div>
        </div>
    </div>
</div>
      
<!-- Modal de Termos e Condições -->
<div class="modal fade" id="termosModal" tabindex="-1" aria-labelledby="termosModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="termosModalLabel">Termos e Condições</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <h1>Termos e Condições</h1>
                
                <h3>Termos e Condições para Motoristas</h3>

                <b>1. Aceitação dos Termos</b>
                <p>Ao se cadastrar como motorista na plataforma de Gestão de Táxi, você concorda com os seguintes Termos e Condições. Se você não concordar com algum dos termos, não poderá utilizar nossos serviços.</p>
                
                <b>2. Cadastro e Elegibilidade</b>
                <ul>
                    <li><b>2.1. Informações Verdadeiras:</b> Você deve fornecer informações verdadeiras, exatas e completas durante o processo de cadastro.</li>
                    <li><b>2.2. Documentação:</b> Você deve fornecer cópias válidas da sua carteira de motorista, documentação do veículo e outras licenças relevantes.</li>
                    <li><b>2.3. Idade Mínima:</b> Você deve ter pelo menos 21 anos de idade para se cadastrar como motorista.</li>
                </ul>

                <b>3. Uso da Plataforma</b>
                <ul>
                    <li><b>3.1. Conformidade com as Leis:</b> Você deve cumprir todas as leis e regulamentos aplicáveis enquanto usa a plataforma.</li>
                    <li><b>3.2. Condição do Veículo:</b> Seu veículo deve estar em boas condições de funcionamento e passar por todas as inspeções necessárias.</li>
                    <li><b>3.3. Comportamento do Motorista:</b> Espera-se que você mantenha um comportamento profissional e cortês com os passageiros.</li>
                </ul>

                <b>4. Pagamentos e Taxas</b>
                <ul>
                    <li><b>4.1. Comissões:</b> A plataforma pode cobrar uma comissão sobre as corridas realizadas. O valor da comissão será especificado separadamente.</li>
                    <li><b>4.2. Métodos de Pagamento:</b> Os pagamentos pelas corridas serão processados através dos métodos de pagamento aceitos pela plataforma.</li>
                </ul>

                <b>5. Rescisão e Suspensão</b>
                <ul>
                    <li><b>5.1. Rescisão pelo Motorista:</b> Você pode encerrar seu cadastro a qualquer momento, notificando a plataforma.</li>
                    <li><b>5.2. Suspensão ou Rescisão pela Plataforma:</b> A plataforma reserva-se o direito de suspender ou encerrar seu cadastro se você violar os termos e condições ou qualquer lei aplicável.</li>
                </ul>

                <b>6. Responsabilidade e Indenização</b>
                <ul>
                    <li><b>6.1. Limitação de Responsabilidade:</b> A plataforma não será responsável por danos diretos, indiretos, incidentais, especiais ou consequentes resultantes do uso da plataforma.</li>
                    <li><b>6.2. Indenização:</b> Você concorda em indenizar e isentar a plataforma de quaisquer reivindicações, danos, responsabilidades, perdas ou despesas (incluindo honorários advocatícios) decorrentes de sua violação destes Termos e Condições.</li>
                </ul>

                <b>7. Privacidade</b>
                <p>Seus dados pessoais serão tratados de acordo com nossa política de privacidade, que pode ser acessada em <a href="#">[link para a política de privacidade]</a>.</p>

                <b>8. Alterações nos Termos</b>
                <p>A plataforma reserva-se o direito de modificar estes Termos e Condições a qualquer momento. As alterações entrarão em vigor assim que publicadas na plataforma.</p>

                <b>9. Contato</b>
                <p>Se você tiver dúvidas sobre estes Termos e Condições, entre em contato conosco através de <a href="mailto:amomade2@unilurio.ac.mz">amomade2@unilurio.ac.mz</a>.</p>
        </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
            </div>
        </div>
    </div>
</div>

    <!-- Footer Section -->
    <footer class="bg-warning text-center py-4">
        <div class="container">
            <p class="mb-0">&copy; 2024 Taxi Service. Todos os direitos reservados.</p>
            <ul class="list-inline mt-3">
                <li class="list-inline-item"><a href="#" class="text-dark"><i class="fab fa-facebook-f"></i> Facebook</a></li>
                <li class="list-inline-item"><a href="#" class="text-dark"><i class="fab fa-instagram"></i> Instagram</a></li>
                <li class="list-inline-item"><a href="#" class="text-dark"><i class="fab fa-twitter"></i> Twitter</a></li>
            </ul>
        </div>
    </footer>

     <!-- Bootstrap 5 JS and Font Awesome JS -->
     <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
     <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/js/all.min.js"></script>
     <script src="js/main.js"></script>
     
     <script>
document.getElementById('registerForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Evita o envio padrão do formulário

</script>

<!-- Incluir JavaScript do Bootstrap --> <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">
        
</script> 
<script type="text/javascript">
        window.onload = function() {
            const params = new URLSearchParams(window.location.search); 
            if (params.has('error')) {
                document.getElementById('errorMessage').innerText = decodeURIComponent(params.get('error'));
                var myModal = new bootstrap.Modal(document.getElementById('errorModal'), { keyboard: false });
                myModal.show(); } }; function redirectToIndex() { window.location.href = "index.jsp"; } </script>

<script>
    // Reabre o modal automaticamente se houver um erro no registro
    <% if (request.getAttribute("showRegisterModal") != null && (boolean) request.getAttribute("showRegisterModal")) { %>
        var registerModal = new bootstrap.Modal(document.getElementById('registerModal'), {});
        registerModal.show();
    <% } %>
</script>

</body>
</html>