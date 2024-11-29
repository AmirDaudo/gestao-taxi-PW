package com.gestaorotas.servlet;

import com.gestaorotas.model.Motoristas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/mostrarFoto")
public class MostrarFotoServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = Persistence.createEntityManagerFactory("my_persistence_unit");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int motoristaId = Integer.parseInt(request.getParameter("id"));
        int fotoIndex = Integer.parseInt(request.getParameter("fotoIndex"));

        EntityManager em = emf.createEntityManager();
        Motoristas motorista = em.find(Motoristas.class, motoristaId);

        byte[] foto = null;
        switch (fotoIndex) {
            case 1:
                foto = motorista.getFoto1();
                break;
            case 2:
                foto = motorista.getFoto2();
                break;
            case 3:
                foto = motorista.getFoto3();
                break;
            case 4:
                foto = motorista.getFoto4();
                break;
        }

        if (foto != null) {
            response.setContentType("image/jpeg");
            OutputStream os = response.getOutputStream();
            os.write(foto);
            os.flush();
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

        em.close();
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
