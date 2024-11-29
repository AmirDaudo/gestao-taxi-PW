package com.gestaorotas.servlet;

import com.gestaorotas.JpaUtil;
import com.gestaorotas.endpoint.AdminEndpoint;
import com.gestaorotas.model.Corridas;
import com.gestaorotas.model.Motoristas;
import com.gestaorotas.model.Usuarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaxiRequestServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(TaxiRequestServlet.class.getName());
    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = JpaUtil.getEntityManagerFactory();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String pickup = request.getParameter("pickup");
        String destination = request.getParameter("destination");

        logger.log(Level.INFO, "Solicitação recebida: pickup={0}, destination={1}", new Object[]{pickup, destination});

        EntityManager em = emf.createEntityManager();
        try {
            // Verificar todos os motoristas ativos
            logger.log(Level.INFO, "Verificando motoristas ativos");
            TypedQuery<Motoristas> query = em.createQuery("SELECT m FROM Motoristas m WHERE m.ativo = TRUE", Motoristas.class);
            List<Motoristas> motoristasAtivos = query.getResultList();
            logger.log(Level.INFO, "Motoristas ativos encontrados: {0}", motoristasAtivos.size());

            if (!motoristasAtivos.isEmpty()) {
                // Enviar a notificação para todos os motoristas ativos
                for (Motoristas motorista : motoristasAtivos) {
                    // Lógica para enviar notificação (Exemplo: usar WebSocket, API de SMS, etc.)
                    logger.log(Level.INFO, "Notificando motorista: {0}", motorista.getId());
                    AdminEndpoint.notificarMotorista(motorista, pickup, destination);
                }
                
                // Salvar a solicitação de corrida no banco de dados
                logger.log(Level.INFO, "Salvando solicitação de corrida no banco de dados");
                Corridas corrida = new Corridas();
                corrida.setPontoPartida(pickup);
                corrida.setDestino(destination);
                corrida.setIdCliente((Usuarios) session.getAttribute("usuario"));
                em.getTransaction().begin();
                em.persist(corrida);
                em.getTransaction().commit();

                logger.log(Level.INFO, "Solicitação de corrida salva no banco de dados: {0}", corrida);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"message\":\"Solicitação enviada aos motoristas ativos.\"}");
            } else {
                logger.log(Level.WARNING, "Nenhum motorista ativo disponível no momento.");

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"error\":\"Nenhum motorista ativo disponível no momento.\"}");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao processar a solicitação de táxi", e);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"error\":\"Erro ao processar a solicitação de táxi.\"}");
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
