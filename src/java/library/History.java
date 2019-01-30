/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package library;

import gen.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Aditi Dandekar
 */
public class History extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
      String DB_URL = null;
        String DB_DRIVER = null;
        String DB_USER = null;
        String DB_PASSWORD = null;
        ServletContext sc=null ;
    public void init() throws ServletException
    {       sc=getServletContext() ;
       
            DB_URL = sc.getInitParameter("DB_URL");
            DB_DRIVER = sc.getInitParameter("DB_DRIVER");
            DB_USER = sc.getInitParameter("DB_USER");
            DB_PASSWORD = sc.getInitParameter("DB_PASSWORD");
        
   
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
              Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            ArrayList<ArrayList> arr=new ArrayList();
            HttpSession session=request.getSession() ;
            out.println("1") ;
           int userid=-1;
           String ini=null;
            ArrayList<String> fac=(ArrayList<String>) session.getAttribute("uid");
           if(fac!=null)
           {
               Statement st=con.createStatement();
                ini=request.getParameter("fac");
               String y="select UserID  from users where Initials='"+ini+"'";
               ResultSet rs0=st.executeQuery(y);
               while(rs0.next())
               {
                   userid=rs0.getInt("UserID");
               }
           }
           else
           {
            User u=(User) session.getAttribute("user") ;
            out.println(u) ;
             userid=(int) u.getUserID() ;
             out.println("12="+userid) ;
           }
// ArrayList<ArrayList> arr=new ArrayList();
            String q="select Book_Id,Issue_Date,Return_Date from book_issued where  UserID="+userid;
            Statement s=con.createStatement();
            ResultSet rs=s.executeQuery(q);
             out.println("3") ;
            while(rs.next())
            {
                String b_id=rs.getString("Book_Id");
                String iss=rs.getString("Issue_Date") ;
                String ret=rs.getString("Return_Date") ;
                ArrayList<String> ar=new ArrayList();
                String q1="select Title from book where Book_Id='"+b_id+"' " ;
                Statement s1=con.createStatement();
                ResultSet rs1=s1.executeQuery(q1);
                 out.println("4") ;
                String title="" ;
                while(rs1.next())
                {
                    title=rs1.getString("Title") ;
                }
                ar.add(b_id) ;
                ar.add(title) ;
                ar.add(iss) ;
                ar.add(ret) ;
                arr.add(ar) ;
            }
                         String flag ;
             if(arr.isEmpty())
            {
                flag="No books issued";
            }
            else
            {
                flag="true";
            }
            
            request.setAttribute("flag",flag);

            request.setAttribute("arr", arr);
            
             
            if(fac!=null)
            {
                request.setAttribute("ini",ini);
                RequestDispatcher rd= request.getRequestDispatcher("ad_history.jsp");
            rd.forward(request,response);
            }
            else
            {
            RequestDispatcher rd= request.getRequestDispatcher("history.jsp");
            rd.forward(request,response);
            }
      
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet History</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet History at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex) {
              Logger.getLogger(History.class.getName()).log(Level.SEVERE, null, ex);
          } catch (ClassNotFoundException ex) {
              Logger.getLogger(History.class.getName()).log(Level.SEVERE, null, ex);
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
