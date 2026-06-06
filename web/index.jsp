<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="VO.Pessoa"%>

<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>

<html>

    <head>
        <title>StudyHub</title>
        <meta charset="UTF-8">
    </head>

    <body>

    <center>

        <h1>StudyHub</h1>

        <p>
            Bem-vindo, <%= usuarioLogado.getNome()%>!
        </p>

        <br><br>

        <a href="PessoasController?op=2">
            Listar Cadastros
        </a>

        <br><br>

        <a href="inserir_pergunta.jsp">
            Fazer Pergunta
        </a>

        <br><br>

        <a href="PerguntasController?op=2">
            Listar Perguntas
        </a>

        <br><br>


        <a href="inserir_material.jsp">
            Cadastrar Material
        </a>

        <br><br>

        <a href="MateriaisController?op=2">
            Listar Materiais
        </a>
        
        <br><br>
        
        <a href="LogoutController">
            Sair
        </a>

    </center>

</body>

</html>