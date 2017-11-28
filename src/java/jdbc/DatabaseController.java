/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import java.math.RoundingMode;
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
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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
    LocalDate now = LocalDate.now();
    String startOfYear = now.with(TemporalAdjusters.firstDayOfYear()).toString();
    String endOfYear = now.with(TemporalAdjusters.lastDayOfYear()).toString();
    String today = now.toString();
    DecimalFormat df = new DecimalFormat("#.##");

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

    public Boolean processClaim(int id, String status) {

        Boolean processed = false;
        String query = "update claims set \"status\" = '" + status + "' where \"id\" =" + id;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            processed = true;
        } catch (SQLException e) {
            System.out.println("way way" + e);
        }
        return processed;
    }

    public Boolean chargeLumpsum() {

        PreparedStatement ps = null;
        Boolean claimed = false;
        double fee = calcLumpsum();
        String query = "UPDATE members SET balance = (balance+" + fee + ") WHERE status!='APPLIED'";

        if (today.equals(endOfYear)) {
            try {
                ps = connection.prepareStatement(query);
                ps.executeUpdate();
                ps.close();
                claimed = true;
            } catch (SQLException s) {
                System.out.println("SQL statement is not executed! " + s.getMessage());
            }
        }
        return claimed;
    }//method

    public Boolean processApplication(int id, String status) {

        Boolean processed = false;
        PreparedStatement ps = null;
        String query = "SELECT * FROM payments'";
        String username;

        try {
            select(query);
            while (resultSet.next() && processed == false) {
                if (id == resultSet.getInt("id")) {
                    username = resultSet.getString("mem_id");

                    updateMembership(username, "APPROVED");
                    updateBalance(username, -10);

                    processed = true;
                }
            }
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed! " + s.getMessage());

        }
        return processed;
    }    

    public Boolean updateMembership(String memid, String status) {

        Boolean updated = false;
        PreparedStatement ps = null;
        String queryApprove = "UPDATE members SET status ='APPROVED', dor =DATE_ADD(dor, INTERVAL 1 YEAR) WHERE id='" + memid + "'";        
        String querySuspend = "update members set \"status\" ='SUSPENDED' WHERE id='" + memid + "'";

        try {
            if (status.equals("APPROVED")) {
                ps = connection.prepareStatement(queryApprove);
                ps.executeUpdate();
                ps.close();
                updateBalance(memid, -10);
                updated = true;

            } else if (status.equals("SUSPENDED")) {
                ps = connection.prepareStatement(querySuspend);
                ps.executeUpdate();
                ps.close();
                updated = true;
            }

        } catch (SQLException s) {
            System.out.println("SQL statement is not executed! " + s.getMessage());

        }
        return updated;
    }

//    public Boolean chargeFee(String id) {
//
//        Boolean charged = false;
//        String query = "select * from members where \"id\"= '" + id + "' and \"status\" ='APPROVED' and \"dor\" <='" + today + "'";
//
//        try {
//            select(query);
//            while (resultSet.next()) {
//                updateMembership(id, "SUSPENDED");
//                updateBalance(id, 10);
//                charged = true;
//            }
//        } catch (SQLException s) {
//            System.out.println("SQL statement is not executed! " + s.getMessage());
//        }
//        return charged;
//    }

    private void updateBalance(String username, double amount) {

        PreparedStatement ps = null;
        String queryUpdate = "UPDATE members SET \"balance\" =(\"balance\"+" + amount + ") WHERE \"id\"='" + username + "'";

        try {
            ps = connection.prepareStatement(queryUpdate);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException s) {
            System.out.println("SQL statement is not executed! " + s.getMessage());

        }
    }

    public String listIncome() throws SQLException {
        String results = "";
        //Double income = 0.00;

        select("select * from payments where \"date\" between '" + startOfYear + "' and '" + endOfYear + "'");

        //while (resultSet.next()) {
        //  income = income + resultSet.getDouble("amount");
        //}
        return makeTable(rsToList());//results;
        //return income;
    }//method

    public String listExpense() throws SQLException {
        String results = "";
        //Double expense =0.00;

        select("select * from claims where \"status\" ='APPROVED' and \"date\" between '" + startOfYear + "' and '" + endOfYear + "'");

        //while (resultSet.next()) {
        //  expense = expense + resultSet.getDouble("amount");
        //}
        return makeTable(rsToList());//results;
        //return expense;
    }//method

    private double calcLumpsum() {

        double fee = 0;
        double total = 0;
        double count = 0;

        df.setRoundingMode(RoundingMode.FLOOR);
        String queryCount = "select count(*) from members";
        String querySum = "select sum(\"amount\") from claims where \"status\" ='APPROVED' and \"date\" between '" + startOfYear + "' and '" + endOfYear + "'";

        try {
            select(queryCount);
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            select(querySum);
            while (resultSet.next()) {
                total = resultSet.getDouble(1);
            }

            fee = new Double(df.format(total / count));

        } catch (SQLException s) {
            System.out.println("SQL statement is not executed! " + s.getMessage());
        }
        return fee;
    }
}
