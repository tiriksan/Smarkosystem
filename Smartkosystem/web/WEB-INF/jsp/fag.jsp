
    Document   : nyvare
    Created on : Oct 31, 2013, 2:52:32 PM
    Author     : Kristian
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



<h3>Registrer nytt fag</h3>

<form:form action="faginnsetting.htm" method="post" modelAttribute="fag" >
    <table>
        <tr><td>Fagnavn: </td><td><form:input path="fagnavn" /></td><td><form:errors path="fagnavn" /></td></tr>
        <tr><td>Emnekode:   </td><td><form:input path="emnekode" /></td><td><form:errors path="emnekode" /></tr>
        <tr><td>Faglaerer:       </td><td><form:input path="faglaerer" /></td><td><form:errors path="faglaerer" /></td></tr>
       
      <br>
        <tr><td colspan="2"><input type="submit" value="Registrer fag"></td></tr>
    </table>
</form:form>


