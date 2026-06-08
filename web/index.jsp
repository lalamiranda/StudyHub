<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="VO.Pessoa"%>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    boolean admin = "ADMIN".equals(usuarioLogado.getPapel());
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>StudyHub - Portal de Estudos</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body>
    <%@include file="includes/menu.jsp" %>

    <div class="container">
        <!-- Entrada -->
        <div class="welcome-card">
            <h1>Olá, <%= usuarioLogado.getNome() %></h1>
            <p>Bem-vindo ao StudyHub. Escolha uma área para continuar.</p>
        </div>

        <!-- Comunidade -->
        <section class="area-section">
            <div class="area-header">
                <h2 class="area-title">Comunidade</h2>
                <p class="area-description">
                    Espaço para publicar dúvidas, responder perguntas e acompanhar discussões.
                </p>
            </div>

            <div class="cards-row">
                <div class="option-card">
                    <h3>Perguntas</h3>
                    <p>
                        Veja as dúvidas publicadas por alunos e professores.
                    </p>
                    <a class="btn btn-primary" href="PerguntasController?op=2">
                        Ver perguntas
                    </a>
                </div>

                <div class="option-card">
                    <h3>Nova pergunta</h3>
                    <p>
                        Publique uma dúvida para que outros usuários possam responder.
                    </p>
                    <a class="btn btn-primary" href="inserir_pergunta.jsp">
                        Fazer pergunta
                    </a>
                </div>
            </div>
        </section>

        <!-- Biblioteca -->
        <section class="area-section">
            <div class="area-header">
                <h2 class="area-title">Biblioteca</h2>
                <p class="area-description">
                    Área para consultar e cadastrar materiais de apoio aos estudos.
                </p>
            </div>

            <div class="cards-row">
                <div class="option-card">
                    <h3>Materiais</h3>
                    <p>
                        Consulte materiais de estudo cadastrados no sistema.
                    </p>
                    <a class="btn btn-primary" href="MateriaisController?op=2">
                        Ver materiais
                    </a>
                </div>

                <div class="option-card">
                    <h3>Novo material</h3>
                    <p>
                        Cadastre links, arquivos ou referências úteis para estudo.
                    </p>
                    <a class="btn btn-primary" href="inserir_material.jsp">
                        Cadastrar material
                    </a>
                </div>
            </div>
        </section>

        <% if (admin) { %>
            <!-- Administração -->
            <section class="area-section">
                <div class="area-header">
                    <h2 class="area-title">Administração</h2>
                    <p class="area-description">
                        Área reservada para gerenciamento do sistema.
                    </p>
                </div>

                <div class="cards-row">
                    <div class="option-card">
                        <h3>Usuários</h3>
                        <p>
                            Visualize os usuários cadastrados no StudyHub.
                        </p>
                        <a class="btn btn-primary" href="PessoasController?op=2">
                            Ver usuários
                        </a>
                    </div>

                    <div class="option-card">
                        <h3>Novo usuário</h3>
                        <p>
                            Cadastre alunos, professores ou administradores.
                        </p>
                        <a class="btn btn-primary" href="inserir_pessoa.jsp">
                            Cadastrar usuário
                        </a>
                    </div>
                </div>
            </section>
        <% } %>
    </div>
</body>
</html>
