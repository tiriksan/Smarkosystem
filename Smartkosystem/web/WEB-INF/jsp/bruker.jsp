<%-- 
   Document   : nyvare
   Created on : Oct 31, 2013, 2:52:32 PM
   Author     : Kristian

--%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>



<c:choose>

    <c:when test="${valget eq '2'}">
        <c:set var="tab1" value="ikkeload"/>
        <c:set var="tab2" value="load"/>
        <c:set var="tab3" value="ikkeload"/>

    </c:when>

    <c:when test="${valget eq '3'}">
        <c:set var="tab1" value="ikkeload"/>
        <c:set var="tab2" value="ikkeload"/>
        <c:set var="tab3" value="load"/>

    </c:when>

    <c:otherwise>
        <c:set var="tab1" value="load"/>
        <c:set var="tab2" value="ikkeload"/>
        <c:set var="tab3" value="ikkeload"/>

    </c:otherwise>


</c:choose>


<table width="600" cellspacing="0" cellpadding="0" class="rundtform" align="center"><tr><td><table cellspacing="0" cellpadding="0"><tr><td class="<c:out value='${tab1}'/>"><a href="bruker.htm">Legg til èn bruker</a></td><td class="<c:out value='${tab2}'/>"><a href="bruker.htm?x=2">Legg til flere brukere</a></td><td class="<c:out value='${tab3}'/>"><a href="bruker.htm?x=3">Legg til fag</a></td></tr></table></td></tr>
    <tr><td></br>






            <c:choose>

                <c:when test="${valget eq '2'}">
                    <form:form action="registrerBrukereFraFil.htm" method="post" modelAttribute="emne">

                        <table>
                            <p>Skriv inn emnekode og emnenavn du ønsker å registrere brukere i:</p>
                            <tr><td>Emnekode:  </td><td><form:input path="emnekode" /></td><td><form:errors path="emnekode" /></tr>
                            <p>Velg så en fil med ny brukere(Du får opp et popup-vindu du kan velge fil fra):</p>
                            <tr><td colspan="2"><input type="submit" value="Registrer brukere"></td></tr>
                        </table>
                    </form:form>

                </c:when>

                <c:when test="${valget eq '3'}">

                    <form:form action="faginnsetting.htm" method="post" modelAttribute="emne" >
                        <table>
                            <tr><td>Fagnavn: </td><td><form:input path="emnenavn" /></td><td><form:errors path="emnenavn" /></td></tr>
                            <tr><td>Emnekode:   </td><td><form:input path="emnekode" /></td><td><form:errors path="emnekode" /></tr>
                                <tr><td><label for="fag">Faglærer: </label></td><td>
                

                                    <form:select path="faglærer">
                                        <form:option value="0" label="Velg faglærer" />
                                        <form:options items="${allelaerere}" />
                                    </form:select>

                            <br>
                            <tr><td colspan="2"><input type="submit" value="Registrer fag"></td></tr>
                        </table>
                    </form:form>
                </c:when>
                <c:otherwise>





                    <form:form action="brukerinnsetning.htm" method="post" modelAttribute="bruker" >
                        <table class="forminputs">
                            <tr><td width="25%">Fornavn: </td><td><form:input path="fornavn" /></td><td>&nbsp;<form:errors path="fornavn" /></td></tr>
                            <tr><td>Etternavn:   </td><td><form:input path="etternavn" /></td><td>&nbsp;<form:errors path="etternavn" /></tr>
                            <tr><td>Brukernavn:       </td><td><form:input path="brukernavn" /></td><td>&nbsp;<form:errors path="brukernavn" /></td></tr>
                            <tr><td><label for="brukerTyper">Brukertype: </label></td>
                                <td>
                                    <form:select path="brukertype" id="brukerTyper">

                                        <form:option value="0">Velg brukertype</form:option>

                                        <form:option value="1">Student</form:option>
                                        <form:option value="2">Lærer</form:option>
                                        <form:option value="3">Admin</form:option>
                                        <form:option value="4">Studass</form:option>


                                    </form:select>
                                </td></tr>

                                    
                            <tr><td><label for="fag">Fag: </label></td><td>
                

                                    <form:select path="fagene">
                                        <form:option value="0" label="Velg fag" />
                                        <form:options items="${allefagene}" />
                                    </form:select>


                                 

                                    <br>
                            <tr><td colspan="2"><input type="submit" value="Registrer bruker"></td></tr>
                        </table>

                    </form:form>




                </c:otherwise>   

            </c:choose>





        </td></tr></table>

