
<%@page import="gen.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>DDU Portal</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--<link rel="stylesheet" type="text/css" href="css/style.css"/>-->
</head>
<body>
    

<div class="container">
      
      <c:if test="${requestScope.message != null}">
          <c:out value="<font color=\'red\'>${param.message}</font>"/>
      </c:if>  
      <c:if test="${sessionScope.user!=null}">
            <c:out value="<font color=\'green\'> Hello, <b>${user.userName}</b> ,You are successfully logged in! </font>"/>
      </c:if>    
      <%--@include file="finallogin.html" --%> 
  </div>
  <%--@include file="footer.html" --%>
</body
<div class="footer" >
      <div class="footer_resize">
          <%@include file="footer.html" %>

      </div>
  </div>
</html>
