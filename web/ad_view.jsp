<%-- 
    Document   : ad_view
    Created on : Aug 13, 2016, 5:26:10 PM
    Author     : Aditi Dandekar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
.wrapper-dropdown-5 {
    /* Size & position */
    position: relative;
    width: 200px;
    margin: 0 auto;
    padding: 12px 15px;

    /* Styles */
    background: #fff;
    border-radius: 5px;
    box-shadow: 0 1px 0 rgba(0,0,0,0.2);
    cursor: pointer;
    outline: none;
    transition: all 0.3s ease-out;
}

.wrapper-dropdown-5:after { /* Little arrow */
    content: "";
    width: 0;
    height: 0;
    position: absolute;
    top: 50%;
    right: 15px;
    margin-top: -3px;
    border-width: 6px 6px 0 6px;
    border-style: solid;
    border-color: #4cbeff transparent;
}
.wrapper-dropdown-5 .dropdown {
    /* Size & position */
    position: absolute;
    top: 100%;
    left: 0;
    right: 0;

    /* Styles */
    background: #fff;
    border-radius: 0 0 5px 5px;
    border: 1px solid rgba(0,0,0,0.2);
    border-top: none;
    border-bottom: none;
    list-style: none;
    transition: all 0.3s ease-out;

    /* Hiding */
    max-height: 0;
    overflow: hidden;
}
.wrapper-dropdown-5 .dropdown option {
    padding: 0 10px ;
}


.wrapper-dropdown-5.active {
    border-radius: 5px 5px 0 0;
    background: white;
    box-shadow: none;
    border-bottom: none;
    color: black;
}

.wrapper-dropdown-5.active:after {
    border-color: #82d1ff transparent;
}

.wrapper-dropdown-5.active .dropdown {
    border-bottom: 1px solid rgba(0,0,0,0.2);
    max-height: 400px;
}
.wrapper-dropdown-5:focus {
    border-radius: 5px 5px 0 0;
    background: white;
    box-shadow: none;
    border-bottom: none;
    color: black;
}

.wrapper-dropdown-5:focus:after {
    border-color: #82d1ff transparent;
}

.wrapper-dropdown-5:focus .dropdown {
    border-bottom: 1px solid rgba(0,0,0,0.2);
    max-height: 400px;
}
table {
    border-collapse: collapse;
    width: 100%;
}

th, td {
    text-align: left;
    padding: 8px;
}

tr:nth-child(even){background-color: #f2f2f2}

th {
    background-color: cyan;
    color: black;
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
        

        <form action="Ad_view" method="post">
          
            
            <div class="dropdown">
            <select name="status" id="dd" class="wrapper-dropdown-5">
            
            <option  value='All'>All</option>
            <option  value='Available'>Available</option>
            <option  value='Issued'>Issued</option>
            <option  value='Requested'>Requested</option>
                
        </select>
                
                <input type="submit" name="submit" value="Submit">
            </div>
            
                    </form>

        <br><br><br>
            <form action="ad_view.jsp" method="post">
                <div id="selection">
                    <center><font face="Castellar" size="6px"><b>
                        ${requestScope.status}</b></font></center>
                </div>
                <br><br>
                
    <c:if test="${requestScope.flag=='true'}">
         <table border="1" align="center" cellspacing="5" id="printTable">
             <th><center>Book_Id</center></th>
            <th><center>Title</center></th>
            <th><center>Publication</center></th>
            <th><center>Author</center></th>
        <c:forEach var="ar" items="${requestScope.arr}">
            <tr>
                <c:forEach var="rs" varStatus="r" items="${ar}">
                    <td>${rs}</td>
                                        
                </c:forEach>
                 
            </tr>
            </c:forEach>
            </table>
         <br><br>
         <font size="4px" face="Castellar" align="center" id="cnt">Count  :  ${count}</font><br>
          
          
               <br>
               <input type="submit" value="Report" onclick="printData()"/>
    </form>
    
         </c:if>
            <br>
            
           
        
   
    </div>
            <script type="text/javascript">
            function printData()
            {
                var divToPrint= document.getElementById("printTable");
                
                var st=document.getElementById("selection") ;
                var cnt=document.getElementById("cnt") ;
        var htmlToPrint="<center><font size='6px'>DHARMSINH DESAI UNIVERSITY,NADIAD</font></center><br>"+"<center><font size='5px'>Faculty Of Technology</font></center><br>"+"<center><font size='5px'>Computer Engineering</font></center><br>"+"<center><font size='5px'>Library Management</font></center><br>"+"<center><font size='4px'>"+st.outerHTML+"</font></center><br>" ;
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
    htmlToPrint += "<br><br>"+cnt.outerHTML;
                newWin= window.open("");
                newWin.document.write(htmlToPrint);
                newWin.print();
                newWin.close();
            }
            function DropDown(el) {
    this.dd = el;
    this.initEvents();
}
DropDown.prototype = {
    initEvents : function() {
        var obj = this;

        obj.dd.on('click', function(event){
            $(this).toggleClass('active');
            event.stopPropagation();
        }); 
    }
}
        </script>
    </body>
</html>
