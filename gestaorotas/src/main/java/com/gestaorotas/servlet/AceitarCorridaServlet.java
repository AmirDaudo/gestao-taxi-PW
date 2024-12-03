package com.gestaorotas.servlet;

import com.gestaorotas.JpaUtil;
import com.gestaorotas.model.Corridas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AceitarCorridaServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = JpaUtil.getEntityManagerFactory();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String corridaIdStr = request.getParameter("corridaId");

        if (corridaIdStr == null || corridaIdStr.trim().isEmpty()) {
            response.sendRedirect("motorista.jsp");
            return;
        }

        int corridaId;
        try {
            corridaId = Integer.parseInt(corridaIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("motorista.jsp");
            return;
        }

        EntityManager em = emf.createEntityManager();
        try {
            Corridas corrida = em.find(Corridas.class, corridaId);

            if (corrida != null && "solicitada".equals(corrida.getStatus())) {
                em.getTransaction().begin();
                corrida.setStatus("aceita");
                em.getTransaction().commit();
                request.setAttribute("message", "Corrida aceita com sucesso.");
            } else {
                request.setAttribute("message", "Corrida não encontrada ou já aceita.");
            }
        } finally {
            em.close();
        }

        request.getRequestDispatcher("motorista.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
