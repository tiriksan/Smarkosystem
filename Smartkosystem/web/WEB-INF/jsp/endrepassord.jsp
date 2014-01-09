<%-- 
    Document   : endrepassord
    Created on : Jan 9, 2014, 1:13:51 PM
    Author     : Kristian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<form:form action="glemtpassordsvar.htm" method="post" modelAttribute="glemtpassordbruker" >
    ${errorMelding}
    <table>
        <tr><td>Brukernavn: </td><td><form:input path="brukernavn" /></td><td><form:errors path="brukernavn" /></td>
        <tr><td><input type="submit" value="Send epost"></td></tr>
        
    </table>
    
</form:form>

