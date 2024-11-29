package com.gestaorotas;

import com.gestaorotas.model.Motoristas;
import com.gestaorotas.model.MotoristasJpaController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@MultipartConfig
public class CadastroDriver extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CadastroDriver.class.getName());
    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = Persistence.createEntityManagerFactory("my_persistence_unit");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verificar se o checkbox dos termos foi marcado
        String termos = request.getParameter("termos");
        if (termos == null) {
            response.sendRedirect("index.jsp?error=Você deve aceitar os Termos e Condições para se cadastrar.");
            return;
        }

        // Obter os dados do formulário
        String nome = request.getParameter("nome");
        String telefone = request.getParameter("telefone");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String marcaCarro = request.getParameter("marca_carro");
        String modeloCarro = request.getParameter("modelo_carro");
        String matricula = request.getParameter("matricula");
        String disponibilidade = request.getParameter("disponibilidade");

        // Criptografar a senha
        String senhaCriptografada = criptografarSenha(senha);

        // Processar e obter os bytes das fotos do carro
        List<Part> fotos = request.getParts().stream()
                .filter(part -> "fotos_carro".equals(part.getName()))
                .collect(Collectors.toList());

        byte[][] fotoBytes = new byte[4][];
        for (int i = 0; i < fotos.size() && i < 4; i++) {
            try (InputStream inputStream = fotos.get(i).getInputStream()) {
                fotoBytes[i] = inputStream.readAllBytes();
            }
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
        motorista.setFoto1(fotoBytes.length > 0 ? fotoBytes[0] : null);
        motorista.setFoto2(fotoBytes.length > 1 ? fotoBytes[1] : null);
        motorista.setFoto3(fotoBytes.length > 2 ? fotoBytes[2] : null);
        motorista.setFoto4(fotoBytes.length > 3 ? fotoBytes[3] : null);

        // Persistir os dados no banco de dados
        MotoristasJpaController motoristasController = new MotoristasJpaController(emf);
        try {
            motoristasController.create(motorista);
            logger.log(Level.INFO, "Motorista cadastrado com sucesso: {0}", motorista);

            // Criar sessão para o motorista e iniciar sessão
            HttpSession session = request.getSession(true); // Cria uma nova sessão se não existir
            session.setAttribute("motorista", motorista);
            session.setAttribute("status", "online");
            logger.log(Level.INFO, "Sessão iniciada para o motorista: {0}", motorista.getNome());

            // Redirecionar para a página do motorista
            response.sendRedirect("motorista.jsp?success=Cadastro realizado com sucesso.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao cadastrar motorista", e);
            response.sendRedirect("cadastro_driver.jsp?error=Erro ao cadastrar: " + e.getMessage());
        } finally {
            emf.close();
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
