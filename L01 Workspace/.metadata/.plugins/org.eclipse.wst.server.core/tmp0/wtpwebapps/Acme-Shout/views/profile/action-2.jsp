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

<p><spring:message code="profile.action.2" /></p>

<form:form modelAttribute="calculator">
	<form:input path="x"/> <form:errors path="x" /> <br/>
	<form:input path="y"/> <form:errors path="y" /> <br/>
	<form:select path="operator">
		<form:option value="+"/>
		<form:option value="-"/>
		<form:option value="*"/>
		<form:option value="/"/>
	</form:select>
	<form:errors path="operator" /> <br/>
	<hr/>
	<jstl:out value="${calculator.result}" />
	
	<br/>
	<input type="submit"
		value="<spring:message code='profile.compute'/> "/>
</form:form>	
