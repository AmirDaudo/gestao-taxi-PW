package com.gestaorotas.servlet;

import com.gestaorotas.JpaUtil;
import com.gestaorotas.model.Motoristas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MotoristasCadastradosServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = JpaUtil.getEntityManagerFactory();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = emf.createEntityManager();
        try {
            List<Motoristas> motoristasCadastrados = em.createNamedQuery("Motoristas.findAll", Motoristas.class).getResultList();
            List<Motoristas> motoristasOnline = em.createNamedQuery("Motoristas.findByStatus", Motoristas.class)
                                                  .setParameter("status", "online").getResultList();

            request.setAttribute("motoristasCadastrados", motoristasCadastrados);
            request.setAttribute("motoristasOnline", motoristasOnline);
            request.getRequestDispatcher("administrador.jsp").forward(request, response);
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
