<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="VO.Resposta"%>
<%@page import="VO.Pessoa"%>
<%@page import="java.text.SimpleDateFormat"%>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    ArrayList<Resposta> lista = (ArrayList<Resposta>) request.getAttribute("lista");

    Integer id_pergunta = (Integer) request.getAttribute("id_pergunta");

    if (id_pergunta == null) {
        String idParam = request.getParameter("id_pergunta");

        if (idParam != null) {
            id_pergunta = Integer.parseInt(idParam);
        }
    }

    if (lista == null) {
        response.sendRedirect("PerguntasController?op=2");
        return;
    }
%>

<!DOCTYPE html>
<html lang="pt-BR">

    <head>
        <meta charset="UTF-8">
        <title>Respostas - StudyHub</title>
        <link rel="stylesheet" href="assets/css/style.css">
    </head>

    <body>

        <%@include file="includes/menu.jsp" %>

        <main class="container">

            <section class="page-header">
                <div>
                    <h1 class="page-title">Respostas</h1>
                </div>

                <p class="page-subtitle">
                    Veja as respostas para esta pergunta
                </p>
            </section>

            <section class="card">
                <div class="card-body">

                    <p class="text-muted">
                        Encontradas:
                        <strong><%= lista.size()%></strong>
                        resposta(s)
                    </p>

                    <% if (lista.isEmpty()) { %>

                    <div class="empty-state">
                        <p>Nenhuma resposta cadastrada para esta pergunta.</p>
                    </div>

                    <% } else { %>

                    <% for (Resposta r : lista) {%>

                    <div class="answer-card">

                        <div class="answer-content">
                            <p><%= r.getResposta()%></p>
                        </div>

                        <div class="answer-footer">

                            <div class="answer-meta">
                                <span>
                                    Autor: <strong><%= r.getNomePessoa()%></strong>

                                    <span class="reputation-mini">
                                        ⭐ <%= r.getReputacaoAutor()%> pts
                                    </span>
                                </span>

                                <span>
                                    <%
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                    %>

                                    <%= sdf.format(r.getDataPostagem())%>
                                </span>

                                <% if ("GOSTEI".equals(r.getVotoUsuario())) { %>

                                <span class="badge badge-success">
                                    ✅ Você curtiu
                                </span>

                                <% } else if ("NAO_GOSTEI".equals(r.getVotoUsuario())) { %>

                                <span class="badge badge-danger">
                                    👎 Você não curtiu
                                </span>

                                <% } else { %>

                                <span class="badge badge-neutral">
                                    ⏳ Você ainda não votou
                                </span>

                                <% }%>


                            </div>

                            <div class="vote-area">

                                <form method="post" action="RespostasController" class="vote-form">
                                    <input type="hidden" name="op" value="3">
                                    <input type="hidden" name="id_resposta" value="<%= r.getIdResposta()%>">
                                    <input type="hidden" name="id_pergunta" value="<%= id_pergunta%>">
                                    <input type="hidden" name="tipo" value="GOSTEI">

                                    <button type="submit"
                                            class="vote-btn up <%= "GOSTEI".equals(r.getVotoUsuario()) ? "active" : ""%>">
                                        👍 <%= r.getQuantidadeGostei()%>
                                    </button>
                                </form>

                                <form method="post" action="RespostasController" class="vote-form">
                                    <input type="hidden" name="op" value="3">
                                    <input type="hidden" name="id_resposta" value="<%= r.getIdResposta()%>">
                                    <input type="hidden" name="id_pergunta" value="<%= id_pergunta%>">
                                    <input type="hidden" name="tipo" value="NAO_GOSTEI">

                                    <button type="submit"
                                            class="vote-btn down <%= "NAO_GOSTEI".equals(r.getVotoUsuario()) ? "active" : ""%>">
                                        👎 <%= r.getQuantidadeNaoGostei()%>
                                    </button>
                                </form>

                            </div>

                        </div>                          

                    </div>

                    <% } %>

                    <% }%>

                </div>
            </section>

            <div class="page-actions">

                <a href="inserir_resposta.jsp?id_pergunta=<%= id_pergunta%>" class="btn btn-primary">
                    Responder esta pergunta
                </a>

                <a href="PerguntasController?op=2" class="btn btn-secondary">
                    Voltar para perguntas
                </a>

                <a href="index.jsp" class="btn btn-secondary">
                    Página inicial
                </a>

            </div>

        </main>

    </body>
</html>