<%-- 
    Document   : adminLogin
    Created on : Nov 11, 2017, 1:31:51 PM
    Author     : chigolumobikile
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login as Administrator</title>
    </head>
    <body>
        <h1>Administrator</h1>
        <form action="adminDashboard.jsp">
            <centre>
                <table>
                    <tr>
                        <th></th>
                        <th>Please provide your Administrator details</th>
                    </tr>
                    <tr>
                        <td>Username:</td>
                        <td><input type="text" name="username"/></td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input type="password" name="password"/></td>
                    </tr>
                    <tr>
                        <td> <input type="submit" value="Login>"/></td>
                    </tr>
                </table>
            </centre>
        </form>
    </body>
</html>
