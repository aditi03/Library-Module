

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.*"%>
<%@page import="gen.*"%>
<%@page import="subjectpreference.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>DDU Portal</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <%--<link rel="stylesheet" type="text/css" href="css/style1.css"/>--%>
    </head>
    <body>
    <%@include file="header.html" %>
    <%@include file="sidebar.html" %>
        <div class="container">
            
             <h3>Hello,<c:if test="${user !=null}"><c:out value="${user.userName}"/></c:if></h3>
             <%--<%@include file="menu.html" %>
            <div id="sidebar">
                <h4>Offered Subjects</h4>
            </div>
            <div id="main">--%>
                <%--
                    if (user != null && user.getUserType().equalsIgnoreCase("admin_sp")) 
                    {--%>
                <h4>Offered Subjects:</h4>
                        <%    String semType = request.getParameter("semType"); %>
                    <%--        if(semType != null)
                            {--%>
                <c:choose>  
                <c:when test="${sessionScope.user !=null && sessionScope.user.userType eq 'admin_sp'}">
                   
                    <c:choose>
                        <c:when test="${param.semType ne null}">
                            
                <%
                    
                            LinkedHashMap<String, ArrayList<Subject>> subjectsMap = new SubjectsGetter(semType,application).fetchSubjects();
                            if (subjectsMap != null) {
                %>
                            <form method="post" action="subject_preferences_settings.jsp">
                <%
                                    for (String sem : subjectsMap.keySet()) {
                %>
                                    <table border="0" style="width:100%">
                                    <tr>
                                        <th colspan="1"><%=sem%></th>
                                    </tr>
                <%
                                    ArrayList<Subject> semSubjects = subjectsMap.get(sem);
                %>
                            <%--        <c:forEach var="s" items="${semSubjects}"> --%>
                                    
                <%                    for (Subject s : semSubjects) {
                                        boolean isOffered = s.getOffered() == 1 ? true : false;
                %>
                                    <tr>
                <%
                                        if (isOffered) {
                %>
                                        <td><%=s.getSubName().trim()%>
                <%
                                        }
                 %>
                                        </td>
                                    </tr>
                <%--                    </c:forEach> --%>
                <%
                                    }
                %>
                                    </table>
                                    <br></br>
                <%
                                    }
                %>
                                    <input type="submit" name="Submit" value="OK">
                                    </form>
                        
                        </c:when>
                        <c:otherwise>            
                                    <h3>You are not authorized to access this page.</h3>
                        </c:otherwise>
                    </c:choose>
                </c:when>                    
                <%--
                               }
                        }
                       
                --%>     
                
                <c:otherwise>
                            <form method="post" action="view_offered_subjects.jsp">

                                <input type="radio" name="semType" value="Odd">Odd
                                <br>
                                <input type="radio" name="semType" value="Even">Even
                                <br><br>    
                                <input type="submit" name="submit" value="View">
                            </form>
                </c:otherwise>
            </c:choose>
                <%--
                        }
                    
                --%>
                            </div>
                            <%--@include file="footer.html" --%>
                            </body>
                            <div class="footer" >
      <div class="footer_resize">
          <%@include file="footer.html" %>

      </div>
  </div>
                            </html>

