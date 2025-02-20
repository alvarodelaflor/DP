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
<div>
<h2><spring:message code="handyWorker.allFixUp" /></h2>
<security:authorize access="hasRole('HANDYWORKER')">
<display:table name="fixUps" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column titleKey="customer.ticker"> 
		<a href="fixUp/handyWorker/show.do?fixUpId=${row.id}">${row.ticker}</a>
	</display:column>
	<display:column property="description" titleKey="customer.description"></display:column>
	<display:column property="maxPrice.quantity" titleKey="customer.price"></display:column>
	<display:column titleKey="customer.name2">
		<a href="customer/handyWorker/show.do?customerId=${row.customer.id}">${row.customer.name}</a>
	</display:column>
	<display:column property="handyWorker.name" titleKey="handyWorker.nameHW"></display:column>
</display:table>
</security:authorize>
</div>

<div>
<h2><spring:message code="handyWorker.allMyFixUp" /></h2>
<security:authorize access="hasRole('HANDYWORKER')">
<display:table name="myFixUps" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column titleKey="customer.ticker"> 
		<a href="fixUp/handyWorker/show.do?fixUpId=${row.id}">${row.ticker}</a>
	</display:column>
	<display:column property="description" titleKey="customer.description"></display:column>
	<display:column property="maxPrice.quantity" titleKey="customer.price"></display:column>
	<display:column property="customer.name" titleKey="customer.name2"></display:column>
		<display:column titleKey="valorar"> 
			<a href="endorsement/handyWorker/create.do?receiverId=${row.customer.id}"><spring:message code="valorar" /></a>
		</display:column>
</display:table>
</security:authorize>
</div>

<form>
	<input type="button" value=<spring:message code="back" /> name="back" onclick="history.back()" />
</form>

</body>