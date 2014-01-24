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
                    <form:form action="registrerBrukereFraFil.htm" method="post" modelAttribute="filOpplasting">

                        <table>
                            <p>Skriv inn emnekode og emnenavn du ønsker å registrere brukere i:</p>
                            <tr><td>Emnekoder(adskill med komma(,): </td><td><form:input path="emner" /></td><td><form:errors path="emner" /></tr>

                            <input type="file" id="fileinput" />
                            <script>
                                function readMultipleFiles(evt) {
                                    //Retrieve all the files from the FileList object
                                    var files = evt.target.files;
                                    if (files) {
                                        for (var i = 0, f; f = files[i]; i++) {
                                            var r = new FileReader();
                                            r.onload = (function(f) {
                                                return function(e) {
                                                    var contents = e.target.result;
                                                    document.getElementById('filInnhold').value = contents;

                                                };
                                            })(f);
                                            r.readAsText(f, "ISO-8859-1");
                                        }
                                    } else {
                                        alert("Feil ved lesning av filen.");
                                    }
                                }
                                document.getElementById('fileinput').addEventListener('change', readMultipleFiles, false);
                            </script>
                            <tr><td colspan="2"><input type="submit" value="Registrer brukere"></td></tr>
                            <tr><td>${feilmelding}</td></tr>
                            <input hidden="true" id="filInnhold" name="filInnhold"/>
                        </table>
                    </form:form>

                </c:when>

                <c:when test="${valget eq '3'}">
                    <c:if test="${not empty Noeerfeil}">
                        <c:out value="${Noeerfeil}"/>
                    </c:if>
                    <form:form action="innsettemne.htm" method="post" modelAttribute="emne" >
                        <table>
                            <tr><td>Fagnavn: </td><td><form:input path="emnenavn" /></td><td><form:errors path="emnenavn" /></td></tr>
                            <tr><td>Emnekode: </td><td><form:input path="emnekode" /></td><td><form:errors path="emnekode" /></td></tr>
                            <tr><td>Beskrivelse: </td><td><form:input path="beskrivelse" /></td><td><form:errors path="beskrivelse" /></td></tr>
                            <tr><td><label id="fagLabel" for="lærer" hidden>Faglærer: </label></td>
                                <td>
                                    <select name="laerer" id="laerer" hidden>
                                        <c:forEach items="${allelaerere}" var="fagen">
                                            <option value="${fagen}">${fagen}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr><td><form:checkbox id="leggtil" label="Legg til faglærer" path="haMedLaerer" /><!--input id="velgLaerer" type="checkbox" name="leggtil" /><label for="leggtil">Legg til faglærer </label--></td></tr>
                            <script type="text/javascript">
                                function toggleFaglaerer() {
                                    var label = document.getElementById('fagLabel');
                                    var select = document.getElementById('laerer');
                                    if (select.hasAttribute('hidden') && label.hasAttribute('hidden')) {
                                        select.removeAttribute('hidden');
                                        label.removeAttribute('hidden');
                                    } else {
                                        select.setAttribute('hidden');
                                        label.setAttribute('hidden');
                                    }
                                }
                                document.getElementById('velgLaerer').addEventListener('change', toggleFaglaerer, false);
                            </script>
                            <br>
                            <tr><td><input type="submit" value="Registrer fag"></td></tr>
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


                                    <select name="fagene" id="fagene">
                                        <c:forEach items="${allefagene}" var="fagen">
                                            <option value="${fagen}">${fagen}</option>
                                        </c:forEach>
                                    </select>



                                    <br>
                            <tr><td colspan="2"><input type="submit" value="Registrer bruker"></td></tr>
                        </table>

                    </form:form>




                </c:otherwise>   

            </c:choose>





        </td></tr></table>

