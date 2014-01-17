<%-- 
    Document   : endreBruker
    Created on : 17.jan.2014, 09:49:35
    Author     : Bruker

Denne siden skal vise en side som viser en søkeboks Da skal man kunne søke etter brukeren som man vil endre 

--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>




<h3>Endre Bruker</h3>


<form action="endrebruker2" method="POST">
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
        
            
            
            <c:forEach items="${sokeresultat}" var="hverbruker" varStatus="k">
        <tr>        <td class="tdko">
            ${hverbruker.getFornavn()} ${hverbruker.getEtternavn()}
            </td><td class="tdko"><c:out value="${k.index}"/></td></tr>
            </c:forEach>
   



</table>
</form>

<td class="tdko">


