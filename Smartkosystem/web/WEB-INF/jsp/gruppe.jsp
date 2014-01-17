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

<br>
Alle medlemmene av gruppen:
<br>

    <table id="brukertabell">
    <form:form method="POST" action="godkjennValgte.htm?x=${fag.emnenavn}">
        <tr>
            <td>Navn:</td>
            <td></td>
            <td>Øvinger som skal godkjennes:</td>

        </tr>
        <c:forEach begin="0" end="${innlegg.getBrukere().size()-1}" var="i">
            <tr>
                <td><ul>
                        <li>
                            ${innlegg.getBrukere().get(i).getFornavn()}
                            ${innlegg.getBrukere().get(i).getEtternavn()}

                        </li>
                    </ul></td>
                <td></td>
                <td>
                    <c:forEach begin="0" end="${innlegg.getOvinger().get(i).size()-1}" var="j">
                        ${innlegg.getOvinger().get(i).get(j).getØvingsnr()}

                        <input  type="checkbox" name="${innlegg.getBrukere().get(i).getBrukernavn()},${innlegg.getOvinger().get(i).get(j).getØvingsnr()}" />

                    </c:forEach>
                </td>

            </tr>
        </c:forEach>
        <tr>
            <td>
                <input type ="submit" value="Godkjenn valgte">
            </td>

        </form:form>

        <form:form  method="POST" action="godkjennalle.htm?x=${fag.emnenavn}">
            <td>
                <input type="submit" id="gkAlle" value="Godkjenn alle">
            </td>
        </form:form> 

        <td>
            <form:form id="utsett" method="POST" action="utsett.htm?x=${fag.emnenavn}">
                <input type="submit" value="utsett">
            </form:form>


        </td>
    </tr>
</table>



