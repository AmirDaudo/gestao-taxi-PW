package com.gestaorotas.servlet;

import com.gestaorotas.JpaUtil;
import com.gestaorotas.model.Motoristas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class UpdateDriverProfileServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = JpaUtil.getEntityManagerFactory();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verificar se o motorista está logado
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("motorista") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        // Obter o motorista da sessão
        Motoristas motorista = (Motoristas) session.getAttribute("motorista");

        // Obter os novos dados do formulário
        String telefone = request.getParameter("telefone");
        String senha = request.getParameter("senha");
        String confirmarSenha = request.getParameter("confirmarSenha");
        String disponibilidade = request.getParameter("disponibilidade");

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Atualizar os dados do motorista
            if (telefone != null && !telefone.isEmpty()) {
                motorista.setTelefone(telefone);
            }
            if (disponibilidade != null && !disponibilidade.isEmpty()) {
                motorista.setDisponibilidade(disponibilidade);
            }
            if (senha != null && !senha.isEmpty() && confirmarSenha != null && !confirmarSenha.isEmpty()) {
                if (senha.equals(confirmarSenha)) {
                    motorista.setSenha(senha);
                } else {
                    response.sendRedirect("confMotorista.jsp?error=Senhas não coincidem.");
                    return;
                }
            }
            em.merge(motorista);
            em.getTransaction().commit();

            // Atualizar a sessão com os novos dados
            session.setAttribute("motorista", motorista);

            response.sendRedirect("confMotorista.jsp?success=Perfil atualizado com sucesso.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            response.sendRedirect("confMotorista.jsp?error=Erro ao atualizar o perfil: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        emf.close();
    }
}
