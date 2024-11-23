package com.gestaorotas;

import com.gestaorotas.model.Usuarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
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
        String telefoneStr = request.getParameter("numero");
        String senha = request.getParameter("senha");

        int telefone;
        try {
            telefone = Integer.parseInt(telefoneStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Número de telefone inválido.");
            request.setAttribute("showRegisterModal", true);
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // Criptografar a senha
        String senhaCriptografada;
        try {
            senhaCriptografada = criptografarSenha(senha);
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "Erro ao criptografar a senha", e);
            request.setAttribute("errorMessage", "Erro interno. Tente novamente mais tarde.");
            request.setAttribute("showRegisterModal", true);
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            // Verificar se o email já está em uso
            TypedQuery<Usuarios> queryEmail = em.createQuery("SELECT u FROM Usuarios u WHERE u.email = :email", Usuarios.class);
            queryEmail.setParameter("email", email);
            if (!queryEmail.getResultList().isEmpty()) {
                request.setAttribute("errorMessage", "Email já está em uso. Por favor, use outro email.");
                request.setAttribute("showRegisterModal", true);
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            // Verificar se o telefone já está em uso
            TypedQuery<Usuarios> queryTelefone = em.createQuery("SELECT u FROM Usuarios u WHERE u.telefone = :telefone", Usuarios.class);
            queryTelefone.setParameter("telefone", telefone);
            if (!queryTelefone.getResultList().isEmpty()) {
                request.setAttribute("errorMessage", "Telefone já está em uso. Por favor, use outro número.");
                request.setAttribute("showRegisterModal", true);
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            // Criar e salvar usuário com senha criptografada
            Usuarios usuario = new Usuarios(nome, email, telefone, senhaCriptografada);
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();

            // Criar sessão para o usuário cadastrado
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);

            // Encaminhar para a página com os dados do usuário
            response.sendRedirect("utilizador.jsp");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao cadastrar usuário", e);
            request.setAttribute("errorMessage", "Erro ao cadastrar usuário: " + e.getMessage());
            request.setAttribute("showRegisterModal", true);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Método para criptografar a senha usando SHA-256.
     *
     * @param senha A senha a ser criptografada
     * @return A senha criptografada
     * @throws NoSuchAlgorithmException Caso o algoritmo SHA-256 não seja encontrado
     */
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
        return "Servlet de cadastro de usuários com sessão";
    }
}
