<%@page import="VO.Pessoa"%>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<%@page import="VO.Material"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Materiais</title>
    </head>

    <body>

        <h1 align="center">Biblioteca Digital</h1>

        <%
            List materiais = (List) request.getAttribute("lista");

            if (materiais != null && !materiais.isEmpty()) {

                out.print("<center>Materiais encontrados: " + materiais.size() + "</center><br>");

                out.print("<table width='70%' border='1' cellspacing='0' align='center'>");

                out.print("<tr>");
                out.print("<th>Título</th>");
                out.print("<th>Descrição</th>");
                out.print("<th>Tipo</th>");
                out.print("<th>Link</th>");
                out.print("<th>Cadastrado por</th>");
                out.print("<th>Data</th>");
                out.print("<th>Ações</th>");
                out.print("</tr>");

                for (int i = 0; i < materiais.size(); i++) {

                    Material m = (Material) materiais.get(i);

                    out.print("<tr>");
                    out.print("<td>" + m.getTitulo() + "</td>");
                    out.print("<td>" + m.getDescricao() + "</td>");
                    out.print("<td>" + m.getTipo() + "</td>");
                    out.print("<td><a href='" + m.getLinkExterno() + "' target='_blank'>Acessar</a></td>");
                    out.print("<td>" + m.getNomePessoa() + "</td>");
                    out.print("<td>" + m.getDataUpload() + "</td>");

                    out.print("<td>");

                    out.print("<a href='MateriaisController?op=4&id_material=" + m.getIdMaterial() + "'>");
                    out.print("Editar");
                    out.print("</a>");

                    out.print(" | ");

                    out.print("<a href='MateriaisController?op=3&id_material=" + m.getIdMaterial() + "' ");
                    out.print("onclick=\"return confirm('Tem certeza que deseja excluir este material?');\">");
                    out.print("Excluir");
                    out.print("</a>");

                    out.print("</td>");

                    out.print("</tr>");
                }

                out.print("</table>");

            } else {
                out.print("<center>Nenhum material cadastrado.</center>");
            }
        %>

        <br><br>

    <center>
        <a href="inserir_material.jsp">Cadastrar novo material</a>
        <br><br>
        <a href="index.jsp">Página inicial</a>
    </center>

</body>
</html>