<%@page import="VO.Pessoa"%>
<%@page import="VO.Material"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List materiais = (List) request.getAttribute("lista");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Biblioteca - StudyHub</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body>
    <%@include file="includes/menu.jsp" %>

    <div class="container">
        <!-- Page Header -->
        <div class="card">
            <div class="card-body">
                <h1 class="page-title">Biblioteca Digital</h1>
                <p class="page-subtitle">Consulte e compartilhe materiais de estudo.</p>

                <div style="display: flex; gap: 1rem; margin-top: 1.5rem; flex-wrap: wrap;">
                    <a class="btn btn-primary" href="inserir_material.jsp">Cadastrar novo material</a>
                    <a class="btn btn-outline" href="biblioteca.jsp">Voltar</a>
                </div>
            </div>
        </div>

        <!-- Lista de materias -->
        <div class="card" style="margin-top: 1.5rem;">
            <div class="card-body">
                <h3 style="font-size: 1rem; font-weight: 600; margin-bottom: 1rem; color: var(--text); border-left: 3px solid var(--accent); padding-left: 0.75rem;">
                    Materiais cadastrados
                </h3>

                <% if (materiais != null && !materiais.isEmpty()) { %>
                    <p style="font-size: 0.9rem; color: var(--text-muted); margin-bottom: 1.5rem;">
                        Materiais encontrados: <strong><%= materiais.size() %></strong>
                    </p>

                    <div class="table-wrap">
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>Título</th>
                                    <th>Descrição</th>
                                    <th>Tipo</th>
                                    <th>Link</th>
                                    <th>Cadastrado por</th>
                                    <th>Data</th>
                                    <th>Ações</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (int i = 0; i < materiais.size(); i++) {
                                    Material m = (Material) materiais.get(i);
                                %>
                                    <tr>
                                        <td><strong><%= m.getTitulo() %></strong></td>
                                        <td><%= m.getDescricao() %></td>
                                        <td>
                                            <span class="badge badge-<%= m.getTipo() %>">
                                                <%= m.getTipo() %>
                                            </span>
                                        </td>
                                        <td>
                                            <a href="<%= m.getLinkExterno() %>" target="_blank" style="color: var(--accent); text-decoration: none; font-weight: 600;">
                                                Acessar
                                            </a>
                                        </td>
                                        <td><%= m.getNomePessoa() %></td>
                                        <td><%= m.getDataUpload() %></td>
                                        <td>
                                            <% if (m.getIdPessoa() == usuarioLogado.getIdPessoa()) { %>
                                            <a href="MateriaisController?op=4&id_material=<%= m.getIdMaterial() %>" class="btn btn-sm">
                                                Editar
                                            </a>
                                            <a href="MateriaisController?op=3&id_material=<%= m.getIdMaterial() %>" 
                                               onclick="return confirm('Tem certeza que deseja excluir este material?');" 
                                               class="btn btn-sm btn-danger">
                                                Excluir
                                            </a>
                                            <% } %>
                                        </td>
                                    </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                <% } else { %>
                    <div class="empty-state">
                        <p style="font-size: 1rem; color: var(--text-muted);">
                            Nenhum material cadastrado.
                        </p>
                        <a class="btn btn-primary" href="inserir_material.jsp" style="margin-top: 1rem;">
                            Cadastrar primeiro material
                        </a>
                    </div>
                <% } %>
            </div>
        </div>
    </div>
</body>
</html>
