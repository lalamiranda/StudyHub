<%@page import="VO.Pessoa"%>
<%@page import="VO.Material"%>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    Material material = (Material) request.getAttribute("material");

    if (material == null) {
        response.sendRedirect("MateriaisController?op=2");
        return;
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Editar Material</title>
    </head>

    <body>

    <center>

        <h1>Editar Material</h1>

        <form method="post" action="MateriaisController">

            <input type="hidden" name="op" value="5">

            <input type="hidden" name="id_material" value="<%= material.getIdMaterial() %>">

            <table>

                <tr>
                    <td>Título</td>
                    <td>
                        <input type="text" name="titulo" value="<%= material.getTitulo() %>" required>
                    </td>
                </tr>

                <tr>
                    <td>Descrição</td>
                    <td>
                        <textarea name="descricao" required><%= material.getDescricao() %></textarea>
                    </td>
                </tr>

                <tr>
                    <td>Link externo</td>
                    <td>
                        <input type="text" name="link_externo" value="<%= material.getLinkExterno() %>" required>
                    </td>
                </tr>

                <tr>
                    <td>Tipo</td>
                    <td>
                        <select name="tipo" required>
                            <option value="PDF" <%= material.getTipo().equals("PDF") ? "selected" : "" %>>PDF</option>
                            <option value="VIDEO" <%= material.getTipo().equals("VIDEO") ? "selected" : "" %>>Vídeo</option>
                            <option value="SITE" <%= material.getTipo().equals("SITE") ? "selected" : "" %>>Site</option>
                            <option value="ARTIGO" <%= material.getTipo().equals("ARTIGO") ? "selected" : "" %>>Artigo</option>
                            <option value="OUTRO" <%= material.getTipo().equals("OUTRO") ? "selected" : "" %>>Outro</option>
                        </select>
                    </td>
                </tr>

            </table>

            <br>

            <button type="submit">Salvar Alterações</button>

        </form>

        <br><br>

        <a href="MateriaisController?op=2">Voltar para materiais</a>

    </center>

    </body>
</html>