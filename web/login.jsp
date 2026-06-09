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

                <h2 style="font-size: 1.5rem; margin-bottom: 0.5rem; color: var(--text);">
                    Acesse sua conta
                </h2>

                <p style="color: var(--text-muted); margin-bottom: 1.5rem; font-size: 0.95rem;">
                    Para continuar usando o StudyHub
                </p>

                <% if (request.getAttribute("erro") != null) { %>
                <div class="message message-error" style="background: var(--danger-soft); color: var(--danger); padding: 0.75rem; border-radius: var(--radius); margin-bottom: 1rem; border-left: 4px solid var(--danger);">
                    ${erro}
                </div>
                <% }%>

                <form action="LoginController" method="post">
                    <div class="form-group">
                        <label class="form-label" for="email">E-mail</label>
                        <input type="email"
                               id="email"
                               name="email"
                               class="form-control"
                               placeholder="seu@email.com"
                               required>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="senha">Senha</label>

                        <div class="password-wrapper">
                            <input type="password"
                                   id="senha"
                                   name="senha"
                                   class="form-control"
                                   placeholder="••••••••"
                                   required>

                            <button type="button"
                                    class="toggle-password"
                                    onclick="mostrarSenha()"
                                    aria-label="Mostrar senha">

                                <span id="iconeSenha">
                                    <svg viewBox="0 0 24 24" fill="none">
                                    <path d="M3 3L21 21"
                                          stroke="currentColor"
                                          stroke-width="2"
                                          stroke-linecap="round"/>
                                    <path d="M10.58 10.58C10.22 10.94 10 11.44 10 12C10 13.1 10.9 14 12 14C12.56 14 13.06 13.78 13.42 13.42"
                                          stroke="currentColor"
                                          stroke-width="2"
                                          stroke-linecap="round"/>
                                    <path d="M9.88 5.18C10.56 5.06 11.27 5 12 5C18.5 5 22.5 12 22.5 12C21.64 13.5 20.63 14.76 19.52 15.77"
                                          stroke="currentColor"
                                          stroke-width="2"
                                          stroke-linecap="round"
                                          stroke-linejoin="round"/>
                                    <path d="M6.23 6.23C3.39 8.15 1.5 12 1.5 12C1.5 12 5.5 19 12 19C13.77 19 15.33 18.48 16.69 17.69"
                                          stroke="currentColor"
                                          stroke-width="2"
                                          stroke-linecap="round"
                                          stroke-linejoin="round"/>
                                    </svg>
                                </span>
                            </button>
                        </div>
                    </div>

                    <button type="submit"
                            class="btn btn-primary"
                            style="width: 100%; margin-bottom: 1rem; text-align: center; display: flex; justify-content: center; align-items: center;">
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

        <script>
            function mostrarSenha() {
                const senhaInput = document.getElementById("senha");
                const iconeSenha = document.getElementById("iconeSenha");
                const botaoSenha = document.querySelector(".toggle-password");

                if (senhaInput.type === "password") {
                    senhaInput.type = "text";
                    botaoSenha.setAttribute("aria-label", "Ocultar senha");

                    iconeSenha.innerHTML = `
                <svg viewBox="0 0 24 24" fill="none">
                    <path d="M1.5 12C1.5 12 5.5 5 12 5C18.5 5 22.5 12 22.5 12C22.5 12 18.5 19 12 19C5.5 19 1.5 12 1.5 12Z"
                          stroke="currentColor"
                          stroke-width="2"
                          stroke-linecap="round"
                          stroke-linejoin="round"/>
                    <circle cx="12"
                            cy="12"
                            r="3"
                            stroke="currentColor"
                            stroke-width="2"/>
                </svg>
            `;
                } else {
                    senhaInput.type = "password";
                    botaoSenha.setAttribute("aria-label", "Mostrar senha");

                    iconeSenha.innerHTML = `
                <svg viewBox="0 0 24 24" fill="none">
                    <path d="M3 3L21 21"
                          stroke="currentColor"
                          stroke-width="2"
                          stroke-linecap="round"/>
                    <path d="M10.58 10.58C10.22 10.94 10 11.44 10 12C10 13.1 10.9 14 12 14C12.56 14 13.06 13.78 13.42 13.42"
                          stroke="currentColor"
                          stroke-width="2"
                          stroke-linecap="round"/>
                    <path d="M9.88 5.18C10.56 5.06 11.27 5 12 5C18.5 5 22.5 12 22.5 12C21.64 13.5 20.63 14.76 19.52 15.77"
                          stroke="currentColor"
                          stroke-width="2"
                          stroke-linecap="round"
                          stroke-linejoin="round"/>
                    <path d="M6.23 6.23C3.39 8.15 1.5 12 1.5 12C1.5 12 5.5 19 12 19C13.77 19 15.33 18.48 16.69 17.69"
                          stroke="currentColor"
                          stroke-width="2"
                          stroke-linecap="round"
                          stroke-linejoin="round"/>
                </svg>
            `;
                }
            }
        </script>
    </body>
</html>