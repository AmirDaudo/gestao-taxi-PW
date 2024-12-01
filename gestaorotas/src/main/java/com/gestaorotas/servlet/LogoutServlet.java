package com.gestaorotas.servlet;

import com.gestaorotas.model.Motoristas;
import com.gestaorotas.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Motoristas motorista = (Motoristas) session.getAttribute("motorista");

        if (motorista != null) {
            EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
            try {
                em.getTransaction().begin();
                motorista.setStatus("offline");
                em.merge(motorista);
                em.getTransaction().commit();
            } finally {
                em.close();
            }

            session.invalidate();  // Invalida a sessão do usuário
        }

        response.sendRedirect("index.jsp");  // Redireciona para a página de login
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);  // Trate as requisições GET da mesma forma que as POST
    }
}
