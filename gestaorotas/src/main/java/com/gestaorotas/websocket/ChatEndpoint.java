/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestaorotas.websocket;

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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat")
public class ChatEndpoint {
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        String userId = session.getQueryString(); // Assuma que o ID do usuário é passado como parâmetro
        sessions.put(userId, session);
        System.out.println("Usuário conectado: " + userId);
    }

    @OnMessage
    public void onMessage(String message, Session senderSession) {
        // A mensagem pode incluir o destinatário, ex.: "destino|mensagem"
        String[] parts = message.split("\\|", 2);
        if (parts.length == 2) {
            String destinatario = parts[0];
            String mensagem = parts[1];
            Session targetSession = sessions.get(destinatario);

            if (targetSession != null && targetSession.isOpen()) {
                try {
                    targetSession.getBasicRemote().sendText(mensagem);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.values().remove(session);
        System.out.println("Usuário desconectado");
    }
}
