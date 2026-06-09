<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="VO.Pergunta"%>
<%@page import="VO.Tag"%>
<%@page import="VO.Pessoa"%>
<%@page import="DAO.TagsDAO"%>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    TagsDAO tagsDAO = new TagsDAO();
    ArrayList<Tag> tagsFiltro = tagsDAO.listar();

    List perguntas = (List) request.getAttribute("lista");

    String idTagSelecionada = request.getParameter("id_tag");

    if (idTagSelecionada == null) {
        idTagSelecionada = "";
    }
%>

<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Perguntas - StudyHub</title>
        <link rel="stylesheet" href="assets/css/style.css">
    </head>

    <body>
        <%@include file="includes/menu.jsp" %>

        <div class="container">
            <!-- Cabeçalho -->
            <div class="card">
                <div class="card-body">
                    <h1 class="page-title">Comunidade</h1>
                    <p class="page-subtitle">Veja as dúvidas publicadas por alunos e professores.</p>

                    <div style="display: flex; gap: 1rem; margin-top: 1.5rem; flex-wrap: wrap;">
                        <a class="btn btn-primary" href="inserir_pergunta.jsp">Fazer nova pergunta</a>
                        <a class="btn btn-outline" href="comunidade.jsp">Voltar</a>
                    </div>
                </div>
            </div>

            <!-- Filtro -->
            <div class="card" style="margin-top: 1.5rem;">
                <div class="card-body">
                    <h3 style="font-size: 1rem; font-weight: 600; margin-bottom: 1rem; color: var(--text); border-left: 3px solid var(--accent); padding-left: 0.75rem;">
                        Filtrar por tag
                    </h3>
                    <div class="tag-filters">

                        <a href="PerguntasController?op=2"
                           class="tag-filter <%= idTagSelecionada.equals("") ? "active" : ""%>">
                            Todas
                        </a>

                        <% if (tagsFiltro != null && !tagsFiltro.isEmpty()) { %>

                        <% for (Tag tag : tagsFiltro) {%>

                        <a href="PerguntasController?op=2&id_tag=<%= tag.getIdTag()%>"
                           class="tag-filter <%= idTagSelecionada.equals(String.valueOf(tag.getIdTag())) ? "active" : ""%>">
                            <%= tag.getNome()%>
                        </a>

                        <% } %>

                        <% } %>

                    </div>  
                </div>
            </div>

            <!-- Lista questao -->
            <div class="card" style="margin-top: 1.5rem;">
                <div class="card-body">
                    <div class="list-header">
                        <h3 style="font-size: 1rem; font-weight: 600; margin-bottom: 0.5rem; color: var(--text); border-left: 3px solid var(--accent); padding-left: 0.75rem;">
                            Perguntas cadastradas
                        </h3>
                        <p style="font-size: 0.85rem; color: var(--text-muted); margin-bottom: 1.5rem;">
                            <% if (perguntas != null) {%>
                            Foram encontradas <strong><%= perguntas.size()%></strong> pergunta(s).
                            <% } else { %>
                            Nenhuma pergunta foi carregada.
                            <% } %>
                        </p>
                    </div>

                    <% if (perguntas == null || perguntas.isEmpty()) { %>
                    <div class="empty-state">
                        <p style="font-size: 1rem; color: var(--text-muted);">
                            Nenhuma pergunta cadastrada até o momento.
                        </p>
                        <a class="btn btn-primary" href="inserir_pergunta.jsp" style="margin-top: 1rem;">
                            Criar primeira pergunta
                        </a>
                    </div>
                    <% } else { %>
                    <div class="question-list">
                        <%
                            for (int cont = 0; cont < perguntas.size(); cont++) {
                                Pergunta p = (Pergunta) perguntas.get(cont);
                                boolean usuarioEhAutor = usuarioLogado.getIdPessoa() == p.getIdPessoa();
                        %>
                        <div class="q-card">
                            <h4 class="q-card-title"><%= p.getTitulo()%></h4>
                            <p class="q-card-body"><%= p.getDescricao()%></p>
                            <div class="q-card-meta">
                                <span>
                                    Autor: <strong><%= p.getNomePessoa()%></strong>

                                    <span class="reputation-mini">
                                        ⭐ <%= p.getReputacaoPessoa()%> pts
                                    </span>
                                </span>

                                <% if (p.getTags() != null && !p.getTags().isEmpty()) {%>
                                <span>
                                    Tags: <%= p.getTags()%>
                                </span>
                                <% }%>

                                <div style="margin-left: auto; display: flex; gap: 0.75rem; align-items: center;">

                                    <a href="RespostasController?op=2&id_pergunta=<%= p.getIdPergunta()%>"
                                       style="color: var(--accent); text-decoration: none; font-weight: 600;">
                                        Ver respostas
                                    </a>

                                    <% if (usuarioEhAutor) {%>

                                    <a href="PerguntasController?op=4&id_pergunta=<%= p.getIdPergunta()%>"
                                       class="btn-action btn-action-edit">
                                        Editar
                                    </a>

                                    <a href="PerguntasController?op=6&id_pergunta=<%= p.getIdPergunta()%>"
                                       class="btn-action btn-action-delete"
                                       onclick="return confirm('Tem certeza que deseja excluir esta pergunta?');">
                                        Excluir
                                    </a>

                                    <% } %>

                                </div>
                            </div>
                        </div>
                        <%
                            }
                        %>
                    </div>
                    <% }%>
                </div>
            </div>
        </div>
    </body>
</html>
