/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package subjectpreference;

import gen.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class OfferSubjectsConfirm extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    ServletConfig servletConfig = null;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.servletConfig = config;
        
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    
        HttpSession session = request.getSession(false);
        User user = null;
        
        if(session!=null)
        {
            user = (User)session.getAttribute("user");
            if(user == null)
            {
                request.setAttribute("message", "You are not Loggedin, Login First");
                request.getRequestDispatcher("dummy2.jsp").forward(request, response);
            }
            if(!user.getUserType().equalsIgnoreCase("admin_sp"))
            {
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
        
        Connection con;
        PreparedStatement pstmt, pstmt1;
        
        String DB_URL = servletConfig.getServletContext().getInitParameter("DB_URL");
        String DB_DRIVER = servletConfig.getServletContext().getInitParameter("DB_DRIVER");
        String DB_USER = servletConfig.getServletContext().getInitParameter("DB_USER");
        String DB_PASSWORD = servletConfig.getServletContext().getInitParameter("DB_PASSWORD");
        
        String[] offeredSubjects = request.getParameterValues("subjects");
        String semType = request.getParameter("semType");
        int remainder = -1;
        
        if(semType != null)
        {
            if(semType.equalsIgnoreCase("Odd"))
                remainder = 1;
            else if(semType.equalsIgnoreCase("Even"))
                remainder = 0;
            else
                remainder = -1;
        }
        
        System.out.println("SemType = "+semType+" and remainder = "+remainder);
       
        ArrayList<Integer> offeredSubjectsId = new ArrayList();
        
        for(String offerSubjectId : offeredSubjects )
        {
            offeredSubjectsId.add(Integer.valueOf(offerSubjectId.trim()));
        }
        
        int size = offeredSubjectsId.size();
        
        System.out.println("Offered Subjects are : "+offeredSubjectsId);
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
             Class.forName(DB_DRIVER);
             con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             
             StringBuilder inClause = new StringBuilder();
             for (int i=0; i < size; i++) {
                inClause.append('?');
                if ( i <  (size - 1)) {
                    inClause.append(',');
                }
             }
             
             pstmt = con.prepareStatement("update semester_wise_subjects set Offered = 1 where SemNo % 2 = "+remainder+" and SubID in (" + inClause.toString() + ")");
             
             for(int i=0; i<size; i++)
             {
                 pstmt.setInt(i+1,offeredSubjectsId.get(i));
             }
             int nRows = pstmt.executeUpdate();
             System.out.println("nRows : "+nRows);
             
             pstmt.close();
             
             pstmt1 = con.prepareStatement("update semester_wise_subjects set Offered = 0 where SemNo % 2 = "+remainder+" and SubID not in (" + inClause.toString() + ")");
             
             for(int i=0; i<size; i++)
             {
                 pstmt1.setInt(i+1,offeredSubjectsId.get(i));
             }
             int nRows1 = pstmt1.executeUpdate();
             System.out.println("nRows1 : "+nRows1);
             
             pstmt1.close();
             
             con.close();
             request.setAttribute("message", "Subject Preference changes are saved");
             request.getRequestDispatcher("display_ack_sp.jsp").forward(request, response); 
        }
        catch(Exception e)
        {
            System.out.println(e);
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
