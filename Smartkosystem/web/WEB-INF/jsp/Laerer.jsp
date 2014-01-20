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
    
function submitform(){
  document.minform.submit();
}
</script>
   
   <c:choose>
<c:when test="${valg eq null}">
 <form:form action="adminlaerer.htm" method="post" modelAttribute="brukerinnlogg" name="minform">
    <table>
                                    
                            <tr><td><label for="emnekode"> Dine emner:</label></td><td>
                

                                    <select name="x" id="x" onchange="submitform()">
                                        <c:forEach items="${allefagene}" var="fagliste">
                                            <option value="${fagliste}">${fagliste}</option>
                                        </c:forEach>
                                    </select>
    </table>
    </form:form>
        
 
</c:when>

       <c:otherwise>
           <div class="systemet">
           <form:form action="adminlaerer.htm" method="post" modelAttribute="brukerinnlogg" name="minform">
               <div class="nedtrekkslisteadm">
                 <select name="x" id="x" onchange="submitform()">
                                        <c:forEach items="${allefagene}" var="fagliste">
                                            <option value="${fagliste}">${fagliste}</option>
                                        </c:forEach>
                                    </select>
               </div>
               <div class="admlaererdiv">
                            <table>
     <thead>
        <tr>
           <th>Øving</th>
           <th>Gruppe</th>
        </tr>
     <tbody>
        <c:forEach items="${alleovinger}" var="admin">
           
           <tr>
        <td class="admlaerer"><a href="admlaerer.htm?y=${admin.øvingsnr}">${admin.øvingsnr} </a></td>
        <td class="admlaerer"><a href="admlaerer.htm?y=${admin.gruppeid}">${admin.gruppeid} </a></td>
           </tr>
            
        </c:forEach>
     </tbody>
  </table>
                </div>  
           </div>        
                                    
                                </form:form>
       </c:otherwise>
   </c:choose>
                                <c:choose>
<c:when test="${ovingvalg eq null}">
    </c:when>

       <c:otherwise>
             <div class="systemet">
           <form:form action="adminlaerer.htm" method="post" modelAttribute="brukerinnlogg" name="minform">
               <div class="nedtrekkslisteadm">
                 <select name="x" id="x" onchange="submitform()">
                                        <c:forEach items="${allefagene}" var="fagliste">
                                            <option value="${fagliste}">${fagliste}</option>
                                        </c:forEach>
                                    </select>
               </div>
               <div class="admlaererdiv">
                            <table>
     <thead>
        <tr>
           <th>Øving</th>
           <th>Gruppe</th>
        </tr>
     <tbody>
        <c:forEach items="${alleovinger}" var="admin">
           
           <tr>
        <td class="admlaerer"><a href="adminlaerer.htm?y=${admin.øvingsnr}">${admin.øvingsnr} </a></td>
        <td class="admlaerer"><a href="adminlaerer.htm?y=${admin.gruppeid}">${admin.gruppeid} </a></td>
           </tr>
            
        </c:forEach>
     </tbody>
  </table>
                </div>  
           </div>        
                                    
                   </form:form>
             
             <h1> are hjelt </h1>
           
       </c:otherwise>
   </c:choose>