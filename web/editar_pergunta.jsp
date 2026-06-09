<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="VO.Pergunta"%>
<%@page import="VO.Pessoa"%>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    Pergunta pergunta = (Pergunta) request.getAttribute("pergunta");

    if (pergunta == null) {
        response.sendRedirect("PerguntasController?op=2");
        return;
    }
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Editar Pergunta - StudyHub</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body>

    <%@include file="includes/menu.jsp" %>

    <main class="container">

        <section class="page-header">
            <div>
                <h1 class="page-title">Editar pergunta</h1>
                <p class="page-subtitle">
                    Atualize o título e a descrição da sua pergunta.
                </p>
            </div>
        </section>

        <div class="card">
            <div class="card-body">

                <form action="PerguntasController?op=5" method="post">

                    <input type="hidden" name="id_pergunta" value="<%= pergunta.getIdPergunta()%>">

                    <div class="form-group">
                        <label class="form-label" for="titulo">Título</label>
                        <input 
                            type="text"
                            id="titulo"
                            name="titulo"
                            class="form-control"
                            value="<%= pergunta.getTitulo()%>"
                            required>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="descricao">Descrição</label>
                        <textarea 
                            id="descricao"
                            name="descricao"
                            class="form-control"
                            rows="6"
                            required><%= pergunta.getDescricao()%></textarea>
                    </div>

                    <div style="display: flex; gap: 1rem; margin-top: 1.5rem; flex-wrap: wrap;">
                        <button type="submit" class="btn btn-primary">
                            Salvar alterações
                        </button>

                        <a href="PerguntasController?op=2" class="btn btn-outline">
                            Cancelar
                        </a>
                    </div>

                </form>

            </div>
        </div>

    </main>

</body>
</html>