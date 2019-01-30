<%@page import="gen.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>DDU Portal</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%--link rel="stylesheet" type="text/css" href="css/style1.css"/--%>
</head>
<body>
    <%@include file="header.html" %>
    <%@include file="sidebar_sp.html" %>    
     
            
<div class="container">
  <%@include file="login_check.jsp" %>   
   <font color="indianred"><h2>Hello,<c:if test="${sessionScope.user!= null}">${sessionScope.user.userName}</c:if></h2></font>
  <%--<%@include file="menu.html" %>
  <div id="sidebar">
    <h4>Message</h4>
  </div>
<div id="main">--%>
    <c:choose>
    <c:when test="${sessionScope.user !=null && sessionScope.user.userType eq 'admin_sp'}">
        <font color="black"><%=(String)request.getAttribute("message")%></font>
        <%-- <font color="black">${requestScope.message}</font>--%>
        <br><br>
        <form method="post" action="subject_preferences.jsp">
            <input type="submit" name="submit" value="OK">
        </form>
    </c:when>
    <c:otherwise>
         <h3>You are not authorized to access this page.</h3>
    </c:otherwise>
    </c:choose>
     
  </div>
  <%--@include file="footer.html" --%>
  <div class="footer" >
           <div class="footer_resize">
               <%@include file="footer.html" %>

           </div>
       </div>

</body>
</html>