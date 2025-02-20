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

<p><spring:message code="administrator.action.1" /></p>

<table>
	<tr>
		<th><spring:message code="administrator.indicator"/></th>
		<th><spring:message code="administrator.value"/></th>	
	</tr>
	
	<tr>
		<td><spring:message code="administrator.count.all.shouts"/></td>
		<td><jstl:out value="${statistics.get('count.all.shouts')}"/></td>	
	</tr>
	
	<tr>
		<td><spring:message code="administrator.count.short.shouts"/></td>
		<td><jstl:out value="${statistics.get('count.short.shouts')}"/></td>	
	</tr>
	
	<tr>
		<td><spring:message code="administrator.count.long.shouts"/></td>
		<td><jstl:out value="${statistics.get('count.long.shouts')}"/></td>	
	</tr>
		
</table>