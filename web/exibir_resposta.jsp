<%@page import="VO.Resposta"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Respostas</title>
</head>
<body>

    <h1>Respostas</h1>

    <%
        List respostas = (List) request.getAttribute("lista");

        if (respostas != null && !respostas.isEmpty()) {

            out.print("<center>Encontradas: " + respostas.size() + "</center><br><br>");
            out.print("<table width=\"50%\" border=\"1\" cellspacing=\"0\" align=\"center\">");
            out.print("<tr><th>Resposta</th><th>Correta?</th></tr>");

            for (int i = 0; i < respostas.size(); i++) {

                Resposta r = (Resposta) respostas.get(i);

                out.print("<tr>");
                out.print("<td>" + r.getResposta() + "</td>");
                out.print("<td>" + (r.isCorreta() ? "✅ Sim" : "❌ Não") + "</td>");
                out.print("</tr>");
            }

            out.print("</table>");

        } else {
            out.print("<center>Nenhuma resposta ainda.</center>");
        }
    %>

    <br><br>

    <center>
        <a href="inserir_resposta.jsp?id_pergunta=${param.id_pergunta}">
            Responder esta pergunta
        </a>

        <br><br>

        <a href="PerguntasController?op=2">
            Voltar para perguntas
        </a>

        <br><br>

        <a href="index.html">Página inicial</a>
    </center>

</body>
</html>