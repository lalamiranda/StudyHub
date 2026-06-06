<%@page import="VO.Pessoa"%>
<%@page import="DAO.TagsDAO"%>
<%@page import="VO.Tag"%>
<%@page import="java.util.ArrayList"%>
<%@page import="VO.Pergunta"%>
<%@page import="java.util.List"%>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<%
    TagsDAO tagsDAO = new TagsDAO();
    ArrayList<Tag> tagsFiltro = tagsDAO.listar();
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listagem</title>
    </head>
    <body>
    <center>
        <b>Filtrar por tag:</b>
        <br><br>

        <a href="PerguntasController?op=2">Todas</a>

        <%
            if (tagsFiltro != null) {
                for (Tag tag : tagsFiltro) {
        %>
        |
        <a href="PerguntasController?op=2&id_tag=<%= tag.getIdTag()%>">
            <%= tag.getNome()%>
        </a>
        <%
                }
            }
        %>
    </center>

    <br><br>
    <%
        List perguntas = (List) request.getAttribute("lista");
        if (perguntas != null) {
            out.print("<center>Achados: " + perguntas.size() + "</center><br><br><br>");
            out.print("<table width=\"80%\" border=\"1\" cellspacing=\"0\" align=\"center\">");

            out.print("<tr>");
            out.print("<th>Título</th>");
            out.print("<th>Descrição</th>");
            out.print("<th>Autor</th>");
            out.print("<th>Tags</th>");
            out.print("<th>Ações</th>");
            out.print("</tr>");

            for (int cont = 0; cont < perguntas.size(); cont++) {
                Pergunta p = new Pergunta();
                p = (Pergunta) perguntas.get(cont);

                out.print("<tr>");
                out.print("<td>" + p.getTitulo() + "</td>");
                out.print("<td>" + p.getDescricao() + "</td>");

                if (p.getNomePessoa() != null) {
                    out.print("<td>" + p.getNomePessoa() + "</td>");
                } else {
                    out.print("<td>Usuário não encontrado</td>");
                }

                if (p.getTags() != null) {
                    out.print("<td>" + p.getTags() + "</td>");
                } else {
                    out.print("<td>Sem tags</td>");
                }

                out.print("<td><a href='RespostasController?op=2&id_pergunta=" + p.getIdPergunta() + "'>Ver respostas</a></td>");
                out.print("</tr>");
            }

            out.print("</table>");
        }
    %>

    <br><br><br>
    <center><a href="index.jsp">Página inicial</a></center>

</body>
</html>