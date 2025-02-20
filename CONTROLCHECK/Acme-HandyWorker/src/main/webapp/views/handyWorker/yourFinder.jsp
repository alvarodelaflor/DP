<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<h2><spring:message code="handyWorker.action.1" /></h2>

<body>
	
<table border=1 cellspacing=0 cellpadding=2 bordercolor="666633">
  <tr>
    <th><spring:message code="finder.maxPrice" /></th>
   	<th><spring:message code="finder.minPrice" /></th>
   	<th><spring:message code="finder.keyword" /></th>
    <th><spring:message code="finder.startDate" /></th>
   	<th><spring:message code="finder.date" /></th>
   	<th><spring:message code="finder.endDate" /></th>
   	<th><spring:message code="finder.warranty" /></th>
   	<th><spring:message code="finder.category" /></th>
   	<th><spring:message code="finder.fixUps" /></th>
  </tr>
   <tr>
  		<td><jstl:out value="${finder.maxPrice}"></jstl:out></td>
 		<td><jstl:out value="${finder.minPrice}"></jstl:out></td>
  		<td><jstl:out value="${finder.keyword}"></jstl:out></td>
  		<td><jstl:out value="${finder.startDate}"></jstl:out></td>
  		<td><jstl:out value="${finder.date}"></jstl:out></td>
  		<td><jstl:out value="${finder.endDate}"></jstl:out></td>
  		<td><jstl:out value="${finder.warranty}"></jstl:out></td>
  		<td><jstl:out value="${finder.category}"></jstl:out></td>
  	 	<jstl:forEach var="fixUp" items="${finder.fixUps}">
  	 		<td>
  				<jstl:out value="${fixUp.ticker}"></jstl:out>
			</td>
		</jstl:forEach> 
</tr>
</table>
<a title="AcmeTitle" href="finder/handyWorker/editYourFinder.do?finderId=${finder.id}"><img src="images/edit.png" alt="Edit" width="3%"/></a>

<c:choose>
    		<c:when test="${language=='English'}">
        		<form>
      				<input type="button" value="Back" name="volver atr�s2" onclick="history.back()" />
	  			</form> 
    		</c:when>    
    		<c:otherwise>
		 		<form>
      				<input type="button" value="Volver" name="volver atr�s2" onclick="history.back()" />
	  			</form>        		
    		</c:otherwise>
		</c:choose>

</body>