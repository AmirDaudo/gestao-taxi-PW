package com.gestaorotas;

import com.gestaorotas.model.Motoristas;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@MultipartConfig
public class CadastroDriver extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = Persistence.createEntityManagerFactory("my_persistence_unit");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verificar se o checkbox dos termos foi marcado
        String termos = request.getParameter("termos");
        if (termos == null) {
            response.getWriter().println("Você deve aceitar os Termos e Condições para se cadastrar.");
            return;
        }

        // Obter os dados do formulário
        String nome = request.getParameter("nome");
        String telefone = request.getParameter("telefone");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String marcaCarro = request.getParameter("marca_carro");
        String modeloCarro = request.getParameter("modelo_carro");
        String matricula = request.getParameter("matricula");
        String disponibilidade = request.getParameter("disponibilidade");

        // Diretório de upload
        String uploadDir = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        // Processar e salvar as fotos do carro
        Part[] fotos = request.getParts().stream()
                .filter(part -> "fotos_carro".equals(part.getName()))
                .toArray(Part[]::new);

        String[] fotoPaths = new String[fotos.length];
        for (int i = 0; i < fotos.length; i++) {
            String fileName = Paths.get(fotos[i].getSubmittedFileName()).getFileName().toString();
            String uploadPath = uploadDir + File.separator + fileName;
            fotos[i].write(uploadPath);
            fotoPaths[i] = "uploads" + File.separator + fileName;
        }

        // Criar uma instância de Motoristas
        Motoristas motorista = new Motoristas();
        motorista.setNome(nome);
        motorista.setTelefone(telefone);
        motorista.setEmail(email);
        motorista.setSenha(senha);
        motorista.setMarcaCarro(marcaCarro);
        motorista.setModeloCarro(modeloCarro);
        motorista.setMatricula(matricula);
        motorista.setDisponibilidade(disponibilidade);
        motorista.setFoto1Path(fotoPaths.length > 0 ? fotoPaths[0] : null);
        motorista.setFoto2Path(fotoPaths.length > 1 ? fotoPaths[1] : null);
        motorista.setFoto3Path(fotoPaths.length > 2 ? fotoPaths[2] : null);
        motorista.setFoto4Path(fotoPaths.length > 3 ? fotoPaths[3] : null);

        // Persistir os dados no banco de dados
        MotoristasJpaController motoristasController = new MotoristasJpaController(emf);
        motoristasController.create(motorista);

        // Redirecionar ou enviar uma resposta ao cliente
        response.sendRedirect("motorista.jsp");
    }

    @Override
    public void destroy() {
        emf.close();
    }
}
