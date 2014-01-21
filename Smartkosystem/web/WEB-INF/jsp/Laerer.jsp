<%-- 
    Document   : Laerer
    Created on : Jan 17, 2014, 9:24:01 AM
    Author     : christmw
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<script type="text/javascript">

    function submitform() {
        document.minform.submit();
    }
</script>

<c:choose>
    <c:when test="${valg eq null}">
        <form:form action="adminlaerer.htm" method="post" modelAttribute="brukerinnlogg" name="minform">
            <table>

                <tr>
                    <td>


                        <select name="x" id="x" onchange="submitform()">
                            <c:forEach items="${allefagene}" var="fagliste">
                                <option value="${fagliste}">${fagliste}</option>
                            </c:forEach>
                        </select>
                    </td></tr>
            </table>
        </form:form>
        <div class="leggtilov">
            <a href="regov2.htm">Legg til øving</a>
        </div>   

    </c:when>

    <c:otherwise>

        <form:form action="adminlaerer.htm" method="post" modelAttribute="brukerinnlogg" name="minform">
            <div class="nedtrekkslisteadm">
                <select name="x" id="x" onchange="submitform()">
                    <c:forEach items="${allefagene}" var="fagliste">
                        <option value="${fagliste}">${fagliste}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="leggtilov">
                <a href="regov2.htm">Legg til øving</a>
            </div>
            <div id="labeladmlaerer">
                <table>
                    <tr>
                        <td>Beskrivelse: </td>
                        <td><form:input path="${emne.øvingsbeskrivelse}" value="${emne.øvingsbeskrivelse}" name="beskrivelseinput" /></td>
                    </tr>
                    <tr> <td> <input type="submit" value="Oppdater" name="oppdater" /></td> </tr>
                </table>
            </div>
   
            <div class="admlaererdiv">
                <table>
                    <thead>
                        <tr>
                            <th>Øving</th>
                            <th>Obligatorisk</th>
                            <th>Velg</th>
                        </tr>
                    <tbody>
                      
                        <c:forEach items="${alleovinger}" var="admin">

                            <tr>
                                <td class="admlaerer"><!--<a href="admlaerer.htm?y={admin.øvingsnr}">-->${admin.øvingsnr} </a></td>

                                <td class="admlaerer"><input type="checkbox" name="obliga" id="oblig" <c:if test="${admin.obligatorisk eq true}">checked</c:if>></td>

                                    <td class="admlaerer"><input type="checkbox" name ="obliga" id="oblig"></td>
                                </tr>
                   

                        </c:forEach>
                                
                                <tr><td> <input type="submit" value="Oppdater øvinger" name="oppdater" /></td></tr>
                    </tbody>
                </table>
            </div>



        </form:form>
    </c:otherwise>

</c:choose>













<c:choose>
    <c:when test="${ovingvalg eq null}">
    </c:when>
    <c:otherwise>

        <form:form action="adminlaerer.htm" method="post" modelAttribute="brukerinnlogg" name="minform">
            <div class="nedtrekkslisteadm">
                <select name="x" id="x" onchange="submitform()">
                    <c:forEach items="${allefagene}" var="fagliste">
                        <option value="${fagliste}">${fagliste}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="leggtilov">
                <a href="regov2.htm">Legg til øving</a>
            </div>
            <div class="admlaererdiv">
                <table>
                    <thead>
                        <tr>
                            <th>Øving</th>
                            <th>Obligatorisk</th>
                        </tr>
                    <tbody>
                        <c:forEach items="${alleovinger}" var="admin">

                            <tr>
                                <td class="admlaerer"><a href="adminlaerer.htm?y=${admin.øvingsnr}">${admin.øvingsnr} </a></td>
                                <td class="admlaerer"><a href="adminlaerer.htm?y=${admin.obligatorisk}">${admin.obligatorisk} </a></td>
                            </tr>

                        </c:forEach>
                    </tbody>
                </table>
            </div>  


        </form:form>


    </c:otherwise>
</c:choose>
