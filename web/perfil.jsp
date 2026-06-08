<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="VO.Pessoa"%>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String msg = request.getParameter("msg");
    String erro = request.getParameter("erro");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Meu Perfil - StudyHub</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body>

    <%@include file="includes/menu.jsp" %>

    <main class="container">

        <section class="page-header">
            <div>
                <h1 class="page-title">Meu Perfil</h1>
                <p class="page-subtitle">
                    Visualize, edite ou exclua sua conta no StudyHub.
                </p>
            </div>
        </section>

        <% if ("editado".equals(msg)) { %>
            <div class="alert alert-success">
                Perfil atualizado com sucesso!
            </div>
        <% } %>

        <% if ("erro".equals(erro)) { %>
            <div class="alert alert-danger">
                Não foi possível realizar a operação.
            </div>
        <% } %>

        <section class="card">
            <div class="card-body">

                <div class="profile-header">
                    <div class="profile-avatar">
                        <%= usuarioLogado.getNome().substring(0, 1).toUpperCase() %>
                    </div>

                    <div>
                        <h2 class="profile-name">
                            <%= usuarioLogado.getNome() %>
                        </h2>

                        <span class="badge">
                            <%= usuarioLogado.getPapel() %>
                        </span>
                    </div>
                </div>

                <form action="PessoasController?op=4" method="post" class="form">

                    <input type="hidden" name="idPessoa" value="<%= usuarioLogado.getIdPessoa() %>">

                    <div class="form-group">
                        <label>Nome</label>
                        <input
                            type="text"
                            name="nome"
                            value="<%= usuarioLogado.getNome() %>"
                            required>
                    </div>

                    <div class="form-group">
                        <label>Email</label>
                        <input
                            type="email"
                            name="email"
                            value="<%= usuarioLogado.getEmail() %>"
                            required>
                    </div>

                    <div class="form-group">
                        <label>Papel</label>

                        <select name="papel" required>
                            <option value="ALUNO"
                                <%= "ALUNO".equals(usuarioLogado.getPapel()) ? "selected" : "" %>>
                                Aluno
                            </option>

                            <option value="PROFESSOR"
                                <%= "PROFESSOR".equals(usuarioLogado.getPapel()) ? "selected" : "" %>>
                                Professor
                            </option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label>CPF</label>
                        <input
                            type="text"
                            value="<%= usuarioLogado.getCpf() %>"
                            readonly>
                    </div>

                    <div class="form-group">
    <label>Sexo</label>

    <div class="form-check">
        <input 
            type="radio" 
            id="sexoFeminino" 
            name="sexo" 
            value="feminino"
            <%= "feminino".equals(usuarioLogado.getSexo()) ? "checked" : "" %>>
        <label for="sexoFeminino">Feminino</label>
    </div>

    <div class="form-check">
        <input 
            type="radio" 
            id="sexoMasculino" 
            name="sexo" 
            value="masculino"
            <%= "masculino".equals(usuarioLogado.getSexo()) ? "checked" : "" %>>
        <label for="sexoMasculino">Masculino</label>
    </div>

    <div class="form-check">
        <input 
            type="radio" 
            id="sexoOutro" 
            name="sexo" 
            value="outro"
            <%= "outro".equals(usuarioLogado.getSexo()) ? "checked" : "" %>>
        <label for="sexoOutro">Outro</label>
    </div>

    <div class="form-check">
        <input 
            type="radio" 
            id="sexoNaoInformado" 
            name="sexo" 
            value="naoInformado"
            <%= usuarioLogado.getSexo() == null || "naoInformado".equals(usuarioLogado.getSexo()) ? "checked" : "" %>>
        <label for="sexoNaoInformado">Prefiro não dizer</label>
    </div>
</div>

                    <div class="form-group">
                        <label>Data de nascimento</label>
                        <input
                            type="date"
                            name="dataNascimento"
                            value="<%= usuarioLogado.getDataNascimento() == null ? "" : usuarioLogado.getDataNascimento() %>">
                    </div>

                    <div class="profile-info">

                        <div class="profile-row">
                            <strong>Status:</strong>
                            <span><%= usuarioLogado.getStatus() %></span>
                        </div>

                        <div class="profile-row">
                            <strong>Reputação:</strong>
                            <span><%= usuarioLogado.getReputacao() %></span>
                        </div>

                        <div class="profile-row">
                            <strong>Data de cadastro:</strong>
                            <span><%= usuarioLogado.getDataCadastro() %></span>
                        </div>

                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            Salvar alterações
                        </button>
                    </div>

                </form>

            </div>
        </section>

        <section class="card danger-zone">
            <div class="card-body">

                <h2 class="danger-title">Excluir conta</h2>

                <p class="danger-text">
                    Ao excluir sua conta, seu acesso será desativado e você será desconectado do sistema.
                </p>

                <form
                    action="PessoasController?op=5"
                    method="post"
                    onsubmit="return confirm('Tem certeza que deseja excluir sua conta? Essa ação irá desativar seu acesso ao sistema.');">

                    <input type="hidden" name="idPessoa" value="<%= usuarioLogado.getIdPessoa() %>">

                    <button type="submit" class="btn btn-danger">
                        Excluir minha conta
                    </button>

                </form>

            </div>
        </section>

    </main>

</body>
</html>