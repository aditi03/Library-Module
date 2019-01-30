<%-- 
    Document   : history
    Created on : Aug 14, 2016, 2:34:28 PM
    Author     : Aditi Dandekar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
        <c:if test="${requestScope.flag=='true'}">
        <table border="1" align="center" cellspacing="5">
            <th>Book Id</th>
            <th>Title</th>
            <th>Issue Date</th>
            <th>Return Date</th>
        <c:forEach var="ar" items="${requestScope.arr}">
            <tr>
                <c:forEach var="rs" varStatus="r" items="${ar}">
                    <td>${rs}</td>
                                        
                </c:forEach>
                 
            </tr>
            </c:forEach>
            </table>
        
        <br>
        </c:if>
        <c:if test="${requestScope.flag!='true'}">
        <center>${requestScope.flag}</center>
        </c:if>
        
    
    </div>


    </body>
</html>
