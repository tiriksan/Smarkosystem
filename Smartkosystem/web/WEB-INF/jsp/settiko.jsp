<%-- 
    Document   : settiko
    Created on : 15.jan.2014, 09:44:07
    Author     : Petter
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<script>



    
    function visinfo(bygg, etasje, rom, bord, emnekode) {



 
document.getElementById("vislaster").style.display = "block";
document.body.style.background = "#777777";
var xmlhttp;
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
document.getElementById("vislaster").style.display = "none";
document.body.style.background = "whitesmoke";
if(bygg == "-1"){
            document.getElementById("etasje").style.display = "none";
        document.getElementById("rom").style.display = "none";
        document.getElementById("bord").style.display = "none";
}else if(bygg != "-1" && etasje == "-1" && rom == "-1"){
        document.getElementById("bygg").style.display = "block";
        document.getElementById("etasje").style.display = "block";
        document.getElementById("rom").style.display = "none";
        document.getElementById("bord").style.display = "none";
        document.getElementById("etasje").innerHTML = xmlhttp.responseText;
} else if(bygg != "-1" && etasje != "-1" && rom == "-1"){
        document.getElementById("bygg").style.display = "block";
        document.getElementById("etasje").style.display = "block";
        document.getElementById("rom").style.display = "block";
        document.getElementById("bord").style.display = "none";
        document.getElementById("rom").innerHTML = xmlhttp.responseText;    
} else if(bygg != "-1" && etasje != "-1" && rom != "-1"){
            document.getElementById("bygg").style.display = "block";
        document.getElementById("etasje").style.display = "block";
        document.getElementById("rom").style.display = "block";
        document.getElementById("bord").style.display = "block";
        document.getElementById("bord").innerHTML = xmlhttp.responseText; 
} else {
    //Feil
}
        
;
    
    
    
    
    
    }
  }
  //alert(xmlhttp.responseText + " og hei");
xmlhttp.open("POST","test.htm?bygg=" + bygg + "&etasje=" + etasje + "&rom=" + rom + "&emnekode=" + emnekode,true);
xmlhttp.send();
}
</script>

















<div id="vislaster" style="display: none;"><img src="resources/bilder/laster.gif"/></div>
<table width="100%" cellspacing="0" cellpadding="0">
    
    <tr><td class="overleggiko" width="30%">Plassering</td><td class="overleggiko">Øvinger/gruppe</td></tr>
    <tr><td class="mainleggiko">
<div id="bygg">
    </br>
    <select id="bygget" name="bygget" onchange="visinfo(this.value, '-1', '-1', '-1', '${emnekode}');">
        <option value="-1">Velg bygg</option>
        <c:forEach items="${byggene}" var="bygg">
            <option value="${bygg}">${bygg}</option>
        </c:forEach>
        
    </select>

    
</div>

<div id="etasje">
    </br>

    
</div>

<div id="rom">
    </br>
    
    
    
</div>



<div id="bord">
    </br>
    
</div>

    
        </td><td class="mainleggiko">
            
            Her kommer brukerne</br>
            <table class="ovingene" cellspacing="0" cellpadding="0">
                <tr>
                    <c:forEach var="foo" begin="5" end="15">
                        
                        <td class="overoving">#${foo}</td>
                    </c:forEach>
                    
                    
                </tr>
                <tr>
                    <c:forEach var="foo" begin="5" end="15">
                        
                        <td class="oving"><input type="checkbox"></td>
                    </c:forEach>
                </tr>
                
            </table>
            
            
        </td></tr></table>

