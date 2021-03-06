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
        
        <table width="100%" cellspacing="0" cellpadding="0"><tr><td class="overtdko" colspan="2">
                    ${emne.emnekode}  -  ${emne.emnenavn}<hr>
                </td></tr>
            <tr><td class="overtdko">Navn</td><td class="overtdko">Øvinger</td></tr>

        <c:if test="${brukerinnlogg.brukertype == 1}">
           
               
            <tr><td class="maingodkjentkrav">
                        ${brukerinnlogg.fornavn}&nbsp;${brukerinnlogg.etternavn}
                </td><td class="maingodkjentkrav"><table><tr>
                    <c:forEach begin="1" end="${fn:length(ovinger)}" var="oving">
                        <td class="tdoving<c:if test="${ovinger[oving-1]==1}">godkjent</c:if>">${oving}</td>
                    </c:forEach>
                        </tr></table></td></tr>
            </table>
                </br>
                <table width="100%" cellspacing="0" cellpadding="0"><tr><td class="overtdko" colspan="2">Arbeidskrav for faget</td></tr>
                <c:forEach begin="0" end="${fn:length(kravgrupper)-1}" var="krav">
                    <tr>
                       
                            <c:if test="${godkjentKrav.get(krav)}">
                               <td class="kravgodkjent">${kravgrupper.get(krav).beskrivelse}</td>
                        <td class="kravgodkjent">  Krav oppnådd
                            </c:if>
                            <c:if test="${!godkjentKrav.get(krav)}">
                               <td class="kravikkegodkjent">${kravgrupper.get(krav).beskrivelse}</td>
                        <td class="kravikkegodkjent">  Krav ikke oppnådd
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
            <tr>
                <td width="40%">Navn</td>
                <td width="30%">Øvinger</td>
                <td id="tdKrav" width="30%">Krav</td>
            </tr>
        </table>
        <table id="ovingoversikttabell" width="100%" cellpadding="0" cellspacing="0" border="1">
            <c:if test="${studenter.size() > 0}">
                <c:forEach begin="0" end="${studenter.size()-1}" var="student">
                    <tr id="ovingoversiktrad">
                        <td id="ovingoversikttd">
                            ${studenter[student].fornavn}&nbsp;${studenter[student].etternavn}
                        </td>

                        <c:forEach begin="1" end="${fn:length(aGO[student])}" var = "i">
                            <td class="tdoving<c:if test="${aGO[student][i-1]==1}"> godkjent</c:if>">${i}</td> 
                        </c:forEach>

                        <td><div id="gkOvingSpace"></div></td>
                            <c:forEach items="${kravgruppeBruker[student]}" var="gkKravBruker">
                            <td class="tdoving<c:if test="${gkKravBruker}"> kravgk</c:if><c:if test="${!gkKravBruker}"> kravikkgk</c:if>">&nbsp;</td>
                        </c:forEach>


                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${studenter.size() <= 0}">
                <tr>Ingen elever i dette emnet</tr>
            </c:if>
        </table>
        <br>
        <table width="100%">
            <form action="resepsjonListe.htm" method="POST">
                <tr>
                    <td>
                        Epost: 
                        <input type="text" name="epost"> 
                        ${epostfeil}
                    </td>
                </tr>
                <tr>
                    <td>  <input title="Send mail til valgt epost som inneholder alle elevene som kan gå opp til eksamen" type="submit" value="Send godkjent mail"></td>
            </form>
            <form action="sendAdvarselMail.htm">
                <td>  <input title="Send mail til alle elever som ikke har godkjent arbeidskravene" type="submit" value="Send advarsel mail"></td>
            </form>
        </tr>
    </table>
</c:if>
</c:if>