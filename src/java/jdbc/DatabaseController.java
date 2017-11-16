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
public class DatabaseController {

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    int ClaimID = 4;

    public boolean NewClaim(String rationale, int amount) {
        PreparedStatement ps = null;

        String member_ID = null;
        try {
            ps = connection.prepareStatement("INSERT INTO Claims VALUES (?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
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

    public void NewPayment(char type, double amount) {

    }

    public void ApprovePayment() {

    }

    private ArrayList rsToList() throws SQLException {
        ArrayList aList = new ArrayList();

        int cols = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()) {
            String[] s = new String[cols];
            for (int i = 1; i <= cols; i++) {
                s[i - 1] = resultSet.getString(i);
            }
            aList.add(s);
        } // while    
        return aList;
    } //rsToList

    private String makeTable(ArrayList list) {
        StringBuilder b = new StringBuilder();
        String[] row;
        b.append("<table border=\"3\">");
        for (Object s : list) {
            b.append("<tr>");
            row = (String[]) s;
            for (String row1 : row) {
                b.append("<td>");
                b.append(row1);
                b.append("</td>");
            }
            b.append("</tr>\n");
        } // for
        b.append("</table>");
        return b.toString();
    }//makeHtmlTable

    private void select(String query) {
        //Statement statement = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            //statement.close();
        } catch (SQLException e) {
            System.out.println("way way" + e);
            //results = e.toString();
        }
    }

    public String listMembers() throws SQLException {
        String results = "";
        select("select * from members");
        return makeTable(rsToList());//results;
    }

    public String listBalance() throws SQLException {
        String results = "";
        select("select * from members where \"status\" not like 'ADMIN' and \"balance\" > 0.0");
        return makeTable(rsToList());//results;
    }

    public String listClaims() throws SQLException {
        String results = "";
        select("select * from claims");
        return makeTable(rsToList());//results;
    }

    public String listApplication() throws SQLException {
        String results = "";
        select("select * from members where \"status\" like 'APPLIED'");
        return makeTable(rsToList());//results;
    }
    
    public Boolean processClaim(int id, String status){
        
        Boolean processed = false;
        String query = "update claims set \"status\" = '"+ status+"' where \"id\" ="+id;
        
        try{
            statement = connection.createStatement();
            statement.executeUpdate(query);
            processed = true;
        }catch (SQLException e) {
            System.out.println("way way" + e);
            //results = e.toString();
        }        
        return processed;//results;
    }
}
