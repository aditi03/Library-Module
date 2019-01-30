
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="gen.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>DDU Portal</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%--link rel="stylesheet" type="text/css" href="css/style1.css"/--%>
</head>
<body>
    <%@include file="header.html" %>
    <c:choose>
    <c:when test="${sessionScope.user !=null && sessionScope.user.userType eq 'admin_sp'}">
            <jsp:include page="sidebar_sp.html" />
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
  
    <font color="indianred"><h3>Hello, 
      <%--<%if(user!=null) out.println(user.getUserName());%></h3>--%>
      <c:if test="${sessionScope.user!= null}">${sessionScope.user.userName}</c:if></h3></font>
  
            <h4>Change Password:</h4>
      <form method="post" action="change_password">
          <table>
              <tr>
                  <td>Current Password</td>
                  <td>:</td>
                  <td><input type="password" name="cPassword" value="" placeholder="Current Password"></input></td> 
              </tr>
              <tr></tr>
              <tr><td colspan="3"></td></tr>
              <tr>
                  <td>New Password</td>
                  <td>:&nbsp;&nbsp;</td>
                  <td><input type="password" name="nPassword" value="" placeholder="New Password"></input></td>    
              </tr>
              <tr></tr>
              <tr><td colspan="3"></td></tr>
              <tr>
                  <td>Confirm Password</td>
                  <td>:</td>
                  <td><input type="password" name="cnPassword" value="" placeholder="Confirm New Password"></input></td>    
              </tr>
              <tr></tr>
          </table>
        <p class="submit"><input type="submit" name="submit" value="OK"></input></p>
      </form>
  </div>
  <%--@include file="footer.html" --%>
<div class="footer" >
    <div class="footer_resize">
        <%@include file="footer.html" %>
      
    </div>
    </div>
</body>
</html>

