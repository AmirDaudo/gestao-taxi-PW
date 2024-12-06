package com.gestaorotas.servlet;

import com.gestaorotas.JpaUtil;
import com.gestaorotas.model.Corridas;
import com.gestaorotas.model.Usuarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class HistoricoCorridasServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = JpaUtil.getEntityManagerFactory();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("index.jsp?error=Por favor, faça o login.");
            return;
        }

        Usuarios usuario = (Usuarios) session.getAttribute("usuario");

        EntityManager em = emf.createEntityManager();
        try {
            List<Corridas> corridas = em.createQuery("SELECT c FROM Corridas c WHERE c.idCliente = :cliente", Corridas.class)
                                        .setParameter("cliente", usuario)
                                        .getResultList();
            request.setAttribute("corridas", corridas);
        } finally {
            em.close();
        }

        request.getRequestDispatcher("utilizador.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
