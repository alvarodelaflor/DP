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
        	<spring:message code="customer.createFixUp" />
        </h2>
      </header>
      
      <div class="content">
		<form action="fixUp/customer/createFixUpTask.do?create=true" method="post">
    		<div>
        		<label for="description"><spring:message code="customer.showing.description" /></label>
        		<input type="text" id="description" />
    		</div>
    		<div>
        		<label for="address"><spring:message code="customer.showing.address" /></label>
        		<input type="text" id="address" />
    		</div>
    		<div>
        		<label for="maxPrice"><spring:message code="customer.showing.price" /></label>
        		<input type="number" id="maxPrice" />
    		</div>
    		<div>
        		<label for="startDate"><spring:message code="customer.showing.startDate" /></label>
        		<input type="datetime" id="startDate" />
    		</div>
    		<div>
        		<label for="endDate"><spring:message code="customer.showing.endDate" /></label>
        		<input type="datetime" id="endDate" />
    		</div>
    		<div class="button">
        		<button type="submit"><spring:message code="customer.createFixUp" /></button>
    		</div>
		</form>
      </div>
      
    </article> <!-- /article -->
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
  
  </section> <!-- / #main-content -->
