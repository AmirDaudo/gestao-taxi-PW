/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestaorotas.endpoint;

/**
 *
 * @author asus
 */

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/motoristas")
public class MotoristasEndpoint {
    private static Set<Session> motoristas = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session) {
        motoristas.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        motoristas.remove(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        // Lógica para tratar mensagens dos motoristas
    }

    public static void enviarPedidoParaMotoristas(String pedido) throws IOException {
        synchronized (motoristas) {
            for (Session motorista : motoristas) {
                motorista.getBasicRemote().sendText(pedido);
            }
        }
    }
}
