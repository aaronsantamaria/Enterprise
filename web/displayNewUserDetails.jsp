<%-- 
    Document   : displayNewUserDetails
    Created on : Nov 29, 2017, 1:43:59 PM
    Author     : aaronsantamaria
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body>
        <h2>New user registered!</h2>
        <%String message1 = (String) request.getAttribute("username");%>
        <%String message2 = (String) request.getAttribute("password");%>

        <%out.println("Username: " + message1);%>
        </br>
        </br>
        <%out.println("Pasword: " + message2);%>
        </br>
        </br>
        Remember your details! Click home to login?<br/>
        Click <a href="homepage.jsp">home</a> to register.
    </body>
</html>
