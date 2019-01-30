<%-- 
    Document   : home
    Created on : Oct 24, 2015, 12:34:38 AM
    Author     : siddharth
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="gen.User"%>
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
    <%@include file="sidebar.html" %>
<div class="container">
  <%--@include file="title.html" %>
  <%@include file="menu.html" %>
  <div id="sidebar">
    <h4>Home</h4>
  </div>
  <div id="main">
      <%--<%
          User user = (User)session.getAttribute("user");
          if(user!=null)
          {
              out.println("<font color=\"green\"> Hello <b>"+user.getUserName() +"</b> ,You are successfully logged in! </font>");
          }
      %>--%>
      <h4>Home</h4>
      <c:if test="${sessionScope.user != null}">
          <font color="indianred">Hello,<b> ${sessionScope.user.userName}</b> ,You are successfully logged in! </font>
      </c:if>
  </div>
  <%--@include file="footer.html" --%>
</body>
<div class="footer" >
      <div class="footer_resize">
          <%@include file="footer.html" %>

      </div>
  </div>
</html>
 