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
        HttpSession session = request.getSession();

        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Claims", "esd", "esd");
            System.out.println(conn.toString());
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }

        //response.setContentType("text/html;charset=UTF-8");
        DatabaseController db = new DatabaseController();
        db.connect(conn);
        session.setAttribute("dbname", db);

        if (conn == null) {
            request.getRequestDispatcher("connErr.jsp").forward(request, response);
        }
        //request.getRequestDispatcher("connErr.jsp").forward(request, response);
        if (db.exists(request.getParameter("username"),request.getParameter("password"))) {
            if(!(request.getParameter("username").equalsIgnoreCase("admin")) && !(request.getParameter("password").equalsIgnoreCase("admin"))){
            request.getRequestDispatcher("userDashboard.jsp").forward(request, response);
            }else{
                request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("connErr.jsp").forward(request, response);
            // out.println("username or password incorrect");
        }

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
