package com.gestaorotas;

import com.gestaorotas.model.Usuarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Author: asus
 */
public class CadastroUser extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CadastroUser.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().write("Método GET não suportado para cadastro de usuários.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        int telefone = Integer.parseInt(request.getParameter("numero"));
        String senha = request.getParameter("senha");

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            // Verificar se o email já está em uso
            TypedQuery<Usuarios> queryEmail = em.createQuery("SELECT u FROM Usuarios u WHERE u.email = :email", Usuarios.class);
            queryEmail.setParameter("email", email);
            if (!queryEmail.getResultList().isEmpty()) {
                String errorMessage = URLEncoder.encode("Email já está em uso. Por favor, use outro email.", StandardCharsets.UTF_8);
                response.sendRedirect("index.jsp?error=" + errorMessage);
                return;
            }

            // Verificar se o telefone já está em uso
            TypedQuery<Usuarios> queryTelefone = em.createQuery("SELECT u FROM Usuarios u WHERE u.telefone = :telefone", Usuarios.class);
            queryTelefone.setParameter("telefone", telefone);
            if (!queryTelefone.getResultList().isEmpty()) {
                String errorMessage = URLEncoder.encode("Telefone já está em uso. Por favor, use outro telefone.", StandardCharsets.UTF_8);
                response.sendRedirect("index.jsp?error=" + errorMessage);
                return;
            }

            // Criação da instância de `Usuarios`
            Usuarios usuarios = new Usuarios(nome, email, telefone, senha);

            // Persistir os dados no banco de dados
            em.getTransaction().begin();
            em.persist(usuarios);
            em.getTransaction().commit();
            response.sendRedirect("utilizador.jsp");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao cadastrar usuário", e);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h1>Erro ao cadastrar usuário</h1>");
            out.println("<p>" + e.getMessage() + "</p>");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private String criptografarSenha(String senha) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(senha.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    @Override
    public String getServletInfo() {
        return "Servlet de cadastro de usuários";
    }
}
