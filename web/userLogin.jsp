<%-- 
    Document   : userLogin
    Created on : Nov 9, 2017, 3:27:58 PM
    Author     : chigolumobikile
--%>
<%@page import = "jdbc.DatabaseController"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login as User</title>
    </head>
    <body>
        <h1>User</h1>

        <form method="POST" action="${pageContext.request.contextPath}/UserServlet" >    
            <centre>
                <table>
                    <tr>
                        <th></th>
                        <th>Please provide your following details</th>
                    </tr>
                    <tr>
                        <td>Username:</td>
                        <td><input type="text" name="username"/></td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input type="password" name="password"/></td>
                        <td><input type="hidden" name="buttonaction" value="Login"></td>
                        
                    </tr>
                    
                    <tr>
                        <td> <input type="submit" value="Login>"  /></td>
                    </tr>
                </table>
            </centre>
        </form> 
        
</html>
