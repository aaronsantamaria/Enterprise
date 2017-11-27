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
public class DatabaseController {

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    int ClaimID = 4;
    int claimIndex = 4;
    String member_ID = null;
    PreparedStatement ps = null;

    public DatabaseController() {
    }

    public void connect(Connection con) {
        connection = con;
    }

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

    public void ApprovePayment() {

    }

    private ArrayList resultSetToList() throws SQLException {
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

    public String retrieve(String query) throws SQLException {
        String results = "";
        select(query);

        return makeTable(resultSetToList());
    }

    public boolean exists(String user, String pass) {
        boolean bool = false;
        try {
            ps = connection.prepareStatement("SELECT * FROM ESD.USERS where \"id\" = ?");
            ps.setObject(1, user);
            resultSet = ps.executeQuery();
            //select("SELECT * FROM ESD.MEMBERS where \"id\" = '" + user + "'");
            if (resultSet.next()) {
                System.out.println("TRUE");
                if (pass.equals(resultSet.getObject("password"))) {

                    System.out.println(pass);
                    bool = true;
                }
            }
        } catch (SQLException ex) {
            //Logger.getLogger(model.Jdbc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bool;
    }
}
