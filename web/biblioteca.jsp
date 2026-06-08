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
    <title>StudyHub - Biblioteca</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body>

    <%@include file="includes/menu.jsp" %>

    <main class="page-container">

        <section class="page-hero">
            <span class="page-label">Biblioteca</span>
            <h1>Materiais de apoio aos estudos</h1>
            <p>
                Consulte, organize e cadastre materiais digitais, links, arquivos e referências úteis.
            </p>
        </section>

        <section class="action-grid">

            <div class="action-card">
                <div class="action-icon">📖</div>
                <h2>Ver materiais</h2>
                <p>
                    Acesse os materiais de estudo cadastrados no sistema.
                </p>

                <a href="MateriaisController?op=2" class="btn btn-primary">
                    Acessar materiais
                </a>
            </div>

            <div class="action-card">
                <div class="action-icon">➕</div>
                <h2>Novo material</h2>
                <p>
                    Cadastre links, arquivos ou referências para ajudar outros usuários.
                </p>

                <a href="inserir_material.jsp" class="btn btn-primary">
                    Cadastrar material
                </a>
            </div>

        </section>

    </main>

</body>
</html>