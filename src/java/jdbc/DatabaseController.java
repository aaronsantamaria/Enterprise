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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    int ClaimID = 6;

    int PaymentID = 16;
    String member_ID = null;

    LocalDate now = LocalDate.now();
    String startOfYear = now.with(TemporalAdjusters.firstDayOfYear()).toString();
    String endOfYear = now.with(TemporalAdjusters.lastDayOfYear()).toString();
    String today = now.toString();
    DecimalFormat df = new DecimalFormat("#.##");

    public DatabaseController() {
    }

    public void connect(Connection con) {
        connection = con;
    }

    public void setmember(String id) {
        member_ID = id;
    }

    PreparedStatement ps = null;

    public boolean NewClaim(String rationale, String amount) {

        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();
        String todaydate = dtf.format(localDate);

        PreparedStatement psM = null;
        try {
            psM = connection.prepareStatement("INSERT INTO ENTERPRISE.CLAIMS VALUES (?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            psM.setString(1, Integer.toString(ClaimID));
            psM.setString(2, member_ID);
            psM.setString(3, todaydate);
            psM.setString(4, rationale);
            psM.setString(5, "PENDING");
            psM.setString(6, amount);
            
            psM.execute();
            psM.close();
            ClaimID++;
            System.out.println("1 row added to CLAIMS.");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }//new claim

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
    } //resultSetToList

    public Boolean NewPayment(String type, double amount) {
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        Double payment = 0.00;
        int newID = 0;
        Time time = new Time(millis);

        try {
            select("SELECT MAX(\"id\") FROM ENTERPRISE.PAYMENTS");
            while(resultSet.next()){
                newID = resultSet.getInt(1) +1;
            }
            
            
            ps = connection.prepareStatement("INSERT INTO ENTERPRISE.PAYMENTS VALUES (?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, newID);
            ps.setString(2, member_ID);
            ps.setString(3, type);
            ps.setDouble(4, amount);
            ps.setString(5, today);
            ps.setString(6, time.toString());
            ps.execute();
            ps.close();
            System.out.println("payment added.");
            //PaymentID++;
            payment = payment - amount;
            updateBalance(member_ID, payment);
            return true;
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            return false;
        }
    }//NewPayment

    public Double CheckBalance() {
        Double memBalance = null;
        try {
            String query = "SELECT \"balance\" FROM ENTERPRISE.MEMBERS WHERE \"id\" LIKE '"+member_ID+"'";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                memBalance = resultSet.getDouble("balance");
            }
        } catch (SQLException ex) {
            System.out.println("SQL exception");
            memBalance = 0.0;
        }
        return memBalance;
    }//CheckBalance

    public String ListMemberPayment() {
        String temp = null;
        try {
            String query = "select * from ENTERPRISE.PAYMENTS WHERE \"mem_id\" = '" + member_ID + "'";
            select(query);
            temp = (makeTable(rsToList()));
        } catch (SQLException ex) {
            System.out.println("SQL exception");
        }
        return temp;
    }//ListMemberPayment

    public String ListMemberClaims() {
        String temp = null;
        try {
            String query = "select * from ENTERPRISE.CLAIMS WHERE \"mem_id\" = '" + member_ID + "'";
            select(query);
            temp = (makeTable(rsToList()));
        } catch (SQLException ex) {
            System.out.println("SQL exception");

        }
        return temp;
    }//ListMemberClaims

    public String retrieve(String query) throws SQLException {
        String results = "";
        select(query);

        return makeTable(resultSetToList());
    }//retrieve

    public boolean exists(String user, String pass) {
        boolean bool = false;
        try {

            ps = connection.prepareStatement("SELECT * FROM ENTERPRISE.USERS where \"id\" = ?");

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
    }//exists

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

    public String listMembers() {
        try {
            String results = "";
            select("select * from members");
            return makeTable(rsToList());//results;
        } catch (SQLException ex) {
            return null;
        }
    }

    public String listBalance() {
        try {
            String results = "";
            select("select * from members where \"status\" not like 'ADMIN' and \"balance\" > 0.0");
            return makeTable(rsToList());//results;
        } catch (SQLException ex) {
            return null;
        }
    }

    public String listClaims() {
        try {
            String results = "";
            select("select * from claims");
            return makeTable(rsToList());//results;
        } catch (SQLException ex) {
            return null;
        }
    }

    public String listApplication() {
        try {
            String results = "";
            select("select * from members where \"status\" like 'APPLIED'");
            return makeTable(rsToList());//results;
        } catch (SQLException ex) {
            return null;
        }
    }

    public Boolean processClaim(int id, String status) {

        Boolean processed = false;
        String query = "UPDATE ENTERPRISE.CLAIMS SET \"status\" = '" + status + "' WHERE \"id\" LIKE" + id;

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

        String query = "update members set \"balance\" = (\"balance\"+" + fee + ") WHERE \"status\"!='APPLIED'";

        //if (today.equals(endOfYear)) {
        try {
            ps = connection.prepareStatement(query);
            ps.executeUpdate();
            ps.close();
            claimed = true;
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed! " + s.getMessage());
        }
        //}
        return claimed;
    }//method

    public Boolean processApplication(String id) {

        Boolean processed = false;
        PreparedStatement ps = null;
        String query = "select * from members where \"id\" ='" + id + "'";

        try {
            select(query);
            while (resultSet.next() && processed == false) {
                updateMembership(id, "APPROVED");
                //updateBalance(id, -10);
                processed = true;

            }
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed! " + s.getMessage());

        }
        return processed;
    }

    public Boolean updateMembership(String memid, String status) {

        Boolean updated = null;
        //select("select * from members where \"status\" like 'APPLIED'");
        String queryApprove = "UPDATE Enterprise.MEMBERS SET \"status\" = '"+status+"' WHERE \"id\" = '"+memid+"'";
        //String querySuspend = "update members set \"status\" ='SUSPENDED' where \"id\"='" + memid + "'";

        try {
//            if (status.equals("APPROVED")) {
                ps = connection.prepareStatement(queryApprove);
                //ps.setString(1, status);
                //ps.setString(2, memid);
                ps.executeUpdate();
                ps.close();
                
                updated = true;

//            } else if (status.equals("SUSPENDED")) {
//                ps = connection.prepareStatement(querySuspend);
//                ps.executeUpdate();
//                ps.close();
//                updated = true;
//            }

        } catch (SQLException s) {
            System.out.println("SQL statement is not executed! " + s.getMessage());
            updated = false;
        }
        return updated;
    }


    public Boolean chargeFee(String id) {

        Boolean charged = false;
        String query = "SELECT * FROM Enterprise.Members WHERE \"id\" LIKE '" + id + "' AND \"status\" LIKE 'APPROVED' and \"dor\" <='" + today + "'";

        try {
            select(query);
            while (resultSet.next()) {
                updateBalance(id, 10);
                charged = true;
            }
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed! " + s.getMessage());
        }
        return charged;
    }


    private void updateBalance(String username, double amount) {

        PreparedStatement ps = null;

        String queryUpdate = "UPDATE ENTERPRISE.MEMBERS SET \"balance\" = (\"balance\"+"+amount+") WHERE \"id\" LIKE '"+username+"'";

        try {
            ps = connection.prepareStatement(queryUpdate);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException s) {
            System.out.println("SQL statement is not executed! " + s.getMessage());

        }
    }

  private double calcLumpsum() {

        double fee = 0;
        double total = 0;
        double count = 0;

        df.setRoundingMode(RoundingMode.FLOOR);
        String queryCount = "SELECT COUNT(*) from MEMBERS";
        String querySum = "SELECT SUM(\"amount\") FROM Enterprise.Claims WHERE \"status\" LIKE 'APPROVED' AND \"date\" BETWEEN '" + startOfYear + "' AND '" + endOfYear + "'";

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

    public void addUser(String id, String pass, String status) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("INSERT INTO ENTERPRISE.USERS VALUES (?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, id);
            ps.setString(2, pass);
            ps.setString(3, status);

            ps.executeUpdate();
            ps.close();

            System.out.println("1 row added to USERS.");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// end - addUser

    public void addMember(String id, String name, String address, String dob, String dor, String status, String balance) {
        PreparedStatement psM = null;
        try {
            psM = connection.prepareStatement("INSERT INTO ENTERPRISE.MEMBERS VALUES (?,?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            psM.setString(1, id);
            psM.setString(2, name);
            psM.setString(3, address);
            psM.setString(4, dob);
            psM.setString(5, dor);
            psM.setString(6, status);
            psM.setString(7, balance);

            psM.executeUpdate();
            psM.close();

            System.out.println("1 row added to MEMBERS.");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// end - addUser

}
