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
        	<a title="AcmeTitle" href="fixUp/customer/editFixUpTask.do?id=${fixUp.id}"><img src="images/edit.png" alt="Edit" width="3%"/></a>
	
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
        				<a onclick="return confirmarEN('accion.html')" title="AcmeTitle" href="fixUp/customer/deleteFixUpTask.do?delete=y&id=${fixUp.id}"><img src="images/delete.png" alt="Delete" width="3%"/></a> 
    				</c:when>    
    				<c:otherwise>
        				<a onclick="return confirmarES('accion.html')" title="AcmeTitle" href="fixUp/customer/deleteFixUpTask.do?delete=y&id=${fixUp.id}"><img src="images/delete.png" alt="Delete" width="3%"/></a>
    				</c:otherwise>
				</c:choose>
        	
        </h2>
      </header>
      
      <div class="content">
			<img class="lupa" src="images/lupa.png" alt="Luga" width="19%"/>
			<table>
				<tr><td><strong><spring:message code="customer.showing.details" /></strong></td></tr>
    						
    			<tr><td><spring:message code="customer.showing.createDate" /><jstl:out value="${fixUp.moment}"></jstl:out></td></tr>
    							
    			<tr><td><spring:message code="customer.showing.address" /><jstl:out value="${fixUp.address}"></jstl:out></td></tr>
    							
    			<tr><td><spring:message code="customer.showing.price" /><jstl:out value="${fixUp.maxPrice.quantity}"></jstl:out><jstl:out value="${fixUp.maxPrice.currency}"></jstl:out></td></tr>
    							
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
								<jstl:forEach var="application" items="${applications}">
  										<jstl:out value="${application.applier.name}"></jstl:out>'s application, <jstl:out value="${application.comments}"></jstl:out>
  										<br>
  								</jstl:forEach>
  								</td>
  								</tr>
								<tr><td><i><spring:message code="customer.showing.complaints" /></i></td></tr>
  								<tr>
  								<td>
								<jstl:forEach var="complaint" items="${complaints}">
  										<jstl:out value="${complaint.ticker}"></jstl:out>, <jstl:out value="${complaint.description}"></jstl:out>
  										<br>
  								</jstl:forEach>
  								</td> 
        						</tr>
        			
  				
          </table>
      </div>
      
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
          
      
    </article> <!-- /article -->
  
  </section> <!-- / #main-content -->
