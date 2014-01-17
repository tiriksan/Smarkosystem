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



    <c:out value="Alle medlemmene av gruppen:"></c:out><br>
        <table id="brukertabell">
        <c:forEach begin="0" end="${innlegg.getBrukere().size()-1}" var="i">
            <tr>
                <td><ul>
                        <li>
                            ${innlegg.getBrukere().get(i).getFornavn()}
                            ${innlegg.getBrukere().get(i).getEtternavn()}
                            <c:forEach begin="0" end="${innlegg.getOvinger().get(i).size()-1}" var="j">
                                ${innlegg.getOvinger().get(i).get(j).getØvingsnr()} ,
                            </c:forEach>
                        </li>
                    </ul></td>
                <td><input type="submit" value="Godkjenn"></td>
            </tr>
        </c:forEach>
            <form:form method="POST" action="utsett.htm?x=${fag.emnenavn}">
                <td><input type="submit" value="utsett"></td>
            </form:form>
                
            <form:form method="POST" action="godkjennalle.htm?x=${fag.emnenavn}">
            <td><input type="submit" id="gkAlle" value="Godkjenn alle"></td>
            </form:form>
    </table>



