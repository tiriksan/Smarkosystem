<%-- 
    Document   : Laerer
    Created on : Jan 17, 2014, 9:24:01 AM
    Author     : christmw
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

 
  
<script type="text/javascript">
    
function submitform(){
  document.minform.submit();
}
</script>
   
   <c:choose>
<c:when test="${valget eq null}">
 <form:form action="adminlaerer.htm" method="post" modelAttribute="brukerinnlogg" name="minform">
    <table>
                                    
                            <tr><td><label for="emnekode"> Dine emner: </label></td><td>
                

                                    <select name="Emner" id="Emner" onchange="submitform()">
                                        <c:forEach items="${allefagene}" var="fagliste">
                                            <option value="${fagliste}">${fagliste}</option>
                                        </c:forEach>
                                    </select>
        
    <tr><td colspan="2"><input type="submit" value="visfag"></td></tr>
    </table>
    </form:form>
        
 
</c:when>

       <c:otherwise>
                            <h1>BLABLABLA</h1>
       </c:otherwise>
   </c:choose>