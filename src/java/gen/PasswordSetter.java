/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gen;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PasswordSetter extends HttpServlet {

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
        
        String message = null;
        String curPassword = request.getParameter("cPassword");
        String newPassword = request.getParameter("nPassword");
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        String sql;
        String DB_URL = servletConfig.getServletContext().getInitParameter("DB_URL");
        String DB_DRIVER = servletConfig.getServletContext().getInitParameter("DB_DRIVER");
        String DB_USER = servletConfig.getServletContext().getInitParameter("DB_USER");
        String DB_PASSWORD = servletConfig.getServletContext().getInitParameter("DB_PASSWORD");
        
        try {
           
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    
            stmt = con.createStatement();
            
            MyDigest md = new MyDigest();
            
            sql = "select Password from users where UserID="+user.getUserID();
            rs = stmt.executeQuery(sql);
            
            String enCurPass = null;
            if(rs.next())
            {
                enCurPass = rs.getString("Password");
            }
            
            rs.close();
            stmt.close();
            
            if(md.verifyPassword(curPassword, enCurPass))
            {
                stmt = con.createStatement();
                String enPass = md.getEncryptedPassword(newPassword);
                sql = "update users set Password = '" + enPass + "' where UserID = " + user.getUserID();

                System.out.println("query = " + sql);

                int nRowsUpdated = stmt.executeUpdate(sql);
                
                stmt.close();
               
                System.out.println("nRowsUpdated = " + nRowsUpdated);
                if (nRowsUpdated == 1) {
                    message = "Your password is successfully changed";
                } else {
                    message = "Error, while changing the password";
                }   
            }
            else
            {
                message = "Sorry, Your current password is incorrect";
            }
            
            con.close();
            
            request.setAttribute("message", message);
            request.getRequestDispatcher("display_ack_user.jsp").forward(request, response);     
        }
        catch(Exception e)
        {
            System.out.println(e);
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
