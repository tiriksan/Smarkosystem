<%-- 
    Document   : endreEmne
    Created on : 20.jan.2014, 09:49:35
    Author     : Rune

Denne siden skal vise en side som viser en søkeboks mer skal man kunne søke etter emne som man vil endre på

--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>




<h3>Endre Emne</h3>


<form action="endreemne2" method="POST">
    <table cellpadding="0px" cellspacing="0px"> 
        <tr> 
            <td style="border-style:solid none solid solid;border-color:#4B7B9F;border-width:1px;">
                <input type="text" name="zoom_query" id = "1" style="width:100px; border:0px solid; height:17px; padding:0px 3px; position:relative;"> 
            </td>
            <td style="border-style:solid;border-color:#4B7B9F;border-width:1px;">

                <input type="submit" value="" style="border-style: none; background: url('resources/bilder/searchbutton3.gif') no-repeat; width: 24px; height: 20px;">

            </td>
        </tr>
    </table></br>
    <table cellspacing="0" cellpadding="0">
</form>
        <ul class ="sokeresultat">
            <form:form action="endreEmne3.htm" modelAttribute="valgtEmne" method="POST">
            <c:forEach items="${sokeresultat}" var="hvertemne" varStatus="k">
                <tr>        <td class="tdko">
                
                    <form:radiobutton  value="${hvertemne.getBrukernavn()}" path="emnenavn" /> ${hvertemne.getEmnenavn()} &nbsp; ${hvertemne.getEmnekode()} &nbsp; ${hvertemne.getØvingsbeskrivelse()}&nbsp; 
                
            </td><td class="tdko"><c:out value="${k.index}"/></td></tr>

    </c:forEach>
    <c:if test="${not empty sokeresultat}">
                <input type="submit" value="Velg emne"/></c:if>
                </form:form>
</table>
</ul>
<c:if test="${funkafint eq true}">
                    Oppdatering vellykket!
                </c:if>

        <c:if test="${not empty emneTilEndring}">
            <form:form action="endreEmne4.htm" modelAttribute="valgtemne" method="POST">
        <table>
       <tr>  
            <td> 
                <form:input type = "text" name="emneendres" path = "emnenavn" value="$emneTilEndring.getEmnenavn()} "/>
                <form:input type ="text" name="emneendres" path="emnekode" value="${emneTilEndring.getEmnekode()}"/>
                <form:input type ="text" name="emnerendres" path= "øvingsbeskrivelseendres" value="${emneTilEndring.getØvingsbeskrivelse()}"/>
                <input type="submit" name="brukerendres" value="Lagre endringer">
                
            </td>
        </tr>



    </table>
            </form:form>
        </c:if>


<td class="tdko">


