
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="gen.*"%>
<%@page import="subjectpreference.*"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>DDU Portal</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <%--<link rel="stylesheet" type="text/css" href="css/style.css"/>--%>
    </head>
    <body>
        <%@include file="header.html"%>
        <c:choose>
        <c:when test="${sessionScope.user !=null && sessionScope.user.userType eq 'admin_sp'}">
                <jsp:include page="sidebar_sp.html" />
        </c:when>
        <c:otherwise>
              <jsp:include page="sidebar.html" />
        </c:otherwise>
        </c:choose>

        <jsp:include page="login_check.jsp"></jsp:include>
        
        <div class="container">
            
            <font color="indianred"><h2>Hello, <c:if test="${sessionScope.user!= null}">${sessionScope.user.userName}</c:if></h2></font>
      <%--      <%@include file="menu.html" %>
            <div id="sidebar">
                <h4>View Subject Preference</h4>
                <%
               
                %>
            </div>
            <div id="main">
        --%>        
            <%
                    User user=(User)session.getAttribute("user");
                    String mes = (String)request.getAttribute("message");
                    if(mes!=null)
                    {
                        if(mes.contains("not"))
                        {
                            request.setAttribute("message", "Your Subject Preferences are not saved"); 
                            request.getRequestDispatcher("display_ack_user.jsp").forward(request, response); 
                        }
                    
                    ArrayList<String> selectedSubjects = new SubjectPreferenceSettingsInfo(application).getSelectedSubjectNames(user.getUserID());
                    if(selectedSubjects != null)
                    {
            %>

                        <form method="post" action="display_ack_user.jsp">
                        <h3>Your Selected Subjects :</h3><br>
                        <ol>
            <%
                        for(String selectedSubject : selectedSubjects )
                        {
            %>
                            <font color="black"><li><%=selectedSubject.trim()%></li></font><br>
            <%
                        }
            %>       
                        </ol>
                        <br>
                            <input type="hidden" name="message" value="<%=mes%>">
                            <input type="submit" name="Submit" value="OK">
                        </form>
            <%      }
                    else
                    {
                        out.println("<font color=\"indianred\"> No subject is selected </font>");
                    }
                    }
            %>
            
            <%--@include file="footer.html" --%>
        </div>
    </body>
        <div class="footer" >
      <div class="footer_resize">
          <%@include file="footer.html" %>

      </div>
  </div>
</html>

