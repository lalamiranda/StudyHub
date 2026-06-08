<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="VO.Pessoa"%>

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
    <title>StudyHub - Comunidade</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body>

    <%@include file="includes/menu.jsp" %>

    <main class="page-container">

        <section class="page-hero">
            <span class="page-label">Comunidade</span>
            <h1>Compartilhe dúvidas e participe das discussões</h1>
            <p>
                Aqui você pode visualizar perguntas, criar novas dúvidas e interagir com outros usuários do StudyHub.
            </p>
        </section>

        <section class="action-grid">

            <div class="action-card">
                <div class="action-icon">❓</div>
                <h2>Ver perguntas</h2>
                <p>
                    Consulte as perguntas cadastradas por alunos e professores.
                </p>

                <a href="PerguntasController?op=2" class="btn btn-primary">
                    Acessar perguntas
                </a>
            </div>

            <div class="action-card">
                <div class="action-icon">✍️</div>
                <h2>Nova pergunta</h2>
                <p>
                    Publique uma dúvida para que outros usuários possam responder.
                </p>

                <a href="inserir_pergunta.jsp" class="btn btn-primary">
                    Fazer pergunta
                </a>
            </div>

        </section>

    </main>

</body>
</html>