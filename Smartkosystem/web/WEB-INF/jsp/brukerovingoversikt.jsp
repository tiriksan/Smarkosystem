<%-- 
    Document   : brukerovingoversikt
    Created on : Jan 21, 2014, 9:19:55 AM
    Author     : Kristian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:if test="${emne == null}">
    <form method="POST" id="emnedrop" action="valgtBrukeroversikt.htm">
        <select name="emnekode">
            <c:forEach items="${fagene}" var="fag">
                <option value="${fag.emnenavn}">${fag.emnekode}</option>
                <c:if test="${not empty emnekodevalgt}">
                </c:if>
            </c:forEach>

        </select>

        <input type="submit" value="Velg">
    </form>
</c:if>

<c:if test="${emne != null}">
    <div>
        ${emne.emnekode}&nbsp;${emne.emnenavn}

        <c:if test="${brukerinnlogg.brukertype == 1}">
            <table width="100%" cellspacing="0" cellpadding="0" id="ovingoversikttabell">
                <tr id="ovingoversiktrad">
                    <td id="ovingoversikttd">
                        ${brukerinnlogg.fornavn}&nbsp;${brukerinnlogg.etternavn}
                    </td>
                    <c:forEach begin="1" end="${fn:length(ovinger)}" var="oving">
                        <td class="tdoving<c:if test="${ovinger[oving-1]==1}"> godkjent</c:if>">${oving}</td>
                    </c:forEach>
                </tr>
            </table>
            <div>Arbeidskrav i dette faget</div>
            <table width="100%">
                <c:forEach begin="0" end="${fn:length(kravgrupper)-1}" var="krav">
                    <tr>
                        <td>${kravgrupper.get(krav).beskrivelse}</td>
                        <td>
                            <c:if test="${godkjentKrav.get(krav)}">
                                Krav oppnådd
                            </c:if>
                            <c:if test="${!godkjentKrav.get(krav)}">
                                Krav ikke oppnådd
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

    </div>


    <c:if test="${brukerinnlogg.brukertype>1}">
        <div>Arbeidskrav i dette faget</div>
        <table>
            <c:forEach items="${kravgrupper}" var="krav">
                <tr>
                    <td>${krav.beskrivelse}</td>
                </tr>
            </c:forEach>
        </table>
        <table width="100%">
            <c:forEach begin="0" end="${studenter.size()-1}" var="student">
                <tr>
                    <td>
                        ${studenter[student].fornavn}&nbsp;${studenter[student].etternavn}
                    </td>
                    <td>

                        <c:forEach begin="1" end="${fn:length(aGO[student])}" var = "i">
                        <td <c:if test="${aGO[student][i-1]==1}">class="godkjent"</c:if>>${i}</td> 
                    </c:forEach>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</c:if>