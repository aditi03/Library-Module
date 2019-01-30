<%-- 
    Document   : issue
    Created on : May 12, 2016, 1:58:55 PM
    Author     : JD
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Issue Page</title>
    </head>
    <body>

        <%@include file="header.html"%>
         <c:choose>
    <c:when test="${sessionScope.user !=null && sessionScope.user.userType eq 'admin_sp'}">
            <jsp:include page="sidebar_sp.html" />
    </c:when>
    
    <c:when test="${sessionScope.user !=null && sessionScope.user.userType eq 'admin_lb'}">
            <jsp:include page="sidebar_lb.html" />
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
        
        <h1 align="center">Issue</h1>
        <br>
<br>

    <center>
        <c:if test="${requestScope.msg!=null}">
            ${requestScope.msg}
        </c:if>
        <form action="book_req" method="post">
           
            <input type="hidden" value="${user.userID}" name="uid"/>
            Book_Id:<input type="text" value="${param.slt}" name="id"/>
            <a href="search.jsp">Search</a>
            <br><br><br>

            <c:if test="${sessionScope.user !=null && sessionScope.user.userType eq 'admin_lb'}">
                Faculty <input type="text" value="${sessionScope.user.userName}"/>
            </c:if>
                  
            <br> <input type="submit" value="Request"/>
        </form>
    </center>
        
    
       </div>
    </body>
</html>
