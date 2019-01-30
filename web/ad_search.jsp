<%-- 
    Document   : ad_search
    Created on : Jun 10, 2016, 5:42:57 PM
    Author     : Aditi Dandekar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            input[type=text], select {
    width: 50%;
    padding: 12px 20px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}
        input[type=submit] {
    background-color: white; /* Green */
    border:2px solid cyan;
    color: black;
    padding: 7px 18px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
    //height: 10px;
    border-radius: 8px;
     -webkit-transition-duration: 0.4s; /* Safari */
    transition-duration: 0.4s;
}
input[type=submit]:hover{
    background-color: cyan;
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

        <center><h1>Search here</h1></center>
    <br>
        <div align='center'>
            <form action="ad_search.jsp">
        <select name="s_by1">
            <option>Book_Id</option>
            <option>Title</option>
        </select>
            <input type="submit" name="submit" value="Submit">
         </form>
         </div>
        <c:if test="${param.submit=='Submit'}">
            <c:set var="s_by1" scope="session" value="${param.s_by1}"></c:set>
            
         </c:if>
        <div align='centre'>
            
        <form action="Ad_search" method="post">
            <br>
            <br>

            <center>    
        <c:choose>
            <c:when test="${sessionScope.s_by1=='Book_Id'}">
                Id <input type="text" name="Book_Id" value="${requestScope.sr}">
                <input type="submit" name="sub" value="Search">
            </c:when>
            <c:when test="${sessionScope.s_by1=='Title'}">
                Title <input type="text" name="Title" value="${requestScope.sr}">
                <input type="submit" name="sub" value="Search">
            </c:when>
        </c:choose>
            </center>
            <br>
            <br>
        </form>
           
            </div>
   
             <c:choose>
                 
        <c:when test="${requestScope.flag=='true'}">        
       
        
        
            <table border="1" align='center' cellspacing='5' id="printTable">
            <tr>
                <th>BOOK ID</th>
                <th>TITLE</th>
                <th>PUBLICATION</th>
                <th>AUTHOR</th>
                <th>STATUS</th>
                <c:if test="${requestScope.fg=='tr'}">
                <th>INITIALS</th>
                </c:if>
               
            </tr>
            <c:forEach var="ar" items="${requestScope.arr}">
            <tr>
                <c:forEach var="rs" varStatus="r" items="${ar}">
                    <td>${rs}</td>                 
                </c:forEach>
                    
            </tr>
            </c:forEach>
            
            </table>
            <br>
            <br>

         
          
        </c:when>
                    <c:when test="${requestScope.flag=='false'}"><h2 align='center'>Book not found!!</h2></c:when>
                    
                    <c:when test="${requestScope.flag=='title'}">
                        <c:if test="${requestScope.cnt!=0}">
                            <align="centre"><h3 id="ini">${param.Title}</h3></align>
             <table border="1" align='center' cellspacing='5' id="printTable">
            <tr>
                <th>BOOK ID</th>
                <th>TITLE</th>
                <th>PUBLICATION</th>
                <th>AUTHOR</th>
                <th>STATUS</th>
                <th>USER ID</th>
                <th>INITIALS</th>
                
            </tr>
            <c:forEach var="ar" items="${requestScope.arr}">
            <tr>
                <c:forEach var="rs" varStatus="r" items="${ar}">
                    <td>${rs}</td>                 
                </c:forEach>
                    
            </tr> 
            </c:forEach>
         </table>
            <br>
            <br>
            <input type="submit" value="Report" onclick="printData()"/>
                </c:if>
            <h2 align='center'>Total books:${requestScope.cnt}
            <br>${msg}</h2>
           
            </c:when>
        </c:choose>
                    
    
                <div class="footer" >
                    <div class="footer_resize">
                        <%@include file="lib_footer.html" %>

                    </div>
                </div>

<script type="text/javascript">
            function printData()
            {
                var divToPrint= document.getElementById("printTable");
                

                var ini=document.getElementById("ini") ;
        var htmlToPrint= "<center>DHARMSINH DESAI UNIVERSITY,NADIAD</center><br>"+"<center>Faculty Of Technology</center><br>"+"<center>Computer Engineering</center><br>"+"<center>Library Management</center><br>"+"<center>"+ini.outerHTML+"</center><br>"  ;
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
