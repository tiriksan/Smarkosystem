<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<table cellspacing="0" cellpadding="0" width="100%"><tr><td class="overtdko">Meny</td></tr></table>
<table class="menutable" cellpadding="0" cellspacing="0">
    <c:if test="${empty brukerinnlogg.brukernavn}">
        <tr><td class="menuvalg"><a href="<c:url value="logginn.htm"/>" ><spring:message code="menyvalg4" /></a></td></tr>
            </c:if>
            <c:if test="${not empty brukerinnlogg.brukernavn}">
                <c:if test="${brukerinnlogg.brukertype eq 0}">

            <tr><td class="menuvalg"><a href="<c:url value="index"/>"><spring:message code="menyvalg1" /></a></td></tr>


        </c:if>
        <c:if test="${brukerinnlogg.brukertype eq 3 or brukerinnlogg.brukertype eq 4}">
            <tr><td class="menuvalg"><a href="<c:url value="bruker.htm"/>" ><spring:message code="menyvalg3" /></a></td></tr>
            <tr><td class="menuvalg"><a href="<c:url value="endreEmne.htm"/>" ><spring:message code="menyvalg6" /></a></td></tr>
            <tr><td class="menuvalg"><a href="<c:url value="emnetilbruker.htm"/>" ><spring:message code="menyvalg14" /></td></tr>
        </c:if>
            
        <tr><td class="menuvalg"><a href="<c:url value="velgEmneBrukeroversikt.htm"  />"> <spring:message code = "menyvalg8" /></a></td></tr>
        <tr><td class="menuvalg"><a href="<c:url value="/studentko.htm"/>" ><spring:message code="menyvalg13" /></a></td></tr>


        <c:if test="${brukerinnlogg.brukertype eq 4}">
            <tr><td class="menuvalg"><a href="<c:url value="endreBruker.htm"/>" ><spring:message code="menyvalg9" /></a></td></tr>
            <tr><td class="menuvalg"><a href="<c:url value="bruker.htm?x=3"/>" ><spring:message code="menyvalg10" /></a></td></tr>
            <tr><td class="menuvalg"><a href="<c:url value="bruker.htm"/>" ><spring:message code="menyvalg11" /></a></td></tr>
            <tr><td class="menuvalg"><a href="<c:url value="endreEmne.htm"/>" ><spring:message code="menyvalg12" /></a></td></tr>    
                </c:if>




        <c:if test="${brukerinnlogg.brukertype eq 3}">
            <tr><td class="menuvalg"><a href="<c:url value="regov2.htm"/>" ><spring:message code="menyvalg5" /></a></td></tr>
            <tr><td class="menuvalg"><a href="<c:url value="/adminlaerer.htm"/>"><spring:message code="menyvalg7" /></a></td></tr>


        </c:if>
    </c:if>
</table>