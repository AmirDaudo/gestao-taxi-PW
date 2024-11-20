package com.gestaorotas.servlet;

import com.gestaorotas.JpaUtil;
import com.gestaorotas.model.Usuarios;
import com.gestaorotas.model.Motoristas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteAccountServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = JpaUtil.getEntityManagerFactory();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verificar se o usuário ou motorista está logado
        HttpSession session = request.getSession(false);
        if (session == null || (session.getAttribute("usuario") == null && session.getAttribute("motorista") == null)) {
            response.sendRedirect("index.jsp");
            return;
        }

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Se o usuário está logado
            Usuarios usuario = (Usuarios) session.getAttribute("usuario");
            if (usuario != null) {
                Usuarios usuarioToDelete = em.find(Usuarios.class, usuario.getId());
                if (usuarioToDelete != null) {
                    em.remove(usuarioToDelete);
                    em.getTransaction().commit();
                    // Invalidar a sessão após deletar o usuário
                    session.invalidate();
                    response.sendRedirect("index.jsp");
                    return;
                } else {
                    em.getTransaction().rollback();
                    response.getWriter().println("Erro: Usuário não encontrado.");
                    return;
                }
            }

            // Se o motorista está logado
            Motoristas motorista = (Motoristas) session.getAttribute("motorista");
            if (motorista != null) {
                Motoristas motoristaToDelete = em.find(Motoristas.class, motorista.getId());
                if (motoristaToDelete != null) {
                    em.remove(motoristaToDelete);
                    em.getTransaction().commit();
                    // Invalidar a sessão após deletar o motorista
                    session.invalidate();
                    response.sendRedirect("index.jsp");
                } else {
                    em.getTransaction().rollback();
                    response.getWriter().println("Erro: Motorista não encontrado.");
                }
            }

        } catch (Exception e) {
            em.getTransaction().rollback();
            response.getWriter().println("Erro ao deletar a conta: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        emf.close();
    }
}
