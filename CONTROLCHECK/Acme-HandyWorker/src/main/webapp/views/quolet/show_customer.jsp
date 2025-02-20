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

      		</header>
      
      		<div class="content">
				<img class="lupa" src="images/lupa.png" alt="Luga" width="19%"/>
				<table>
					<tr><td><strong><spring:message code="customer.showing.details" /></strong></td></tr>
    						
    				<tr><td><spring:message code="ticker" /><jstl:out value="${quolet.ticker}"></jstl:out></td></tr>
    							
  					<tr><td><font color=<jstl:out value="${color}"></jstl:out>><spring:message code="moment" />
  					
  						<c:choose>
    						<c:when test="${language=='English'}">
								${dateENparse}
    						</c:when>    
    						<c:otherwise>
								${dateESparse}
    						</c:otherwise>
						</c:choose>
  					
  					</font></td></tr>
    							
    				<tr><td><spring:message code="body" /><jstl:out value="${quolet.body}"></jstl:out></td></tr>			
    							
    				<tr>
						<td><spring:message code="picture" /><br>
							<img width="95" class="customer_photo" src="${quolet.picture}" alt=<jstl:out value="${quolet.picture}"></jstl:out> /></td>
					</tr>

          			</table>
      			</div>
      
			<form method="get" action="quolet/customer/list.do">
				<button type="submit">
					<spring:message code="button.back" />
				</button>
			</form>
				
    	</article> <!-- /article -->
	</section> <!-- / #main-content -->
