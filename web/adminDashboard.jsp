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
                        
                        <h2> Suspend / Resume membership </h2>
                        
                        <td>Enter user ID: </td>
                        <td><input type="text" name="id" style="width:150px;"/></td>

                        <button type="Suspend" name="suspend">
					Suspend
				</button>
                        <button type="Resume" name="resume">
					Resume
				</button>
                        

                        <h2> List of all current claims </h2>
				
                        <h2> Process individual claims </h2>
                         
                        <td>Enter user ID: </td>
                        <td><input type="text" name="indid" style="width:150px;"/></td>
                        
                        <button type="Approve" name="claimApprove">
					Approve
				</button>
                        
                        <button type="Decline" name="claimDecline">
					Decline
				</button>

                        <h2> Provisional member applications </h2>
                        

                        <h2> Process membership applications </h2>  
                        
                        <td>Enter user ID: </td>
                        <td><input type="text" name="appid" style="width:150px;"/></td>
                        
                        <td>Enter status (Approve or Deny) </td>
                        <td><input type="text" name="status" style="width:150px;"/></td>
                        
                        <td><input type="submit" value="Submit"/></td>
                        

                        
                       <h2> Annual Turnover: </h2>
                       <form
                       <td><input type="text" name="total" style="width:500px;"/></td>
                       </form>
                        

                        <form method="POST" action="adminLogin.jsp"> 
                        <button type="Logout" name="logout">
					Logout
			</button>
                        </form> 
		</div>
	</div>
</div>
    
</html>
