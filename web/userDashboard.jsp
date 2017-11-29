<%-- 
    Document   : userDashboard
    Created on : Nov 9, 2017, 1:44:21 PM
    Author     : Jamie O'Neill
--%>

<%@page import = "jdbc.DatabaseController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title> </title>
    </head>
    <body>
        <h1>User DashBoard</h1>

        <h2>Outstanding balance</h2>

        <%=(String) request.getAttribute("outstandingbalance")%>

        <form method="POST" action="${pageContext.request.contextPath}/UserServlet" >    
            <centre>
                <table>
                    <tr>                  
                        <td><input type="text" name="balance" style="width:220px;"/></td>
                         <td><input type="hidden" name="buttonaction" value="payBalance"/></td>
                        <td><input type="submit" value="Make payment"/></td>
                    </tr>
                </table>
            </centre>
        </form> 

        <h2> Make a Claim </h2>
        <%=(String) (request.getAttribute("claimmessage"))%>
        <form method="POST" action="${pageContext.request.contextPath}/UserServlet" >    
            <centre>
                <table>
                    <tr>
                        <td>Reason for Claim: </td>
                        <td><input type="text" name="reason" style="width:150px;"/></td>

                        <td>Amount of Claim: </td>
                        <td><input type="text" name="amount" style="width:150px;"/></td>
                        <td><input type="hidden" name="buttonaction" value="newclaim"/></td>
                        <td> <input type="submit" value="Submit Claim"/></td>

                    </tr>
                </table>
            </centre>
        </form> 

        <h2> List of Claims </h2>

         <%=(String) (request.getAttribute("claimlist"))%>

        <h2> List of Payments </h2>

         <%=(String) (request.getAttribute("paymentlist"))%>

        <form method="POST" action="userLogin.jsp"> 
            <button type="Logout">
                Logout
            </button>
        </form> 
    </div>
</div>
</div>

</html>
