<%@page import="VO.Pergunta"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listagem</title>
    </head>
    <body>

        <%
            List perguntas = (List) request.getAttribute("lista");
            if (perguntas != null) {
                out.print("<center>Achados: " + perguntas.size() + "</center><br><br><br>");
                out.print("<table width=\"50%\" border=\"1\" cellspacing=\"0\" align=\"center\">");
                for (int cont = 0; cont < perguntas.size(); cont++) {
                    Pergunta p = new Pergunta();
                    p = (Pergunta) perguntas.get(cont);
                    out.print("<tr>");
                    out.print("<td>" + p.getTitulo() + "</td>");
                    out.print("<td>" + p.getDescricao() + "</td>");
                    out.print("<td><a href='RespostasController?op=2&id_pergunta=" + p.getIdPergunta() + "'>Ver respostas</a></td>");
                    out.print("</tr>");
                }
                out.print("</table>");
            }            
        %>

        <br><br><br>
        <center><a href="index.html">Página inicial</a></center>

    </body>
</html>