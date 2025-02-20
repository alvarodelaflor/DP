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

<body>

<div class="content">
				<table>
					<tr><td><strong><spring:message code="application.show" /></strong></td></tr>
    						
    				<tr><td><spring:message code="application.show.moment" /><jstl:out value="${application.moment}"></jstl:out></td></tr>
    				
    				<c:choose>
    					<c:when test="${empty application.state}">
        					<tr><td><font color=<jstl:out value="${color}"></jstl:out>><spring:message code="application.show.state" /><spring:message code="application.pending" /></font></td></tr> 
    					</c:when>
    					<c:when test="${application.state == true}">
        					<tr><td><font color=<jstl:out value="${color}"></jstl:out>><spring:message code="application.show.state" /><spring:message code="application.accepted" /></font></td></tr>  
    					</c:when>    
    					<c:otherwise>
        					<tr><td><font color=<jstl:out value="${color}"></jstl:out>><spring:message code="application.show.state" /><spring:message code="application.denied" /></font></td></tr>
    					</c:otherwise>
					</c:choose>
    				  								
					<tr><td><spring:message code="application.show.offered" /><jstl:out value="${application.offered.quantity} ${application.offered.currency}"></jstl:out>(${iva})</td></tr>
					
					<tr><td><spring:message code="application.show.comments" /><jstl:out value="${application.comments}"></jstl:out></td></tr>
					
					<tr><td><spring:message code="application.show.commentsCus" /><jstl:out value="${application.commentsCus}"></jstl:out></td></tr>
					
					<tr><td><spring:message code="application.show.applier" /><jstl:out value="${application.applier.name}"></jstl:out></td></tr>
          			
          			<tr><td><spring:message code="application.show.fixUp" /><jstl:out value="${application.fixUp.ticker}"></jstl:out></td></tr>
          	
          	        <tr><td><spring:message code="application.show.creditCard" /><jstl:out value="${application.creditCard.number}"></jstl:out></td></tr>
          			</table>
      			</div>
      			<form>
					<input type="button" value=<spring:message code="back" /> name="back" onclick="history.back()" />
				</form>
</body>