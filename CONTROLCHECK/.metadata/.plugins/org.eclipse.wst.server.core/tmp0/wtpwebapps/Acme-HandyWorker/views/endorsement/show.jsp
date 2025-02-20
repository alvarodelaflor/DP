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
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

					
      		<div class="content">
				<img class="lupa" src="images/lupa.png" alt="Luga" width="19%"/>
					<table>
								<tr><td>
									<p><spring:message code="endorsement.send" />
									<display:table name="endorsementSend" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
										<display:column titleKey="button.edit"> 
											<a href="<jstl:out value="${editURL}"></jstl:out>=${row.id}"><spring:message code="button.edit" /></a>
										</display:column>
										<display:column titleKey="delete"> 
											<a href="<jstl:out value="${deleteURL}"></jstl:out>=${row.id}"><spring:message code="delete" /></a>
										</display:column>												
										<display:column property="endorsableReceiver.name" titleKey="endorsement.endorsableReceiver" ></display:column>
										<display:column property="comments" titleKey="endorsement.comments" ></display:column>
									</display:table>
								</td></tr>
								<tr><td>
									<p><spring:message code="endorsement.received" />
									<display:table name="endorsementReceived" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">											
										<display:column property="endorsableSender.name" titleKey="endorsement.endorsableSender" ></display:column>
										<display:column property="comments" titleKey="endorsement.comments" ></display:column>
									</display:table>
								</td></tr>
					</table>
			</div>


				<form>
					<input type="button" value=<spring:message code="back" /> name="back" onclick="history.back()" />
				</form>
