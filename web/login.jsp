<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - StudyHub</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body class="no-pad">
    <div class="auth-wrap">
        <div class="auth-card">
            <div class="auth-logo">StudyHub</div>

            <h2 style="font-size: 1.5rem; margin-bottom: 0.5rem; color: var(--text);">Acesse sua conta</h2>
            <p style="color: var(--text-muted); margin-bottom: 1.5rem; font-size: 0.95rem;">
                Para continuar usando o StudyHub
            </p>

            <% if (request.getAttribute("erro") != null) { %>
                <div class="message message-error" style="background: var(--danger-soft); color: var(--danger); padding: 0.75rem; border-radius: var(--radius); margin-bottom: 1rem; border-left: 4px solid var(--danger);">
                    ${erro}
                </div>
            <% } %>

            <form action="LoginController" method="post">
                <div class="form-group">
                    <label class="form-label" for="email">E-mail</label>
                    <input type="email" id="email" name="email" class="form-control" placeholder="seu@email.com" required>
                </div>

                <div class="form-group">
                    <label class="form-label" for="senha">Senha</label>
                    <input type="password" id="senha" name="senha" class="form-control" placeholder="••••••••" required>
                </div>

                <button type="submit" class="btn btn-primary" style="width: 100%; margin-bottom: 1rem;">
                    Entrar
                </button>
            </form>

            <div class="login-footer" style="text-align: center; font-size: 0.9rem; color: var(--text-muted);">
                <p>
                    Ainda não tem conta?
                    <a href="cadastro.jsp" style="color: var(--accent); text-decoration: none; font-weight: 600;">
                        Criar conta
                    </a>
                </p>
            </div>
        </div>
    </div>
</body>
</html>
