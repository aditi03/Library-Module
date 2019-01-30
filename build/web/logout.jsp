
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="gen.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Logout</title>
    </head>
    <body>
        <%-- <%
            //System.out.println("User "+((User)session.getAttribute("user")).getUserName()+"is being removed");
            session.removeAttribute("user");
            session.invalidate();
            
            request.getRequestDispatcher("index.jsp").forward(request, response);
        %>--%>
        <c:remove var="dfrom" scope="session"/>
        <c:remove var="dto" scope="session"/>
        <c:remove var="leavearr" scope="session"/>
        <c:remove var="empid" scope="session"/>
        <c:remove var="user" scope="session"/>
        <% 
            session.invalidate();
        %>
        <jsp:forward page="start.jsp"/>
    </body>
</html>
