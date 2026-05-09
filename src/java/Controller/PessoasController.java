package Controller;

import DAO.PessoasDAO;
import VO.Pessoa;
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

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            int operacao = Integer.parseInt(request.getParameter("op"));
            PessoasDAO p = new PessoasDAO();

            switch (operacao) {
                case 1 -> {
                    Pessoa pes = new Pessoa();
                    pes.setCpf(request.getParameter("cpf"));
                    pes.setNome(request.getParameter("nome"));   
                    pes.setEmail(request.getParameter("email"));   
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
                        response.sendRedirect("index.html?erro=1"); // volta com erro
                    }
                }
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
