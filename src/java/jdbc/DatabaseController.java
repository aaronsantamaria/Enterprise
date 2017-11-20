/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import java.sql.Connection;
import java.util.Date;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import static java.sql.Types.NULL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author alexl
 */
public class DatabaseController  {
    
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    int ClaimID = 4;
    int PaymentID = 16;
    String member_ID = null;
    PreparedStatement ps = null; 
    
    
//    public static void main(){
//        if (NewClaim("car", 50.0)){
//            System.out.println("claim made");
//        }
//        else{
//            System.out.println("claim not made");
//        }
//    }
    
    public boolean NewClaim(String rationale, Double amount) {
        PreparedStatement ps = null;
        
        
        try {
            ps = connection.prepareStatement("INSERT INTO CLAIMS , values (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, ClaimID);
            ps.setString(2, member_ID);
            ps.setString(3, "date"); 
            ps.setString(4, rationale);
            ps.setString(5, "pending");
            ps.setDouble(6, amount);
            ps.execute();

            ps.close();
            System.out.println("claim added.");
            ClaimID++;
            return true;
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            return false;
        }
    }
    public Boolean NewPayment(String type, double amount){
        
        try {
            ps = connection.prepareStatement("INSERT INTO PAYMENTS , values (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, PaymentID);
            ps.setString(2, member_ID);
            ps.setString(3, type);
            ps.setDouble(4, amount); 
            ps.setString(5, "date");
            ps.setString(6, "time");
            ps.execute();
            

            ps.close();
            System.out.println("payment added.");
            PaymentID++;
            return true;
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            return false;
        }
        
    }
    public Double CheckBalance(String member_id) throws SQLException{
        
        String query = "select id, balance from MEMBERS";
        Double memBalance = null;
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        while(resultSet.next()){
            String id = resultSet.getString("id");
            Double balance = resultSet.getDouble("balance");
            if(id == member_id){
                memBalance = balance;
            }
        }
        return memBalance;  
    }
    public String[][] ListPayment(String member_id) throws SQLException{
        int i =1;
        String[][] payarray = new String[100][5];
        String query = "select id, mem_id, type_of_payment, amount, date from PAYMENTS";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        while(resultSet.next()){
            String id = resultSet.getString("mem_id");
            if(member_id == id){
                payarray[i][1] = resultSet.getString("id");;
                payarray[i][2] = id;
                payarray[i][3] = resultSet.getString("type_of_payment");
                payarray[i][4] = resultSet.getString("amount");
                payarray[i][5] = resultSet.getString("date");
            }
        }
        return payarray;
    }
    private void select(String query){
        //Statement statement = null;
        
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            //statement.close();
        }
        catch(SQLException e) {
            System.out.println("way way"+e);
            //results = e.toString();
        }
    }
    public boolean exists(String user) {
        boolean bool = false;
        try  {
            select("select id from users where id='"+user+"'");
            if(resultSet.next()) {
                System.out.println("TRUE");         
                bool = true;
            }
        } catch (SQLException ex) {
            //Logger.getLogger(model.Jdbc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bool;
    }
}
