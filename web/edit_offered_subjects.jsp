
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

        <jsp:include page="login_check.jsp"></jsp:include>
        <div class="container">
            
            <font color="indianred"> <h2>Hello, <c:if test="${sessionScope.user!= null}">${sessionScope.user.userName}</c:if></h2></font>

                <%
                    User user=(User)session.getAttribute("user");
                    if (user != null && user.getUserType().equalsIgnoreCase("admin_sp")) {

                        String semType = request.getParameter("semType");
                        if(semType != null)
                        {
                            LinkedHashMap<String, ArrayList<Subject>> subjectsMap = new SubjectsGetter(semType,application).fetchSubjects();
                            if (subjectsMap != null) 
                            {
                %>
                                       <form method="post" action="offer_subjects_confirm">
                <%
                                            for (String sem : subjectsMap.keySet()) {
                %>
                                            <table border="0" style="width:100%">
                                            <tr>
                                                <th colspan="1"><%=sem%></th>
                                            </tr>
                <%
                                            ArrayList<Subject> semSubjects = subjectsMap.get(sem);
                                            for (Subject s : semSubjects) {
                                            boolean isOffered = s.getOffered() == 1 ? true : false;
                %>
                                            <tr>
                            
                                                <td><input type="checkbox" name="subjects" value="<%=s.getSubId()%>"
                <%
                                                    if (isOffered) {
                %>
                                                        checked
                <%
                                                    }
                %>
                                                >&nbsp;&nbsp;<%=s.getSubName()%>
                                                </td>
                                            </tr>
                <%
                                            }
                %>
                                            </table>
                                            <br>
                <%
                                            }
                %>
                                        <input type="hidden" name="semType" value="<%=semType%>">
                                        <input type="submit" name="submit" value="Confirm">
                                               
                                    </form>
                <%  
                               } 
                               else 
                               {
                %>
                                <h3>You are not authorized to access this page.</h3>
                <%
                               }
                        }
                        else
                        {
                %>          <form method="post" action="edit_offered_subjects.jsp">

                                <input type="radio" name="semType" value="Odd">Odd
                                <br>
                                <input type="radio" name="semType" value="Even">Even
                                <br><br>    
                                <input type="submit" name="submit" value="View">
                            </form>
                <%
                        }
                    }
                %>

                            </div>
                            <%--@include file="footer.html" --%>
                            <div class="footer" >
                                <div class="footer_resize">
                                    <%@include file="footer.html" %>

                                </div>
                            </div>
                            </body>
                            </html>

