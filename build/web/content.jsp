<%-- 
    Document   : content
    Created on : May 13, 2016, 12:11:37 PM
    Author     : Aditi 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Content Page</title>
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
        <h1 align="center">Library Management</h1>
        <div align='center'>
        <a href="search_1.jsp"><h2>Search</h2></a>
        <a href="issue.jsp"><h2>Request Book</h2></a>
       <a href="IssueList"><h2>Issued Book  list</h2></a>
       <a href="History"><h2>History </h2></a>
        </div>
    </body>
</html>
