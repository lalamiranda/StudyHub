<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
<head>

    <meta charset="UTF-8">

    <title>Nova Pergunta</title>

</head>

<body>

    <h1>Fazer Pergunta</h1>

    <form action="PerguntasController" method="post">

        <label>Título:</label>
        <br>

        <input type="text"
               name="titulo"
               required>

        <br><br>

        <label>Descrição:</label>
        <br>

        <textarea name="descricao"
                  rows="6"
                  cols="50"
                  required></textarea>

        <br><br>

        <button type="submit">
            Postar Pergunta
        </button>

    </form>

</body>
</html>