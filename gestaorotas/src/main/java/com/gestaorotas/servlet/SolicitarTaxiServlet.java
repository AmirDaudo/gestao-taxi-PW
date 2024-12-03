package com.gestaorotas.servlet;

import com.gestaorotas.JpaUtil;
import com.gestaorotas.model.Corridas;
import com.gestaorotas.model.Motoristas;
import com.gestaorotas.model.Usuarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class SolicitarTaxiServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = JpaUtil.getEntityManagerFactory();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String clienteNome = (String) session.getAttribute("usuarioLogadoNome"); // Obtenha o nome do cliente da sessão
        Usuarios cliente = (Usuarios) session.getAttribute("usuarioLogado"); // Obtenha o objeto Usuário logado da sessão

        // Verificação para garantir que clienteNome não seja nulo ou vazio
        if (clienteNome == null || clienteNome.trim().isEmpty()) {
            clienteNome = "Cliente Anônimo"; // Define um nome padrão se não estiver disponível
        }

        String pickup = request.getParameter("pickup");
        String destination = request.getParameter("destination");
        String distanceStr = request.getParameter("distance");
        String fareStr = request.getParameter("fare");

        if (pickup == null || pickup.trim().isEmpty() || destination == null || destination.trim().isEmpty() ||
            distanceStr == null || distanceStr.trim().isEmpty() || fareStr == null || fareStr.trim().isEmpty()) {
            request.setAttribute("message", "Local de partida, destino, distância e valor são obrigatórios.");
            request.setAttribute("status", "error");
            request.getRequestDispatcher("utilizador.jsp").forward(request, response);
            return;
        }

        double distance;
        double fare;
        try {
            distance = Double.parseDouble(distanceStr);
            fare = Double.parseDouble(fareStr);
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Distância ou valor inválidos.");
            request.setAttribute("status", "error");
            request.getRequestDispatcher("utilizador.jsp").forward(request, response);
            return;
        }

        EntityManager em = emf.createEntityManager();
        try {
            // Buscar motoristas online
            List<Motoristas> motoristasOnline = em.createNamedQuery("Motoristas.findByStatus", Motoristas.class)
                                                  .setParameter("status", "online").getResultList();

            if (motoristasOnline.isEmpty()) {
                request.setAttribute("message", "Nenhum motorista online no momento.");
                request.setAttribute("status", "error");
                request.getRequestDispatcher("utilizador.jsp").forward(request, response);
                return;
            }

            // Registrar a corrida na tabela de corridas
            Corridas corrida = new Corridas();
            corrida.setClienteNome(clienteNome); // Define o nome do cliente
            corrida.setMotoristaId(motoristasOnline.get(0)); // Define o ID do motorista
            corrida.setIdCliente(cliente); // Define o objeto Usuário cliente
            corrida.setPickup(pickup);
            corrida.setDestino(destination);
            corrida.setDistancia(distance);
            corrida.setValorEstimado(fare); // Define o valor estimado
            corrida.setPreco(new BigDecimal(fare)); // Define o preço
            corrida.setStatus("solicitada");
            corrida.setDataHoraSolicitacao(new Timestamp(System.currentTimeMillis()));
            corrida.setDataHora(new Date());
            corrida.setPontoPartida(pickup); // Define o ponto de partida

            em.getTransaction().begin();
            em.persist(corrida);
            em.getTransaction().commit();

            // Enviar notificação para motoristas online (exemplo de lógica)
            System.out.println("Notificando motorista: " + motoristasOnline.get(0).getNome());

            request.setAttribute("message", "Solicitação enviada para motoristas online.");
            request.setAttribute("status", "success");
        } catch (Exception e) {
            em.getTransaction().rollback();
            request.setAttribute("message", "Erro ao enviar solicitação: " + e.getMessage());
            request.setAttribute("status", "error");
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
