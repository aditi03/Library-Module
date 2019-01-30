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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Aditi Dandekar
 */
public class Ad_search extends HttpServlet {

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
           
             Class.forName(DB_DRIVER);
           Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = con.createStatement();
            Statement stmt1 = con.createStatement();
            Statement stmt2 = con.createStatement();
            
            HttpSession session=(HttpSession)request.getSession() ;
            String sby=(String) session.getAttribute("s_by1") ;
            String sr=request.getParameter(sby) ;
            ArrayList<ArrayList<String>> arr=new ArrayList() ;
            String flag=null;
            
            if(sby.equals("Book_Id"))
            {    out.println(sby) ;
                 String query="SELECT * FROM book WHERE "+sby+" LIKE '%"+sr+"%'" ;
                 ResultSet rs=stmt.executeQuery(query) ;
                 out.println(sr) ;
                 while(rs.next())
                 {
                     String st=rs.getString(5);
                     out.println(st+"") ;
                     if(st.equals("Available"))
                     {  out.println(sby) ;
                         flag="true";
                         ArrayList<String> ar=new ArrayList() ;
                         ar.add(rs.getString(1)) ;
                         ar.add(rs.getString(2)) ;
                         ar.add(rs.getString(3)) ;
                         ar.add(rs.getString(4)) ;
                         ar.add(rs.getString(5)) ;
                         arr.add(ar);
                         request.setAttribute("flag", flag);
                         request.setAttribute("arr", arr);
                         out.println("hhhrr") ;
                         RequestDispatcher rd=request.getRequestDispatcher("ad_search.jsp");
                         rd.forward(request, response);
                     }
                     else if(st.equals("Issued"))
                     {   //out.println("        hhhrr") ;
                         out.println(sby) ;
                         flag="true";
                         String test="";
                         out.println(sr+"");
                         String q="SELECT UserID from book_issued WHERE Book_Id='"+sr+"' AND Return_Date is NULL";
                         ResultSet rs1=stmt1.executeQuery(q) ;
                         //out.println("        hhhrr") ;
                          ArrayList<String> ar=new ArrayList() ;
                         ar.add(rs.getString(1)) ;
                         ar.add(rs.getString(2)) ;
                         ar.add(rs.getString(3)) ;
                         ar.add(rs.getString(4)) ;
                         ar.add(rs.getString(5)) ;
                         
                         while(rs1.next())
                         {out.println("        hhhrr") ;
                             //ar.add(rs1.getString(1));
                             out.println(rs1.getString(1));
                             int id=rs1.getInt(1);
                             out.println(id) ;
                            String q5="select Initials from users where UserID="+id;
                            ResultSet rs2=stmt2.executeQuery(q5);
                            while(rs2.next())
                            {   out.println(rs2.getString(1)) ;
                               ar.add(rs2.getString(1)); 
                            }
                         
                         }
                         arr.add(ar);
                         String fg="tr";
                         request.setAttribute("fg",fg );
                            request.setAttribute("flag", flag);
                         request.setAttribute("arr", arr);
                         RequestDispatcher rd=request.getRequestDispatcher("ad_search.jsp");
                         rd.forward(request, response);
                     }
                     else if(st.equals(""))
                     {
                         flag="false";
                         request.setAttribute("flag", flag);
                         RequestDispatcher rd=request.getRequestDispatcher("ad_search.jsp");
                         rd.forward(request, response);
                     }
                 }
            }
            else if(sby.equals("Title"))
            {   out.println("        hhhrr") ;
                 flag="title";
                 String query="SELECT * FROM book WHERE "+sby+" like '%"+sr+"%' and (Status='Issued' or Status='Requested' )";
                 ResultSet rs=stmt.executeQuery(query) ;
                 int cnt=0;
                 
                 out.println("hi");
                 while(rs.next())
                 {  ArrayList<String> ar=new ArrayList() ;
                     String bid=rs.getString("Book_Id") ;
                     ar.add(bid) ;
                     ar.add(rs.getString("Title")) ;
                     ar.add(rs.getString("Publication")) ;
                     ar.add(rs.getString("Author")) ;
                    String st=rs.getString("Status") ;
                    ar.add(st) ;
                    String q1="";
                    if(st.equals("Issued"))
                     q1="select UserID from book_issued where Book_Id ='"+bid+"'" ;
                     else
                      q1="select Fac_id from book_req where Book_Id ='"+bid+"'";
                    Statement stmt4=con.createStatement() ;
                    ResultSet rs1=stmt4.executeQuery(q1) ;
                    int id=0 ;
                    while(rs1.next())
                    {
                        id=rs1.getInt(1) ;
                        
                    }
                    ar.add(id+"") ;
                    String q2="select Initials from users where UserID="+id ;
                     Statement stmt5=con.createStatement() ;
                    ResultSet rs2=stmt5.executeQuery(q2) ;
                    String ini="" ;
                    while(rs2.next())
                    {
                        ini=rs2.getString("Initials") ;
                        ar.add(ini) ;
                    }
                    
                    cnt++;
                     out.println(rs.getString("Book_Id"));
                     arr.add(ar) ;
                 }
                 request.setAttribute("arr",arr) ;
                 if(cnt==0)
                  request.setAttribute("msg","Book not owned.");
                 else
                     request.setAttribute("msg","");
                 request.setAttribute("cnt", cnt);
                 request.setAttribute("flag", flag);
                 RequestDispatcher rd=request.getRequestDispatcher("ad_search.jsp");
                 rd.forward(request, response);

            }
                
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Ad_search</title>");            
            out.println("</head>");
            
            out.println("<body>");
            out.println("<h1>Servlet Ad_search at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
        catch(Exception e){
            System.out.println();
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
