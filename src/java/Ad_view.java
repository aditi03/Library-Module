/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

/**
 *
 * @author Aditi Dandekar
 */
public class Ad_view extends HttpServlet {

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
            Statement stmt = con.createStatement();
            Statement stmt1 = con.createStatement();
            String status=request.getParameter("status");
            request.setAttribute("status",status);
            ArrayList<String> hd=new ArrayList();
            hd.add("Book_Id");
            hd.add("Title");
            hd.add("Publication");
            hd.add("Author");
            request.setAttribute("hd",hd);
            ArrayList<ArrayList> arr=new ArrayList();
            String q,c;
            int count=0;
            if(status.equals("All"))
            {
                q="select * from book";  
                c="select count(*) as count from book";
            }
            else
            {
                q="select * from book where Status='"+status+"'";
                c="select count(*) as count from book where Status='"+status+"'" ;
            }
            ResultSet rs=stmt.executeQuery(q);
            ResultSet rs1=stmt1.executeQuery(c);
            while(rs.next())
            {
                ArrayList<String> ar=new ArrayList();
                ar.add(rs.getString(1));
                ar.add(rs.getString(2));
                ar.add(rs.getString(3));
                ar.add(rs.getString(4));
                arr.add(ar);
            }
            while(rs1.next())
            {
                count=rs1.getInt("count");
            }
                                     request.setAttribute("arr", arr);
                                           String flag ;
             if(arr.isEmpty())
            {
                flag="no";
            }
            else
            {
                flag="true";
            }
            
            request.setAttribute("flag",flag);
            request.setAttribute("count",count);
            
                         out.println("hhhrr") ;
                         RequestDispatcher rd=request.getRequestDispatcher("ad_view.jsp");
                         rd.forward(request, response);

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Ad_view</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Ad_view at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }           catch (ClassNotFoundException ex) {
                        Logger.getLogger(Ad_view.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(Ad_view.class.getName()).log(Level.SEVERE, null, ex);
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
