package Controller;

import DAO.PessoasDAO;
import VO.Pessoa;
import Util.Validadorcpf;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jean
 */
public class PessoasController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int operacao = Integer.parseInt(request.getParameter("op"));
        PessoasDAO p = new PessoasDAO();

        switch (operacao) {
            case 1 -> {
                String cpf = request.getParameter("cpf");

                // Valida o CPF antes de inserir
                if (!Validadorcpf.validar(cpf)) {
                    response.sendRedirect("exibe_resultado.jsp?result=CPF inválido");
                    return;
                }

                Pessoa pes = new Pessoa();
                pes.setCpf(cpf);
                pes.setNome(request.getParameter("nome"));
                pes.setEmail(request.getParameter("email"));
                pes.setPapel(request.getParameter("papel"));
                pes.setSenha(request.getParameter("senha"));
                response.sendRedirect("exibe_resultado.jsp?result=" + p.inserir(pes));
            }
            case 2 -> {
                request.setAttribute("lista", p.listar());
                RequestDispatcher rd = request.getRequestDispatcher("/exibe_pessoas.jsp");
                rd.forward(request, response);
            }
            case 3 -> {
                String email = request.getParameter("email");
                String senha = request.getParameter("senha");
                boolean autenticado = p.autenticar(email, senha);
                if (autenticado) {
                    request.getSession().setAttribute("usuarioLogado", email);
                    response.sendRedirect("exibe_pessoas.jsp");
                } else {
                    response.sendRedirect("index.html?erro=1");
                }
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