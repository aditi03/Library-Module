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
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author siddharth
 */
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    String userName = null;
    User user;
    ServletConfig servletConfig = null;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.servletConfig = config;
        
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        try 
        { 
            String uname=request.getParameter("user");  
            String password=request.getParameter("password");  
          
            if(verify(uname,password))
            {  
                //System.out.println("New User = "+user.getUserName());
                session.setAttribute("user", user);
                request.setAttribute("message", "You are successfully logged in!");
                if (uname.equals("cebss"))
                    request.getRequestDispatcher("faculty").forward(request, response);
                request.getRequestDispatcher("display_ack_user.jsp").forward(request, response); 
            }
            else 
            {  
                //Below if condition is added by me so check if it is working correctly always
                if(session.getAttribute("user")==null )
                {
                    request.setAttribute("message", "Sorry, username or password is incorrect!");
                }
            /*    else
                {
                    request.setAttribute("message", "Sorry, you are already logged in!");
          
                }*/
                //out.print("sorry, username or password is incoorect!");  
                request.getRequestDispatcher("dummy2.jsp").forward(request, response);  
            }  
        }
        catch(Exception e)
        {
            System.out.println("Exception : "+e);
        }
        finally
        {
            out.close();
        }
    }
    
    private boolean verify(String uname, String password)
    {
        boolean result = false;
        Connection con = null;
        Statement stmt = null;
        String DB_URL = servletConfig.getServletContext().getInitParameter("DB_URL");
        String DB_DRIVER = servletConfig.getServletContext().getInitParameter("DB_DRIVER");
        String DB_USER = servletConfig.getServletContext().getInitParameter("DB_USER");
        String DB_PASSWORD = servletConfig.getServletContext().getInitParameter("DB_PASSWORD");
        
        if(DB_PASSWORD == null)
        {
            System.out.println("Password is null");
        }
        try {
           
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = con.createStatement();
            String sql;
            sql = "SELECT * FROM users";
            ResultSet rs = stmt.executeQuery(sql);

            int id=-1;
            String userType=null;
            
            while (rs.next()) {
                String name = rs.getString("UserName");
                String pass = rs.getString("Password");
                id = rs.getInt("UserID");
                userType = rs.getString("UserType");

                MyDigest md = new MyDigest();
                
                if (name.equals(uname) && md.verifyPassword(password, pass)) {
                    //System.out.println("name = "+name);
                    //System.out.println("pass = "+pass);
                    result = true;   
                    break;
                }
            }
            
            rs.close();
            stmt.close();
            
            if(result)
            {
                stmt = con.createStatement();
                sql = "SELECT FirstName,LastName FROM employees where ID="+id;
                rs = stmt.executeQuery(sql);
                
                while (rs.next()) {
                    String fname = rs.getString("FirstName");
                    String lname = rs.getString("LastName");
                    String name = fname+" "+lname;
                    
                    if (name != null) {
                    userName = name;   
                    }
                }
            }
            //System.out.println("user is set with name = "+userName);
            user = new User();
            user.setUserID(id);
            user.setUserName(userName);
            user.setUserType(userType);
            
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try

        return result;
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
