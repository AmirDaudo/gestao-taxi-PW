package com.gestaorotas.servlet;

import com.gestaorotas.JpaUtil;
import com.gestaorotas.model.Usuarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class RecuperarSenhaServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = JpaUtil.getEntityManagerFactory();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");

        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Usuarios> query = em.createQuery("SELECT u FROM Usuarios u WHERE u.email = :email", Usuarios.class);
            query.setParameter("email", email);
            Usuarios usuario = query.getSingleResult();

            if (usuario != null) {
                String token = UUID.randomUUID().toString();
                usuario.setResetToken(token);
                em.getTransaction().begin();
                em.merge(usuario);
                em.getTransaction().commit();

                // Redirecionar para a página de redefinição de senha com o token
                response.sendRedirect("redefinir_senha.jsp?token=" + token);
            } else {
                response.sendRedirect("recuperar_senha.jsp?error=Email não encontrado.");
            }
        } catch (Exception e) {
            response.sendRedirect("recuperar_senha.jsp?error=Ocorreu um erro ao processar sua solicitação.");
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
