<%-- 
    Document   : regov2
    Created on : 12.jan.2014, 17:28:37
    Author     : Bruker
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h3> registrere �ving </h3>

<%-- <c:when test="${valget eq '2'}"> 

--%>
<form:form action="regov23" method="post" modelAttribute="�ving" >
    <table>
        <tr><td>�vingsNr: </td><td><form:input path="�vingsnr" /></td><td><form:errors path="�vingsnr" /></td></tr>
        <tr><td>Emnekode:   </td><td><form:input path="emnekode" /></td><td><form:errors path="emnekode" /></tr>
        <tr><td>GruppeId:       </td><td><form:input path="gruppeid" /></td><td><form:errors path="gruppeid" /></td></tr>
       <tr><td><label for="obligatorisk"> Obligatorisk: </label></td>
                                <td>
                                  
       
       
      <br>
        <tr><td colspan="2"><input type="submit" value="regoving"></td></tr>
    </table>
</form:form>
    
   



