<%-- 
    Document   : login_check
    Created on : Jan 13, 2016, 2:05:29 AM
    Author     : Jeel
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@page import="gen.User"%>--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>        
        <title>Authenticate User</title>
    </head>
    <body>
        <c:if test="${sessionScope.user == null}">
            <c:set var="message" value="You are not Loggedin, Login First" scope="request"/>
            <jsp:forward page="dummy2.jsp"></jsp:forward>
        </c:if>
    </body>
</html>
