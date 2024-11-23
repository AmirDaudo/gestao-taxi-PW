package com.gestaorotas.servlet;

import com.gestaorotas.JpaUtil;
import com.gestaorotas.model.Denuncias;
import com.gestaorotas.model.Usuarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class DenunciaServlet extends HttpServlet {
    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = JpaUtil.getEntityManagerFactory();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String mensagem = request.getParameter("denuncia");
        String usuarioNome = usuario.getNome();
        String usuarioEmail = usuario.getEmail();

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Denuncias denuncia = new Denuncias();
            denuncia.setUsuarioNome(usuarioNome);
            denuncia.setUsuarioEmail(usuarioEmail);
            denuncia.setMensagem(mensagem);
            denuncia.setUsuarioId(usuario);
            denuncia.setData(new java.util.Date());

            em.persist(denuncia);  // Utilize persist se a entidade for nova
            em.getTransaction().commit();

            // Adicionar flag de sucesso na sessão
            session.setAttribute("denunciaEnviada", true);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new ServletException("Erro ao salvar denúncia", e);
        } finally {
            em.close();
        }

        // Redirecionar de volta para a página de denúncia
        response.sendRedirect("utilizador.jsp");
    }

    @Override
    public void destroy() {
        emf.close();
    }
}
