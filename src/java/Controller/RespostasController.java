package Controller;

import DAO.RespostasDAO;
import VO.Resposta;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RespostasController", urlPatterns = {"/RespostasController"})
public class RespostasController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = request.getParameter("op");

        if (op == null) {
            op = "1";
        }

        switch (op) {

            case "1":
                Resposta resposta = new Resposta();

                resposta.setIdPergunta(
                        Integer.parseInt(request.getParameter("id_pergunta")));

                resposta.setIdUsuario(9);

                resposta.setResposta(
                        request.getParameter("resposta"));

                resposta.setCorreta(null);

                RespostasDAO dao = new RespostasDAO();
                dao.inserir(resposta);

                response.sendRedirect("RespostasController?op=2&id_pergunta="
                        + resposta.getIdPergunta());
                break;

            case "2":
                int idPergunta = Integer.parseInt(
                        request.getParameter("id_pergunta"));

                RespostasDAO daoListar = new RespostasDAO();

                request.setAttribute("lista",
                        daoListar.listarPorPergunta(idPergunta));

                request.setAttribute("id_pergunta", idPergunta);

                request.getRequestDispatcher("exibir_resposta.jsp")
                        .forward(request, response);
                break;
            case "3":

                int idResposta = Integer.parseInt(
                        request.getParameter("id_resposta"));

                boolean novoValor = Boolean.parseBoolean(
                        request.getParameter("correta"));

                String atualStr = request.getParameter("correta_atual");

                Boolean corretaFinal;
                if (atualStr != null && !atualStr.equals("null")
                        && Boolean.parseBoolean(atualStr) == novoValor) {
                    corretaFinal = null;
                } else {
                    corretaFinal = novoValor;
                }

                RespostasDAO daoCurtir = new RespostasDAO();
                daoCurtir.atualizarCorreta(idResposta, corretaFinal);

                int idPerg = Integer.parseInt(
                        request.getParameter("id_pergunta"));

                response.sendRedirect(
                        "RespostasController?op=2&id_pergunta=" + idPerg);

                break;
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
