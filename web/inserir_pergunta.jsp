<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="VO.Tag"%>
<%@page import="DAO.TagsDAO"%>
<%@page import="VO.Pessoa"%>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    TagsDAO tagsDAO = new TagsDAO();
    ArrayList<Tag> tags = tagsDAO.listar();
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nova Pergunta - StudyHub</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body>
    <%@include file="includes/menu.jsp" %>

    <div class="container-sm">
        <!-- Cabeçalho -->
        <div class="card">
            <div class="card-body">
                <h1 class="page-title">Fazer nova pergunta</h1>
                <p class="page-subtitle">Publique uma dúvida para que alunos e professores possam responder.</p>
            </div>
        </div>

        <!-- Formulario -->
        <div class="card" style="margin-top: 1.5rem;">
            <div class="card-body">
                <form action="PerguntasController?op=3" method="post">
                    <div class="form-group">
                        <label class="form-label" for="titulo">Título <span class="req">*</span></label>
                        <input type="text" id="titulo" name="titulo" class="form-control" placeholder="Ex: Como funciona o padrão MVC?" required>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="descricao">Descrição <span class="req">*</span></label>
                        <textarea id="descricao" name="descricao" class="form-control" rows="6" placeholder="Explique sua dúvida com detalhes..." required></textarea>
                    </div>

                    <div class="form-group">
                        <label class="form-label">Tags</label>
                        <p class="form-hint">Selecione uma ou mais tags relacionadas à pergunta.</p>

                        <% if (tags != null && !tags.isEmpty()) { %>
                            <div class="checkbox-group">
                                <% for (Tag tag : tags) { %>
                                    <label class="checkbox-item">
                                        <input type="checkbox" name="tags" value="<%= tag.getIdTag() %>">
                                        <span><%= tag.getNome() %></span>
                                    </label>
                                <% } %>
                            </div>
                        <% } else { %>
                            <p class="empty-small">Nenhuma tag cadastrada.</p>
                        <% } %>
                    </div>

                    <div style="display: flex; gap: 1rem; margin-top: 2rem;">
                        <button type="submit" class="btn btn-primary">Postar pergunta</button>
                        <a href="comunidade.jsp" class="btn btn-outline">Cancelar</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
