<%-- 
    Document   : header
    Created on : Feb 2, 2016, 4:23:24 PM
    Author     : admin
--%>
<link href="css/newstyle.css" rel="stylesheet" type="text/css" />

<html>
    <style>
 .down
 {
     position:absolute;
     right:10px;
    
 }
    </style>
<body>
    <!--<form action="finallogin.html" method="post" name="frm1">-->
<div class="header">
		<div class="header_resize">
    <form method="post" action="logout.jsp">
    <table border="0">
	<tr>
		
	<td><div class="img"><img src="images/ddu_logo.jpg" height="100" width="100" /></div></td>
		<td>
			<div class="logo">
				<h2>Dharmsinh Desai University</h2>
				<h3>Department of Computer Engineering</h3>
				<h3>FACULTY <span>PORTAL</span></h3>				
			</div>		
		</td>
	</tr>
	
  </table>
        <div class="down">
        <c:choose>
            <c:when test="${sessionScope.user eq null}">
                <%--input type="submit" disabled="disabled" name="logout" value="LOGOUT"/--%>
            </c:when>
            <c:otherwise>
                <input type="submit" name="logout" value="LOGOUT"/>
            </c:otherwise>
            
        </c:choose>
        </div>
  </form>
  </div>
  </div>
      </body>
</html>