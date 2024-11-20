/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.gestaorotas.servlet;


import com.gestaorotas.endpoint.MotoristasEndpoint;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author asus
 */


public class PedidoTaxiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String contato = request.getParameter("contato");
        String pontoPartida = request.getParameter("pontoPartida");
        String destino = request.getParameter("destino");

        // Gerar a mensagem do pedido
        String pedido = String.format("Novo pedido de táxi:\nNome: %s\nContato: %s\nLocal de Partida: %s\nDestino: %s",
                nome, contato, pontoPartida, destino);

        // Enviar o pedido para todos os motoristas logados
        MotoristasEndpoint.enviarPedidoParaMotoristas(pedido);

        // Responder ao cliente
        response.getWriter().println("Pedido enviado aos motoristas logados.");
    }
}
