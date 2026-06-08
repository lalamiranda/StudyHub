package Controller;

import DAO.RespostasDAO;
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

        switch (op) {

            case "1": {
                int idPergunta = Integer.parseInt(request.getParameter("id_pergunta"));
                String textoResposta = request.getParameter("resposta");

                Resposta resposta = new Resposta();

                resposta.setIdPergunta(idPergunta);
                resposta.setIdUsuario(usuarioLogado.getIdPessoa());
                resposta.setResposta(textoResposta);
                resposta.setCorreta(null);

                boolean resultado = dao.inserir(resposta);

                if (resultado) {
                    response.sendRedirect("RespostasController?op=2&id_pergunta=" + idPergunta);
                } else {
                    response.sendRedirect("inserir_resposta.jsp?id_pergunta=" + idPergunta + "&erro=1");
                }

                break;
            }

            case "2": {
                int idPergunta = Integer.parseInt(request.getParameter("id_pergunta"));

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

            case "3": {
                int idResposta = Integer.parseInt(request.getParameter("id_resposta"));
                int idPergunta = Integer.parseInt(request.getParameter("id_pergunta"));
                String tipo = request.getParameter("tipo");

                if (!"GOSTEI".equals(tipo) && !"NAO_GOSTEI".equals(tipo)) {
                    response.sendRedirect("RespostasController?op=2&id_pergunta=" + idPergunta);
                    return;
                }

                dao.votarResposta(
                        idResposta,
                        usuarioLogado.getIdPessoa(),
                        tipo
                );

                response.sendRedirect("RespostasController?op=2&id_pergunta=" + idPergunta);

                break;
            }

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