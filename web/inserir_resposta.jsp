<%@page import="VO.Pessoa"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String id_pergunta = request.getParameter("id_pergunta");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Responder Pergunta - StudyHub</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body>
    <%@include file="includes/menu.jsp" %>

    <div class="container-sm">
        <!-- Cabeçalho -->
        <div class="card">
            <div class="card-body">
                <h1 class="page-title">Responder Pergunta</h1>
                <p class="page-subtitle">Compartilhe seu conhecimento com a comunidade.</p>
            </div>
        </div>

        <!-- Formulario -->
        <div class="card" style="margin-top: 1.5rem;">
            <div class="card-body">
                <form action="RespostasController" method="post">
                    <input type="hidden" name="id_pergunta" value="<%= id_pergunta != null ? id_pergunta : "" %>">
                    <input type="hidden" name="op" value="1">

                    <div class="form-group">
                        <label class="form-label" for="resposta">Sua Resposta <span class="req">*</span></label>
                        <textarea id="resposta" name="resposta" class="form-control" rows="6" placeholder="Escreva sua resposta aqui..." required></textarea>
                        <div class="form-hint">Seja claro e detalhado em sua resposta</div>
                    </div>

                    <div style="display: flex; gap: 1rem; margin-top: 2rem;">
                        <button type="submit" class="btn btn-primary">Postar Resposta</button>
                        <a href="RespostasController?op=2&id_pergunta=<%= id_pergunta != null ? id_pergunta : "" %>" class="btn btn-outline">Cancelar</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
