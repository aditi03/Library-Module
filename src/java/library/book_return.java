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
public class book_return extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String uname=request.getParameter("fac") ;
               String q1="select UserID from users where Initials='"+uname+"'";
               Statement st=con.createStatement() ;
                ResultSet rs=st.executeQuery(q1);
           int id=0;
            while(rs.next())
            {
              id=rs.getInt("UserID");
              out.println(id);
               out.println(rs.getInt("UserID")) ;
            }
            String b_is="select * from book_issued where UserID="+id;
            ResultSet rs1=st.executeQuery(b_is);
            ArrayList<ArrayList> arr=new ArrayList();
           /* while(rs1.next())
            {
                out.println(rs1.getInt(1)+rs1.getString(2)+rs1.getString(3)+rs1.getString(4)) ;
            }*/
            String flag=null;
            int i=0;
            while(rs1.next())
            {
              //  flag="true";
                ArrayList<String> ar=new ArrayList();
                Statement st1=con.createStatement() ;
                out.println("hi");
                String q_title=null;
                String ret=rs1.getString("Return_Date");
               // ResultSet rs2[i]=null;
                if(ret==null)
                {
                    out.println("hello");
                    String title=null;
                    String b_id=rs1.getString("Book_Id");
                     q_title="select * from book where Book_Id='"+b_id+"'";
                     //out.println(q_title);
                      ResultSet rs2=st1.executeQuery(q_title);
                   out.println("");
                    while(rs2.next())
                    {
                        
                    title=rs2.getString("Title");
                    out.println(title);
                    }
                   out.println(id+" "+b_id+ret+" ");
                   ar.add(b_id);
                   ar.add(title);
                   arr.add(ar);
                   
                }
                
            }
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
            request.setAttribute("id", id);
            RequestDispatcher rd=request.getRequestDispatcher("return.jsp");
            rd.forward(request, response);
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet book_return</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet book_return at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
        catch(Exception e)
        {
            
        }
        finally {
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
