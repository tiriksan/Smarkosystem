<%-- 
    Document   : endreOving
    Created on : 24.jan.2014, 08:29:40
    Author     : Rune
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<script type="text/javascript">

    function submitform() {
        document.minform.submit();
    }

</script>



        <h3>Slette Øving</h3>
        <c:choose>
            <c:when test="${emnevalg eq null}">
             
    <form:form action="endreOving.htm" method="get" modelAttribute="brukerinnlogg" name="minform">
           <table>
                <tr>
                    <td>
                    Velg Emne: 
                    </td>
                    <td>
                        <select name="x" id="x" onchange="submitform()">
                            <c:forEach items="${emnetabell}" var="emtabell">
                                <option value="${emtabell}">${emtabell}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </table>
      </form:form> 
            </c:when>
            
    <c:otherwise>
        
           <form:form action="endreOving.htm" method="get" modelAttribute="brukerinnlogg" name="minform">
           <table>
                <tr>
                    <td>
                    Velg Emne: 
                    </td>
                    <td>
                        <select name="x" id="x" onchange="submitform()">
                            <c:forEach items="${emnetabell}" var="emtabell">
                                <option value="${emtabell}">${emtabell}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </table>
               
               <table>
               
                <tr>
                    <td>
                    Slett øving: 
                    </td>
                    <td>
                        <select name="y" id="y" onchange="submitform()">
                            <c:forEach items="${ovingtabell}" var="ovtabell">
                                <option value="${ovtabell}">${ovtabell}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </table>
           </form:form>
    </c:otherwise>
            
        </c:choose>
           
        <c:if test="${valg2 >0}">      
            

            <form:form action="endreOving.htm" modelAttribute="valgtOving" method="get">
                <h3> Øving ${valg2} er slettet: </h3>

        

         <table>
           
              
       <tr>   
           <td>
               <input type="text"  value="Øvingsnr" />
               <input type="text"  value="Gruppe id" />
               <input type="text"  value="Obligatorisk" />
                
           
       </tr>
       <tr>
            <td> 
                <form:input type ="text" readonly="true" name="ovingnr" path = "øvingsnr" value="${valg2}"/>
                <form:input type ="text" readonly="true" name="gruppeid" path= "gruppeid" value="${ovinger.getGruppeid()}"/>
                <form:input type ="text" readonly="true" name="obligatorisk" path= "obligatorisk" value="${ovinger.getObligatorisk()}"/>
            </td>
            </tr>
           
        
                
                   </table>
      </form:form>
</c:if>