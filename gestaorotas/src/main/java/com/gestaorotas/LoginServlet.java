package com.gestaorotas;

import com.gestaorotas.endpoint.AdminEndpoint;
import com.gestaorotas.model.Usuarios;
import com.gestaorotas.model.Motoristas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String telefone = request.getParameter("numero");
        String senha = request.getParameter("senha");

        logger.log(Level.INFO, "Telefone: " + telefone);
        logger.log(Level.INFO, "Senha: " + senha);

        if (telefone != null && senha != null) {
            HttpSession session = request.getSession();

            // Verificar administradores
            if (telefone.equals("1234") && senha.equals("admin")) {
                session.setAttribute("adminName", "Amir Daudo");
                response.sendRedirect("administrador.jsp");
                return;
            } else if (telefone.equals("3333") && senha.equals("admin")) {
                session.setAttribute("adminName", "Quimba");
                response.sendRedirect("administrador.jsp");
                return;
            }

            EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
            try {
                Usuarios usuario = null;
                Motoristas motorista = null;

                // Verificar na tabela de Usuários
                TypedQuery<Usuarios> queryUser = em.createQuery("SELECT u FROM Usuarios u WHERE u.telefone = :telefone AND u.senha = :senha", Usuarios.class);
                queryUser.setParameter("telefone", telefone);
                queryUser.setParameter("senha", senha);

                try {
                    usuario = queryUser.getSingleResult();
                } catch (Exception e) {
                    logger.log(Level.INFO, "Não encontrado na tabela de Usuários", e);
                }

                if (usuario == null) {
                    // Verificar na tabela de Motoristas
                    TypedQuery<Motoristas> queryDriver = em.createQuery("SELECT m FROM Motoristas m WHERE m.telefone = :telefone AND m.senha = :senha", Motoristas.class);
                    queryDriver.setParameter("telefone", telefone);
                    queryDriver.setParameter("senha", senha);
                    try {
                        motorista = queryDriver.getSingleResult();
                    } catch (Exception e) {
                        logger.log(Level.INFO, "Não encontrado na tabela de Motoristas", e);
                    }
                }

                if (usuario != null) {
                    session.setAttribute("usuario", usuario);
                    response.sendRedirect("utilizador.jsp");
                } else if (motorista != null) {
                    session.setAttribute("motorista", motorista);
                    session.setAttribute("status", "online");
                    // Notificar WebSocket
                    AdminEndpoint.notificarAdministrador("Motorista " + motorista.getNome() + " está online.");
                    response.sendRedirect("motorista.jsp");
                } else {
                    response.getWriter().println("Credenciais inválidas. Tente novamente.");
                }

            } finally {
                em.close();
            }
        } else {
            response.getWriter().println("Campos de telefone ou senha não podem estar vazios.");
        }
    }
}
