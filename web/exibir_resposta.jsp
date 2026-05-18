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

        <h1 align="center">Respostas</h1>

        <%
            List respostas = (List) request.getAttribute("lista");

            if (respostas != null && !respostas.isEmpty()) {

                out.print("<center>Encontradas: " + respostas.size() + "</center><br><br>");

                out.print("<table width='60%' border='1' cellspacing='0' align='center'>");

                out.print("<tr>");
                out.print("<th>Resposta</th>");
                out.print("<th>Status</th>");
                out.print("<th>Ações</th>");
                out.print("</tr>");

                for (int i = 0; i < respostas.size(); i++) {

                    Resposta r = (Resposta) respostas.get(i);

                    out.print("<tr>");

                    // RESPOSTA
                    out.print("<td>" + r.getResposta() + "</td>");

                    // STATUS
                    String status = "";

                    if (r.getCorreta() == null) {
                        status = "⏳ Ainda não avaliado";
                    } else if (r.getCorreta()) {
                        status = "✅ Gostei";
                    } else {
                        status = "❌ Não gostei";
                    }

                    out.print("<td align='center'>" + status + "</td>");

                    // AÇÕES
                    out.print("<td align='center'>");

                    // BOTÃO GOSTEI
                    out.print("<form action='RespostasController' method='post'>");

                    out.print("<input type='hidden' name='op' value='3'>");

                    out.print("<input type='hidden' name='id_resposta' value='" 
                            + r.getIdResposta() + "'>");

                    out.print("<input type='hidden' name='id_pergunta' value='" 
                            + request.getAttribute("id_pergunta") + "'>");

                    out.print("<input type='hidden' name='correta' value='true'>");

                    out.print("<button type='submit'>👍 Gostei</button>");

                    out.print("</form>");

                    out.print("<br>");

                    // BOTÃO NÃO GOSTEI
                    out.print("<form action='RespostasController' method='post'>");

                    out.print("<input type='hidden' name='op' value='3'>");

                    out.print("<input type='hidden' name='id_resposta' value='" 
                            + r.getIdResposta() + "'>");

                    out.print("<input type='hidden' name='id_pergunta' value='" 
                            + request.getAttribute("id_pergunta") + "'>");

                    out.print("<input type='hidden' name='correta' value='false'>");

                    out.print("<button type='submit'>👎 Não gostei</button>");

                    out.print("</form>");

                    out.print("</td>");

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

            <a href="index.html">
                Página inicial
            </a>

        </center>

    </body>
</html>