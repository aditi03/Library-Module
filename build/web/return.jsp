<%-- 
    Document   : return
    Created on : May 13, 2016, 12:09:23 PM
    Author     : Aditi Dandekar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Return Page</title>
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
        <h1 align='center'>Return Books</h1>
        <table align='center' cellspacing='5'>
            <tr><td>
                    
        <form action="book_return" method="post"> 
        Faculty 
            <select name="fac" align="centre">
                <c:forEach var="ui" items="${sessionScope.uid}">
                <option>
                    ${ui}
                </option>
                </c:forEach>

            </select></td></tr>
            <tr><td> <input type="submit" /></td>
            </form>
            </tr>
            
                
            
        </table>
        <c:if test="${requestScope.flag=='true'}">
        <form action="ret_bk1" method="post">
            <h4 align='center'> Book issued by Faculty Id:${requestScope.id}</h4>
        <table border="1" align="center" cellspacing="5">
            <th>Book_Id</th>
            <th>Title</th>
        <c:forEach var="ar" items="${requestScope.arr}">
            <tr>
                <c:forEach var="rs" varStatus="r" items="${ar}">
                    <td>${rs}</td>
                 <c:if test="${r.count==1}">
                        <c:set var="val" value="${rs}"></c:set>
                    </c:if> 
                                        
                </c:forEach>
                    <td><input type="checkbox" name="ret_bk" value="${val}"/></td> 
            </tr>
            </c:forEach>
            </table>
        
        <br>
        <div align='center'><input type="submit"/></div>
        </form>
        </c:if>
        <c:if test="${requestScope.flag!='true'}">
        <center>${requestScope.flag}</center>
        </c:if>
        
    <center>${msg}</center>
    </div>
    </body>
</html>
