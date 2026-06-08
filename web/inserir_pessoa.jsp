<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="VO.Pessoa"%>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    boolean admin = "ADMIN".equals(usuarioLogado.getPapel());

    if (!admin) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastrar Usuário - StudyHub</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body>
    <%@include file="includes/menu.jsp" %>

    <div class="container-sm">
        <!-- Cabeçalho -->
        <div class="card">
            <div class="card-body">
                <h1 class="page-title">Cadastrar usuário</h1>
                <p class="page-subtitle">Preencha os dados abaixo para cadastrar um novo usuário no sistema.</p>
            </div>
        </div>

        <!-- Formulario -->
        <div class="card" style="margin-top: 1.5rem;">
            <div class="card-body">
                <form name="frm" method="post" action="PessoasController?op=1">
                    <div class="form-group">
                        <label class="form-label" for="nome">Nome <span class="req">*</span></label>
                        <input type="text" id="nome" name="nome" class="form-control" placeholder="Nome completo" required>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="cpf">CPF <span class="req">*</span></label>
                        <input type="text" id="cpf" name="cpf" class="form-control" placeholder="000.000.000-00" required>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="email">E-mail <span class="req">*</span></label>
                        <input type="email" id="email" name="email" class="form-control" placeholder="seu@email.com" required>
                    </div>

                    <div class="form-group">
                        <label class="form-label">Sexo <span class="req">*</span></label>
                        <div class="form-check">
                            <input type="radio" id="feminino" name="sexo" value="feminino" checked>
                            <label for="feminino">Feminino</label>
                        </div>
                        <div class="form-check">
                            <input type="radio" id="masculino" name="sexo" value="masculino">
                            <label for="masculino">Masculino</label>
                        </div>
                        <div class="form-check">
                            <input type="radio" id="outro" name="sexo" value="outro">
                            <label for="outro">Outro</label>
                        </div>
                        <div class="form-check">
                            <input type="radio" id="naoInformado" name="sexo" value="naoInformado">
                            <label for="naoInformado">Prefiro não dizer</label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="dataNascimento">Data de nascimento <span class="req">*</span></label>
                        <input type="date" id="dataNascimento" name="dataNascimento" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="papel">Tipo de perfil <span class="req">*</span></label>
                        <select id="papel" name="papel" class="form-control" required>
                            <option value="">Selecione uma opção</option>
                            <option value="ALUNO">Aluno</option>
                            <option value="PROFESSOR">Professor</option>
                            <option value="ADMIN">Administrador</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="senha">Senha <span class="req">*</span></label>
                        <input type="password" id="senha" name="senha" class="form-control" placeholder="••••••••" required>
                    </div>

                    <div style="display: flex; gap: 1rem; margin-top: 2rem;">
                        <button type="submit" class="btn btn-primary">Cadastrar</button>
                        <a href="PessoasController?op=2" class="btn btn-outline">Cancelar</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
