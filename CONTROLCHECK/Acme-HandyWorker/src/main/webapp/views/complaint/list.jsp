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


<table>
<tr><td><i><spring:message code="hw.complaint" /></i></td></tr>
					<tr><td>
					<div>
						<security:authorize access="hasRole('HANDYWORKER')">
							<display:table name="complaint" id="row" requestURI="${requestURI}" pagesize="3" class="displaytag">
								<display:column titleKey="complaint.ticker" >
										<a href="complaint/handyWorker/show.do?complaintId=${row.id}">${row.ticker}</a>
								</display:column>
								<display:column property="fixUp.ticker" titleKey="complaint.fixUp" ></display:column>
								<display:column property="moment" titleKey="complaint.moment" ></display:column>
								<display:column property="description" titleKey="complaint.description"></display:column>
							</display:table>
						</security:authorize>
					</div>
					</td></tr>
		
</table>


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


