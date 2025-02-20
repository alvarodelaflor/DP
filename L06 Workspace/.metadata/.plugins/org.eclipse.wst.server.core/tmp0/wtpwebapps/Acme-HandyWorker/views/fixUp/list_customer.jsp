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

<p class="create"><input type="button" value=<spring:message code="customer.createFixUp" /> id="buttonFixUp" name="buttonFixUp"  onclick="location.href='fixUp/customer/create.do';"/></p>
<p><spring:message code="customer.action.1" /></p>
<body>
<div>
<security:authorize access="hasRole('CUSTOMER')">
<display:table name="fixUps" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column titleKey="customer.ticker"> 
		<a href="fixUp/customer/show.do?fixUpId=${row.id}">${row.ticker}</a>
	</display:column>
	<display:column property="description" titleKey="customer.description"></display:column>
	<display:column titleKey="customer.price"> 
		<jstl:out value="${row.maxPrice.quantity}"></jstl:out><jstl:out value="${row.maxPrice.currency}"></jstl:out>
	</display:column>
	<display:column property="handyWorker.name" titleKey="handyWorker.nameHW"></display:column>
	<c:choose>
   		<c:when test="${row.handyWorker != null}">
        	<display:column titleKey="valorar"> 
				<a href="endorsement/customer/create.do?receiverId=${row.handyWorker.id}"><spring:message code="valorar" /></a>
			</display:column> 
    	</c:when>    
    	<c:otherwise>
        	<tr><td></td></tr>
    	</c:otherwise>
	</c:choose>
</display:table>
</security:authorize>
</div>

<form>
	<input type="button" value=<spring:message code="back" /> name="back" onclick="history.back()" />
</form> 

</body>