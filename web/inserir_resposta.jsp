<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nova Resposta</title>
</head>
<body>

    <h1>Responder Pergunta</h1>

    <form action="RespostasController" method="post">

        <input type="hidden"
               name="id_pergunta"
               value="${param.id_pergunta}">

        <input type="hidden"
               name="op"
               value="1">

        <label>Sua Resposta:</label>
        <br>

        <textarea name="resposta"
                  rows="6"
                  cols="50"
                  required></textarea>

        <br><br>

        <button type="submit">
            Postar Resposta
        </button>

    </form>

</body>
</html>