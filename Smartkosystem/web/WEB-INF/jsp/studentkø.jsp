<%-- 
    Document   : studentkø
    Created on : 10.jan.2014, 14:18:06
    Author     : Undis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
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
                    <form:form method="POST" action="aktiverkø">
                        <input type ="submit" value ="Aktiver kø!">
                    </form:form>
                </c:if>
                <c:choose>
                    <c:when test="${not empty IngenAktiv}">
                        </br><c:out value="${IngenAktiv}"/>
                    </c:when>
                    <c:otherwise>
                        <table class="hovedko" cellspacing="0" cellpadding="0">
                            <tr><td class="overtdko">Innleggene</td><td class="overtdko">Brukerne</td><td class="overtdko">Øvinger</td></tr>
                            <c:choose>
                                <c:when test="${empty innleggene}">
                                    <tr><td class="tdko" colspan="3">Ingen innlegg i kø.</td></tr>

                                </c:when>
                                <c:otherwise>
                                    <c:set var="hvorlangt" value="0"/>
                                    <c:forEach items="${innleggene}" var="innlegg" varStatus="hvorlangt">

                                        <tr><td class="tdko">
                                                Bygning: ${innlegg.getPlass().getBygning()}</br>
                                                Etasje: ${innlegg.getPlass().getEtasje()}</br>
                                                Rom: ${innlegg.getPlass().getRom()}</br>
                                                Ekstra informasjon: ${innlegg.getPlass().getKommentar()}

                                            </td><td class="tdko">
                                                <c:forEach items="${innlegg.getBrukere()}" var="hverbruker">
                                                    - <c:out value="${hverbruker.getFornavn()}"/> <c:out value="${hverbruker.getEtternavn()}"/></br>
                                                </c:forEach>
                                            </td><td class="tdko"><c:out value="${ovingtekster.get(hvorlangt.index)}"/></td></tr>

                                    </c:forEach>

                                </c:otherwise>
                            </c:choose>
                        </table>
                    </c:otherwise>
                </c:choose>
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
