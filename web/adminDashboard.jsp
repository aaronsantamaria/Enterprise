<%-- 
    Document   : adminDashboard
    Created on : Nov 11, 2017, 1:40:02 PM
    Author     : Jamie O'Neill
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<h1>
				Admin DashBoard
			</h1>
 
			<h2>
				List of all members!             
			</h2>
                        
			<table class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th>
							Name:
						</th>
                                                <th>
							Outstanding Balances:
						</th>
                                                  <br/>
						
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							*Name one*
						</td>
						
					</tr>
					<tr class="active">
						<td>
							*Name two*
						</td>
						
					</tr>
					<tr class="success">
						<td>
							*Name three*
						</td>
						
					</tr>
					<tr class="warning">
						<td>
							*Name four*
						</td>
						
					</tr>
					<tr class="danger">
						<td>
							*Name five*
						</td>
					
					</tr>
				</tbody>
			</table>
                        <br/>
                        

                        
                        
			<table class="table table-bordered table-condensed">
                            <h2>
				List of all current claims!             
			</h2>
				<thead>
					<tr>
                                                <th>
							Claim ID:
						</th>
                                            	<th>
							Reason:
						</th>	
                                                
                                                  <br/>
						<th>
							Claim Amount:
						</th>
						<th>
							Date:
                                                </th>
                                                <th>
							Status:
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							1
						</td>
						
					</tr>
					<tr class="active">
						<td>
							2
						</td>
						
					</tr>
					<tr class="success">
						<td>
							3
						</td>
						
					</tr>
					<tr class="warning">
						<td>
							4
						</td>
						
					</tr>
					<tr class="danger">
						<td>
							5
						</td>
					
					</tr>
				</tbody>
			</table>
                        
                        
                                    
			<table class="table table-bordered table-condensed">
                            <h2>
				Provisional member applications!             
			</h2>
				<thead>
					<tr>
                                                <th>
							Name:
						</th>
                                            	<th>
							Status:
						</th>	
                                               
					</tr>
                                        <tbody>
					<tr>
						<td>
							*Name one*
						</td>
						
					</tr>
					<tr class="active">
						<td>
							*Name Two*
						</td>
						
					</tr>

			</table>
                         <button type="Approve" class="btn btn-default">
					Approve
				</button>
                        <button type="Decline" class="btn btn-default">
					Decline
				</button>

                        <h2>
				Process individual claims!             
			</h2>
                          <h3>
                                Search for name:
                            </h3>
                           <form>
                            <input type="text" name="search" placeholder="Search..">
                            </form>  
                        <table class="table table-bordered table-condensed">   
                            <thead>
					<tr>
                                                <th>
							Claims:
						</th>        
					</tr>
                                        <tbody>
					<tr>
						<td>
							*Name one*
						</td>	
					</tr>
					<tr class="active">
						<td>
							*Name Two*
						</td>						
					</tr>
                        </table>
                        <button type="Approve" class="btn btn-default">
					Approve
				</button>
                        <button type="Decline" class="btn btn-default">
					Decline
				</button>

                        <h2>
				Process membership applications!             
			</h2>  
                        
                        <table class="table table-bordered table-condensed">   
                            <thead>
					<tr>
                                                <th>
							Name:
						</th>     
					</tr>
                                        <tbody>	
                        </table>               
                        <form>
                            <input type="text" name="search" placeholder="">
                            </form> 
                        <form>
                            <input type="text" name="search" placeholder="">
                            </form> 
                        <form>
                            <input type="text" name="search" placeholder="">
                            </form> 
                         <button type="Approve" class="btn btn-default">
					Approve
				</button>
                        <button type="Decline" class="btn btn-default">
					Decline
				</button>
                        
                        
                        <h2>
				Suspend / Resume membership!             
			</h2>
                        <form>
                            <input type="text" name="search" placeholder="">
                            </form> 
                        <form>
                            <form>
                            <input type="text" name="search" placeholder="">
                            </form> 
                        <form>
                            <form>
                            <input type="text" name="search" placeholder="">
                            </form> 
                        <form>
                        <button type="Suspend" class="btn btn-default">
					Suspend
				</button>
                        <button type="Resume" class="btn btn-default">
					Resume
				</button>
                        
                        <h2>
				Annual Turnover:   *Includes total income and Payouts* 
                                
			</h2>
                        <div style="width:500px;height:30px;border:1px solid #000;"></div>
               
                        <p/>
                        </form>
                         <form method="POST" action="adminLogin.jsp"> 
                        <button type="Logout" class="btn btn-default">
					Logout
				</button>
                             </form> 
		</div>
	</div>
</div>
    
</html>
