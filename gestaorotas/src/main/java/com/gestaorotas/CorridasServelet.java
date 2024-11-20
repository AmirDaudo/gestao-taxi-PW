package com.gestaorotas;

import com.gestaorotas.model.Corridas;
import com.gestaorotas.model.Usuarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

public class CorridasServelet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = Persistence.createEntityManagerFactory("my_persistence_unit");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Obter dados do usuário logado da sessão
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");

        // Verificar se o usuário está logado
        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Obter os dados adicionais do formulário
        String pontoPartida = request.getParameter("pontoPartida");
        String destino = request.getParameter("destino");
        BigDecimal preco = new BigDecimal(request.getParameter("preco")); // Supondo que o preço é enviado no formulário
        String status = "Pendente"; // Status inicial

        // Criar uma instância de Corrida
        Corridas corrida = new Corridas();
        corrida.setPontoPartida(pontoPartida);
        corrida.setDestino(destino);
        corrida.setPreco(preco);
        corrida.setStatus(status);
        corrida.setDataHora(new Date());
        corrida.setIdCliente(usuario);

        // Persistir os dados no banco de dados
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(corrida);
        em.getTransaction().commit();
        em.close();

        // Redirecionar ou enviar uma resposta ao cliente
        response.sendRedirect("success.jsp");
    }

    @Override
    public void destroy() {
        emf.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (var out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Corridas</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Corridas at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
