<%-- 
    Document   : regbrukerinput
    Created on : Jan 8, 2014, 1:31:55 PM
    Author     : christmw
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<style>
.error {
	color: #ff0000;
}
 
.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>
</head>
 
<body>
	<h2>Fil opplastinga av brukere</h2>
 
	<form:form method="POST" commandName="filopplasting"
		enctype="multipart/form-data">
 
		<form:errors path="*" cssClass="errorblock" element="div" />
 
		Velg en fil å laste opp: <input type="fil" name="fil" />
		<input type="submit" value="Last opp" />
		<span><form:errors path="fil" cssClass="error" />
		</span>
 
	</form:form>
 
</body>
</html>