<%-- 
    Document   : header
    Created on : Oct 31, 2013, 12:39:16 PM
    Author     : Kristian
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<table width="100%" height="100%" cellspacing="0" cellpadding="0"><tr><td width="75%" height="100%"><center><h2>Smart Kø System 2.0</h2></center></td>
        
        <td class="infoiheader" valign="top">
    
    <c:if test="${not empty brukerinnlogg.brukernavn}">
        Logget inn som:</br>
        <c:out value="${brukerinnlogg.fornavn}"/> <c:out value="${brukerinnlogg.etternavn}"/>
        
        </br></br></br>
        <form method="post" action="loggut">
            <input type="submit" value="Logg ut!">
        </form>
        
    </c:if>    
        </td></tr></table>