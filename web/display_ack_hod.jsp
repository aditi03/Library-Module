<%-- 
    Document   : display_ack_hod
    Created on : Mar 13, 2016, 12:33:09 PM
    
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DDU Portal</title>
    </head>
    <body>
        <%@include file="header.html" %>
        <%@include file="sidebar_admin.html" %>    
        <div class="container">
        <%@include file="login_check.jsp" %>   
        <a href="../src/java/gen/LoginServlet.java"></a>
        <font color="indianred"><h2>Hello,<c:if test="${sessionScope.user!= null}">${sessionScope.user.userName}</c:if></h2></font>
         <font color="black"><%=(String)request.getAttribute("message")%></font>
        </div>
        <div class="footer" >
           <div class="footer_resize">
               <%@include file="footer.html" %>

           </div>
       </div>
    </body>
</html>
