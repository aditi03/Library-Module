
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
    <c:choose>
    <c:when test="${sessionScope.user !=null && sessionScope.user.userType eq 'admin_sp'}">
            <jsp:include page="sidebar_sp.html" />
    </c:when>
    <c:when test="${sessionScope.user !=null && sessionScope.user.userType eq 'admin'}">
            <jsp:include page="sidebar_admin.html" />
    </c:when>
    <c:when test="${sessionScope.user !=null && sessionScope.user.userType eq 'admin_lb'}">
            <jsp:include page="sidebar_lb.html" />
    </c:when>
    
    <c:otherwise>
          <jsp:include page="sidebar.html" />
    </c:otherwise>
    </c:choose>

    <%@include file="login_check.jsp" %>
<div class="container">
  
  <font color="indianred"><h2>Hello, <c:if test="${sessionScope.user !=null}"><c:out value="${sessionScope.user.userName}"/></c:if></h2></font>
  <%-- <%@include file="menu.html" %>
<div id="sidebar">--%>
    <h4>Access Restricted</h4>
  
  <%--<div id="main">--%>
      <h3>You are not authorized to access this page.</h3>
  </div>
  <%--@include file="footer.html" --%>

</body>
  <div class="footer" >
      <div class="footer_resize">
          <%@include file="footer.html" %>

      </div>
  </div>
</html>
