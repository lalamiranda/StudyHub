package Controller;

import DAO.RespostasDAO;
import DAO.PessoasDAO;
import VO.Pessoa;
import VO.Resposta;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "RespostasController", urlPatterns = {"/RespostasController"})
public class RespostasController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuarioLogado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

        String op = request.getParameter("op");

        if (op == null) {
            response.sendRedirect("PerguntasController?op=2");
            return;
        }

        RespostasDAO dao = new RespostasDAO();
        PessoasDAO pessoasDAO = new PessoasDAO();

        switch (op) {

            //inserir resposta
            case "1": {
                int idPergunta = Integer.parseInt(request.getParameter("id_pergunta"));
                String textoResposta = request.getParameter("resposta");

                Resposta resposta = new Resposta();

                //Monta o objeto resposta
                resposta.setIdPergunta(idPergunta);
                resposta.setIdUsuario(usuarioLogado.getIdPessoa());
                resposta.setResposta(textoResposta);
                resposta.setCorreta(null);

                //Salva no banco
                boolean resultado = dao.inserir(resposta);

                if (resultado) {

                    pessoasDAO.atualizarReputacao(usuarioLogado.getIdPessoa());

                    Pessoa usuarioAtualizado = pessoasDAO.buscarPorId(usuarioLogado.getIdPessoa());

                    if (usuarioAtualizado != null) {
                        session.setAttribute("usuarioLogado", usuarioAtualizado);
                    }

                    response.sendRedirect("RespostasController?op=2&id_pergunta=" + idPergunta);

                } else {
                    response.sendRedirect("inserir_resposta.jsp?id_pergunta=" + idPergunta + "&erro=1");
                }

                break;
            }

            //listar respostas
            case "2": {
                int idPergunta = Integer.parseInt(request.getParameter("id_pergunta"));

                //Lista as respostas daquela pergunta e também identifica se o usuário já votou
                ArrayList<Resposta> lista = dao.listarPorPergunta(
                        idPergunta,
                        usuarioLogado.getIdPessoa()
                );

                request.setAttribute("lista", lista);
                request.setAttribute("id_pergunta", idPergunta);

                RequestDispatcher rd = request.getRequestDispatcher("exibir_resposta.jsp");
                rd.forward(request, response);

                break;
            }

            //votar resposta
            case "3": {
                int idResposta = Integer.parseInt(request.getParameter("id_resposta"));
                int idPergunta = Integer.parseInt(request.getParameter("id_pergunta"));
                
                //Pega o tipo do voto.
                String tipo = request.getParameter("tipo");

                if (!"GOSTEI".equals(tipo) && !"NAO_GOSTEI".equals(tipo)) {
                    response.sendRedirect("RespostasController?op=2&id_pergunta=" + idPergunta);
                    return;
                }

                //Registra ou atualiza o voto
                dao.votarResposta(
                        idResposta,
                        usuarioLogado.getIdPessoa(),
                        tipo
                );

                int idAutorResposta = dao.buscarAutorResposta(idResposta);

                if (idAutorResposta > 0) {
                    pessoasDAO.atualizarReputacao(idAutorResposta);
                }

                if (idAutorResposta == usuarioLogado.getIdPessoa()) {
                    Pessoa usuarioAtualizado = pessoasDAO.buscarPorId(usuarioLogado.getIdPessoa());

                    if (usuarioAtualizado != null) {
                        session.setAttribute("usuarioLogado", usuarioAtualizado);
                    }
                }

                response.sendRedirect("RespostasController?op=2&id_pergunta=" + idPergunta);

                break;
            }

            //Marcar resposta correta/incorreta
            case "4": {
                int idResposta = Integer.parseInt(request.getParameter("id_resposta"));
                int idPergunta = Integer.parseInt(request.getParameter("id_pergunta"));
                String valor = request.getParameter("correta");

                Boolean correta = null;

                if ("true".equals(valor)) {
                    correta = true;
                } else if ("false".equals(valor)) {
                    correta = false;
                }

                //Só professor ou admin pode marcar resposta como correta/incorreta
                if ("PROFESSOR".equals(usuarioLogado.getPapel()) || "ADMIN".equals(usuarioLogado.getPapel())) {
                    dao.atualizarCorreta(idResposta, correta);
                }

                response.sendRedirect("RespostasController?op=2&id_pergunta=" + idPergunta);

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
        return "RespostasController";
    }
}