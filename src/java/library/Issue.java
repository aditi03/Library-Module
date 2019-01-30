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
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class Issue extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
       PrintWriter out = response.getWriter() ;
        try{
            /* TODO output your page here. You may use following sample code. */
                 Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String[] cbox=request.getParameterValues("req_bk");
            if(cbox.length==0)
            {
                String msg="Select books to issue." ;
                request.setAttribute("msg", msg);
                RequestDispatcher rd= request.getRequestDispatcher("req.jsp");
            rd.forward(request,response);
            } 
            else{
            Statement st=con.createStatement();
            for(int i=0 ; i<cbox.length ; i++)
            {   String s=cbox[i] ;   
                out.println(s+"" );
                String q="select Fac_id from book_req where Book_Id='"+s+"' and Status='Req'" ;
               
                ResultSet rs=st.executeQuery(q);
                while(rs.next())
                {
                    int id=rs.getInt("Fac_id") ;

                    SimpleDateFormat d;
                    d = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");
                    Date today=new Date() ;
                    String da=d.format(today).toString();
                    String q1="insert into book_issued (UserID,Book_Id,Issue_Date) values(?,?,?)" ;
                    PreparedStatement ps = con.prepareStatement(q1) ;

                    ps.setInt(1,id);
                    ps.setString(2,s);
                    ps.setString(3,da);
                    //ps.setString(4, "");
                    ps.executeUpdate(); 
                }
                
                String q2="update book set Status='Issued' where Book_Id='"+s+"'" ;
                Statement stq=con.createStatement();
                stq.executeUpdate(q2) ;
                String q3="update book_req set Status='Iss' where Book_Id='"+s+"' and Status='Req'" ;
                Statement st2=con.createStatement();
                st2.executeUpdate(q3) ;
                
            }
               String msg="Books successfully issued." ;
                request.setAttribute("msg", msg);
                RequestDispatcher rd= request.getRequestDispatcher("req.jsp");
            rd.forward(request,response);
            }
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Issue</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Issue at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }catch(Exception e)
        {
            out.println(e) ;
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
         try {
             processRequest(request, response);
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(Issue.class.getName()).log(Level.SEVERE, null, ex);
         } catch (SQLException ex) {
             Logger.getLogger(Issue.class.getName()).log(Level.SEVERE, null, ex);
         }
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
         try {
             processRequest(request, response);
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(Issue.class.getName()).log(Level.SEVERE, null, ex);
         } catch (SQLException ex) {
             Logger.getLogger(Issue.class.getName()).log(Level.SEVERE, null, ex);
         }
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
