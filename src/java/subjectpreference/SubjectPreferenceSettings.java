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
import java.sql.Statement;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SubjectPreferenceSettings extends HttpServlet {

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
            if(!user.getUserType().equalsIgnoreCase("admin_sp"))
            {
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
        
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>SubjectPreferenceSettings</title>");            
            out.println("</head>");
            out.println("<body>");
            
            String rbOnOff = request.getParameter("setOnOff");
            if(rbOnOff != null && rbOnOff.equalsIgnoreCase("on"))
            {
                System.out.println("Radio Button "+rbOnOff);
                String semType = request.getParameter("semType");
                if(semType != null)
                {
                    System.out.println("SemType = "+semType);
                    setIt(semType,1);
                    request.setAttribute("message", "Subject Preference Module is Enabled");
                    request.getRequestDispatcher("display_ack_sp.jsp").forward(request, response);
                    //request.getRequestDispatcher("subject_preferences.jsp").forward(request, response); 
                }
            }
            
            if(rbOnOff != null && rbOnOff.equalsIgnoreCase("off"))
            {
                System.out.println("Radio Button "+rbOnOff);
                setIt(null,0);
                request.setAttribute("message", "Subject Preference Module is Disabled");
                request.getRequestDispatcher("display_ack_sp.jsp").forward(request, response);
                //request.getRequestDispatcher("subject_preferences.jsp").forward(request, response);  
            }
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

    private void setIt(String semType, int status) {
        Connection con = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        String sql = null;
        String DB_URL = servletConfig.getServletContext().getInitParameter("DB_URL");
        String DB_DRIVER = servletConfig.getServletContext().getInitParameter("DB_DRIVER");
        String DB_USER = servletConfig.getServletContext().getInitParameter("DB_USER");
        String DB_PASSWORD = servletConfig.getServletContext().getInitParameter("DB_PASSWORD");
        try {
           
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    
            if(status == 1)
            {
                sql = "update subject_preference_settings set SemType = ?, Status = ? where ID = 1";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1,semType);
                pstmt.setInt(2, status);
                pstmt.executeUpdate();
                pstmt.close();
            }
            
            else if(status==0)
            {
                sql = "update subject_preference_settings set Status = ? where ID = 1";
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, status);
                pstmt.executeUpdate();
                pstmt.close();
            }
            con.close();
        }catch(Exception e)
        {
            System.out.println(e);
        }
    }

}
