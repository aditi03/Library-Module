<%-- 
    Document   : index
    Created on : May 12, 2016, 2:28:57 PM
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
        <h1>Hello World!</h1>
        <c:choose>
        <c:when test="${sessionScope.user !=null && sessionScope.user.userType eq 'admin_lb'}">
            <jsp:forward page="/faculty"/>
            
        
    </c:when>
    <c:otherwise>
          <jsp:forward page="/content.jsp"/>
    </c:otherwise>
        </c:choose>
    </body>
</html>
