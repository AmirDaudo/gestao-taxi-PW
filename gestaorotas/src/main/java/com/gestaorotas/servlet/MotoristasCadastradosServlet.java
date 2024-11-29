package com.gestaorotas.servlet;

import com.gestaorotas.JpaUtil;
import com.gestaorotas.model.Motoristas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MotoristasCadastradosServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(MotoristasCadastradosServlet.class.getName());
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
            TypedQuery<Motoristas> query = em.createQuery("SELECT m FROM Motoristas m", Motoristas.class);
            List<Motoristas> motoristasCadastrados = query.getResultList();

            logger.log(Level.INFO, "Motoristas buscados: {0}", motoristasCadastrados.size());
            request.setAttribute("motoristasCadastrados", motoristasCadastrados);

            // Redirecionar para a página administrador.jsp
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
