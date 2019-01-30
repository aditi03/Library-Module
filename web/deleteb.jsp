<%-- 
    Document   : deleteb
    Created on : Jun 11, 2016, 10:32:15 PM
    Author     : JD
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="header.html" %>
        <jsp:include page="sidebar_lb.html" />
              
    <div class="container">
        <jsp:include page="login_check.jsp"></jsp:include>

      <h2><font color="indianred">Hello, <c:if test="${sessionScope.user!= null}">${sessionScope.user.userName}</c:if></font></h2>
        
      <center>
          
        <h2>Delete book(s) here</h2>
        </center>
        <form action="DeleteBook" method="post">
            <c:forEach var="ar" items="${requestScope.list}">
            
            <c:forEach var="x" varStatus="id" items="${ar}">
              <c:if test="${id.first==true}">
                <input type="checkbox" name="id" value="${x}">    
              </c:if>  
                
                ${x} 
               
              
            </c:forEach>
                <br>
            </c:forEach>
            <c:if test="${requestScope.list!=null}">
                <br>
                 <input type="submit" value="Submit">
            </c:if>
            
        </form>
      <center>${requestScope.msg}</center>
           
     </div>
                <div class="footer" >
                    <div class="footer_resize">
                        <%@include file="footer.html" %>

                    </div>
                </div>
    </body>
</html>
