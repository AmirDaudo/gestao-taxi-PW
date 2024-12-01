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

public class ApagarMotoristaServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = JpaUtil.getEntityManagerFactory();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String motoristaIdStr = request.getParameter("id");
        if (motoristaIdStr == null || motoristaIdStr.trim().isEmpty()) {
            request.setAttribute("message", "ID do motorista não pode estar vazio.");
            request.setAttribute("status", "error");
            request.getRequestDispatcher("administrador.jsp").forward(request, response);
            return;
        }

        int motoristaId;
        try {
            motoristaId = Integer.parseInt(motoristaIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("message", "ID do motorista inválido.");
            request.setAttribute("status", "error");
            request.getRequestDispatcher("administrador.jsp").forward(request, response);
            return;
        }

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Motoristas motorista = em.find(Motoristas.class, motoristaId);
            if (motorista != null) {
                em.remove(motorista);
                em.getTransaction().commit();
                request.setAttribute("message", "Motorista apagado com sucesso.");
                request.setAttribute("status", "success");
            } else {
                request.setAttribute("message", "Motorista não encontrado.");
                request.setAttribute("status", "error");
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            request.setAttribute("message", "Erro ao apagar o motorista: " + e.getMessage());
            request.setAttribute("status", "error");
        } finally {
            em.close();
        }

        request.getRequestDispatcher("administrador.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
