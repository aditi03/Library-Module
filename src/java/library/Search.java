/*
 * To change this template, choose Tools | Templates
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author JD
 */
public class Search extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
           Class.forName(DB_DRIVER);
           Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = con.createStatement();  
            HttpSession session=(HttpSession)request.getSession() ;
            String sby=(String) request.getAttribute("s_by") ;
            String sr=request.getParameter(sby) ;
           out.println("sr="+sr) ;
           
           request.setAttribute("sr", sr);
            String query="SELECT * FROM book WHERE "+sby+" LIKE '%"+sr+"%' ;" ;
            ResultSet rs=stmt.executeQuery(query) ;
            ArrayList<ArrayList<String>> arr=new ArrayList() ;
            String flag=null;
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Search</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Search at " + request.getContextPath() + "</h1>");
            out.println(""+sby+" "+sr) ;
            String path1=null ;
            out.println(session.getAttribute("path")) ;
            if(sr==null)
            {
                path1=session.getAttribute("path")+".jsp" ;
            //RequestDispatcher rd=request.getRequestDispatcher(path1) ;
            //rd.forward(request,response) ;
            }
            int i=0 ;
            while(rs.next())
            {
                ArrayList<String> ar=new ArrayList() ;
                ar.add(rs.getString(1)) ;
                ar.add(rs.getString(2)) ;
                ar.add(rs.getString(3)) ;
                ar.add(rs.getString(4)) ;
                ar.add(rs.getString(5)) ;
                i++ ;
                arr.add(ar) ;
            //out.println(rs.getString(1)+" "+rs.getString(2)+" " +rs.getString(3)+" " +rs.getString(4)+ " "+rs.getString(5)) ;    
            }
            
            if(i==0)
                flag="false";
            else
                flag="true";
            request.setAttribute("s_by",sby) ;
            request.setAttribute("flag",flag);
            request.setAttribute("arr",arr) ;
            //String path=request.getRequestURI().substring(request.getContextPath().length());
            //out.println(path);
            //String path=request.getParameter("sub")+".jsp" ;
            
            String path=null ;
            if(request.getParameter("sub").equals("Search"))
                path="search_1.jsp" ;
            else
                path=request.getParameter("sub")+".jsp" ; 
            
            out.println(path) ;
            
            
            //RequestDispatcher rd=request.getRequestDispatcher(path) ;
            //rd.forward(request,response) ;
            out.println("sby="+sby) ;
            out.println("</body>");
            out.println("</html>");
        }catch(Exception e)
        {}
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
