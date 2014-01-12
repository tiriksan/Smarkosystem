<%-- 
    Document   : regov2
    Created on : 12.jan.2014, 17:28:37
    Author     : Bruker
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h3> registrere øving </h3>

<c:when test="${valget eq '2'}"> 


<form:form action="regov2.htm" method="post" modelAttribute="øving" >
    <table>
        <tr><td>ØvingsNr: </td><td><form:input path="øvingsnr" /></td><td><form:errors path="øvingsnr" /></td></tr>
        <tr><td>Emnekode:   </td><td><form:input path="emnekode" /></td><td><form:errors path="emnekode" /></tr>
        <tr><td>GruppeId:       </td><td><form:input path="gruppeid" /></td><td><form:errors path="gruppeid" /></td></tr>
       <tr><td><label for="obligatorisk"> Obligatorisk: </label></td>
                                <td>
                                    <form:select path="obligatorisk" id="obligatorisk">

                                        <form:option value="0">Velg Ja/Nei</form:option>

                                        <form:option value="1">Ja</form:option>
                                        <form:option value="2">Nei</form:option>

                                    </form:select> 
       
       
      <br>
        <tr><td colspan="2"><input type="submit" value="Registrer Øving"></td></tr>
    </table>
</form:form>
    
    </c:when>



