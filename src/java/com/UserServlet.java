/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.DatabaseController;
//
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author chigolumobikile
 */
public class UserServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String qry = "select username,password from USERS";

        Connection conn = null;
        String memberId = null;
        String htmlmessage = null;
        HttpSession session = request.getSession();
        

//        try {
//            //Class.forName("com.mysql.jdbc.Driver");
//            Class.forName("org.apache.derby.jdbc.ClientDriver");
//            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Claims", "esd", "esd");

        String memberId;

//            System.out.println(conn.toString());
//        } catch (ClassNotFoundException | SQLException e) {
//            System.out.println(e);
//        }

        conn = (Connection) request.getServletContext().getAttribute("connection");
        //response.setContentType("text/html;charset=UTF-8");
        DatabaseController db = new DatabaseController();
        db.connect(conn);
        session.setAttribute("dbname", db);

        if (conn == null) {
            request.getRequestDispatcher("connErr.jsp").forward(request, response);
        }
      
        request.setAttribute("adminclaimlist", db.listClaims());
        request.setAttribute("adminbalancelist", db.listBalance()); 
        request.setAttribute("adminmemberlist", db.listMembers());
        request.setAttribute("adminapplicationlist", db.listApplication());
        
        switch (request.getParameter("buttonaction")){
            default: 
                request.getRequestDispatcher("userLogin.jsp").forward(request, response);
                break;
                
            case "Login" :
                if (db.exists(request.getParameter("username"), request.getParameter("password"))) {
                    if (!(request.getParameter("username").equalsIgnoreCase("admin")) && !(request.getParameter("password").equalsIgnoreCase("admin"))) {
                        memberId = request.getParameter("username");
                        request.getRequestDispatcher("userDashboard.jsp").forward(request, response);
                        db.setmember(memberId);
                        request.setAttribute("claimlist", db.ListMemberClaims());
                        request.setAttribute("paymentlist", db.ListMemberPayment());
                      } else {
                        request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
                    }
                } else {
                    request.getRequestDispatcher("registration.jsp").forward(request, response);
                    // out.println("username or password incorrect");
                }
                break;
            case "register":

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.now();

                String name = request.getParameter("firstname") + " "
                        + request.getParameter("lastname");

                String dob = request.getParameter("yyyy") + "-"
                        + request.getParameter("mm") + "-"
                        + request.getParameter("dd");

                String dor = dtf.format(localDate);

                String status = "APPLIED";

                String balance = "10";

                String address = request.getParameter("addressline1") + ", "
                        + request.getParameter("addressline2") + ", "
                        + request.getParameter("city") + ", "
                        + request.getParameter("postcode");

                String[] splitName = request.getParameter("firstname").split("");
                String usernameID = splitName[0] + "-" + request.getParameter("lastname");

                usernameID = usernameID.toLowerCase();

                String[] splitYear = request.getParameter("yyyy").split("");
                String password = request.getParameter("dd")
                        + request.getParameter("mm")
                        + splitYear[2]
                        + splitYear[3];

                db.addMember(usernameID, name, address, dob, dor, status, balance);
                db.addUser(usernameID, password, status);

                request.setAttribute("username", usernameID);
                request.setAttribute("password", password);
                RequestDispatcher rd = request.getRequestDispatcher("displayNewUserDetails.jsp");
                rd.include(request, response);

                //request.getRequestDispatcher("displayNewUserDetails.jsp").forward(request, response);
                break;
            case "newclaim" :
                double claimamount = Double.parseDouble(request.getParameter("amount"));
                String reason = request.getParameter("reason");
                if(db.NewClaim(reason, claimamount)){
                    htmlmessage = "Claim has been added";
                }
                else{
                    htmlmessage = "Error, claim has not been added";
                }
                request.setAttribute("message", htmlmessage);
                break;
            case "newpayment":
                double payamount = Double.parseDouble(request.getParameter("amount"));
                String paytype = request.getParameter("type");
                if(db.NewPayment(paytype, payamount)){
                    htmlmessage = "Payment has been added";
                }
                else {
                    htmlmessage = "error, payment has not been added";
                }
                request.setAttribute("message", htmlmessage);
                break;
            
            case "checkbalance":
                double balance = db.CheckBalance();
                htmlmessage = Double.toString(balance);
                request.setAttribute("message", htmlmessage);
                break;
            
            case "processclaim":
                int id = Integer.parseInt(request.getParameter("id"));
                String status = request.getParameter("status");
                if(db.processClaim(id, status)){
                    htmlmessage = "Claim processed";
                }
                else {
                    htmlmessage = "Error processing claim";
                }
                request.setAttribute("message", htmlmessage);
                break;
            case "chargesum":
                if(db.chargeLumpsum()){
                    htmlmessage = "lump sum charged";
                }
                else{
                    htmlmessage = "error charging lump sum";
                }
                request.setAttribute("message", htmlmessage);
                break;
            case "processsapplication":
                String applicationid = request.getParameter("appID");
                String applicationstatus = request.getParameter("appstatus");
                if(db.updateMembership(applicationid, applicationstatus)){
                    htmlmessage = "application updated";
                }
                else {
                    htmlmessage = "application updating failed";
                }
                break;
            case "updatemember":
                String updatememid = request.getParameter("updatememid");
                String updatestatus = request.getParameter("updatestaus");
                if(db.updateMembership(updatememid, updatestatus)){
                    htmlmessage = "member updated";
                }
                else{
                    htmlmessage = "member updating failed";
                }
                request.setAttribute("message", htmlmessage);
                break;            
        }
        //request.getRequestDispatcher("connErr.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
