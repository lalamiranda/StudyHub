package Controller;

import DAO.PerguntasDAO;
import DAO.PessoasDAO;
import DAO.TagsDAO;
import VO.Pergunta;
import VO.Pessoa;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "PerguntasController", urlPatterns = {"/PerguntasController"})
public class PerguntasController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuarioLogado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String op = request.getParameter("op");

        PerguntasDAO dao = new PerguntasDAO();

        if (op != null && op.equals("2")) {

            String idTagParam = request.getParameter("id_tag");

            if (idTagParam != null && !idTagParam.isEmpty()) {
                int idTag = Integer.parseInt(idTagParam);

                request.setAttribute("lista", dao.listarPorTag(idTag));
            } else {
                request.setAttribute("lista", dao.listar());
            }

            request.getRequestDispatcher("exibe_perguntas.jsp")
                    .forward(request, response);

        } else {

            Pergunta pergunta = new Pergunta();

            pergunta.setTitulo(request.getParameter("titulo"));
            pergunta.setDescricao(request.getParameter("descricao"));

            Pessoa pessoaLogada = (Pessoa) session.getAttribute("usuarioLogado");

            pergunta.setIdPessoa(pessoaLogada.getIdPessoa());

            int idPergunta = dao.inserir(pergunta);

            if (idPergunta > 0) {

                String[] tagsSelecionadas = request.getParameterValues("tags");

                TagsDAO tagsDAO = new TagsDAO();
                tagsDAO.salvarTagsDaPergunta(idPergunta, tagsSelecionadas);

                PessoasDAO pessoasDAO = new PessoasDAO();
                pessoasDAO.atualizarReputacao(pessoaLogada.getIdPessoa());

                Pessoa pessoaAtualizada = pessoasDAO.buscarPorId(pessoaLogada.getIdPessoa());

                if (pessoaAtualizada != null) {
                    session.setAttribute("usuarioLogado", pessoaAtualizada);
                }

                response.sendRedirect("PerguntasController?op=2&msg=inserido");

            } else {
                response.sendRedirect("PerguntasController?op=2&erro=erro");
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}