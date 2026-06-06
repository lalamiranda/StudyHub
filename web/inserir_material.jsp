<%@page import="VO.Pessoa"%>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Cadastrar Material</title>
    </head>

    <body>

    <center>

        <h1>Cadastrar Material</h1>

        <form method="post" action="MateriaisController">

            <input type="hidden" name="op" value="1">

            <table>

                <tr>
                    <td>Título</td>
                    <td>
                        <input type="text" name="titulo" required>
                    </td>
                </tr>

                <tr>
                    <td>Descrição</td>
                    <td>
                        <textarea name="descricao" required></textarea>
                    </td>
                </tr>

                <tr>
                    <td>Link externo</td>
                    <td>
                        <input type="text" name="link_externo" required>
                    </td>
                </tr>

                <tr>
                    <td>Tipo</td>
                    <td>
                        <select name="tipo" required>
                            <option value="PDF">PDF</option>
                            <option value="VIDEO">Vídeo</option>
                            <option value="SITE">Site</option>
                            <option value="ARTIGO">Artigo</option>
                            <option value="OUTRO">Outro</option>
                        </select>
                    </td>
                </tr>

            </table>

            <br>

            <button type="submit">Cadastrar</button>

        </form>

        <br><br>

        <a href="index.jsp">Página inicial</a>

    </center>

    </body>
</html>