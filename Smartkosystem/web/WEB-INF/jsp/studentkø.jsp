<%-- 
    Document   : studentkø
    Created on : 10.jan.2014, 14:18:06
    Author     : Undis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SKS 2.0</title>
    </head>
    <body>
        
    <form:form method="POST" modelAttribute="studentkø" >
        <table>
            <form:select path="emnenavn" items="${emnenavn}"></form:select>
        </table>
        
    </form:form>
        
        
    </body>
</html>
