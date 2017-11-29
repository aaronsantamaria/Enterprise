<%-- 
    Document   : adminDashboard
    Created on : Nov 11, 2017, 1:40:02 PM
    Author     : Jamie O'Neill
--%>

<%@page import = "jdbc.DatabaseController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
        <title></title>
            </head>
                <body>
			<h1> Admin DashBoard </h1>
 
			<h2> List of all members </h2>
                        
                        <%=(String) (request.getAttribute("adminmemberlist"))%>
                        
                        <h2> Suspend / Resume membership </h2>
                        <form method="POST" action="${pageContext.request.contextPath}/UserServlet" > 
                            
                        <td>Enter user ID: </td>
                        <td><input type="text" name="updatememid" style="width:150px;"/></td
                        <td>Enter user status: </td>
                        <td><input type="text" name="updatestatus" style="width:150px;"/></td
                        <td><input type="hidden" name="buttonaction" value="updatemember"/></td

                        <td><input type="submit" value="submit"/></td>
                        <td><%=request.getAttribute("message")%></td>
                            
                        </form>
                        
                       
                        

                        <h2> List of all current claims </h2>
                        <%=(String) (request.getAttribute("adminclaimlist"))%>
				
                        <h2> Process individual claims </h2>
                        <form method="POST" action="${pageContext.request.contextPath}/UserServlet" > 
                        <td><input type="hidden" name="buttonaction" value="processclaim"/></td 
                        <td>Enter claim ID: </td>
                        <td><input type="text" name="id" style="width:150px;"/></td>
                        <td>Enter claim status: </td>
                        <td><input type="text" name="status" style="width:150px;"/></td>
                        <td><input type="submit" value="Submit"/></td>
                        
                        
 </form>
                        <%=(String) (request.getAttribute("processsclaimmessage"))%>
                        <h2> Provisional member applications </h2>
                        <td><%=request.getAttribute("adminapplicationlist")%></td>
                        <form method="POST" action="${pageContext.request.contextPath}/UserServlet" > 
                        <h2> Process membership applications </h2>  
                        <td><input type="hidden" name="buttonaction" value="updatemember"/></td> 
                        <td>Enter user ID: </td>
                        <td><input type="text" name="appid" style="width:150px;"/></td>
                        
                        <td>Enter status (Approve or Deny): </td>
                        
                        <td><input type="text" name="appstatus" style="width:150px;"/></td>
                        
                        <td><input type="submit" value="Submit"/></td>
                        <%=(String) (request.getAttribute("processsappmessage"))%>
                        </form>


                        
                       <h2> Charge share of annual claim to all members: </h2>
                       <form method="POST" action="${pageContext.request.contextPath}/UserServlet" >
                       <td><input type="hidden" name="buttonaction" value="chargesum"/></td>
                       <td><input type="submit" value="Submit"/></td>
                       </form>
                        <%=(String) (request.getAttribute("calcsummessage"))%>

                        <form method="POST" action="adminLogin.jsp"> 
                        <button type="Logout" name="logout">
					Logout
			</button>
                        </form> 
		</div>
	</div>
</div>
    
</html>
