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
					<tr><td><strong><spring:message code="category.show" /></strong></td></tr>
    						
    				<tr><td><spring:message code="category.show.nameES" /><jstl:out value="${category.nameES}"></jstl:out></td></tr>
    							
    				<tr><td><spring:message code="category.show.nameEN" /><jstl:out value="${category.nameEN}"></jstl:out></td></tr>
    				  								
					<tr><td><spring:message code="category.show.parentCategory" /><jstl:out value="${category.parentCategory}"></jstl:out></td></tr>
										          			
          			</table>
          			
          			<a onclick="return confirmar('accion.html')" title="AcmeTitle" href="category/administrator/delete.do?id=${category.id}"><img src="images/delete.png" alt="Delete" width="3%"/></a>
          			
      			</div>
      			
			<form method="get" action=" ">
        		<form>
      				<input type="button" value="Back" name="volver atr�s2" onclick="history.back()" />
	  			</form> 
			</form>