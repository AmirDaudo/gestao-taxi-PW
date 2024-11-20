/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestaorotas.endpoint;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 *
 * @author asus
 */


@ServerEndpoint("/admin")
public class AdminEndpoint {
    private static Set<Session> admins = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session) {
        admins.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        admins.remove(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        // Lógica para tratar mensagens dos administradores
    }

    public static void notificarAdministrador(String mensagem) throws IOException {
        synchronized (admins) {
            for (Session admin : admins) {
                admin.getBasicRemote().sendText(mensagem);
            }
        }
    }
}
