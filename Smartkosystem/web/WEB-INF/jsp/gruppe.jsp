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

<c:if test="${hjelp == true}">
    
    
    <c:forEach items="${brukere}" var="bruker">
            ${bruker.getFornavn()};
            <input type="submit" value="godkjent">
        </c:forEach>
</c:if>
