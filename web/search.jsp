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
    <center><h1>Search here</h1></center>
    <br>
        <div align='center'>
        <form action="search.jsp" method="post">
        <select name="s_by">
            <option>Book_Id</option>
            <option>Title</option>
            <option>Publication</option>
            <option>Author</option>
        </select>
            
                            <input type="submit" name="submit" value="Submit">
                    </form>
            </div>
        <c:if test="${param.submit=='Submit'}">
        
        <c:set var="path" scope="session" value="search"></c:set>

        </c:if>
        <div align='centre'>
        <form action="search.jsp" method="post">
            <br>
            <br>
psby=${param.s_by}
            <center>    
        <c:choose>
            <c:when test="${param.s_by=='Book_Id'}">
                Id <input type="text" name="Book_Id" value="${requestScope.sr}">
                <input type="submit" name="sub" value="search">
            </c:when>
            <c:when test="${requestScope.s_by=='Title'}">
                Title <input type="text" name="Title" value="${requestScope.sr}">
                <input type="submit" name="sub" value="search">
            </c:when>
            <c:when test="${requestScope.s_by=='Publication'}">
                Publication <input type="text" name="Publication" value="${requestScope.sr}">
                <input type="submit" name="sub" value="search">
            </c:when>
            <c:when test="${requestScope.s_by=='Author'}">
                Author <input type="text" name="Author" value="${requestScope.sr}">
                <input type="submit" name="sub" value="search">
            </c:when>
        </c:choose>

            <% String s_by=request.getParameter("s_by") ;
            out.println("script s_by="+s_by) ;
    request.setAttribute("s_by",s_by) ;%>
            </center>
            <br>
            <br>
sby=${requestScope.s_by}
</form>
        </div>
                <c:choose>         
        <c:when test="${requestScope.flag=='true'}">        
        flag=${requestScope.flag}
        <form action="issue.jsp" method="post">
            <table border="1" align='center' cellspacing='5'>
            <tr>
                <th>BOOK ID</th>
                <th>TITLE</th>
                <th>PUBLICATION</th>
                <th>AUTHOR</th>
                <th>STATUS</th>
                <td></td>
            </tr>
            <c:forEach var="ar" items="${requestScope.arr}">
            <tr>
                <c:forEach var="rs" varStatus="r" items="${ar}">
                    <td>${rs}</td>
                    <c:if test="${r.count==1}">
                        <c:set var="val" value="${rs}"></c:set>
                    </c:if> 
                    
                    <c:if test="${rs=='Available'}">
                        <td><input type="radio" name="slt" value="${val}"/></td>
                    </c:if>
                           
                                       
                </c:forEach>
                    
            </tr>
            </c:forEach>
            
            </table>
            <br>
            <br>

            <center><input type="submit"/></center>
        </form>  
        </c:when>
                    <c:when test="${requestScope.flag=='false'}"><h2 align='center'>Book not found!!</h2></c:when>
        </c:choose>
    </div>
    </body>
</html>
