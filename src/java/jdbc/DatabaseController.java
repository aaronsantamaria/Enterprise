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
import java.time.Instant;
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
    public String[][] ListPayment(){
        int i =1;
        String[][] payarray = new String[100][5];
        try{
            String query = "select id, mem_id, type_of_payment, amount, date from PAYMENTS";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String id = resultSet.getString("mem_id");
                if(member_ID == id){
                    payarray[i][1] = resultSet.getString("id");;
                    payarray[i][2] = id;
                    payarray[i][3] = resultSet.getString("type_of_payment");
                    payarray[i][4] = resultSet.getString("amount");
                    payarray[i][5] = resultSet.getString("date");
                    i++;
                }
            }
        }catch (SQLException ex) {
            System.out.println("SQL exception");
        }
        return payarray;
    }
    public String[][] ListClaims() {
        String[][] claimarray = new String[100][6];
        int i =1;
        try{
            String query = "select id, mem_id, rationale, amount, date, status from CLAIMS";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String id = resultSet.getString("mem_id");
                if(member_ID == id){
                    claimarray[i][1] = resultSet.getString("id");;
                    claimarray[i][2] = id;
                    claimarray[i][3] = resultSet.getString("rationale");
                    claimarray[i][4] = resultSet.getString("amount");
                    claimarray[i][5] = resultSet.getString("date");
                    claimarray[i][6] = resultSet.getString("status");
                    i++;
                }
            }
        }catch (SQLException ex) {
            System.out.println("SQL exception");
        }
        return claimarray;
    }
    private void select(String query){

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
            ps = connection.prepareStatement("SELECT * FROM APP.USERS where \"id\" = ?");
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
        try{
            String results = "";
            select("select * from members");
            return makeTable(rsToList());//results;
        } catch (SQLException ex){
            return null;
        }
    }

    public String listBalance()  {
        try{
            String results = "";
            select("select * from members where \"status\" not like 'ADMIN' and \"balance\" > 0.0");
            return makeTable(rsToList());//results;
        }catch (SQLException ex){
            return null;
        }
    }

    public String listClaims()  {
        try{
            String results = "";
            select("select * from claims");
            return makeTable(rsToList());//results;
        }catch (SQLException ex){
            return null;
        }
    }

    public String listApplication()  {
        try{
            String results = "";
            select("select * from members where \"status\" like 'APPLIED'");
            return makeTable(rsToList());//results;
        }catch (SQLException ex){
            return null;
        }
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

