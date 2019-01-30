
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="gen.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<title>FACULTY PORTAL</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/newstyle.css" rel="stylesheet" type="text/css" />
<%--<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/script.js"></script>
<script type="text/javascript" src="js/coin-slider.min.js"></script>
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>--%>
<script src="js/formindex.js"></script>
<link rel="stylesheet" href="css/formstyle.css">

    <!--<link rel="stylesheet" href="layout/styles/layout.css" type="text/css" />-->
    <html xmlns="http://www.w3.org/1999/xhtml">
        
        <script language="javascript">
	function validate()
	{	
			if (loginpage.user.value == "")
			{
				alert('Please,Enter the username..');
				loginpage.user.focus();
				return false;
			}
                        if (loginpage.password.value == "")
			{
				alert('Please,Enter the password!!');
				loginpage.password.focus();
				return false;
			}	
                        
        }
        </script>
        <head>
        </head>
        <body>
            <%@include file="header.html" %>
                <form method="post" action="login" name="loginpage" onsubmit="return validate()">
                
                    <c:choose>
                    <c:when test="${sessionScope.user ne null}">
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
                    </c:when>
                    <c:otherwise></c:otherwise>
                </c:choose>
                
                    <div class="container">
                            
                    <c:if test="${requestScope.message != null}">
                        <h2><font color="indianred">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${requestScope.message}</font></h2>
                    </c:if>                              
                    <br></br>
                    <c:choose>
                        <c:when test="${sessionScope.user ne null}">
                            <center><h2><font color="indianred">Hello, <b>${sessionScope.user.userName}</b> ,You are already logged in!</center>
                        </c:when>                            
                        <c:otherwise>
                        
                        </div>
                        <div class="login-page">
                        <div class="form">
                        <form class="login-form">    
                        <input type="text" name="user" placeholder="username"/>
                        <input type="password" name="password" placeholder="password"/>
                        <%--<input type="submit" name="submit" value="LOGIN"/>--%>
                        
                        <button>login</button>
                       
                        <%--<p class="message">Not registered? <a href="#">Create an account</a></p>--%>
                        </form>
                        </form>
                        </div>
                        </div>
                        </c:otherwise>
                    </c:choose>                    
                    
                    <div class="footer" >
                        <div class="footer_resize">
                            <%@include file="footer.html" %>
                        </div>
                    </div>
        </body>                
        
    </html>
