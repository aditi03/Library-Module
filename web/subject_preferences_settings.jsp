
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="gen.User"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>DDU Portal</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%--link rel="stylesheet" type="text/css" href="css/style1.css"/--%>
</head>
<body>
    <%@include file="header.html" %>
    <%@include file="sidebar.html" %>    
    <%@include file="login_check.jsp"%>
<div class="container">
    

   <font color="indianred"><h2>Hello,<c:if test="${sessionScope.user !=null}"><c:out value="${sessionScope.user.userName}"/></c:if></h2></font>
   <%--<div id="sidebar">
    <h4>Subject Preference Settings</h4>
  </div>
  <div id="main">--%>
    <c:choose>
        <c:when test="${sessionScope.user !=null && sessionScope.user.userType eq 'admin_sp'}">           
            <a href="enable_disable_sp_settings.jsp">Enable/Disable Subject Preference Module</a><br><br>
            <a href="view_offered_subjects.jsp?">View Offered Subjects</a><br><br>
            <a href="edit_offered_subjects.jsp?">Edit Offered Subjects</a><br><br>
            <a href="view_subject_preferences_all.jsp?">Print the Subject Preference List</a>
        </c:when>
        <c:otherwise>    
            <h3>You are not authorized to access this page.</h3>
        </c:otherwise>
    </c:choose>        
  </div>
  <%--@include file="footer.html"--%>
</body>
<div class="footer" >
      <div class="footer_resize">
          <%@include file="footer.html" %>

      </div>
  </div>
</html>

