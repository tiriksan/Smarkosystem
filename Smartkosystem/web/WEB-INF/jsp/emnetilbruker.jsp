<%-- 
    Document   : emnetilbruker
    Created on : 24-Jan-2014, 09:15:40
    Author     : Bjornars
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<form:form action="emnetilbruker2.htm" method="get" modelAttribute="brukerhentet" name="minform">
    <table>
        <tr>
            <td>
                <div id="brukere">
                    <select multiple="true" name="bruker" id="bruker" >
                        <c:forEach items="${brukere}" var="bruker">
                            <option value="${bruker.getBrukernavn()}">${bruker.getFornavn()} ${bruker.getEtternavn()}</option>
                        </c:forEach>
                    </select>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div id="emner">
                    <select multiple="true" name="emne" id="emne">
                        <c:forEach items="${emner}" var="emne">
                            <option value="${emne.getEmnenavn()}"> ${emne.getEmnenavn()} - ${emne.getEmnekode()} </option>
                        </c:forEach>
                    </select>
                    <input type="submit" value="Lagre">
                </div>
            </td>
        </tr>
    </table>
</form:form>