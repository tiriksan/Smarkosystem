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
                    Velg Emne: 
                    </td>
                    <td>
                        <select name="x" id="x" onchange="submitform()">
                            <c:forEach items="${allefagene}" var="fagliste">
                                <option value="${fagliste}">${fagliste}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </table>
        </form:form>
        <div class="leggtilov" id="leggtilov">
            <a href="regov2.htm">Legg til øving</a>
        </div>   

    </c:when>

    <c:otherwise>

        <form:form action="adminlaerer.htm" method="post" modelAttribute="brukerinnlogg" name="minform">
           <table>
                <tr>
                    <td>
                    Velg Emne: 
                    </td>
                    <td>
                        <select name="x" id="x" onchange="submitform()">
                            <c:forEach items="${allefagene}" var="fagliste">
                                <option value="${fagliste}">${fagliste}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </table>
        <div class="leggtilov" id="leggtilov">
            <a href="regov2.htm">Legg til ny øving</a>
        </div>   

            
            
            
            
            
            
            
            <div id="labeladmlaerer">
                <table>
                    <tr>
                        <td>Beskrivelse: </td>
                        <td><form:input path="${emne.beskrivelse}" value="${emne.beskrivelse}" name="beskrivelseinput" /></td>

                        <td> <input type="submit" value="Oppdater" name="oppdater2" /></td> </tr>
                </table>
            </div>

            <div class="admlaererdiv">
                <table>
                    <thead>
                        <tr>
                            <th>Øving</th>
                            <th>Obligatorisk</th>

                        </tr>
                    <tbody>

                        <c:forEach begin="0" items="${alleovinger}" var="admin">

                            <tr>
                                <td class="admlaerer"><!--<a href="admlaerer.htm?y={admin.øvingsnr}">-->${admin.øvingsnr} </td>

                                <td class="admlaerer"><input type="checkbox" value="${admin.øvingsnr}"  name="obliga" id="oblig" <c:if test="${admin.obligatorisk eq true}">checked</c:if>></td>


                                    <!--    <td class="admlaerer"><input type="checkbox"  value="$"{admin.øvingsnr}" name ="valget" id="valget" onchange="fnctcheckbox()"></td> -->
                                </tr>



                        </c:forEach>

                    <tr><td> <input type="submit" value="Gå videre" name="oppdater" /></td>
                        
                        <td>   
                            <c:if test="${iftest > 0}"> 
                                <select name="valget" id="valget" onchange="submitform()"> 
                                    <c:forEach items="${alleAntall}" var="antall">
                                        <option value="${antall}">${antall}</option>
                                    </c:forEach>
                                </select>
                            </c:if>
                        </td>  
                    </tr>
                    </tbody>
                </table>
            </div>

        </form:form>
    </c:otherwise>

</c:choose>












<!--
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
-->