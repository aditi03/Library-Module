/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package library;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
public class reqlist extends HttpServlet {

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
        PrintWriter out = response.getWriter() ;
        try {
            /* TODO output your page here. You may use following sample code. */
            Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            ArrayList<ArrayList> arr=new ArrayList();
            String q="select Book_Id,Fac_id from book_req where Status='Req'";
            Statement s=con.createStatement();
            ResultSet rs=s.executeQuery(q);
            while(rs.next())
            {
                String b_id=rs.getString("Book_Id");
                int uid=rs.getInt("Fac_id");
                ArrayList<String> ar=new ArrayList();
                
                ar.add(uid+"") ;
                
                Statement s2=con.createStatement() ;
                String q2="select Initials from users where UserId='"+uid+"'" ;
                
                ResultSet rs2=s2.executeQuery(q2) ;
                while(rs2.next())
                {
                ar.add(rs2.getString("Initials").toString())   ; 
                }
                ar.add(b_id+"") ;
                Statement s1=con.createStatement() ;
                String q1="select Title from book where Book_Id='"+b_id+"'" ;
                ResultSet rs1=s1.executeQuery(q1) ;
                while(rs1.next())
                {
                ar.add(rs1.getString("Title").toString())   ; 
                }
                arr.add(ar) ;
            }
             for(ArrayList<String> a : arr)
            {   out.println("hello") ;
               for(String b:a)
               {
                   out.println(b+"") ;
                   out.println("hi") ;
               }
               out.println("\n") ;
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
           
            request.setAttribute("br", arr);
            out.println("<h1>Servlet reqlist at " + request.getContextPath() + "</h1>");
             RequestDispatcher rd= request.getRequestDispatcher("req.jsp");
            rd.forward(request,response);
            /*ArrayList<ArrayList> arr1=(ArrayList<ArrayList>)request.getAttribute("br") ;
            for(ArrayList<String> a : arr1)
            {   out.println("hello") ;
               for(String b:a)
               {
                   out.println(b+"") ;
                   out.println("hi") ;
               }
               out.println("\n") ;
            }*/
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet reqlist</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet reqlist at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
        catch(Exception e)
        {
            out.println(e);
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
