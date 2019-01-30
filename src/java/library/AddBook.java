/*
 * To change this template, choose Tools | Templates
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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author JD
 */
public class AddBook extends HttpServlet {
   String DB_URL = null;
        String DB_DRIVER = null;
        String DB_USER = null;
        String DB_PASSWORD = null;
        ServletContext sc=null ;
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        PrintWriter out = response.getWriter();
        
   
        try {
            /* TODO output your page here. You may use following sample code. */
             Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        String bid=request.getParameter("bookid") ;
        String title=request.getParameter("title") ;
        String pub=request.getParameter("publication") ;
        String author=request.getParameter("author") ;
        String q="select * from book where Book_Id='"+bid+"'" ;
        Statement s=con.createStatement() ;
        ResultSet rs=s.executeQuery(q );
        if(!rs.next())
        {
         String query="Insert into book (Book_Id,Title,Publication,Author,Status) VALUES(?,?,?,?,?)" ;
          PreparedStatement ps = con.prepareStatement(query) ;
           out.println("skjdjsjskdhsj   ") ;
            ps.setString(1,bid);
            ps.setString(2,title);
            ps.setString(3,pub);
            ps.setString(4,author);
            ps.setString(5,"Available") ;
            ps.executeUpdate(); 
         out.println("skjdjsj") ;
            request.setAttribute("msg", "Book successfully added");}
        else
        {
            String msg="Duplicate Book Id not allowed." ;
            request.setAttribute("msg",msg) ;
        }
            RequestDispatcher rd=request.getRequestDispatcher("addq.jsp") ;
            rd.forward(request,response) ;
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddBook</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddBook at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }catch(Exception e)
        {
            System.out.println(e) ;
        }
        finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
