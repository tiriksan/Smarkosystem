<%-- 
    Document   : endrepassord
    Created on : Jan 9, 2014, 1:13:51 PM
    Author     : Kristian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

    <form:form action="endrepassordsvar.htm" method="post" modelAttribute="endrepassordbruker">
    ${melding}
    ${endrepassordbruker.brukertype}
    <form:errors path="brukertype" />
    <table>
        <tr><td>Nytt passord:    </td><td><form:password  path="passord" /></td><td><form:errors path="passord" /></td>
 <%--   <tr><td>Bekreft passord: </td><td><form:input path="passord" /></td><td><form:errors path="passord" /></td>--%>
        ${endrepassordbruker.passord}
        <tr><td><input type="submit" value="Endre passord"></td></tr>
        
    </table>
    
</form:form>

