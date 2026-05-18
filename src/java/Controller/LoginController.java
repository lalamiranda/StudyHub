package Controller;

import DAO.PessoasDAO;
import DAO.PerguntasDAO;
import VO.Pessoa;
import VO.Pergunta;

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
            String tituloTemp = (String) session.getAttribute("tituloTemp");
            String descricaoTemp = (String) session.getAttribute("descricaoTemp");

            if (tituloTemp != null && descricaoTemp != null) {

                Pergunta pergunta = new Pergunta();

                pergunta.setTitulo(tituloTemp);
                pergunta.setDescricao(descricaoTemp);
                pergunta.setIdPessoa(pessoa.getIdPessoa());

                PerguntasDAO perguntasDAO = new PerguntasDAO();
                perguntasDAO.inserir(pergunta);

                session.removeAttribute("tituloTemp");
                session.removeAttribute("descricaoTemp");
            }

            response.sendRedirect("PerguntasController?op=2");
        } else {
            request.setAttribute("erro", "Email ou senha inválidos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
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
