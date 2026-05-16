package Controller;

import DAO.PerguntasDAO;
import VO.Pergunta;

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

        String op = request.getParameter("op");

        PerguntasDAO dao = new PerguntasDAO();

        if (op != null && op.equals("2")) {

            request.setAttribute(
                    "lista",
                    dao.listar());

            request.getRequestDispatcher(
                    "exibe_perguntas.jsp")
                    .forward(request, response);

        } else {

            Pergunta pergunta = new Pergunta();

            pergunta.setTitulo(
                    request.getParameter("titulo"));

            pergunta.setDescricao(
                    request.getParameter("descricao"));

            pergunta.setIdPessoa(1);

            dao.inserir(pergunta);

            response.sendRedirect(
                    "PerguntasController?op=2");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
