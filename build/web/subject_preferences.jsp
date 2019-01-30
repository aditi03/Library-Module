
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="subjectpreference.SubjectPreferenceSettingsInfo"%>
<%@page import="subjectpreference.SubjectsGetter"%>
<%@page import="gen.*"%>
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

       <jsp:include page="login_check.jsp"/>
       
       <div class="container">
            <%--@include file="title.html" --%>
            <font color="indianred"><h2>Hello,  <c:if test="${sessionScope.user!= null}">${sessionScope.user.userName}</c:if></h2></font>
                
                <%
                    User user = (User)session.getAttribute("user");
                    LinkedHashMap<String, ArrayList<Subject>> subjectsMap = new SubjectsGetter(application).fetchSubjects();
                    SubjectPreferenceSettingsInfo spsi = new SubjectPreferenceSettingsInfo(application);
                    ArrayList<Integer> selectedSubIds = spsi.getSelectedSubjects(user.getUserID());
                    String slotPreference = spsi.getSlotPreference(user.getUserID());
                    
                    System.out.println("Slot Preference from JSP= "+slotPreference);
                  
                    if(selectedSubIds != null)
                        System.out.println("selectedSubIds from JSP : "+selectedSubIds);
                    else
                        System.out.println("selectedSubIds is null");
                    
                    if(subjectsMap != null)
                    {
                %>
                 
                <form method="post" action="insert_subject_preferences" onsubmit="return validateSelection();">
                <%
                    for (String sem : subjectsMap.keySet()) {
                %>
                <table border="0" style="width:100%">
                    <tr>
                        <th colspan="1"><%=sem%></th>
                    </tr>
                    <%
                        ArrayList<Subject> semSubjects = subjectsMap.get(sem);
                        
                        for (Subject s : semSubjects) 
                        {
                    %>
                      <%--<c:forEach items="${semSubjects}" var="s">--%>
                        <%
                            if(s.getOffered() == 1 )
                            {
                        %>
                        <%--<c:if test="${s.offered eq 1}">--%>                        
                            <tr>
                                <td><input type="checkbox" name="subjects" value="<%=s.getSubId()%>"
                                    
                                           <%--<td><input type="checkbox" name="subjects" value="${s.subId}">--%>
                                            <%
                                            if(selectedSubIds != null && selectedSubIds.contains(s.getSubId()))
                                            {
                                            %>
                                        
                                            checked
                                            <%
                                            }
                                            %>
                                       >
                                       &nbsp;&nbsp;<%=s.getSubName()%>
                                       <%--${s.subName}--%>
                                </td>
                            </tr>
                        <%
                            } 
                        }
                    %>
                </table>
                <br>
                <% 
                        }
                %>
                                            
                
                 <table boder="0">
                    <tr><th colspan="3">Preferred Time Slot</th></tr>
                    <tr>
                        <td><input type="radio" name="slot" value="Morning"
                                   <% 
                                        if(slotPreference != null && slotPreference.equalsIgnoreCase("Morning"))
                                        { 
                                            System.out.println("in side morning");
                                   %>   checked
                                   <%   } %>
                                   >Morning
                        </td>
                                   <%--<c:if test="${slotPreference ne null && slotPeference eq 'Noon'}"></c:if>
                                   --%>
                                    <td><input type="radio" name="slot" value="Noon"
                                   <% 
                                        if(slotPreference != null && slotPreference.equalsIgnoreCase("Noon"))
                                        {
                                                        
                                   %>   checked
                                   <%   } %>
                                   >Noon
                        </td>
                        <td><input type="radio" name="slot" value="Any"
                        <% 
                                        if(slotPreference != null && slotPreference.equalsIgnoreCase("Any"))
                                        {
                                                        
                         %>   checked
                         <%             } 
                                        else if(slotPreference == null)
                                        {
                         %>   checked
                         <%             } 
                         %>
                         >Any
                        </td>    
                    </tr>
                </table>
                 <br>
                <input type="submit" name="Submit" value="Confirm">
                </form>
                <%
                }                
                else
                    {
                %>   
                <%--out.println("<font color=\"green\"> Subject Preference Module is disabled </font>");--%>
                <font color="green">Subject Preference Module is disabled</font>
                <%
                    }
                %>
                
                
       </div>
       <div class="footer" >
           <div class="footer_resize">
               <%@include file="footer.html" %>

           </div>
       </div>
            <%--@include file="footer.html" --%>
             
    </body>
        
        <script type="text/javascript">
            function validateSelection()
            {
            var howMany =0;
            var cboxes=document.getElementsByName('subjects');
            var len=cboxes.length;
            for(var i=0;i<len;i++)
            {
                if(cboxes[i].checked)
                    howMany++;
            }
            //var howMany = $('input[type="checkbox"]:checked').length;
            if(howMany<3)
            {
                alert("Select minimum 3 subjects");
                return false;
            }
            else
            {
                //document.myForm.submit();
                return true;
            }
        }
        </script>
</html>
