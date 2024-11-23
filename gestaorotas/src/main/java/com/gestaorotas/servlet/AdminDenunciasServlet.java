package com.gestaorotas.servlet;

import com.gestaorotas.JpaUtil;
import com.gestaorotas.model.Denuncias;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class AdminDenunciasServlet extends HttpServlet {
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
            List<Denuncias> denuncias = em.createQuery("SELECT d FROM Denuncias d", Denuncias.class).getResultList();
            request.setAttribute("denuncias", denuncias);
            getServletContext().getRequestDispatcher("/administrador.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String action = request.getParameter("action");
        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            EntityManager em = emf.createEntityManager();
            try {
                em.getTransaction().begin();
                Denuncias denuncia = em.find(Denuncias.class, id);
                if (denuncia != null) {
                    if ("apagar".equals(action)) {
                        em.remove(denuncia);
                    } else if ("aprovar".equals(action)) {
                        denuncia.setStatus("aprovada");
                        em.merge(denuncia);
                    } else if ("rejeitar".equals(action)) {
                        denuncia.setStatus("rejeitada");
                        em.merge(denuncia);
                    }
                    em.getTransaction().commit();
                }
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ServletException("Erro ao processar denúncia", e);
            } finally {
                em.close();
            }
        }
        response.sendRedirect(request.getContextPath() + "/AdminDenunciasServlet");
    }

    @Override
    public void destroy() {
        emf.close();
    }
}
