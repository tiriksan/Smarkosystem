<%-- 
    Document   : endreBruker
    Created on : 17.jan.2014, 09:49:35
    Author     : Bruker

Denne siden skal vise en side som viser en søkeboks Da skal man kunne søke etter brukeren som man vil endre 

--%>


<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>




<h3>Endre Bruker</h3>


<form:form action="endrebruker2" method="POST">
<table cellpadding="0px" cellspacing="0px"> 
<tr> 
<td style="border-style:solid none solid solid;border-color:#4B7B9F;border-width:1px;">
<input type="text" name="zoom_query" id = "1" style="width:100px; border:0px solid; height:17px; padding:0px 3px; position:relative;"> 
</td>
<td style="border-style:solid;border-color:#4B7B9F;border-width:1px;">
    
<input type="submit" value="" style="border-style: none; background: url('resources/bilder/searchbutton3.gif') no-repeat; width: 24px; height: 20px;">

    </td>
</tr>
</table>
</form:form>