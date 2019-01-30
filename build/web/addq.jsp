<%-- 
    Document   : addq
    Created on : Jun 10, 2016, 10:58:34 PM
    Author     : JD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <%@include file="header.html" %>
        <jsp:include page="sidebar_lb.html" />
              
    <div class="container">
        <jsp:include page="login_check.jsp"></jsp:include>


        
      <center>
      <h2 align='center'>Add a book here</h2>
      </center>
        <form action="AddBook" method="post">
        <center><h3>Enter details here</h3></center>
        <br><br>
        Book Id:    <input type="text" name="bookid">
        <br><br>
        Title:      <input type="text" name="title">
        <br><br>
        Publication:<input type="text" name="publication">
        <br><br>
        Author:     <input type="text" name="author">
        <br><br>
        <center><input type="submit" value="submit"></center>
        </form>
        <center>${requestScope.msg}</center>
        
     </div>
                <div class="footer" >
                    <div class="footer_resize">
                        <%@include file="footer.html" %>

                    </div>
                </div>
        
    </body>
</html>
