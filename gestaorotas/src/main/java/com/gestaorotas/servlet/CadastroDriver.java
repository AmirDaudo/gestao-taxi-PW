package com.gestaorotas.servlet;

import com.gestaorotas.MotoristasJpaController;
import com.gestaorotas.model.Motoristas;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@MultipartConfig
public class CadastroDriver extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = Persistence.createEntityManagerFactory("my_persistence_unit");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Configurar encoding para UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Obter os dados do formulário
        String nome = request.getParameter("nome");
        String telefone = request.getParameter("telefone");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String confirmarSenha = request.getParameter("confirmar_senha");
        String marcaCarro = request.getParameter("marca_carro");
        String modeloCarro = request.getParameter("modelo_carro");
        String matricula = request.getParameter("matricula");
        String disponibilidade = request.getParameter("disponibilidade");

        // Verificar se as senhas coincidem
        if (!senha.equals(confirmarSenha)) {
            response.sendRedirect("index.jsp?error=As senhas não coincidem.");
            return;
        }

        // Criptografar a senha
        String senhaCriptografada = criptografarSenha(senha);

        // Diretório de upload
        String uploadDir = getServletContext().getRealPath("/") + "uploads";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        // Processar e salvar as fotos do carro
        List<Part> fotos = request.getParts().stream()
                .filter(part -> "fotos_carro".equals(part.getName()))
                .collect(Collectors.toList());

        String[] fotoPaths = new String[4];
        for (int i = 0; i < fotos.size() && i < 4; i++) {
            Part foto = fotos.get(i);
            String fileName = Paths.get(foto.getSubmittedFileName()).getFileName().toString();
            fileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_"); // Sanitizar o nome do arquivo
            String filePath = uploadDir + File.separator + fileName;
            File file = new File(filePath);
            try (InputStream inputStream = foto.getInputStream()) {
                Files.copy(inputStream, file.toPath());
            }
            fotoPaths[i] = "uploads/" + fileName;
        }

        // Criar uma instância de Motoristas
        Motoristas motorista = new Motoristas();
        motorista.setNome(nome);
        motorista.setTelefone(telefone);
        motorista.setEmail(email);
        motorista.setSenha(senhaCriptografada);
        motorista.setMarcaCarro(marcaCarro);
        motorista.setModeloCarro(modeloCarro);
        motorista.setMatricula(matricula);
        motorista.setDisponibilidade(disponibilidade);
        motorista.setFoto1(fotoPaths.length > 0 ? fotoPaths[0] : null);
        motorista.setFoto2(fotoPaths.length > 1 ? fotoPaths[1] : null);
        motorista.setFoto3(fotoPaths.length > 2 ? fotoPaths[2] : null);
        motorista.setFoto4(fotoPaths.length > 3 ? fotoPaths[3] : null);
        motorista.setStatus("offline");
        motorista.setBloqueado(false);

        // Persistir os dados no banco de dados
        MotoristasJpaController motoristasController = new MotoristasJpaController(emf);
        try {
            motoristasController.create(motorista);

            // Criar sessão para o usuário
            HttpSession session = request.getSession();
            session.setAttribute("motorista", motorista);

            // Redirecionar para a página do motorista
            response.sendRedirect("motorista.jsp?success=Cadastro realizado com sucesso.");
        } catch (Exception e) {
            response.sendRedirect("cadastro_driver.jsp?error=Erro ao cadastrar: " + e.getMessage());
        }
    }

    private String criptografarSenha(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(senha.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao criptografar a senha", e);
        }
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
