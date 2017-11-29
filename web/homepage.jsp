<%-- 
    Document   : login
    Created on : Nov 9, 2017, 2:50:31 PM
    Author     : chigolumobikile
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>XYZ Associations</title>
    </head>
    <body>
    <center><h1>XYZ Associations</h1>
    <h2><i>make claims easily....</i></h2>
        <form action="userLogin.jsp">
            <input type="submit" value="Login as User" />
        </form>
        <form action="adminLogin.jsp">
            <input type="submit" value="Login as Admin"/>
        </form>
        
        Don't have an account?<br/>
        Click <a href="registration.jsp">here</a> to register.
</center>
    </body>
</html>
