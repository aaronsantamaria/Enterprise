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
    int claimIndex = 4;
    String member_ID = null;
    PreparedStatement ps = null; 
    
    public boolean NewClaim(String rationale, int amount) {
        PreparedStatement ps = null;
        
        
        try {
            ps = connection.prepareStatement("INSERT INTO CLAIMS , values (?, ?, ?, ?, ?, ?)");
            ps.setInt(2, ClaimID);
            ps.setString(3, member_ID);
            ps.setString(4, "date"); 
            ps.setString(5, "pending");
            ps.setDouble(6, amount);
            ps.execute();

            ps.close();
            System.out.println("claim added.");
            ClaimID++;
            claimIndex++;
            return true;
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            return false;
        }
    }
//    public void NewPayment(char type, double amount){
//                try {
//            ps = connection.prepareStatement("INSERT INTO CLAIMS , values (?, ?, ?, ?, ?, ?)");
//            ps.setInt(1, claimIndex);
//            ps.setInt(2, ClaimID);
//            ps.setString(3, member_ID);
//            ps.setString(4, rationale); 
//            ps.setString(5, "pending");
//            ps.setDouble(6, amount);
//            ps.execute();
//
//            ps.close();
//            System.out.println("claim added.");
//            ClaimID++;
//            claimIndex++;
//            return true;
//        } catch (SQLException ex) {
//            System.out.println("SQL exception");
//            return false;
//        }
//        
//    }
    public void ApprovePayment(){
        
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
