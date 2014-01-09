<%-- 
    Document   : innsetting
    Created on : Jan 8, 2014, 9:07:42 AM
    Author     : christmw
--%>
_______________________________________________________-----------------------
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
        <spring:message code="welcome.message" />

    
    <form:form method="POST" modelAttribute="brukerinnlogg" action="login">
        <form:errors path="*" />
    <center>
                <table><tr><td>
                           Brukernavn:</td><td> <form:input path="bruker.brukernavn" /></td>
                        <td><form:errors path="bruker.brukernavn" /></td>
                                                      </tr>
                    <tr><td>Passord:</td><td> <form:input password="passord" path="bruker.passord" /></td>
                        <td><form:errors path="bruker.passord" /></td>
</tr>
           <tr><td>&nbsp;</td><td><input type="submit" name=login" value="Login"></td></tr></table></center>
   
    </form:form>
                         </body>
</html>
