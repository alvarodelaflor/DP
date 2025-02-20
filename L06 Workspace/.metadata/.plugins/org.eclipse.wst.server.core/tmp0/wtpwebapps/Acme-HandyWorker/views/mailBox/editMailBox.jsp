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

<p><spring:message code="editMailBox" /></p>
<body>
	<form:form action="mailBox/edit.do" method="POST"	modelAttribute="mailBox">
		
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="messages" />
		<form:hidden path="isDefault" />
				
		<form:label path="name">
			<spring:message code="mailBox.name" />:
		</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name" />
		
		<br />
				
		
		<input type="submit" name="save" value="<spring:message code="editMailBox" />" />
		
		</form:form>
		
		<input type="button" name="cancel" value="<spring:message code="mailBox.cancel"/>" onclick="javascript:relativeRedir('mailBox/list.do');"/>
</body>