<%@page import="java.util.List"%>
<%@page import="VO.Pessoa"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listagem</title>
    </head>
    <body>

        <%
            List pessoas = (List) request.getAttribute("lista");
            if (pessoas != null) {
                out.print("<center>Achados: " + pessoas.size() + "</center><br><br><br>");
                out.print("<table width=\"50%\" border=\"1\" cellspacing=\"0\" align=\"center\">");
                for (int cont = 0; cont < pessoas.size(); cont++) {
                    Pessoa p = new Pessoa();
                    p = (Pessoa) pessoas.get(cont);
                    out.print("<tr>");
                    out.print("<td>" + p.getCpf() + "</td>");
                    out.print("<td>" + p.getNome()+ "</td>"); 
                    out.print("</tr>");
                }
                out.print("</table>");
            }            
        %>
        
        
        <br><br><br>
    <center><a href="index.html">Página inicial</a></center>

    </body>
</html>
