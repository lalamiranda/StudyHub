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

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuarioLogado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Pessoa pessoaLogada = (Pessoa) session.getAttribute("usuarioLogado");

        String op = request.getParameter("op");

        PerguntasDAO dao = new PerguntasDAO();

        if (op == null) {
            op = "2";
        }

        switch (op) {

            //listar perguntas
            case "2": {

                String idTagParam = request.getParameter("id_tag");

                if (idTagParam != null && !idTagParam.isEmpty()) {
                    int idTag = Integer.parseInt(idTagParam);
                    request.setAttribute("lista", dao.listarPorTag(idTag));
                } else {
                    request.setAttribute("lista", dao.listar());
                }

                request.getRequestDispatcher("exibe_perguntas.jsp")
                        .forward(request, response);

                break;
            }

            //inserir pergunta
            case "3": {

                Pergunta pergunta = new Pergunta();

                //Monta o objeto pergunta com os dados do formulário
                pergunta.setTitulo(request.getParameter("titulo"));
                pergunta.setDescricao(request.getParameter("descricao"));
                pergunta.setIdPessoa(pessoaLogada.getIdPessoa());

                //Insere a pergunta no banco e retorna o ID.
                int idPergunta = dao.inserir(pergunta);

                if (idPergunta > 0) {

                    String[] tagsSelecionadas = request.getParameterValues("tags");

                    TagsDAO tagsDAO = new TagsDAO();
                    //Salva as tags relacionadas à pergunta
                    tagsDAO.salvarTagsDaPergunta(idPergunta, tagsSelecionadas);

                    PessoasDAO pessoasDAO = new PessoasDAO();
                    //Atualiza a reputação do usuário.
                    pessoasDAO.atualizarReputacao(pessoaLogada.getIdPessoa());

                    Pessoa pessoaAtualizada = pessoasDAO.buscarPorId(pessoaLogada.getIdPessoa());

                    if (pessoaAtualizada != null) {
                        session.setAttribute("usuarioLogado", pessoaAtualizada);
                    }

                    response.sendRedirect("PerguntasController?op=2&msg=inserido");

                } else {
                    response.sendRedirect("PerguntasController?op=2&erro=erro");
                }

                break;
            }

            //abrir edição
            case "4": {

                int idPergunta = Integer.parseInt(request.getParameter("id_pergunta"));

                Pergunta pergunta = dao.buscarPorId(idPergunta);

                if (pergunta == null) {
                    response.sendRedirect("PerguntasController?op=2&erro=nao_encontrada");
                    return;
                }

                //Impede editar pergunta de outra pessoa
                if (pergunta.getIdPessoa() != pessoaLogada.getIdPessoa()) {
                    response.sendRedirect("PerguntasController?op=2&erro=sem_permissao");
                    return;
                }

                request.setAttribute("pergunta", pergunta);

                request.getRequestDispatcher("editar_pergunta.jsp")
                        .forward(request, response);

                break;
            }

            //salvar edição
            case "5": {

                int idPergunta = Integer.parseInt(request.getParameter("id_pergunta"));

                Pergunta pergunta = new Pergunta();

                pergunta.setIdPergunta(idPergunta);
                pergunta.setTitulo(request.getParameter("titulo"));
                pergunta.setDescricao(request.getParameter("descricao"));
                pergunta.setIdPessoa(pessoaLogada.getIdPessoa());

                //Atualiza somente se o usuário for autor
                boolean atualizou = dao.atualizarSeForAutor(pergunta);

                if (atualizou) {
                    response.sendRedirect("PerguntasController?op=2&msg=editado");
                } else {
                    response.sendRedirect("PerguntasController?op=2&erro=sem_permissao");
                }

                break;
            }

            //excluir pergunta
            case "6": {

                int idPergunta = Integer.parseInt(request.getParameter("id_pergunta"));

                //Exclui somente se o usuário for o autor
                boolean excluiu = dao.excluirSeForAutor(idPergunta, pessoaLogada.getIdPessoa());

                if (excluiu) {

                    PessoasDAO pessoasDAO = new PessoasDAO();
                    pessoasDAO.atualizarReputacao(pessoaLogada.getIdPessoa());

                    Pessoa pessoaAtualizada = pessoasDAO.buscarPorId(pessoaLogada.getIdPessoa());

                    if (pessoaAtualizada != null) {
                        session.setAttribute("usuarioLogado", pessoaAtualizada);
                    }

                    response.sendRedirect("PerguntasController?op=2&msg=excluido");

                } else {
                    response.sendRedirect("PerguntasController?op=2&erro=sem_permissao");
                }

                break;
            }

            default: {
                response.sendRedirect("PerguntasController?op=2");
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

    @Override
    public String getServletInfo() {
        return "Perguntas Controller";
    }
}