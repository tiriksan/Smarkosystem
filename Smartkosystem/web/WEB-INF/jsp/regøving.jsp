<%-- 
    Document   : regøving
    Created on : 12-Jan-2014, 16:41:10
    Author     : Bjornars
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrere og oppdatere øvinger</title>
    </head>
    <body>
           <form:form method="POST" modelAttribute="brukerinnlogg" action="login">         
                   

    
        <table><tr><td colspan="2"> <spring:message code="velkomsthilsen" /></td></tr>
            <tr><td>emnenavn/kode</td><td><form:input path="brukernavn" /></td><td><form:errors path="brukernavn" /></td></tr>
            <tr><td>Antall &oslashvinger</td><td><form:password password="passord" path="passord" /></td><td><form:errors path="passord" /></td></tr>
            <tr><td>Oppdater</td><td><input type="submit" name=login" value="Login"></td></tr></table>
        <c:out value="${bruker.md5('Petter')} og kake"/>
        
        <br>
        <c:url value="/glemtpassord.htm" var="glemtpassordLink" />
        <a id="glemtpassord" href="${glemtpassordLink}">Glemt passord?</a>

   
    </form:form
    </body>
</html>
