<%--
 * complaintShow.jsp
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
<security:authorize access="hasRole('CUSTOMER')">
	<div>
				<script>
				function confirmarEN(url) {
					if(confirm('Are you sure?')) {
						window.location=url;
					} else {
						return false;
					}	
				}
			</script>
						<script>
				function confirmarES(url) {
					if(confirm('�Estas seguro?')) {
						window.location=url;
					} else {
						return false;
					}	
				}
			</script>
			
			   <c:choose>
    				<c:when test="${language=='English'}">
        				<a onclick="return confirmarEN('accion.html')" title="AcmeTitle" href="complaint/customer/deleteComplaint.do?id=${complaint.id}"><img src="images/delete.png" alt="Delete" width="3%"/></a> 
    				</c:when>    
    				<c:otherwise>
        				<a onclick="return confirmarES('accion.html')" title="AcmeTitle" href="complaint/customer/deleteComplaint.do?id=${complaint.id}"><img src="images/delete.png" alt="Delete" width="3%"/></a>
    				</c:otherwise>
				</c:choose>
		<a href="complaint/customer/list.do"><input type="button" value="<spring:message code='button.back'></spring:message>"></a>
		<a href="complaint/customer/edit.do"><input type="button" value="<spring:message code='button.edit'></spring:message>"></a>
	</div>
	<div>
		<display:table name="complaints" id="row" requestURI="complaint/customer/show.do?complaintId=${row.id}" class="displaytag">
			<display:column property="<spring:message code='complaint.moment'/>" titleKey="complaint.moment" format="{0,date,dd/MM/yyyy HH:mm}"></display:column>
			<display:column property="<spring:message code='complaint.referee'/>" titleKey="complaint.referee"></display:column>
			<display:column property="<spring:message code='complaint.fixUp'/>" titleKey="complaint.fixUp"></display:column>	
		</display:table>
	</div>
	<div>
		<display:table name="complaints" id="row" requestURI="complaint/customer/show.do?complaintId=${row.id}" class="displaytag">
			<display:column property="<spring:message code='complaint.description'/>" titleKey="complaint.description"></display:column>
			<display:column property="<spring:message code='complaint.attachment'/>" titleKey="complaint.attachment"></display:column>	
		</display:table>
	</div>
</security:authorize>
 </div>
</body>