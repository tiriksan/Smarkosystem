<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%--<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administrer varer</title>
    </head>

    <body>
      --%>  
        <form:form action="oversikt.htm" method="post" modelAttribute="utilsBean"> 
    <table border="1" width="100%">
        <tr>
            <th>Varenummer</th>
            <th>Varenavn</th>
            <th>Pris</th>
            <th>Velg </th>
        </tr>
        <c:forEach var="vare" items="${utilsBean.alleVarer}" varStatus="status">
            <tr>
                <td><c:out value="${vare.varenr}"/></td> 
                <td><input name="alleVarer[${status.index}].varenavn" value="${vare.varenavn}"/> </td>  
                <td><input name="alleVarer[${status.index}].pris" value="${vare.pris}"/> </td>
                <td><input type="checkbox" name="valgteVarer" value="${vare}" /> </td>
            </tr>
        </c:forEach>
    </table>
    <input type="submit" value="Hent vare" name="send" />  <input type="submit" value="Oppdater vare" name="oppdater" /><input type="submit" value="Slett vare" name="slett" />
</form:form>
<%--

    </body>
</html>
--%>