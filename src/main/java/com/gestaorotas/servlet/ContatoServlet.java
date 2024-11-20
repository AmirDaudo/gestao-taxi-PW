/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.gestaorotas.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asus
 */




public class ContatoServlet extends HttpServlet {
    private static final List<String> reclamacoes = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nome = request.getParameter("name");
        String telefone = request.getParameter("phone");
        String mensagem = request.getParameter("message");

        // Armazenar a mensagem de reclamação
        String reclamacao = "Nome: " + nome + ", Telefone: " + telefone + ", Mensagem: " + mensagem;
        synchronized (reclamacoes) {
            reclamacoes.add(reclamacao);
        }

        // Adicionar a lista de reclamações na sessão
        HttpSession session = request.getSession();
        session.setAttribute("reclamacoes", reclamacoes);

        // Redirecionar para a página do administrador
        response.sendRedirect("admin/administrador.jsp");
    }
}
