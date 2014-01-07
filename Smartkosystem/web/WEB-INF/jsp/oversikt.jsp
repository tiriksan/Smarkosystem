<%-- 
    Document   : oversikt
    Created on : Nov 6, 2013, 3:22:44 PM
    Author     : Kristian
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<h2>Valgte varer</h2>
<h5>Utskrift via modelattribute valgteVarer</h5>

<c:forEach items="${utilsBean.valgteVarer}" var="vare">
    ${vare}<br>
</c:forEach>