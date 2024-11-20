package com.gestaorotas.servlet;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.ArrayList;

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        ServletContext context = event.getSession().getServletContext();
        List<HttpSession> activeSessions = (List<HttpSession>) context.getAttribute("activeSessions");
        if (activeSessions == null) {
            activeSessions = new ArrayList<>();
            context.setAttribute("activeSessions", activeSessions);
        }
        activeSessions.add(event.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        ServletContext context = event.getSession().getServletContext();
        List<HttpSession> activeSessions = (List<HttpSession>) context.getAttribute("activeSessions");
        if (activeSessions != null) {
            activeSessions.remove(event.getSession());
        }
    }
}
