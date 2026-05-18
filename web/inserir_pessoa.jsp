<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastrar Usuário</title>

        <style>
            body{
                text-align: center;
            }

            form{
                display: inline-block;
            }

            table{
                text-align: left;
            }

            td{
                padding: 8px;
            }
        </style>
    </head>

    <body>

        <h1>Cadastrar Usuário</h1>

        <form name="frm" method="post" action="PessoasController?op=1">

            <table>

                <tr>
                    <td>Nome</td>
                    <td><input type="text" name="nome"></td>
                </tr>

                <tr>
                    <td>CPF</td>
                    <td><input type="text" name="cpf"></td>
                </tr>

                <tr>
                    <td>E-mail</td>
                    <td><input type="text" name="email"></td>
                </tr>

                <tr>
                    <td>Tipo de perfil</td>
                    <td>
                        <select name="papel">
                            <option value="ALUNO">Aluno</option>
                            <option value="PROFESSOR">Professor</option>
                        </select>
                    </td>
                </tr>

                <tr>
                    <td>Senha</td>
                    <td><input type="password" name="senha"></td>
                </tr>

                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" value="Cadastrar">
                    </td>
                </tr>

            </table>

        </form>

    </body>
</html>