<%@page import="DAO.TagsDAO"%>
<%@page import="VO.Tag"%>
<%@page import="java.util.ArrayList"%>

<%
    TagsDAO tagsDAO = new TagsDAO();
    ArrayList<Tag> tags = tagsDAO.listar();
%>

<%@page import="VO.Pessoa"%>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
    <head>

        <meta charset="UTF-8">

        <title>Nova Pergunta</title>

    </head>

    <body>

        <h1>Fazer Pergunta</h1>

        <form action="PerguntasController" method="post">

            <label>Título:</label>
            <br>

            <input type="text"
                   name="titulo"
                   required>

            <br><br>

            <label>Descrição:</label>
            <br>

            <textarea name="descricao"
                      rows="6"
                      cols="50"
                      required></textarea>

            <br><br>

            <tr>
                <td>Tags</td>
                <td>
                    <%
                        if (tags != null && !tags.isEmpty()) {
                            for (Tag tag : tags) {
                    %>
                    <input type="checkbox" name="tags" value="<%= tag.getIdTag()%>">
                    <%= tag.getNome()%>
                    <br>
                    <%
                        }
                    } else {
                    %>
                    Nenhuma tag cadastrada.
                    <%
                        }
                    %>
                </td>
            </tr>

            <button type="submit">
                Postar Pergunta
            </button>

        </form>
        <br><br>
        <a href="index.jsp">Página inicial</a>

    </body>
</html>