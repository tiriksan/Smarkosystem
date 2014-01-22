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
        

    
    
    
    
    
    }
  }
  //alert(xmlhttp.responseText + " og hei");
xmlhttp.open("POST","test.htm?bygg=" + bygg + "&etasje=" + etasje + "&rom=" + rom + "&emnekode=" + emnekode,true);
xmlhttp.send();
}
</script>








<script>

var stud = new Array();
stud[0] = "${brukerinnlogg.getBrukernavn()}";
  document.getElementById("hidden").innerHTML = "${brukerinnlogg.getBrukernavn()}";  
    function leggtilstudent(brukernavnet, emnekode) {
if(brukernavnet.length > 2){
stud.push(brukernavnet);
}

 

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

    
    
    document.getElementById("brukereogovinger").innerHTML = xmlhttp.responseText;
    
    
    }
  }
  //alert(xmlhttp.responseText + " og hei");
  var sendvidere = "";
  for(var i = 0; i < stud.length; i++){
      sendvidere += "," + stud[i];
  }
  document.getElementById("hidden").innerHTML = sendvidere;
xmlhttp.open("POST","leggtilstudent.htm?emnekode=" + emnekode + "&brukernavn=" + sendvidere,true);
xmlhttp.send();
}









function slett(navn, emnekode){
    
    var index = stud.indexOf(navn);
    if(index != -1){
        stud.splice(index, 1);
    }
    
    leggtilstudent('', emnekode);
    
    
}
</script>
























<form method="post" action="submitko.htm">
<div id="vislaster" style="display: none;"><img src="resources/bilder/laster.gif"/></div>
<table width="100%" cellspacing="0" cellpadding="0">
    <tr><td class="overleggiko" colspan="2">Sett deg i kø</td></tr> 
    <tr><td class="mainleggiko" colspan="2"></br>Hva trenger du/dere hjelp til? <input type="text" name="hjelp" id="hjelp" style="width: 200px;"></br></br></td></tr>
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

     <div id="brukereogovinger"> 
                            <table width="100%"><tr><td>
                                        Studenter: <select id="studgruppe" name="studgruppe" onchange="leggtilstudent(this.value, '${emnekode}');">
                                            <option value="-1">Velg studenter i gruppen</option>
                                            <c:forEach items="${brukerneiliste}" var="pers">
                                                <option value="${pers.getBrukernavn()}">${pers.getFornavn()} ${pers.getEtternavn()}</option>
                                            </c:forEach>
                                        </select>
                        </td></tr>
                                 
            <c:forEach items="${brukerne}" var="bruker" varStatus="hvorlangtbruker">
                <tr><td width="40%" class="mainbrukeroversikt">
                ${bruker.getFornavn()} ${bruker.getEtternavn()}
                    </td>
                    <td>
                        <table class="ovingene" cellspacing="0" cellpadding="0">
                            <tr>
                    <c:set var="indexen" value="${antallovinger-1}"/>            
                <c:forEach begin="0" end="${indexen}" var="enkeltoving" varStatus="hvorlangt">
                    
                    <c:set var="funnet" value="0"/>
                    <c:forEach items="${bruker.getØvinger()}" var="ovingen">
                        <c:if test="${ovingen.getØvingsnr() eq hvorlangt.count}">
                    <c:set var="funnet" value="1"/>        
                        </c:if>
                        
                    </c:forEach>
                    
                    <c:if test="${funnet eq 0}">
                        <td class="overovinggodkjent">#${hvorlangt.count}</td>
                    </c:if>
                    
                    <c:if test="${funnet eq 1}">
                        <td class="overoving">#${hvorlangt.count}</td>
                    </c:if>
                    
                    
                    
                </c:forEach>
                                </tr>
                                
                            <tr>
                <c:forEach begin="0" end="${indexen}" var="enkeltoving" varStatus="hvorlangt">
                    
                                        <c:set var="funnet" value="0"/>
                    <c:forEach items="${bruker.getØvinger()}" var="ovingen">
                        <c:if test="${ovingen.getØvingsnr() eq hvorlangt.count}">
                    <c:set var="funnet" value="1"/>        
                        </c:if>
                        
                    </c:forEach>

                    
                                        <c:if test="${funnet eq 0}">
                    <td class="oving"><input type="checkbox" disabled></td>
                    </c:if>
                                        <c:if test="${funnet eq 1}">
                    <td class="oving"><input type="checkbox" name="oving[]" value="${hvorlangt.index}"></td>                    
                                        </c:if>
                </c:forEach>
                                </tr>
                                
                                
                                
                        </table>
                    </td></tr>
                
            </c:forEach>
            </table>
            
            
           
            
            
            
            </div>
            
        </td></tr>
    <tr><td class="mainleggiko" colspan="2"></br><input type="submit"></br>
            <textarea id="hidden" name="hidden" style="display: none;">,${brukerinnlogg.getBrukernavn()}</textarea><input type="hidden" value="${emnekode}" name="emnekode"></form></td></tr></table>

