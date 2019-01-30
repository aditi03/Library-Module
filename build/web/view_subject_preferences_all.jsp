
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="gen.*"%>
<%@page import="subjectpreference.*"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>DDU Portal</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <%--link rel="stylesheet" type="text/css" href="css/style1.css"/--%>
        <style>
            
            .myrow{
                 border: 1px solid black;
                 border-collapse: collapse;      
                 font-family:areial;
                 font-size:100%;
                 text-align: left;
                 height: 20px;
                 line-height: 150%;
                 padding: 5px;
            }            
        </style>
    </head>
    <body>
        <%@include file="header.html" %>
        <c:choose>
        <c:when test="${sessionScope.user !=null && sessionScope.user.userType eq 'admin_sp'}">
                <jsp:include page="sidebar_sp.html" />
        </c:when>
        <c:when test="${sessionScope.user !=null && sessionScope.user.userType eq 'admin'}">
                <jsp:include page="sidebar_admin.html" />
        </c:when>
        <c:otherwise>
              <jsp:include page="sidebar.html" />
        </c:otherwise>
        </c:choose>


        <%@include file="login_check.jsp"%>
        <%--jsp:useBean id="user" class="User" scope="session" />
        <c:if test="${user==null}">
        <c:set var="message" value="You are not Loggedin, Login First" scope="request">
        <% request.getRequestDispatcher("index.jsp").forward(request, response); %>    
</c:if--%>
        <div class="container">
            <%--@include file="title.html" --%>
            <font color="indianred" ><h2>Hello, <%--if(user!=null) out.println(user.getUserName());--%><c:if test="${sessionScope.user !=null}"><c:out value="${sessionScope.user.userName}"/></c:if></h2></font>
            <%--@include file="menu.html" %>
            <div id="sidebar">
                <h4>View Subject Preference</h4>
            </div>
            <div id="main"--%>
              <h4>View Subject Preference:</h4>
  
            <%
                    User user = (User) session.getAttribute("user");
                    if (user != null && user.getUserType().equalsIgnoreCase("admin_sp")) 
                 
                   {
                        
                        String reportType = request.getParameter("listType");
                        String title_1 = null;
                        String title_2 = null;
                        String sub_title = null;
                        
                        if(reportType != null)
                        {   
                            System.out.println("reportType from JSP= "+reportType);
                            if(reportType.equalsIgnoreCase("sub"))
                            {
                                title_1 = "Subject";
                                title_2 = "Faculty Names";
                                sub_title = "Subject Wise";
                            }
                            
                            else if(reportType.equalsIgnoreCase("faculty"))
                            {
                                title_1 = "Faculty Name";
                                title_2 = "Subjects";
                                sub_title = "Faculty Wise";
                            }
                            else if(reportType.equalsIgnoreCase("slot"))
                            {
                                title_1 = "Prefered Slot";
                                title_2 = "Faculty Names";
                                sub_title = "Slot Wise";
                            }
                        
                        LinkedHashMap<String, ArrayList<String>> selectedSubjects = new SubjectPreferenceSettingsInfo(application).getTheList(reportType);
                        if(selectedSubjects != null && !selectedSubjects.isEmpty())
                        {
            %>

                        <form method="post" action="view_subject_preferences_all.jsp">
                           <div id="printTable">
                               <h4>Subject Preference List (<%=sub_title%>)</h4>
                           <table class="myrow">
                        <tr>
                            <th class="myrow"><%=title_1%></th>
                            <th class="myrow"><%=title_2%></th>
                        </tr>
            <%
                        for (String name : selectedSubjects.keySet()) {
            %>
                        <tr>
                            <td class="myrow"><%=name%></td>
            <%
                        ArrayList<String> list = selectedSubjects.get(name);
                        System.out.println("List = "+list);
                        String allNames = "";  
                        
                        if(!list.contains(null))
                        {  
                            for (String s : list) 
                            {
                                if(allNames.equals(""))
                                    allNames = s;
                                else
                                    allNames = allNames+", "+s;
                            }
                        }
                        else
                            allNames = " - ";
                       
            %>
                        <td class="myrow"> <%=allNames%> </td> 
                        </tr>
            <%
                        }
            %>
                       </table>
                           </div>
                       <br> <input type="button" name="print" value="Print" onclick="printData()">
                               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                               <input type="submit" name="submit" value="Back">
                       </form>
            <%       }
                     else
                     {
                        out.println("<font color=\"green\"> No subject is selected </font>");
                     }
                   }
                    
                    else
                    {
             %>
                         <form method="post" action="view_subject_preferences_all.jsp">

                                <input type="radio" name="listType" value="sub">Subject Wise
                                    <br><br>
                                <input type="radio" name="listType" value="faculty">Faculty Wise
                                <br><br>
                                <input type="radio" name="listType" value="slot">Slot Wise
                                <br><br>    
                                <input type="submit" name="submit" value="View">
                         </form>
               
             <%
             }
                   }
            %>
            </div>
            <%--@include file="footer.html" --%>
        <%--/div--%>
    </body>
    <div class="footer" >
      <div class="footer_resize">
          <%@include file="footer.html" %>

      </div>
  </div>
        <script type="text/javascript">
            function printData()
            {
                var divToPrint=document.getElementById("printTable");
                 var htmlToPrint = '' +
                    '<style type="text/css">' +
        'table, table th, table td {' +
                  'border: 1px solid black;'+
                 'border-collapse: collapse;'+      
                 'font-family:areial;'+
                 'text-align: left;'+
                 'font-size:100%;'+
                 'height: 10px;'+
                 'line-height: 120%;'+
                 'padding: 5px;'+
        '}' +
        '</style>';
    htmlToPrint += divToPrint.outerHTML;
                newWin= window.open("");
                newWin.document.write(htmlToPrint);
                newWin.print();
                newWin.close();
            }
        </script>
</html>

