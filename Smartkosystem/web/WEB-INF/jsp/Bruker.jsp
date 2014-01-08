<%-- 
    Document   : nyvare
    Created on : Oct 31, 2013, 2:52:32 PM
    Author     : Kristian
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h2>Registrer ny Bruker</h2>

<form:form action="svarinnsetting.htm" method="post" modelAttribute="bruker" >
    <table>
        <tr><td>Fornavn: </td><td><form:input path="fornavn" /></td><td><form:errors path="fornavn" /></td></tr>
        <tr><td>Etternavn:   </td><td><form:input path="varenavn" /></td><td><form:errors path="etternavn" /></tr>
        <tr><td>Brukernavn:       </td><td><form:input path="brukernavn" /></td><td><form:errors path="brukernavn" /></td></tr>
        <tr><td><label for="brukerTyper">Brukertype: </label>
        <form:select path="brukertype" id="brukerTyper">

        <form:option value="">Velg brukertype</form:option>

        <form:option value="Student">Student</form:option>
        <form:option value="Laerer">Laerer</form:option>
        <form:option value="administrator">Admin</form:option>
        <form:option value="studass">Studass</form:option>

      </form:select>

      <br/>
        <tr><td colspan="2"><input type="submit" value="Registrer bruker"></td></tr>
    </table>
</form:form>

