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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table cellpadding="0" cellspacing="0" width="100%" id="infoheader">
    <tr>
        <td id="gruppeinfo">Informasjon om gruppen</td>
    </tr>
</table> 

<table id="brukertabell" width="100%" cellpadding="0" cellspacing="0">
    <form:form method="POST" action="godkjennValgte.htm?x=${fag.emnenavn}">
        <tr id="thGruppe">
            <td> Navn</td>
            <td> Øvinger</td>
        </tr>

        <c:forEach begin="0" end="${innlegg.getBrukere().size()-1}" var="i">
            <tr <c:if test="${i%2==1}"> class="brukerSkille"</c:if>>
                <td>
                    ${innlegg.getBrukere().get(i).getFornavn()}
                    ${innlegg.getBrukere().get(i).getEtternavn()}
                </td>
                <td id="ovingcelle" width="41.8%">
                    <c:forEach begin="0" end="${innlegg.getOvinger().get(i).size()-1}" var="j">
                        ${innlegg.getOvinger().get(i).get(j).getØvingsnr()}

                        <input  type="checkbox" name="${innlegg.getBrukere().get(i).getBrukernavn()},${innlegg.getOvinger().get(i).get(j).getØvingsnr()}" />

                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
    </table>
    <table id="gruppetabell" width="58.3%" cellpadding="0" cellspacing="0">

        <tr>
            <td>
                <input type ="submit" id="gkValgte" value="Godkjenn valgte">
            </td>

        </form:form>
            
            <td>
            <form:form id="utsett" method="POST" action="utsett.htm?x=${fag.emnenavn}">
                <input type="submit" id="utsett" value="Utsett">
            </form:form>
        </td>
        </tr><tr>

        <form:form  method="POST" action="godkjennalle.htm?x=${fag.emnenavn}">
            <td>
                <input type="submit" id="gkAlle" value="Godkjenn alle">
            </td>
        </form:form> 
        
        <td>
            <form:form method="POST" action="fjern.htm?x=${fag.emnenavn}">
                <input type="submit" id="fjern" value="Fjern">
            </form:form>
        </td>
    </tr>
</table>

<%--
<table id="ovingKomnt" width="41.7%">
    <tr>
        <td>kommentar</td>
    </tr>
</table>
--%>



