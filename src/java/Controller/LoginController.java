package Controller;

import DAO.PessoasDAO;
import VO.Pessoa;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        PessoasDAO dao = new PessoasDAO();

        Pessoa pessoa = dao.login(email, senha);

        if (pessoa != null) {

            HttpSession session = request.getSession();

            session.setAttribute("usuarioLogado", pessoa);

            response.sendRedirect("index.jsp");

        } else {

            request.setAttribute(
                    "erro",
                    "Email ou senha inválidos.");

            request.getRequestDispatcher("login.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {

        return "Short description";
    }
}