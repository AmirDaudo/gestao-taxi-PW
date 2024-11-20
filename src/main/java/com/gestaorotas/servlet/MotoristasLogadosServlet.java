package com.gestaorotas.servlet;

import com.gestaorotas.model.Motoristas;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MotoristasLogadosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = getServletContext();
        List<HttpSession> activeSessions = (List<HttpSession>) context.getAttribute("activeSessions");

        List<Motoristas> motoristasLogados = new ArrayList<>();
        if (activeSessions != null) {
            for (HttpSession session : activeSessions) {
                Motoristas motorista = (Motoristas) session.getAttribute("motorista");
                if (motorista != null) {
                    motoristasLogados.add(motorista);
                }
            }
        }

        request.setAttribute("motoristasLogados", motoristasLogados);
        getServletContext().getRequestDispatcher("/admin/motoristasLogados.jsp").forward(request, response);
    }
}
