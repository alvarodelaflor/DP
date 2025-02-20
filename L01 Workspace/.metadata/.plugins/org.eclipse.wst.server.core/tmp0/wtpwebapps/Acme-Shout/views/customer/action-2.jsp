<%--
 * action-2.jsp
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

<p><spring:message code="customer.action.2" /></p>

<form:form modelAttribute="shout">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="username"><spring:message code="customer.username"/>:</form:label>
	<form:input path="username"/>
	<form:errors path="username"/>
	<br />
	
	<form:label path="link"><spring:message code="customer.link"/>:</form:label>
	<form:input path="link"/>
	<form:errors path="link"/>
	<br />
	
	<form:label path="text"><spring:message code="customer.text"/>:</form:label>
	<form:input path="text"/>
	<form:errors path="text"/>
	<br />
	
	<input type="submit" name="save" value="<spring:message code="customer.save"/>"/>
	
	<input type="button" name ="cancel" value="<spring:message code="customer.cancel"/>"
		onclick="javascript: relativeRedir('customer/action-1.do');" />
	</form:form>
