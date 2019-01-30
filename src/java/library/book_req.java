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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Arjun
 */
public class book_req extends HttpServlet {

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
        //HttpSession session=request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try  
        {
            Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            SimpleDateFormat d;
            d = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");
            Date today=new Date() ;
            String da=d.format(today).toString();
            out.println("hello");
            /* TODO output your page here. You may use following sample code. */
            String b_id=request.getParameter("id");
            out.println("hello bid"+b_id);
            int uid=Integer.parseInt(request.getParameter("uid"));
            out.println("hello uid"+uid);
            out.println(b_id+" "+uid);
            
            //out.println(b_id+" "+uid);
         String q1="select Status from book where Book_Id='"+b_id+"' and Status='Available'";
         Statement st1=con.createStatement();
         ResultSet rs=st1.executeQuery(q1);
         if(!rs.next())
         {
             String msg1="Book not Available";
                request.setAttribute("msg",msg1);
 
            RequestDispatcher rd=request.getRequestDispatcher("issue.jsp");
            rd.forward(request,response);

         }
         else{
         String q2="INSERT INTO book_req (Fac_id,Book_Id,Status,Req_Date) VALUES (?,?,'Req',?)";
             
           
           //String q="insert into book_req (Fac_id,Book_Id) values ("+uid+",'"+b_id+"')";
           //out.println("insert into book_req (Fac_id,Book_Id) values ("+uid+",'"+b_id+"')");
           PreparedStatement ps = con.prepareStatement(q2) ;
           out.println("hi");
            ps.setInt(1,uid);
            ps.setString(2,b_id);
            ps.setString(3,da);
            out.println("hi1");
            ps.executeUpdate(); 
          out.println(b_id+" hi "+uid);
           Statement st=con.createStatement();
           
         //st.executeUpdate(q);
            out.println("hi1");
           //String s=null;*/
            //st.executeUpdate(q) ;
            String q3="update book set Status='Requested' where Book_Id='"+b_id+"'";
            st.executeUpdate(q3);
            String msg="Book requested successfully";
                request.setAttribute("msg",msg);
 
            RequestDispatcher rd=request.getRequestDispatcher("issue.jsp");
            rd.forward(request,response);
         }
            out.println(b_id+" hiee"+uid);
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet book_req</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet book_req at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
        catch(Exception e)
        {
            out.println(e);
        }
        finally
        {
            out.close();
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
