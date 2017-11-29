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
    public void setmember(String id){
        member_ID = id;
    }

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
      long millis=System.currentTimeMillis();  
      Date date=new Date(millis);  


        try {
            ps = connection.prepareStatement("INSERT INTO CLAIMS , values (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, ClaimID);
            ps.setString(2, member_ID);
            ps.setDate(3, date); 

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

//    private String makeTable(ArrayList list) {
//        StringBuilder b = new StringBuilder();
//        String[] row;
//        b.append("<table border=\"3\">");
//        for (Object s : list) {
//            b.append("<tr>");
//            row = (String[]) s;
//            for (String row1 : row) {
//                b.append("<td>");
//                b.append(row1);
//                b.append("</td>");
//            }
//            b.append("</tr>\n");
//        } // for
//        b.append("</table>");
//        return b.toString();
//    }//makeHtmlTable


    public Boolean NewPayment(String type, double amount){
        long millis=System.currentTimeMillis();  
        Date date=new Date(millis);
        Time time=new Time(millis);

        try {
            ps = connection.prepareStatement("INSERT INTO PAYMENTS , values (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, PaymentID);
            ps.setString(2, member_ID);
            ps.setString(3, type);
            ps.setDouble(4, amount); 
            ps.setDate(5, date);
            ps.setTime(6, time);
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
    public Double CheckBalance() {
        Double memBalance = null;
        try{
            String query = "select id, balance from MEMBERS";
            
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String id = resultSet.getString("id");
                Double balance = resultSet.getDouble("balance");
                if(id == member_ID){
                    memBalance = balance;
                }

            }
        }catch (SQLException ex) {
            System.out.println("SQL exception");
            memBalance = 0.0;
        }
        return memBalance;
    }

  public String ListMemberPayment(){
        String temp = null;
        String[][] payarray = new String[100][5];
        try{
            String query = "select * from PAYMENTS WHERE \"mem_id\" = '"+member_ID+"'";
            select(query);
            temp = (makeTable(rsToList()));
//            while(resultSet.next()){
//                String id = resultSet.getString("mem_id");
//                if(member_ID == id){
//                    payarray[i][1] = resultSet.getString("id");;
//                    payarray[i][2] = id;
//                    payarray[i][3] = resultSet.getString("type_of_payment");
//                    payarray[i][4] = resultSet.getString("amount");
//                    payarray[i][5] = resultSet.getString("date");
//                    i++;
//                }
//            }
        }catch (SQLException ex) {
            System.out.println("SQL exception");
        }
        return temp;
    }
    public String ListMemberClaims() {
        String temp = null;
        try{
            String query = "select * from PAYMENTS WHERE \"mem_id\" = '"+member_ID+"'";
            select(query);
            temp = (makeTable(rsToList()));

        }catch (SQLException ex) {
            System.out.println("SQL exception");



        }
        return temp;
    }

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


    public String listMembers() {

        select("select * from members");
        String temp = null;
        try{
            String query = "select * from members";
            select(query);
            temp = (makeTable(rsToList()));

        }catch (SQLException ex) {
            System.out.println("SQL exception");
        }
        return temp;
    }

    public String listBalance() {

        String temp = null;
        try{
            String query = "select * from members where \"status\" not like 'ADMIN' and \"balance\" > 0.0";
            select(query);
            temp = (makeTable(rsToList()));

        }catch (SQLException ex) {
            System.out.println("SQL exception");
        }
        return temp;
    }

    public String listClaims() {

        String temp = null;
        try{
            String query = "select * from claims";
            select(query);
            temp = (makeTable(rsToList()));

        }catch (SQLException ex) {
            System.out.println("SQL exception");
        }
        return temp;
    }

    public String listApplication() {

        String temp = null;
        try{
            String query = "select * from members where \"status\" like 'APPLIED'";
            select(query);
            temp = (makeTable(rsToList()));

        }catch (SQLException ex) {
            System.out.println("SQL exception");
        }
        return temp;
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
        String query = "select * from members where \"id\" ='"+id+"'";


        try {
            select(query);
            while (resultSet.next() && processed == false) {
                    updateMembership(id, "APPROVED");
                    updateBalance(id, -10);
                    processed = true;


            }
        } catch (SQLException s) {
            System.out.println("SQL statement is not executed! " + s.getMessage());

        }
        return processed;
    }    

    public Boolean updateMembership(String memid, String status) {

        Boolean updated = false;
        PreparedStatement ps = null;
        String queryApprove = "UPDATE Enterprise.MEMBERS SET \"status\" = '"+status+"' WHERE \"id\" LIKE '"+memid+"'";        

        try {
                ps = connection.prepareStatement(queryApprove);
                ps.executeUpdate();
                ps.close();
                updated = true;

        } catch (SQLException s) {
            System.out.println("SQL statement is not executed! " + s.getMessage());

        }
        return updated;
    }


    public Boolean chargeFee(String id) {

        Boolean charged = false;
        String query = "select * from members where \"id\" LIKE '" + id + "' AND \"status\" LIKE 'APPROVED' and \"dor\" <='" + today + "'";

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

    private void updateBalance(String memid, double amount) {

        PreparedStatement ps = null;

        String queryUpdate = "UPDATE Enterprise.MEMBERS SET \"balance\" =(\"balance\"+" + amount + ") where \"id\" LIKE '" + memid + "'";


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
        String queryCount = "select count(*) from MEMBERS";
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
