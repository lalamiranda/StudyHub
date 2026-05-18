<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - StudyHub</title>
</head>
<body>

    <h1>Login</h1>

    <% if (request.getAttribute("erro") != null) { %>
        <p style="color:red;">${erro}</p>
    <% } %>

    <form action="LoginController" method="post">

        <label>Email:</label><br>
        <input type="email" name="email" required><br><br>

        <label>Senha:</label><br>
        <input type="password" name="senha" required><br><br>

        <button type="submit">Entrar</button>

    </form>

</body>
</html>