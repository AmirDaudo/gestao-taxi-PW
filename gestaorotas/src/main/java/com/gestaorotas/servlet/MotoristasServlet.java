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

public class MotoristasServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = JpaUtil.getEntityManagerFactory();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null || action.isEmpty() || "carregar".equals(action)) {
            carregarMotoristas(request, response);
        } else {
            doPost(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String motoristaIdStr = request.getParameter("id");
        String action = request.getParameter("action");

        if (motoristaIdStr == null || motoristaIdStr.trim().isEmpty()) {
            request.setAttribute("message", "ID do motorista não pode estar vazio.");
            request.setAttribute("status", "error");
            carregarMotoristas(request, response);
            return;
        }

        int motoristaId;
        try {
            motoristaId = Integer.parseInt(motoristaIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("message", "ID do motorista inválido.");
            request.setAttribute("status", "error");
            carregarMotoristas(request, response);
            return;
        }

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Motoristas motorista = em.find(Motoristas.class, motoristaId);
            if (motorista != null) {
                switch (action) {
                    case "bloquear":
                        motorista.setBloqueado(true);
                        request.setAttribute("message", "Motorista bloqueado com sucesso.");
                        break;
                    case "desbloquear":
                        motorista.setBloqueado(false);
                        request.setAttribute("message", "Motorista desbloqueado com sucesso.");
                        break;
                    case "apagar":
                        em.remove(motorista);
                        request.setAttribute("message", "Motorista apagado com sucesso.");
                        break;
                    default:
                        request.setAttribute("message", "Ação desconhecida.");
                        request.setAttribute("status", "error");
                        em.getTransaction().rollback();
                        carregarMotoristas(request, response);
                        return;
                }
                em.getTransaction().commit();
                request.setAttribute("status", "success");
            } else {
                request.setAttribute("message", "Motorista não encontrado.");
                request.setAttribute("status", "error");
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            request.setAttribute("message", "Erro ao processar a solicitação: " + e.getMessage());
            request.setAttribute("status", "error");
        } finally {
            em.close();
        }

        carregarMotoristas(request, response);
    }

    private void carregarMotoristas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = emf.createEntityManager();
        try {
            List<Motoristas> motoristasCadastrados = em.createNamedQuery("Motoristas.findAll", Motoristas.class).getResultList();
            request.setAttribute("motoristasCadastrados", motoristasCadastrados);
            request.getRequestDispatcher("Administrador.jsp").forward(request, response);
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
