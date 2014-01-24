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

<c:if test="${!RegistrertArbeidskrav}">
    <h3>Legg til arbeidskrav:</h3>
    <c:if test="${emne==null}">
        <p id="overEmnedrop">Velg emne</p>
        <form method="POST" id="emnedrop" action="arbeidskravOving.htm">
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
        <form method="POST" action="arbeidskravVelgAnt.htm">
            Velg øvinger:

            <c:forEach items="${øvinger}" var="øving">
                ${øving.øvingsnr}:<input type="checkbox" name="valgteØvinger" value="${øving.øvingsnr}" <c:if test="${erØvingValgt != null && erØvingValgt[øving.øvingsnr-1]}">checked</c:if>>
            </c:forEach>
            <input type="submit" value="<c:if test="${!gåVidere}">Velg øvinger</c:if><c:if test="${gåVidere}">Oppdater øvinger</c:if>">
            </form>
        <c:if test="${!gåVidere}">antall øvinger og beskrivelse til arbeidskravet kommer opp etter du har valgt øvinger</c:if>
        <c:if test="${gåVidere}">
            <form action="registrerArbeidskrav.htm" method="GET">
                <label for="antallØvinger">Antall øvinger:</label><input type="number" id="antallØvinger" value="1" name="antØvinger" min="1" max="${fn:length(valgteØvingerListe)}">
                <br>
                <label for="arbeidsKravTekst">Beskrivelse på arbeidskravet</label>
                <input id="arbeidsKravTekst" type="text" name="arbKravText">
                ${tekstFeilmelding}
                <br>
                <input type="submit" value="Registrer arbeidskrav">
            </form>
        </c:if>
    </c:if>
</c:if>
<c:if test="${RegistrertArbeidskrav}">
    <p>Registrering av arbeidskrav vellykket!</p>
    <form action="leggTilNyttArbeidskrav.htm">
        <input type="submit" value="Legg til nytt arbeidskrav">
    </form>
    
</c:if>