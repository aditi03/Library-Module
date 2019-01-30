<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="gen.*"%>
<%@page import="subjectpreference.*"%>
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
    <c:otherwise>
          <jsp:include page="sidebar.html" />
    </c:otherwise>
    </c:choose>

    
   <jsp:include page="login_check.jsp"></jsp:include>
   <div class="container">
  <%--@include file="title.html" --%>
   <font color="indianred"><h2>Hello, <c:if test="${sessionScope.user!= null}">${sessionScope.user.userName}</c:if></h2></font>
      
      <c:choose>
      <c:when test="${(sessionScope.user!= null) && (sessionScope.user.userType eq 'admin_sp')}">
      <%
                //if(user != null && user.getUserType().equalsIgnoreCase("admin_sp"))
                    String[] spInfo = new SubjectPreferenceSettingsInfo(application).getCurrentSettings();
                    boolean isEnabled = spInfo[1].equalsIgnoreCase("1");
      %>
                    <form method="post" action="sp_settings">
                        <%-- <%
                        if(!isEnabled){
%>--%>
                        <c:choose>
                        <c:when test="${not isEnabled}">
                            <input id="on" type="radio" name="setOnOff" value="On" onclick="show(this)">&nbsp;&nbsp;Enable Subject Preferences
                        </c:when>
                        <c:otherwise>
                            <input id="off" type="radio" name="setOnOff" value="Off" onclick="show(this)">&nbsp;&nbsp;Disable Subject Preferences    
                        </c:otherwise>
                        </c:choose>
                        <%--<%                }
                        else
                        {
      %>
                        <input id="off" type="radio" name="setOnOff" value="Off" onclick="show(this)">&nbsp;&nbsp;Disable Subject Preferences
      <%                }
      %>--%>
                        <br><br>
                        <div id="odd" class="hide"><input  type="radio" name="semType" value="Odd" >&nbsp;&nbsp;Odd</div>
                        <br>
                        <div id="even" class="hide"><input  type="radio" name="semType" value="Even" >&nbsp;&nbsp;Even</div>
                        <br><br>    
                        <input type="submit" name="submit" value="Set">
                        
                    </form>
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
<script type="text/javascript">
    
    function show(elm) {
        var odd = document.getElementById("odd");
        var even = document.getElementById("even");
        if(elm.id === 'on'){
            odd.classList.remove('hide');
            even.classList.remove('hide');
        }
        else{
            odd.classList.add('hide');
            even.classList.add('hide');
        }
            
} 
    
    
</script>
</html>
