<%-- 
    Document   : glemtpassord
    Created on : Jan 9, 2014, 10:29:32 AM
    Author     : Kristian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form action="glemtpassordsvar.htm" method="post" modelAttribute="glemtpassordbruker" >
    ${errorMelding}
    ${sendMelding}
    <table>
        <tr><td>Brukernavn: </td><td><form:input path="brukernavn" /></td><td><form:errors path="brukernavn" /></td>
        <tr><td><input type="submit" value="Send epost"></td></tr>
        
    </table>
    
</form:form>
