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
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class PessoasController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        int operacao = Integer.parseInt(request.getParameter("op"));

        PessoasDAO p = new PessoasDAO();

        HttpSession session = request.getSession(false);

        boolean cadastroPublico = operacao == 1 && "1".equals(request.getParameter("publico"));
        boolean login = operacao == 3;

        if (!cadastroPublico && !login) {
            if (session == null || session.getAttribute("usuarioLogado") == null) {
                response.sendRedirect("login.jsp");
                return;
            }
        }

        switch (operacao) {

            case 1 -> {
                String cpf = request.getParameter("cpf");

                if (!Validadorcpf.validar(cpf)) {
                    if (cadastroPublico) {
                        response.sendRedirect("cadastro.jsp?erro=cpf");
                    } else {
                        response.sendRedirect("exibe_resultado.jsp?result=CPF inválido");
                    }
                    return;
                }

                Pessoa pes = new Pessoa();

                pes.setCpf(cpf);
                String nome = request.getParameter("nome");

                if (nome != null) {
                    nome = nome.trim().toUpperCase(new Locale("pt", "BR"));
                }

                pes.setNome(nome);
                pes.setEmail(request.getParameter("email"));
                pes.setSexo(request.getParameter("sexo"));
                pes.setDataNascimento(request.getParameter("dataNascimento"));
                pes.setSenha(request.getParameter("senha"));

                String papel = request.getParameter("papel");

                if (cadastroPublico) {
                    if (!"ALUNO".equals(papel) && !"PROFESSOR".equals(papel)) {
                        papel = "ALUNO";
                    }
                }

                pes.setPapel(papel);

                boolean resultado = p.inserir(pes);

                if (cadastroPublico) {
                    if (resultado) {
                        response.sendRedirect("login.jsp");
                    } else {
                        response.sendRedirect("cadastro.jsp?erro=1");
                    }
                } else {
                    response.sendRedirect("exibe_resultado.jsp?result=" + resultado);
                }
            }

            case 2 -> {
                request.setAttribute("lista", p.listar());

                RequestDispatcher rd = request.getRequestDispatcher("/exibe_pessoas.jsp");
                rd.forward(request, response);
            }

            case 3 -> {
                String email = request.getParameter("email");
                String senha = request.getParameter("senha");

                Pessoa pessoa = p.login(email, senha);

                if (pessoa != null) {
                    request.getSession().setAttribute("usuarioLogado", pessoa);
                    response.sendRedirect("index.jsp");
                } else {
                    response.sendRedirect("login.jsp");
                }
            }

            case 4 -> {
                Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

                Pessoa pes = new Pessoa();

                pes.setIdPessoa(usuarioLogado.getIdPessoa());
                String nome = request.getParameter("nome");

                if (nome != null) {
                    nome = nome.trim().toUpperCase(new Locale("pt", "BR"));
                }

                pes.setNome(nome);
                pes.setEmail(request.getParameter("email"));
                pes.setSexo(request.getParameter("sexo"));
                pes.setDataNascimento(request.getParameter("dataNascimento"));

                String papel = request.getParameter("papel");

                if (!"ALUNO".equals(papel) && !"PROFESSOR".equals(papel)) {
                    papel = usuarioLogado.getPapel();
                }

                pes.setPapel(papel);

                boolean resultado = p.atualizarPerfil(pes);

                if (resultado) {
                    usuarioLogado.setNome(pes.getNome());
                    usuarioLogado.setEmail(pes.getEmail());
                    usuarioLogado.setSexo(pes.getSexo());
                    usuarioLogado.setDataNascimento(pes.getDataNascimento());
                    usuarioLogado.setPapel(pes.getPapel());

                    session.setAttribute("usuarioLogado", usuarioLogado);

                    response.sendRedirect("perfil.jsp?msg=editado");
                } else {
                    response.sendRedirect("perfil.jsp?erro=erro");
                }
            }

            case 5 -> {
                Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

                boolean resultado = p.excluirConta(usuarioLogado.getIdPessoa());

                if (resultado) {
                    session.invalidate();
                    response.sendRedirect("login.jsp");
                } else {
                    response.sendRedirect("perfil.jsp?erro=erro");
                }
            }

            default -> {
                response.sendRedirect("index.jsp");
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
        return "PessoasController";
    }
}
