<%-- 
    Document   : regArbeidskrav
    Created on : Jan 24, 2014, 3:37:35 PM
    Author     : Kristian
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<c:if test="${emne==null}">
    <p id="overEmnedrop">Velg ønsket emne!</p>
    <form method="GET" id="emnedrop" action="arbeidskravOving.htm">
        <select name="emnet">
            <c:forEach items="${emner}" var="emnet">
                <option value="${emnet.emnenavn}">${emnet.emnekode}</option>
                <c:if test="${not empty emnekodevalgt}">
                </c:if>
            </c:forEach>
        </select>

        <input type="submit" value="Velg">
    </form>
</c:if>
<c:if test="${emne != null}">
    <form action="arbeidskravVelgAnt.htm">
        Velg øvinger:
        <c:forEach items="${øvinger}" var="øving">
            ${øving.øvingsnr}:<input type="checkbox" name="valgteØvinger" value="${øving.øvingsnr}">
        </c:forEach>
        <input type="submit" value="Gå videre">
        here?watat
    </form>
    <c:if test="${gåVidere}">
        do i even here
        <form>
            <input type="number" name="antØvinger" min="1" max="${fn:length(valgteØvingerListe)}">
            <input type="text" name="arbKravText">
            
            <input type="submit" value="Registrer arbeidskrav">
        </form>
    </c:if>
</c:if>