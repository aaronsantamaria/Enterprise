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
            <div class="container">
                <label><b>Username</b></label>
                <input type="text" placeholder="Enter Username" name="uname" required/>

                <label><b>Password</b></label>
                <input type="password" placeholder="Enter Password" name="psw" required/>

                <button type="submit">Login</button>   
            </div>
        </form>
    </body>
</html>
