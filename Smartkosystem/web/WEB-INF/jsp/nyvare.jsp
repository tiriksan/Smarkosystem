<%-- 
    Document   : nyvare
    Created on : Oct 31, 2013, 2:52:32 PM
    Author     : Kristian
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h2>Registrer ny vare</h2>

<form:form action="svarside.htm" method="post" modelAttribute="vare" >
    <table>
        <tr><td>Varenummer: </td><td><form:input path="varenr" /></td><td><form:errors path="varenr" /></td></tr>
        <tr><td>Varenavn:   </td><td><form:input path="varenavn" /></td><td></td></tr>
        <tr><td>Pris:       </td><td><form:input path="pris" /></td><td><form:errors path="pris" /></td></tr>

        <tr><td colspan="2"><input type="submit" value="Registrer vare"></td></tr>
    </table>
</form:form>

