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
    <title>StudyHub - Início</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body>

    <%@include file="includes/menu.jsp" %>

    <main class="home-container">

        <section class="home-welcome">
            <h1>Olá, <%= usuarioLogado.getNome() %></h1>
            <p>Bem-vindo ao StudyHub. Escolha uma área para continuar.</p>
        </section>

        <section class="portal-grid">

            <a href="comunidade.jsp" class="portal-card comunidade-card">
                <div class="portal-icon">💬</div>

                <h2>Comunidade</h2>

                <p>
                    Espaço para publicar dúvidas, responder perguntas e acompanhar discussões com outros usuários.
                </p>

                <div class="portal-tags">
                    <span>Perguntas</span>
                    <span>Discussões</span>
                </div>
            </a>

            <a href="biblioteca.jsp" class="portal-card biblioteca-card">
                <div class="portal-icon">📚</div>

                <h2>Biblioteca</h2>

                <p>
                    Área para consultar e cadastrar materiais digitais de apoio aos estudos.
                </p>

                <div class="portal-tags">
                    <span>Materiais</span>
                    <span>Referências</span>
                </div>
            </a>

        </section>

    </main>

</body>
</html>