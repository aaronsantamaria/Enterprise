/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.DatabaseController;
//
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String qry = "select username,password from USERS";
        
        Connection conn = null;
        String memberId = null;
        String htmlmessage = null;
        HttpSession session = request.getSession();
        
        conn = (Connection) request.getServletContext().getAttribute("connection");
        //response.setContentType("text/html;charset=UTF-8");
        DatabaseController db = new DatabaseController();
        db.setmember((String) session.getAttribute("memberID"));
        db.connect(conn);
        session.setAttribute("dbname", db);
        
        if (conn == null) {
            request.getRequestDispatcher("connErr.jsp").forward(request, response);
        }
        
        request.setAttribute("adminclaimlist", db.listClaims());
        request.setAttribute("adminbalancelist", db.listBalance());        
        request.setAttribute("adminmemberlist", db.listMembers());
        request.setAttribute("adminapplicationlist", db.listApplication());
        
        switch (request.getParameter("buttonaction")) {
            default:                
                request.getRequestDispatcher("userLogin.jsp").forward(request, response);
                break;
            
            case "Login":
                if (db.exists(request.getParameter("username"), request.getParameter("password"))) {
                    if (!(request.getParameter("username").equalsIgnoreCase("admin")) && !(request.getParameter("password").equalsIgnoreCase("admin"))) {
                        memberId = request.getParameter("username");
                        session.setAttribute("memberID", memberId);
                        request.setAttribute("claimlist", db.ListMemberClaims());
                        request.setAttribute("paymentlist", db.ListMemberPayment());
                        request.setAttribute("outstandingbalance", Double.toString(db.CheckBalance()));
                        request.getRequestDispatcher("userDashboard.jsp").forward(request, response);
                        
                        
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
                
                String statusNew = "APPLIED";
                
                String balance10 = "10";
                
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
                
                db.addMember(usernameID, name, address, dob, dor, statusNew, balance10);
                db.addUser(usernameID, password, statusNew);
                
                request.setAttribute("username", usernameID);
                request.setAttribute("password", password);
                RequestDispatcher rd = request.getRequestDispatcher("displayNewUserDetails.jsp");
                rd.include(request, response);
                //request.getRequestDispatcher("displayNewUserDetails.jsp").forward(request, response);
                break;
            case "newclaim":
                String claimamount = request.getParameter("claimamount");
                String claimreason = request.getParameter("reason");
                if (db.NewClaim(claimreason, claimamount)) {
                    htmlmessage = "Claim has been added";
                } else {
                    htmlmessage = "Error, claim has not been added";
                }
                request.setAttribute("claimmessage", htmlmessage);
                request.setAttribute("claimlist", db.ListMemberClaims());
                request.getRequestDispatcher("userDashboard.jsp").forward(request, response);
                
                break;
            case "newpayment":
                double payamount = Double.parseDouble(request.getParameter("balance"));
                //String paytype = request.getParameter("type");
                if (db.NewPayment("FEE", payamount)) {
                    htmlmessage = "Payment has been added";
                } else {
                    htmlmessage = "error, payment has not been added";
                }
                request.setAttribute("paymentmessage", htmlmessage);
                request.setAttribute("paymentlist", db.ListMemberPayment());
                request.getRequestDispatcher("userDashboard.jsp").forward(request, response);
                break;
            
            case "checkbalance":
                double balance = db.CheckBalance();
                htmlmessage = Double.toString(balance);
                request.setAttribute("balancemessage", htmlmessage);
                break;
            
            case "processclaim":
                int id = Integer.parseInt(request.getParameter("id"));
                String status = request.getParameter("status");
                if (db.processClaim(id, status)) {
                    htmlmessage = "Claim processed";
                } else {
                    htmlmessage = "Error processing claim";
                }
                request.setAttribute("processclaimmessage", htmlmessage);
                request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
                break;
            case "chargesum":
                if (db.chargeLumpsum()) {
                    htmlmessage = "lump sum charged";
                } else {
                    htmlmessage = "error charging lump sum";
                }
                request.setAttribute("calcsummessage", htmlmessage);
                request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
                break;
            case "processsapplication":
                String applicationid = request.getParameter("appID");
                String applicationstatus = request.getParameter("appstatus");
                if (db.updateMembership(applicationid, applicationstatus)) {
                    htmlmessage = "application updated";
                } else {
                    htmlmessage = "application updating failed";
                }
                request.setAttribute("processappmessage", htmlmessage);
                break;
            case "updatemember":
                String updatememid = request.getParameter("updatememid");
                String updatestatus = request.getParameter("updatestatus");
                if (db.updateMembership(updatememid, updatestatus)) {
                    htmlmessage = "member updated";
                    request.setAttribute("adminmemberlist", db.listMembers());
                } 
                else {
                    htmlmessage = "member updating failed";
                }
                request.setAttribute("updatememmessage", htmlmessage);
                request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
                break;            
        }//end switch
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
