<%-- 
    Document   : brukerovingoversikt
    Created on : Jan 21, 2014, 9:19:55 AM
    Author     : Kristian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div>
    ${emne.emnekode + ", " + emne.emnenavn}
    
    <c:if test="${brukerinnlogg.brukertype == 1}">
        <table>
            <tr>
                <td>
                    ${brukerinnlogg.fornavn + " " + brukerinnlogg.etternavn}
                </td>
                <td>
                    <c:forEach items="${ovinger}" var="oving">
                        <div <c:if test="${sjekkGodkjent(emne.emnekode,brukerinnlogg.brukernavn,oving.øvingsnr)}">class="godkjentoving"</c:if>>${oving.øvingsnr}</div>
                    </c:forEach>
                </td>
            </tr>
        </table>
    </c:if>
    
</div>
<div>Arbeidskrav i dette faget</div>
<ul>
    <c:forEach items="${arbeidskrav}" var="krav">
        <li>
            <%--TODO WHEN ARBEIDSKRAV--%>
        </li>
    </c:forEach>
</ul>
<c:if test="${brukerinnlogg.brukertype>1}">
    <table>
        <c:forEach items="${studenter}" var="student">
            <tr>
                <td>
                    ${student.fornavn + " " + student.etternavn}
                </td>
                <td>
                    <%--            <c:forEach items="${}">
                        
                    </c:forEach> --%>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
