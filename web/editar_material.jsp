<%@page import="VO.Pessoa"%>
<%@page import="VO.Material"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

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

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Material - StudyHub</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body>
    <%@include file="includes/menu.jsp" %>

    <div class="container-sm">
        <!-- Page Header -->
        <div class="card">
            <div class="card-body">
                <h1 class="page-title">Editar Material</h1>
                <p class="page-subtitle">Atualize as informações do material.</p>
            </div>
        </div>

        <!-- Formulario -->
        <div class="card" style="margin-top: 1.5rem;">
            <div class="card-body">
                <form method="post" action="MateriaisController">
                    <input type="hidden" name="op" value="5">
                    <input type="hidden" name="id_material" value="<%= material.getIdMaterial() %>">

                    <div class="form-group">
                        <label class="form-label" for="titulo">Título <span class="req">*</span></label>
                        <input type="text" id="titulo" name="titulo" class="form-control" value="<%= material.getTitulo() %>" required>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="descricao">Descrição <span class="req">*</span></label>
                        <textarea id="descricao" name="descricao" class="form-control" rows="4" required><%= material.getDescricao() %></textarea>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="link_externo">Link externo <span class="req">*</span></label>
                        <input type="url" id="link_externo" name="link_externo" class="form-control" value="<%= material.getLinkExterno() %>" required>
                        <div class="form-hint">Cole o link para o material (PDF, vídeo, artigo, etc)</div>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="tipo">Tipo <span class="req">*</span></label>
                        <select id="tipo" name="tipo" class="form-control" required>
                            <option value="PDF" <%= material.getTipo().equals("PDF") ? "selected" : "" %>>PDF</option>
                            <option value="VIDEO" <%= material.getTipo().equals("VIDEO") ? "selected" : "" %>>Vídeo</option>
                            <option value="SITE" <%= material.getTipo().equals("SITE") ? "selected" : "" %>>Site</option>
                            <option value="ARTIGO" <%= material.getTipo().equals("ARTIGO") ? "selected" : "" %>>Artigo</option>
                            <option value="OUTRO" <%= material.getTipo().equals("OUTRO") ? "selected" : "" %>>Outro</option>
                        </select>
                    </div>

                    <div style="display: flex; gap: 1rem; margin-top: 2rem;">
                        <button type="submit" class="btn btn-primary">Salvar Alterações</button>
                        <a href="MateriaisController?op=2" class="btn btn-outline">Cancelar</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
