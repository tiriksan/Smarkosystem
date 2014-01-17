<%-- 
    Document   : Laerer
    Created on : Jan 17, 2014, 9:24:01 AM
    Author     : christmw
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
         
        <form:form action="adminlaerer" method="post" modelAttribute="emne" >
    <table>
        <tr><td>ØvingsNr: </td><td><form:input path="øvingsnr" /></td><td><form:errors path="øvingsnr" /></td></tr>
        

                                    
                            <tr><td><label for="emnekode"> Emne: </label></td><td>
                

                                    <select name="Emner">
                                        <c:forEach items="${allefagene}" var="fagliste">
                                            <option value="${fagliste}">${fagliste}</option>
                                        </c:forEach>
                                    </select>
        
        
      
        <tr><td colspan="2"><input type="submit" value="visfag"></td></tr>
    </table>
    </form:form>
        
    </body>
</html>
