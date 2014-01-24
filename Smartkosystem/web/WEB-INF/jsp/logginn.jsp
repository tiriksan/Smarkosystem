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
        
       
        
        <c:if test="${not empty error}">
            
       <div id="feilLogginn"><c:out value="${error}"/></div>              
        </c:if>
               <c:if test="${empty error}">
            
       <div id="LogginnVelkommen"><spring:message code="velkomsthilsen" /></div>              
        </c:if>
  
       
        <div id="logginnform">
            
       
            
    <form:form method="POST" action="login">         
                   

    
        <table class="logginntable">
            <tr><td><label for="brukernavninnlogging">Brukernavn:</label></td><td><input type="email" name="brukernavninnlogging" id="brukernavninnlogging"></td></tr>
            <tr><td><label for="passordinnlogging">Passord:</label></td><td><input type="password" name="passordinnlogging" id="passordinnlogging"></td></tr>
            <tr><td>&nbsp;</td><td><input type="submit" name=login" value="Login"></td></tr></table>
        
        
        <br>
        <c:url value="/glemtpassord.htm" var="glemtpassordLink" />
        <a id="glemtpassord" href="${glemtpassordLink}">Glemt passord?</a>

   
    </form:form>
        
            
            
        </div>
  
                         </body>
</html>
