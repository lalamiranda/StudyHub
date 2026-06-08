<%@page import="VO.Pessoa"%>

<%
    Pessoa usuarioMenu = (Pessoa) session.getAttribute("usuarioLogado");

    boolean adminMenu = false;

    if (usuarioMenu != null && "ADMIN".equals(usuarioMenu.getPapel())) {
        adminMenu = true;
    }
%>

<div class="navbar">
    <div class="navbar-brand">
        <img src="assets/img/studyhub-icon.svg" alt="StudyHub" width="28" height="28" />
        StudyHub
    </div>

    <div class="navbar-links">
        <a href="index.jsp">Início</a>

        <span class="nav-group-label">Comunidade</span>
        <a href="PerguntasController?op=2">Perguntas</a>
        <a href="inserir_pergunta.jsp">Nova pergunta</a>

        <span class="nav-group-label">Biblioteca</span>
        <a href="MateriaisController?op=2">Materiais</a>
        <a href="inserir_material.jsp">Novo material</a>

        <% if (adminMenu) { %>
            <span class="nav-group-label">Admin</span>
            <a href="PessoasController?op=2">Usuários</a>
            <a href="inserir_pessoa.jsp">Novo usuário</a>
        <% } %>
    </div>

    <div class="navbar-right">
    <div class="navbar-user">
        <% if (usuarioMenu != null) { %>
            <span><%= usuarioMenu.getNome() %></span>
        <% } %>
    </div>

    <a href="perfil.jsp" class="navbar-profile">Meu perfil</a>

    <a href="LogoutController" class="navbar-logout">Sair</a>
</div>
</div>
        
