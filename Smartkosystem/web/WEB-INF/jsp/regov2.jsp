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
        

                                    
                            <tr><td><label for="emnekode"> Emnekode: </label></td><td>
                

                                    <select name="emner">
                                        <option value="0" label="Velg fag" />
                                        <options items="${allefagene}" />
                                    </select>
        
        <tr>
                    <td>Obligatorisk:</td>
                    <td><checkbox name="obligatorisk" value="true" /></br>
                    <td><errors path="obligatorisk" /></td>
                    
                </tr>
        
        
      
        <tr><td colspan="2"><input type="submit" value="regoving"></td></tr>
    </table>
</form:form>
    
   



