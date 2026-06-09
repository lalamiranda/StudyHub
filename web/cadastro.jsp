<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cadastro - StudyHub</title>
        <link rel="stylesheet" href="assets/css/style.css">
    </head>

    <body class="no-pad">
        <div class="auth-wrap">
            <div class="auth-card">
                <div class="auth-logo">StudyHub</div>

                <h2 style="font-size: 1.5rem; margin-bottom: 0.5rem; color: var(--text); text-align: center;">Criar conta</h2>
                <p style="color: var(--text-muted); margin-bottom: 1.5rem; font-size: 0.95rem; text-align: center;">
                    Cadastre-se para acessar o StudyHub
                </p>

                <% if ("cpf".equals(request.getParameter("erro"))) { %>
                <div class="message message-error" style="background: var(--danger-soft); color: var(--danger); padding: 0.75rem; border-radius: var(--radius); margin-bottom: 1rem; border-left: 4px solid var(--danger);">
                    CPF inválido. Verifique os dados informados.
                </div>
                <% } else if ("1".equals(request.getParameter("erro"))) { %>
                <div class="message message-error" style="background: var(--danger-soft); color: var(--danger); padding: 0.75rem; border-radius: var(--radius); margin-bottom: 1rem; border-left: 4px solid var(--danger);">
                    Não foi possível criar a conta. Verifique os dados e tente novamente.
                </div>
                <% }%>

                <form action="PessoasController?op=1&publico=1" method="post">
                    <div class="form-group">
                        <label class="form-label" for="nome">Nome</label>
                        <input type="text" id="nome" name="nome" class="form-control" placeholder="Seu nome completo" required>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="cpf">CPF</label>
                        <input type="text"
                               id="cpf"
                               name="cpf"
                               class="form-control"
                               placeholder="000.000.000-00"
                               maxlength="14"
                               required>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="email">E-mail</label>
                        <input type="email" id="email" name="email" class="form-control" placeholder="seu@email.com" required>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="papel">Tipo de perfil</label>
                        <select id="papel" name="papel" class="form-control" required>
                            <option value="">Selecione uma opção</option>
                            <option value="ALUNO">Aluno</option>
                            <option value="PROFESSOR">Professor</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label class="form-label">
                            Sexo 
                        </label>

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
                        <label class="form-label" for="dataNascimento">Data de nascimento</label>
                        <input type="date" id="dataNascimento" name="dataNascimento" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="senha">Senha</label>
                        <input type="password" id="senha" name="senha" class="form-control" placeholder="••••••••" required>
                    </div>

                    <button type="submit" class="btn btn-primary" style="width: 100%; margin-bottom: 1rem;">
                        Criar Conta
                    </button>
                </form>

                <div class="login-footer" style="text-align: center; font-size: 0.9rem; color: var(--text-muted);">
                    <p>
                        Já tem conta?
                        <a href="login.jsp" style="color: var(--accent); text-decoration: none; font-weight: 600;">
                            Entrar
                        </a>
                    </p>
                </div>
            </div>
        </div>
        <script>
            const cpfInput = document.getElementById("cpf");

            cpfInput.addEventListener("input", function () {
                let cpf = cpfInput.value;

                // Remove tudo que não for número
                cpf = cpf.replace(/\D/g, "");

                // Limita a 11 números
                cpf = cpf.substring(0, 11);

                // Coloca os pontos e o traço automaticamente
                if (cpf.length > 9) {
                    cpf = cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{1,2})/, "$1.$2.$3-$4");
                } else if (cpf.length > 6) {
                    cpf = cpf.replace(/(\d{3})(\d{3})(\d{1,3})/, "$1.$2.$3");
                } else if (cpf.length > 3) {
                    cpf = cpf.replace(/(\d{3})(\d{1,3})/, "$1.$2");
                }

                cpfInput.value = cpf;
            });
        </script>
    </body>
</html>
