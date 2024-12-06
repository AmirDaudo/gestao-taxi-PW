package com.gestaorotas.servlet;

import com.gestaorotas.JpaUtil;
import com.gestaorotas.model.Corridas;
import com.gestaorotas.model.Motoristas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("motorista") == null) {
            request.setAttribute("message", "Por favor, faça o login como motorista.");
            request.setAttribute("status", "error");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        Motoristas motorista = (Motoristas) session.getAttribute("motorista");
        int corridaId = Integer.parseInt(request.getParameter("corridaId"));

        EntityManager em = emf.createEntityManager();
        try {
            Corridas corrida = em.find(Corridas.class, corridaId);
            if (corrida == null) {
                request.setAttribute("message", "Corrida não encontrada.");
                request.setAttribute("status", "error");
                request.getRequestDispatcher("motorista.jsp").forward(request, response);
                return;
            }

            // Verifique se a corrida já foi aceita por outro motorista
            if (!"solicitada".equals(corrida.getStatus())) {
                request.setAttribute("message", "Esta corrida já foi aceita por outro motorista.");
                request.setAttribute("status", "error");
                request.getRequestDispatcher("motorista.jsp").forward(request, response);
                return;
            }

            em.getTransaction().begin();
            corrida.setStatus("aceita");
            corrida.setMotoristaId(motorista); // Certifique-se de que o motorista está sendo atribuído à corrida
            em.getTransaction().commit();

            // Enviar notificação para o cliente
            HttpSession clienteSession = (HttpSession) getServletContext().getAttribute("clienteSession_" + corrida.getIdCliente().getId());
            if (clienteSession != null) {
                clienteSession.setAttribute("message", "Sua corrida foi aceita pelo motorista " + motorista.getNome());
                clienteSession.setAttribute("status", "success");
            }

            request.setAttribute("message", "Você aceitou a corrida.");
            request.setAttribute("status", "success");
        } catch (Exception e) {
            em.getTransaction().rollback();
            request.setAttribute("message", "Erro ao aceitar corrida: " + e.getMessage());
            request.setAttribute("status", "error");
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
