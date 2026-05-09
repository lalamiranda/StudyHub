<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inserir</title>
    </head>
    <body>
        <form name="frm" method="post" action="PessoasController?op=1">
            Nome <input type="text" name="nome"> 
            <br><br>
            CPF <input type="text" name="cpf"> 
            <br><br>
            E-mail <input type="text" name="email"> 
            <br><br>
            Senha <input type="password" name="senha"> 
            <br><br>
            <input type="submit" value="Cadastrar">    
        </form>
    </body>
</html>
