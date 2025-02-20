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

	<section id="main-content">
  
    	<article>
      		<header>
        		<h2>
        			<spring:message code="customer.welcome.listing" />
        			<jstl:out value="${fixUp.ticker}"></jstl:out>
        			<a title="AcmeTitle" href="fixUp/customer/edit.do?id=${fixUp.id}"><img src="images/edit.png" alt="Edit" width="3%"/></a>

					<script>
					 	var abc="<spring:message code="customer.confirm"/>";
						function confirmar(url) {
							if(confirm(abc)) {
								window.location=url;
							} else {
								return false;
							}	
						}
					</script>
					<a onclick="return confirmar('accion.html')" title="AcmeTitle" href="fixUp/customer/delete.do?id=${fixUp.id}"><img src="images/delete.png" alt="Delete" width="3%"/></a>
        		</h2>
      		</header>
      
      		<div class="content">
				<img class="lupa" src="images/lupa.png" alt="Luga" width="19%"/>
				<table>
					<tr><td><strong><spring:message code="customer.showing.details" /></strong></td></tr>
    						
    				<tr><td><spring:message code="customer.showing.createDate" /><jstl:out value="${fixUp.moment}"></jstl:out></td></tr>
    							
    				<tr><td><spring:message code="customer.showing.address" /><jstl:out value="${fixUp.address}"></jstl:out></td></tr>
    							
    				<tr><td><spring:message code="customer.showing.warranty" /><jstl:out value="${fixUp.warranty.title}"></jstl:out></td></tr>			
    							
    				<tr><td><spring:message code="customer.showing.price" /><jstl:out value="${fixUp.maxPrice.quantity}"></jstl:out><jstl:out value="${fixUp.maxPrice.currency}"></jstl:out>(${iva})</td></tr>

    				<tr><td><spring:message code="customer.showing.moment" /><jstl:out value="${fixUp.moment}"></jstl:out></td></tr>
    							
    				<tr><td><spring:message code="customer.showing.startDate" /><jstl:out value="${fixUp.startDate}"></jstl:out></td></tr>
    							
    				<tr><td><spring:message code="customer.showing.endDate" /><jstl:out value="${fixUp.endDate}"></jstl:out></td></tr>
    							
    				<tr><td><spring:message code="customer.showing.handyWorker" /><jstl:out value="${fixUp.handyWorker.name}"></jstl:out></td></tr>
    							
    				<c:choose>
    					<c:when test="${language=='English'}">
        					<tr><td><spring:message code="customer.showing.category" /><jstl:out value="${category.nameEN}"></jstl:out></td></tr> 
    					</c:when>    
    					<c:otherwise>
        					<tr><td><spring:message code="customer.showing.category" /><jstl:out value="${category.nameES}"></jstl:out></td></tr>
    					</c:otherwise>
					</c:choose>
        			<tr><td><strong><spring:message code="customer.showing.description" /></strong></td></tr>
        			
        			<tr><td><jstl:out value="${fixUp.description}"></jstl:out></td></tr>
  				
    				<tr><td><strong><spring:message code="customer.showing.attachment" /></strong></td></tr>
    				
    				<tr><td><i><spring:message code="customer.showing.applications" /></i></td></tr>
    					<tr><td>
    				    	<div>
									<display:table name="applications" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
										<display:column titleKey="customer.ticker"> 
											<a href="application/customer/show.do?applicationId=${row.id}"><spring:message code="application.show" /></a>
										</display:column>
										<display:column property="applier.name" titleKey="application.applier.name"></display:column>
										<display:column property="moment" titleKey="application.moment"></display:column>
										<display:column titleKey="application.offered"> 
											<jstl:out value="${row.offered.quantity}"></jstl:out><jstl:out value="${row.offered.currency}"></jstl:out>
										</display:column>
										<display:column property="comments" titleKey="application.comments"></display:column>
									</display:table>
							</div>
  							
  						</td></tr>
								
						<tr><td><i><spring:message code="customer.showing.complaints" /></i></td></tr>
  								
  						<tr><td>

    				   		<div>
								<security:authorize access="hasRole('CUSTOMER')">
									<display:table name="complaints" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
										<display:column property="ticker" titleKey="complaint.ticker"></display:column>
										<display:column property="description" titleKey="complaint.description"></display:column>
									</display:table>
								</security:authorize>
							</div>
  								
  						</td></tr>
          			</table>
      			</div>
      
				<form>
					<input type="button" value=<spring:message code="back" /> name="back" onclick="history.back()" />
				</form>
				
    	</article> <!-- /article -->
	</section> <!-- / #main-content -->
