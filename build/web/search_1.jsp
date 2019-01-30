<%-- 
    Document   : index
    Created on : May 12, 2016, 9:59:35 AM
    Author     : JD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
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
        <h1 align="center">Search Here</h1>
    <center>
        <form action="" method="post">
            <br><br>
        <select name="s_by">
            <option>Book_Id</option>
            <option>Title</option>
            <option>Publication</option>
            <option>Author</option>
        </select>
            <input type="submit" name="submit" value="Submit">
            
        </form>
        <c:if test="${param.submit=='Submit'}">
        <c:set var="s_by" scope="session" value="${param.s_by}"></c:set>
        <c:set var="path" scope="session" value="search_1"></c:set>
        </c:if>
        
        <form action="Search" method="post">
            <br><br><br>
        <c:choose>
            <c:when test="${sessionScope.s_by=='Book_Id'}">
                Id <input type="text" name="Book_Id" value="${requestScope.sr}">
                <input type="submit" name="sub" value="Search">
            </c:when>
            <c:when test="${sessionScope.s_by=='Title'}">
                Title <input type="text" name="Title" value="${requestScope.sr}">
                <input type="submit" name="sub" value="Search">
            </c:when>
            <c:when test="${sessionScope.s_by=='Publication'}">
                Publication <input type="text" name="Publication" value="${requestScope.sr}">
                <input type="submit" name="sub" value="Search">
            </c:when>
            <c:when test="${sessionScope.s_by=='Author'}">
                Author <input type="text" name="Author" value="${requestScope.sr}">
                <input type="submit" name="sub" value="Search">
            </c:when>
        </c:choose>    
                <br><br><br>
                <c:choose>         
        <c:when test="${requestScope.flag=='true'}">        
        </form>
       
            <table border="1" align="center">
            <tr>
                <td>BOOK ID</td>
                <td>TITLE</td>
                <td>PUBLICATION</td>
                <td>AUTHOR</td>
                <td>STATUS</td>
                <td></td>
            </tr>
            <c:forEach var="ar" items="${requestScope.arr}">
            <tr>
                <c:forEach var="rs" varStatus="r" items="${ar}">
                    <td>${rs}</td>
                    
                           
                                     
                </c:forEach>
                    
            </tr>
            </c:forEach>
            
            </table>
            
        
        </c:when>
                    <c:when test="${requestScope.flag=='false'}">Book not found!!</c:when>
        </c:choose>
    </center>
        </div>
    </body>
</html>
