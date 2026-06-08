<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="VO.Resposta"%>
<%@page import="VO.Pessoa"%>

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
                    <strong><%= lista.size() %></strong>
                    resposta(s)
                </p>

                <% if (lista.isEmpty()) { %>

                    <div class="empty-state">
                        <p>Nenhuma resposta cadastrada para esta pergunta.</p>
                    </div>

                <% } else { %>

                    <% for (Resposta r : lista) { %>

                        <div class="answer-card">

                            <div class="answer-content">
                                <p><%= r.getResposta() %></p>
                            </div>

                            <div class="answer-footer">

                                <div class="answer-meta">
                                    <span>
                                        Autor:
                                        <strong><%= r.getNomePessoa() %></strong>
                                    </span>

                                    <span>
                                        <%= r.getDataPostagem() %>
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

                                    <% } %>

                                    <% if (r.getCorreta() != null) { %>

                                        <% if (r.getCorreta()) { %>
                                            <span class="badge badge-success">
                                                ✔ Resposta correta
                                            </span>
                                        <% } else { %>
                                            <span class="badge badge-danger">
                                                ✘ Resposta incorreta
                                            </span>
                                        <% } %>

                                    <% } %>
                                </div>

                                <div class="vote-area">

                                    <form method="post" action="RespostasController" class="vote-form">
                                        <input type="hidden" name="op" value="3">
                                        <input type="hidden" name="id_resposta" value="<%= r.getIdResposta() %>">
                                        <input type="hidden" name="id_pergunta" value="<%= id_pergunta %>">
                                        <input type="hidden" name="tipo" value="GOSTEI">

                                        <button type="submit"
                                                class="vote-btn up <%= "GOSTEI".equals(r.getVotoUsuario()) ? "active" : "" %>">
                                            👍 <%= r.getQuantidadeGostei() %>
                                        </button>
                                    </form>

                                    <form method="post" action="RespostasController" class="vote-form">
                                        <input type="hidden" name="op" value="3">
                                        <input type="hidden" name="id_resposta" value="<%= r.getIdResposta() %>">
                                        <input type="hidden" name="id_pergunta" value="<%= id_pergunta %>">
                                        <input type="hidden" name="tipo" value="NAO_GOSTEI">

                                        <button type="submit"
                                                class="vote-btn down <%= "NAO_GOSTEI".equals(r.getVotoUsuario()) ? "active" : "" %>">
                                            👎 <%= r.getQuantidadeNaoGostei() %>
                                        </button>
                                    </form>

                                </div>

                            </div>

                            <% if ("PROFESSOR".equals(usuarioLogado.getPapel()) || "ADMIN".equals(usuarioLogado.getPapel())) { %>

                                <div class="answer-actions">

                                    <form method="post" action="RespostasController">
                                        <input type="hidden" name="op" value="4">
                                        <input type="hidden" name="id_resposta" value="<%= r.getIdResposta() %>">
                                        <input type="hidden" name="id_pergunta" value="<%= id_pergunta %>">
                                        <input type="hidden" name="correta" value="true">

                                        <button type="submit" class="btn btn-success">
                                            Marcar correta
                                        </button>
                                    </form>

                                    <form method="post" action="RespostasController">
                                        <input type="hidden" name="op" value="4">
                                        <input type="hidden" name="id_resposta" value="<%= r.getIdResposta() %>">
                                        <input type="hidden" name="id_pergunta" value="<%= id_pergunta %>">
                                        <input type="hidden" name="correta" value="false">

                                        <button type="submit" class="btn btn-danger">
                                            Marcar incorreta
                                        </button>
                                    </form>

                                    <form method="post" action="RespostasController">
                                        <input type="hidden" name="op" value="4">
                                        <input type="hidden" name="id_resposta" value="<%= r.getIdResposta() %>">
                                        <input type="hidden" name="id_pergunta" value="<%= id_pergunta %>">
                                        <input type="hidden" name="correta" value="null">

                                        <button type="submit" class="btn btn-secondary">
                                            Limpar correção
                                        </button>
                                    </form>

                                </div>

                            <% } %>

                        </div>

                    <% } %>

                <% } %>

            </div>
        </section>

        <div class="page-actions">

            <a href="inserir_resposta.jsp?id_pergunta=<%= id_pergunta %>" class="btn btn-primary">
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