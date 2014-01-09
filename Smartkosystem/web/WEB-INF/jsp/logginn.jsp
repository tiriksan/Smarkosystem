<%-- 
    Document   : innsetting
    Created on : Jan 8, 2014, 9:07:42 AM
    Author     : christmw
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Innloggings side</title>
    </head>
    <body>
        
        <div id="logginnform">
            
            
    <form:form method="POST" modelAttribute="brukerinnlogg" action="login">         
                   

    
        <table><tr><td colspan="2"> <spring:message code="velkomsthilsen" /></td></tr>
            <tr><td>Brukernavn:</td><td><form:input path="brukernavn" /></td><td><form:errors path="brukernavn" /></td></tr>
            <tr><td>Passord:</td><td><form:password password="passord" path="passord" /></td><td><form:errors path="passord" /></td></tr>
            <tr><td>&nbsp;</td><td><input type="submit" name=login" value="Login"></td></tr></table>
        <c:out value="${bruker.md5('Petter')} og kake"/>
        
        <br>
        <c:url value="/glemtpassord.htm" var="glemtpassordLink" />
        <a id="glemtpassord" href="${glemtpassordLink}">Glemt passord?</a>

   
    </form:form>
            
            
        </div>
  
                         </body>
</html>
