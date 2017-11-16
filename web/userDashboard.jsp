<%-- 
    Document   : userDashboard
    Created on : Nov 9, 2017, 1:44:21 PM
    Author     : Jamie O'Neill
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<h1>
				User DashBoard
			</h1>
			<h2>
				Outstanding balance
			</h2>
                    <h3>
				*Amount goes here*          
			</h3>
                            
                      <button type="Make a payment" class="btn btn-default">
					Make the payment!
				</button>
                    
			<p>
                            <br/>
                            <h2>
				Make a Claim          
			</h2>

			</p>
			<form role="form">
				<div class="form-group">
					 
                                    
                                    
					<label for="exampleInputReason">
						Reason for Claim:
					</label>
					<input type="email" class="form-control" id="exampleInputEmail1" />
				</div>
				<div class="form-group">
					 
                                    
                                    
					<label for="exampleInputAmount">
						Amount of Claim: 
					</label>
					<input type="date" class="form-control" id="exampleInputDate" />
				</div>
				<div class="form-group">
                                    
                                 
                               
					</p>
				
				<button type="Submit Claim" class="btn btn-default">
					Submit Claim
				</button>
			</form>
                        
                      	</p>

			<br/>
                       
			<h2>
				List of Claims!             
			</h2>
                        
			<table class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th>
							Claim ID:
						</th>
                                                  <br/>
						<th>
							Amount:
						</th>
						<th>
							Reason:
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
                        <br/>
                        
                        
                            
                     
                        
                        <h2>
				List of Payments!             
			</h2>
                        
			<table class="table table-bordered table-condensed">
				<thead>
					<tr>
                                                <th>
							Claim ID:
						</th>
                                            
						<th>
							Type of payment:
						</th>
                                                  <br/>
						<th>
							Amount:
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
                        </p>

                         <form method="POST" action="userLogin.jsp"> 
                        <button type="Logout" class="btn btn-default">
					Logout
				</button>
                             </form> 
		</div>
	</div>
</div>
    
</html>
