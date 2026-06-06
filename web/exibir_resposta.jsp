<%@page import="VO.Pessoa"%>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

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

                out.print("<table width='80%' border='1' cellspacing='0' align='center'>");

                out.print("<tr>");
                out.print("<th>Resposta</th>");
                out.print("<th>Autor</th>");
                out.print("<th>Data</th>");
                out.print("<th>Votos</th>");
                out.print("<th>Ações</th>");
                out.print("</tr>");
                for (int cont = 0; cont < respostas.size(); cont++) {
                    Resposta r = (Resposta) respostas.get(cont);

                    out.print("<tr>");

                    out.print("<td>" + r.getResposta() + "</td>");

                    if (r.getNomePessoa() != null) {
                        out.print("<td>" + r.getNomePessoa() + "</td>");
                    } else {
                        out.print("<td>Usuário não encontrado</td>");
                    }

                    out.print("<td>" + r.getDataPostagem() + "</td>");
                    out.print("<td align='center'>👍 " + r.getQuantidadeGostei() + " | 👎 " + r.getQuantidadeNaoGostei() + "</td>");
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

                    out.print("<input type='hidden' name='tipo' value='GOSTEI'>");

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

                    out.print("<input type='hidden' name='tipo' value='NAO_GOSTEI'>");

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

        <a href="inserir_resposta.jsp?id_pergunta=<%= request.getAttribute("id_pergunta")%>">
            Responder esta pergunta
        </a>

        <br><br>

        <a href="PerguntasController?op=2">
            Voltar para perguntas
        </a>

        <br><br>

        <a href="index.jsp">
            Página inicial
        </a>

    </center>

</body>
</html>