/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import java.sql.Connection;
import java.sql.Date;
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
    
    public boolean NewClaim(String rationale, int amount) {
        PreparedStatement ps = null;
        
        String member_ID = null;
        try {
            ps = connection.prepareStatement("INSERT INTO Claims VALUES (?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(2, ClaimID);
            ps.setString(3, member_ID);
            ps.setString(4, rationale.trim()); 
            ps.setString(5, "pending");
            ps.setInt(6, amount);
            ps.executeUpdate();

            ps.close();
            System.out.println("claim added.");
            ClaimID++;
            return true;
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            return false;
        }
    }
    public void NewPayment(char type, double amount){
        
    }
    public void ApprovePayment(){
        
    }
}
