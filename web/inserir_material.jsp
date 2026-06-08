<%@page import="VO.Pessoa"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastrar Material - StudyHub</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body>
    <%@include file="includes/menu.jsp" %>

    <div class="container-sm">
        <!-- Cabeçalho -->
        <div class="card">
            <div class="card-body">
                <h1 class="page-title">Cadastrar Material</h1>
                <p class="page-subtitle">Compartilhe um material de estudo com a comunidade.</p>
            </div>
        </div>

        <!-- Formulario -->
        <div class="card" style="margin-top: 1.5rem;">
            <div class="card-body">
                <form method="post" action="MateriaisController">
                    <input type="hidden" name="op" value="1">

                    <div class="form-group">
                        <label class="form-label" for="titulo">Título <span class="req">*</span></label>
                        <input type="text" id="titulo" name="titulo" class="form-control" placeholder="Ex: Guia de Java Avançado" required>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="descricao">Descrição <span class="req">*</span></label>
                        <textarea id="descricao" name="descricao" class="form-control" rows="4" placeholder="Descreva o material e seu conteúdo..." required></textarea>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="link_externo">Link externo <span class="req">*</span></label>
                        <input type="url" id="link_externo" name="link_externo" class="form-control" placeholder="https://exemplo.com/material" required>
                        <div class="form-hint">Cole o link para o material (PDF, vídeo, artigo, etc)</div>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="tipo">Tipo <span class="req">*</span></label>
                        <select id="tipo" name="tipo" class="form-control" required>
                            <option value="">Selecione um tipo</option>
                            <option value="PDF">PDF</option>
                            <option value="VIDEO">Vídeo</option>
                            <option value="SITE">Site</option>
                            <option value="ARTIGO">Artigo</option>
                            <option value="OUTRO">Outro</option>
                        </select>
                    </div>

                    <div style="display: flex; gap: 1rem; margin-top: 2rem;">
                        <button type="submit" class="btn btn-primary">Cadastrar</button>
                        <a href="biblioteca.jsp" class="btn btn-outline">Cancelar</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
