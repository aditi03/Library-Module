
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="gen.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>DDU Portal</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%--link rel="stylesheet" type="text/css" href="css/style1.css"--%>
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
            <%--@include file="title.html" --%>
  </center><font color="indianred"><h2>Hello, 
        <c:if test="${sessionScope.user!= null}">${sessionScope.user.userName}</c:if></h2></font></center>
        
     
            <c:if test="${sessionScope.user!= null}">
                <c:choose>
                <c:when test="${requestScope.message == null}">
                    </center><font color="black"><h3>${param.message}</h3></font></center>
                </c:when>
                <c:otherwise>
                        <center><font color="black"><h3>${requestScope.message}</h3></font></center>
                </c:otherwise>
                </c:choose>
            </c:if>
     
</div>
<div class="footer" >
           <div class="footer_resize">
               <%@include file="footer.html" %>

           </div>
       </div>
</body>
</html>
