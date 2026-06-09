package Controller;

import DAO.MateriaisDAO;
import VO.Material;
import VO.Pessoa;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "MateriaisController", urlPatterns = {"/MateriaisController"})
public class MateriaisController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuarioLogado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String op = request.getParameter("op");

        if (op == null) {
            op = "1";
        }

        MateriaisDAO dao = new MateriaisDAO();

        switch (op) {

            case "1": {
                Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

                Material material = new Material();

                material.setTitulo(request.getParameter("titulo"));
                material.setDescricao(request.getParameter("descricao"));
                material.setLinkExterno(request.getParameter("link_externo"));
                material.setTipo(request.getParameter("tipo"));
                material.setIdPessoa(usuarioLogado.getIdPessoa());

                dao.inserir(material);

                response.sendRedirect("MateriaisController?op=2");
                break;
            }

            case "2": {
                request.setAttribute("lista", dao.listar());

                request.getRequestDispatcher("exibe_materiais.jsp")
                        .forward(request, response);
                break;
            }
            case "3": {
                int idMaterial = Integer.parseInt(request.getParameter("id_material"));
                Pessoa usuarioLogado3 = (Pessoa) session.getAttribute("usuarioLogado");

                Material materialExcluir = dao.buscarPorId(idMaterial);

                if (materialExcluir == null || materialExcluir.getIdPessoa() != usuarioLogado3.getIdPessoa()) {
                    response.sendRedirect("MateriaisController?op=2");
                    return;
                }

                dao.excluir(idMaterial);

                response.sendRedirect("MateriaisController?op=2");
                break;
            }
            case "4": {
                int idMaterial = Integer.parseInt(request.getParameter("id_material"));
                Pessoa usuarioLogado4 = (Pessoa) session.getAttribute("usuarioLogado");

                Material material = dao.buscarPorId(idMaterial);

                if (material == null || material.getIdPessoa() != usuarioLogado4.getIdPessoa()) {
                    response.sendRedirect("MateriaisController?op=2");
                    return;
                }

                request.setAttribute("material", material);

                request.getRequestDispatcher("editar_material.jsp")
                        .forward(request, response);

                break;
            }
            case "5": {
                Pessoa usuarioLogado5 = (Pessoa) session.getAttribute("usuarioLogado");
                int idMaterial = Integer.parseInt(request.getParameter("id_material"));

                Material materialExistente = dao.buscarPorId(idMaterial);

                if (materialExistente == null || materialExistente.getIdPessoa() != usuarioLogado5.getIdPessoa()) {
                    response.sendRedirect("MateriaisController?op=2");
                    return;
                }

                Material material = new Material();

                material.setIdMaterial(idMaterial);
                material.setTitulo(request.getParameter("titulo"));
                material.setDescricao(request.getParameter("descricao"));
                material.setLinkExterno(request.getParameter("link_externo"));
                material.setTipo(request.getParameter("tipo"));

                dao.alterar(material);

                response.sendRedirect("MateriaisController?op=2");

                break;
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
