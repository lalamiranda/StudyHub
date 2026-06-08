<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="VO.Pessoa"%>
<%@page import="java.util.ArrayList"%>

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

    ArrayList<Pessoa> lista = (ArrayList<Pessoa>) request.getAttribute("lista");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Usuários - StudyHub</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body>
    <%@include file="includes/menu.jsp" %>

    <div class="container">
        <!-- Cabeçalho -->
        <div class="card">
            <div class="card-body">
                <h1 class="page-title">Usuários cadastrados</h1>
                <p class="page-subtitle">Visualize os usuários registrados no StudyHub.</p>

                <div style="display: flex; gap: 1rem; margin-top: 1.5rem; flex-wrap: wrap;">
                    <a class="btn btn-primary" href="inserir_pessoa.jsp">Cadastrar novo usuário</a>
                    <a class="btn btn-outline" href="index.jsp">Voltar</a>
                </div>
            </div>
        </div>

        <!-- Lista usuarios -->
        <div class="card" style="margin-top: 1.5rem;">
            <div class="card-body">
                <% if (lista == null || lista.isEmpty()) { %>
                    <div class="empty-state">
                        <p style="font-size: 1rem; color: var(--text-muted);">
                            Nenhum usuário cadastrado.
                        </p>
                        <a class="btn btn-primary" href="inserir_pessoa.jsp" style="margin-top: 1rem;">
                            Cadastrar primeiro usuário
                        </a>
                    </div>
                <% } else { %>
                    <p style="font-size: 0.9rem; color: var(--text-muted); margin-bottom: 1.5rem;">
                        Total de usuários: <strong><%= lista.size() %></strong>
                    </p>

                    <div class="table-wrap">
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>Nome</th>
                                    <th>E-mail</th>
                                    <th>Perfil</th>
                                    <th>Sexo</th>
                                    <th>Data de nascimento</th>
                                    <th>Status</th>
                                    <th>Reputação</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (Pessoa p : lista) { %>
                                    <tr>
                                        <td><strong><%= p.getNome() %></strong></td>
                                        <td><%= p.getEmail() %></td>
                                        <td>
                                            <span class="badge badge-<%= p.getPapel() %>">
                                                <%= p.getPapel() %>
                                            </span>
                                        </td>
                                        <td><%= p.getSexo() %></td>
                                        <td><%= p.getDataNascimento() %></td>
                                        <td>
                                            <span class="badge badge-<%= p.getStatus() %>">
                                                <%= p.getStatus() %>
                                            </span>
                                        </td>
                                        <td><strong><%= p.getReputacao() %></strong></td>
                                    </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                <% } %>
            </div>
        </div>
    </div>
</body>
</html>
