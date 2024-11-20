package com.gestaorotas;

import com.gestaorotas.model.Usuarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AlterarSenhaServlet extends HttpServlet {
    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = JpaUtil.getEntityManagerFactory();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmNewPassword = request.getParameter("confirmNewPassword");

        if (!usuario.getSenha().equals(currentPassword)) {
            response.getWriter().println("Senha atual incorreta.");
            return;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            response.getWriter().println("A nova senha e a confirmação não coincidem.");
            return;
        }

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Usuarios usuarioAtualizado = em.find(Usuarios.class, usuario.getId());
        usuarioAtualizado.setSenha(newPassword);
        em.getTransaction().commit();
        em.close();

        usuario.setSenha(newPassword);

        response.getWriter().println("Senha alterada com sucesso!");
    }

    @Override
    public void destroy() {
        emf.close();
    }
}
