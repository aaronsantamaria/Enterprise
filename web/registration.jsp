<%-- 
    Document   : registration
    Created on : Nov 11, 2017, 1:45:42 PM
    Author     : aaronsantamaria
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Registration Page</h1>

        <form method="POST" action="${pageContext.request.contextPath}/UserServlet">    
            <table>
                <tr>
                    <th></th>
                    <th>Please Enter Your Details</th>
                    <th></th>
                    <th></th>
                </tr>
                <tr>
                    <td>First Name:</td>
                    <td><input type="text" name="firstname" style="width:150px;"/></td>
                </tr>
                <tr>
                    <td>Last Name:</td>
                    <td><input type="text" name="lastname" style="width:150px;"/></td>
                </tr>

                <tr>
                    <td>Date of Birth:</td>
                    <td><input type="text" name="dd" style="width:20px;" placeholder="DD" /> 
                        /
                        <input type="text" name="mm" style="width:20px;" placeholder="MM"/>
                        /
                        <input type="text" name="yyyy" style="width:35px;" placeholder="YYYY"/>
                    </td>
                </tr>

                <tr>
                    <td>Address Line 1:</td>
                    <td><input type="text" name="addressline1" style="width:200px;"/></td>
                </tr>
                <tr>
                    <td>Address Line 2:</td>
                    <td><input type="text" name="addressline2" style="width:200px;"/></td>
                </tr>
                <tr>
                    <td>City:</td>
                    <td><input type="text" name="city" style="width:100px;"/></td>
                </tr>
                <tr>
                    <td>Postcode:</td>
                    <td><input type="text" name="postcode" style="width:100px;"/></td>
                </tr>
                <tr>
                    <td></td>
                     <td><input type="hidden" name="buttonaction" value="register"></td>
                    <td> <input type="submit" value="register" name="buttonaction"/></td>
                </tr>
                
            </table>
        </form>  

    </body>
</html>
