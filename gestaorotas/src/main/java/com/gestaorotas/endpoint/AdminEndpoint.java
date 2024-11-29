package com.gestaorotas.endpoint;

import com.gestaorotas.model.Motoristas;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ServerEndpoint("/admin")
public class AdminEndpoint {
    private static final List<Session> sessions = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        for (Session s : sessions) {
            if (s.isOpen()) {
                s.getBasicRemote().sendText(message);
            }
        }
    }

    public static void notificarMotorista(Motoristas motorista, String pickup, String destination) {
        String notification = "Nova solicitação de táxi: Partida em " + pickup + " com destino " + destination;
        for (Session session : sessions) {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(notification);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void notificarAdministrador(String mensagem) {
        for (Session session : sessions) {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(mensagem);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
