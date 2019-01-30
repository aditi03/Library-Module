/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package subjectpreference;

import gen.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InsertSubjectPreferences extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    ServletConfig servletConfig = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.servletConfig = config;
        
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession(false);
        User user = null;
        
        if(session!=null)
        {
            user = (User)session.getAttribute("user");
            if(user == null)
            {
                request.setAttribute("message", "You are not Loggedin, Login First");
                request.getRequestDispatcher("dummy2.jsp").forward(request, response);
            }
        }
        
        try (PrintWriter out = response.getWriter()) {
            
            String[] selectedSubjectsIds = request.getParameterValues("subjects");
            String slotPreference = request.getParameter("slot");
            System.out.println("slot preference = "+slotPreference);
            
            
            if(insertSelectedSubjects(selectedSubjectsIds,user.getUserID(),slotPreference)>0)
            {
                request.setAttribute("message", "Your Subject Preferences are saved");
            }
            else
            {
                request.setAttribute("message", "Your Subject Preferences are not saved");
            }
            request.getRequestDispatcher("view_subject_preferences.jsp").forward(request, response); 
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

    private int insertSelectedSubjects(String[] selectedSubjects, int userID, String slotPreference) {
        
        Connection con = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        int nRowInserted=0;
        int nRowDeleted=0;
        String DB_URL = servletConfig.getServletContext().getInitParameter("DB_URL");
        String DB_DRIVER = servletConfig.getServletContext().getInitParameter("DB_DRIVER");
        String DB_USER = servletConfig.getServletContext().getInitParameter("DB_USER");
        String DB_PASSWORD = servletConfig.getServletContext().getInitParameter("DB_PASSWORD");
        
        try {
           
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    
            stmt = con.createStatement();
            nRowDeleted = stmt.executeUpdate("delete from subject_preferences where UserID="+userID);
            
            System.out.println("nRowDeleted = "+nRowDeleted);
            
            String sql = "insert into subject_preferences (UserID,SubID) values(?,?)";
            pstmt = con.prepareStatement(sql);
            
            for(String subjectId : selectedSubjects)
            {
                pstmt.setInt(1,userID);
                pstmt.setInt(2,Integer.parseInt(subjectId));
                nRowInserted = pstmt.executeUpdate();
            }
            System.out.println("nRow = "+nRowInserted);
            pstmt.close();
            
            stmt = con.createStatement();
            stmt.executeUpdate("delete from slot_preference where userid="+userID);
            stmt.close();           
            stmt = con.createStatement();
            stmt.executeUpdate("insert into slot_preference (UserID, Slot) values("+userID+",'"+slotPreference+"')");
            stmt.close();            
            con.close();
        }catch(Exception e)
        {
            System.out.println(e);
        }
        return nRowInserted;
    }

}
