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
            <table>
                <tr>
                    <td>
                        ${brukerinnlogg.fornavn}&nbsp;${brukerinnlogg.etternavn}
                    </td>
                    <td>
                        <c:forEach begin="1" end="${fn:length(ovinger)-1}" var="oving">
                            <div <c:if test="${ovinger[oving-1]==1}">class="godkjent"</c:if>>${oving}</div>
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
        <table width="100%">
            <c:forEach begin="0" end="${studenter.size()}" var="student">
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