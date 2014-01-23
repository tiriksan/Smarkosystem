<%-- 
    Document   : endreBruker
    Created on : 17.jan.2014, 09:49:35
    Author     : Bruker

Denne siden skal vise en side som viser en søkeboks Da skal man kunne søke etter brukeren som man vil endre 

--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Endre Bruker</title>
        <link rel="stylesheet" href="resources/css/stilark.css"/>
    </head>
    <body>


<h3>Endre Bruker</h3>


<form action="endrebruker2" method="POST">
    <table cellpadding="0px" cellspacing="0px"> 
        <tr> 
          <td id ="sokeboks1" >
               <input type="text" name="zoom_query" id = "soke2"> 
            </td>
            <td id = "soke3">
                <input type="submit" value="" id="sokeknapp" style="border-style: none; background: url('resources/bilder/searchbutton3.gif') no-repeat; width: 24px; height: 20px;">

            </td>
        </tr>
    </table></br>
    <table cellspacing="0" cellpadding="0">
</form>
        <ul class ="sokeresultat">
            <form:form action="endreBruker3.htm" modelAttribute="valgtBruker" method="POST">
            <c:forEach items="${sokeresultat}" var="hverbruker" varStatus="k">
                <tr>        <td class="tdko">
              <%-- <li> <a href =  "endreBruker.htm" >${hverbruker.getFornavn()} ${hverbruker.getEtternavn()}</a> </li> --%>
                
                    <form:radiobutton  value="${hverbruker.getBrukernavn()}" path="brukernavn" /> ${hverbruker.getFornavn()} &nbsp; ${hverbruker.getEtternavn()} 
                
            </td><td class="tdko"><c:out value="${k.index}"/></td></tr>

    </c:forEach>
    <c:if test="${not empty sokeresultat}">
                <input type="submit" value="Velg bruker"/></c:if>
                </form:form>
</table>
</ul>
<c:if test="${funkafint eq true}">
                    Oppdatering vellykket!
                </c:if>

        <c:if test="${not empty brukerTilEndring}">
            <form:form action="endreBruker4.htm" modelAttribute="valgtBruker" method="POST">
        <table>
       <tr>  
            <td> 
                <form:input type = "text" name="brukerendres" path = "fornavn" value="${brukerTilEndring.getFornavn()} "/>
                <form:input type ="text" name="brukerendres" path="etternavn" value="${brukerTilEndring.getEtternavn()}"/>
                <form:input type ="text" readonly="true" name="brukerendres" path= "brukernavn" value="${brukerTilEndring.getBrukernavn()}"/>
                <input type="submit" name="brukerendres" value="Lagre endringer">
                <input type="submit" name="brukerSlettes" value="Slett bruker">
            </td>
        </tr>



    </table>
            </form:form>
        </c:if>

    </body>
    
</html>
                    

<td class="tdko">


