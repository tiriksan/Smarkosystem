<%-- 
    Document   : studentkø
    Created on : 10.jan.2014, 14:18:06
    Author     : Undis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
${feilmelding}
<c:choose>
    <c:when test="${not empty fagene}">

        <c:forEach items="${fagene}" var="fag">
            <a href="<c:url value="/studentko.htm?x=${fag.emnenavn}"/>">${fag.emnekode}</a>

            <c:if test="${not empty emnekodevalgt}">



            </c:if>


        </c:forEach>

        <c:choose>
            <c:when test="${not empty emnenavnvalgt}">
                <c:out value="Du ser nå køen for faget ${emnenavnvalgt}"/>
                <c:if test="${brukerinnlogg.brukertype == 3}">
                    <form:form method="POST" action="aktiverko.htm?x=${emnenavnvalgt}">
                        <input type ="submit" value ="Aktiver kø!">
                    </form:form>
                </c:if>
                <c:out value="Status: "/>
                <c:if test = "${aktiv}">
                    Aktiv
                    <c:if test="${brukerinnlogg.brukertype == 1}">
                        <form:form method="POST" action="settiko.htm?x=${emnenavnvalgt}">
                            <input type ="submit" value ="Still i kø">
                        </form:form>
                    </c:if>
                </c:if>
                <c:if test = "${!aktiv}">
                    Stengt
                    <c:if test="${brukerinnlogg.brukertype == 1}">
                        <form:form method="POST" action="stilliko.htm?x=${emnenavnvalgt}">
                            <input type ="submit" value ="Still i kø" disabled>
                        </form:form>
                    </c:if>

                </c:if>

                <table class="hovedko" cellspacing="0" cellpadding="0">
                    <tr><td class="overtdko">Innleggene</td><td class="overtdko">Brukerne</td><td class="overtdko">Øvinger</td></tr>
                    <c:choose>
                        <c:when test="${empty innleggene}">
                            <tr><td class="tdko" colspan="3">Ingen innlegg i kø.</td></tr>

                        </c:when>
                        <c:otherwise>
                            <c:set var="hvorlangt" value="0"/>
                            <c:forEach items="${innleggene}" var="innlegg" varStatus="hvorlangt">
                                <tr <c:if test="${innlegg.hjelp != null}"> class="hjelp"</c:if>><td class="tdko">
                                        Bygning: ${innlegg.getPlass().getBygning()}</br>
                                        Etasje: ${innlegg.getPlass().getEtasje()}</br>
                                        Rom: ${innlegg.getPlass().getRom()}</br>
                                        Ekstra informasjon: ${innlegg.getKommentar()}
                                        <c:if test="${innlegg.hjelp != null}">
                                            <br>
                                            <c:out value="Får hjelp av: ${innlegg.hjelp.getFornavn()}  ${innlegg.hjelp.getEtternavn()}" ></c:out>
                                        </c:if>
                                        <%--<c:if test="${brukerinnlogg.brukertype != 0}">--%>
                                        <form:form action="hjelp.htm?id=${innlegg.getId()}&x=${emnenavnvalgt}" method="post">
                                            <table>
                                                <tr>
                                                    <td><input type="submit" id="hjelp" value="hjelp"></td>
                                                </tr>
                                            </table>

                                       </form:form>
                                    </td><td class="tdko">
                                        <c:forEach items="${innlegg.getBrukere()}" var="hverbruker">
                                            - <c:out value="${hverbruker.getFornavn()}"/> <c:out value="${hverbruker.getEtternavn()}"/></br>
                                        </c:forEach>
                                    </td><td class="tdko"><c:out value="${ovingtekster.get(hvorlangt.index)}"/></td></tr>

                            </c:forEach>

                        </c:otherwise>
                    </c:choose>
                </table>

            </c:when>

            <c:otherwise>
                Du må velge et fag.    

            </c:otherwise>

        </c:choose>
    </c:when>        


    <c:otherwise>
        Du har ingen fag registrert.

    </c:otherwise>
</c:choose>
