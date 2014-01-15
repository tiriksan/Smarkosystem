<%-- 
    Document   : gruppe
    Created on : 14.jan.2014, 12:23:23
    Author     : Undis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:if test="${hjelp == true}">

    lkj
    ${id}
    <c:out value="Alle medlemmene av gruppen:"></c:out><br>


        <table id="brukertabell">
        <c:forEach items="${brukere}" var="bruker">
            <tr>
                <td>${bruker.getFornavn()}
                    ${bruker.getEtternavn()}
                    <%--${bruker.getOvinger()}--%>

                <td><input type="submit" class="gkKnapp" value="Godkjenn"></td></tr>
                </c:forEach>

        <form:form method="POST" action="godkjennalle.htm?x=${fag.emnenavn}">
            <td><input type="submit" value="Godkjenn alle"></td>
            
            </form:form>
        <td><input type="submit" value="Utsett gruppen"></td>
    </table>
</c:if>


