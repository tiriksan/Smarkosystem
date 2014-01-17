<%-- 
    Document   : Laerer
    Created on : Jan 17, 2014, 9:24:01 AM
    Author     : christmw
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

   <script>
       function javas(kode){
           window.location = "/adminlaerer.htm?x=" + kode;
       }
                                        
   </script>
   
   <c:choose>
<c:when test="${valget eq null}">
        <c:set var="visfag" value="load"/>
        <c:set var="visov" value="ikkeload"/>
</c:when>
</c:choose>
    

         
        <form:form action="adminlaerer" method="post" modelAttribute="brukerinnlogg" >
    <table>
                                    
                            <tr><td><label for="emnekode"> Dine emner: </label></td><td>
                

                                    <select name="Emner" id="Emner" onchange="javas(this.value)">
                                        <c:forEach items="${allefagene}" var="fagliste">
                                            <option value="${fagliste}">${fagliste}</option>
                                        </c:forEach>
                                    </select>
        
                                 
      
        <tr><td colspan="2"><input type="submit" value="visfag"></td></tr>
    </table>
    </form:form>
        
 
