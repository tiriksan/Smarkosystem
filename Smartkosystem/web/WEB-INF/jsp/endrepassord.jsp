<%-- 
    Document   : endrepassord
    Created on : Jan 9, 2014, 1:13:51 PM
    Author     : Kristian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
${melding}
    <form:form action="endrepassordsvar.htm" method="post" modelAttribute="endrepassordbruker">
    <table>
        <c:set var="endrepassordbruker.brukernavn" value="${endrepassordbrukernavn}" />
        <tr><td>Nytt passord:    </td><td><form:password  path="passord" /></td><td><form:errors path="passord" /></td>
 <%--   <tr><td>Bekreft passord: </td><td><form:input path="passord" /></td><td><form:errors path="passord" /></td>--%>
        <tr><td><input type="submit" value="Endre passord"></td></tr>
        
    </table>
    
</form:form>

