<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="VO.Pessoa"%>
<%@page import="VO.Material"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<Material> materiais = (List<Material>) request.getAttribute("lista");

    // Formatador de data legível
    SimpleDateFormat sdfEntrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdfSaida   = new SimpleDateFormat("dd/MM/yyyy 'às' HH'h'mm");
%>

<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Materiais - StudyHub</title>
        <link rel="stylesheet" href="assets/css/style.css">
    </head>

    <body>
        <%@include file="includes/menu.jsp" %>

        <div class="container">

            <!-- Cabeçalho -->
            <div class="card">
                <div class="card-body">
                    <h1 class="page-title">Materiais Digitais</h1>
                    <p class="page-subtitle">Acesse vídeos, artigos, PDFs e outros recursos compartilhados pela comunidade.</p>
                    <div style="display:flex; gap:.75rem; margin-top:1.25rem; flex-wrap:wrap;">
                        <a class="btn btn-primary" href="inserir_material.jsp">Cadastrar material</a>
                        <a class="btn btn-outline" href="index.jsp">Voltar</a>
                    </div>
                </div>
            </div>

            <!-- Lista -->
            <div class="card" style="margin-top:1.5rem;">
                <div class="card-body">
                    <h3 class="section-heading">Materiais cadastrados</h3>
                    <p class="section-count">
                        <%
                            if (materiais != null && !materiais.isEmpty()) {
                        %>
                        Materiais encontrados: <strong><%= materiais.size() %></strong>
                        <%
                            } else {
                        %>
                        Nenhum material encontrado.
                        <%
                            }
                        %>
                    </p>

                    <%
                        if (materiais == null || materiais.isEmpty()) {
                    %>
                    <div class="empty-state">
                        <p>Nenhum material cadastrado até o momento.</p>
                        <a class="btn btn-primary" href="inserir_material.jsp" style="margin-top:1rem;">
                            Cadastrar primeiro material
                        </a>
                    </div>

                    <%
                        } else {
                    %>

                    <div class="mat-list">
                        <%
                            for (Material m : materiais) {
                                boolean ehAutor = usuarioLogado.getIdPessoa() == m.getIdPessoa();

                                // Formatar data
                                String dataFormatada = m.getDataUpload();
                                try {
                                    Date d = sdfEntrada.parse(m.getDataUpload());
                                    dataFormatada = sdfSaida.format(d);
                                } catch (Exception ex) { /* mantém original se falhar */ }

                                // Badge CSS por tipo
                                String tipo = (m.getTipo() != null ? m.getTipo().toUpperCase() : "OUTRO");
                                String badgeClass = "badge-" + tipo;
                        %>
                        <div class="mat-card">
                            <div class="mat-card-top">
                                <!-- Título + badge de tipo -->
                                <div class="mat-card-header">
                                    <span class="mat-title"><%= m.getTitulo() %></span>
                                    <span class="badge <%= badgeClass %>"><%= tipo %></span>
                                </div>
                                <!-- Descrição -->
                                <p class="mat-desc"><%= m.getDescricao() %></p>
                                <!-- Meta: autor + data -->
                                <div class="mat-meta">
                                    <span>Cadastrado por <strong><%= m.getNomePessoa() %></strong></span>
                                    <span class="mat-meta-sep">·</span>
                                    <span><%= dataFormatada %></span>
                                </div>
                            </div>

                            <!-- Rodapé com ações -->
                            <div class="mat-card-footer">
                                <a href="<%= m.getLinkExterno() %>"
                                   target="_blank"
                                   rel="noopener noreferrer"
                                   class="btn btn-primary btn-sm">
                                    ↗ Acessar material
                                </a>

                                <% if (ehAutor) { %>
                                <a href="MateriaisController?op=4&id_material=<%= m.getIdMaterial() %>"
                                   class="btn-action btn-action-edit">
                                    Editar
                                </a>
                                <a href="MateriaisController?op=6&id_material=<%= m.getIdMaterial() %>"
                                   class="btn-action btn-action-delete"
                                   onclick="return confirm('Tem certeza que deseja excluir este material?');">
                                    Excluir
                                </a>
                                <% } %>
                            </div>
                        </div>
                        <%
                            }
                        %>
                    </div>

                    <%
                        }
                    %>
                </div>
            </div>

        </div>
    </body>
</html>
