<%-- 
    Document   : regov2
    Created on : 12.jan.2014, 17:28:37
    Author     : Bruker
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h3> registrere øving </h3>

<%-- <c:when test="${valget eq '2'}"> 

--%>
<script type="text/javascript">
    function submitform() {
        document.minform.submit();
    }
</script>

<c:choose>
    <c:when test="${valg eq null}">
        <form:form action="regov2.htm" method="get" modelAttribute="øving" name="minform">
            <table>
                <tr><td>ØvingsNr: </td><td><form:input path="øvingsnr" value="${ovinger}" readonly="true" /></td>
                    <td><form:errors path="øvingsnr" /></td></tr>



                <tr><td><label for="emnekode"> Emne: </label></td><td>


                        <select name="emnevalgt" id="emnevalgt" onchange="submitform()">
                            <c:forEach items="${allefagene}" var="fagen">
                                <option value="${fagen}">${fagen}</option>
                            </c:forEach>
                        </select>
            </table>
        </form:form>
    </c:when>

    <c:otherwise>
        <form:form action="regov2.htm" method="get" modelAttribute="øving" name="minform">
            <table>
                <tr><td>ØvingsNr: </td><td>
                        
                        <form:input path="øvingsnr" value="${ovinger}" readonly="true" /></td>
                    <td><form:errors path="øvingsnr" /></td></tr>



                <tr><td><label for="emnekode"> Emne: </label></td><td>


                        <select name="emnevalgt" id="emnevalgt" onchange="submitform()">
                            <c:forEach items="${allefagene}" var="fagen">
                                <option value="${fagen}">${fagen}</option>
                            </c:forEach>
                        </select>
           
       
        <tr><td colspan="2"><input type="submit" name="submitted" value="Registrer ${valg}"></td></tr>
        
    </table>
 </form:form>

</c:otherwise>
</c:choose>









