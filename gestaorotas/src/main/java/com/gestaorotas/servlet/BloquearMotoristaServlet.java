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

public class BloquearMotoristaServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = JpaUtil.getEntityManagerFactory();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int motoristaId = Integer.parseInt(request.getParameter("id"));
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Motoristas motorista = em.find(Motoristas.class, motoristaId);
            if (motorista != null) {
                if ("bloqueado".equals(motorista.getDisponibilidade())) {
                    motorista.setDisponibilidade("online");
                } else {
                    motorista.setDisponibilidade("bloqueado");
                }
                em.merge(motorista);
                em.getTransaction().commit();
            }
            response.sendRedirect("MotoristasCadastradosServlet?success=Ação realizada com sucesso.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            response.sendRedirect("MotoristasCadastradosServlet?error=Erro ao realizar a ação.");
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
