<%-- 
    Document   : emnetilbruker
    Created on : 24-Jan-2014, 09:15:40
    Author     : Bjornars
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>



<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<body>


    <div id="brukere">
        <select name="brukerne2" id="brukerne2">

            <c:forEach items="${brukerne}" var="bruker">

                <option value="${bruker.getBrukernavn()}">${bruker.getFornavn()} ${bruker.getEtternavn()}</option>
            </c:forEach>

        </select>
    </div >
    </br>
    <div id="emner">
        <select multiple="true" name="emner2" id="emner2">

            <c:forEach items="${emnene}" var="emne">
                
                <option value="${emne.getEmnekode()}"> ${emne.getEmnenavn()} - ${emne.getEmnekode()} </option>
            </c:forEach>

        </select>
        <td><input type="submit" value="Lagre"></td>
        
        
    </div>


</body>
</html>