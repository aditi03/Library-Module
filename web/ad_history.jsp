<%-- 
    Document   : ad_history
    Created on : Aug 20, 2016, 4:21:20 PM
    Author     : Aditi Dandekar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            
            .myrow{
                 border: 1px solid black;
                 border-collapse: collapse;      
                 font-family:areial;
                 font-size:100%;
                 text-align: left;
                 height: 20px;
                 line-height: 150%;
                 padding: 5px;
            }            
        </style>
    
    </head>
    <body>
        <%@include file="header.html"%>
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

    <jsp:include page="login_check.jsp"></jsp:include>
    <div class="container">
    
           <form action="History" method="post"> 
        <h3>Faculty</h3>
        
            <select name="fac" align="centre">
                <c:forEach var="ui" items="${sessionScope.uid}">
                <option>
                    ${ui}
                </option>
                </c:forEach>

            </select>
            <input type="submit" value='Submit' name='submit' /></td>

           </form>
        <br/>
        
    <c:if test="${param.submit=='Submit'}">
        <form>
        <c:if test="${requestScope.flag=='true'}">
            <center><h3 id="bh">BOOK HISTORY</h3></center>
            <center><h3 id="ini">${requestScope.ini}</h3></center>
            <br/>
            <table border="1" align="center" cellspacing="5" id= "printTable">
            <th>Book Id</th>
            <th>Title</th>
            <th>Issue Date</th>
            <th>Return Date</th>
        <c:forEach var="ar" items="${requestScope.arr}">
            <tr>
                <c:forEach var="rs" varStatus="r" items="${ar}">
                    <td>${rs}</td>
                                        
                </c:forEach>
                 
            </tr>
            </c:forEach>
            </table>
        
        <br>
        </c:if>
        <c:if test="${requestScope.flag!='true'}">
        
            <center><h3>BOOK HISTORY</h3></center>
            <center>${requestScope.ini}</center>
            <br/>
            <center>${requestScope.flag}</center>
        </c:if>
            <input type='submit' value='Report' onclick="printData()"/>
        </form>
    </c:if>
    </div>

<script type="text/javascript">
            function printData()
            {
                var divToPrint= document.getElementById("printTable");
                var bh=document.getElementById("bh") ;

                var ini=document.getElementById("ini") ;
        var htmlToPrint="<center>DHARMSINH DESAI UNIVERSITY,NADIAD</center><br>"+"<center>Faculty Of Technology</center><br>"+"<center>Computer Engineering</center><br>"+"<center>Library Management</center><br>"+"<center>" +bh.outerHTML+"</center><br>"+"<center>"+ini.outerHTML+"</center><br>"  ;
        htmlToPrint += '' +
                    '<style type="text/css">' +
        'table, table th, table td {' +
                  'border: 1px solid black;'+
                 'border-collapse: collapse;'+      
                 'font-family:areial;'+
                 'text-align: left;'+
                 'font-size:100%;'+
                 'height: 10px;'+
                 'line-height: 120%;'+
                 'padding: 5px;'+
        '}' +
        '</style>';
    htmlToPrint += divToPrint.outerHTML;
                newWin= window.open("");
                newWin.document.write(htmlToPrint);
                newWin.print();
                newWin.close();
            }
        </script>
    </body>
</html>
