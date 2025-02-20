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

<p><spring:message code="administrator.listWarranties"/></p>
<body>
  
   <display:table name="warranties" id="row"  requestURI="${requestURI}"	pagesize="5" class="displaytag" >
  	
  	<display:column titleKey="administrator.showWarranty"> 
		<a href="warranty/administrator/show.do?warrantyId=${row.id}"><spring:message code="administrator.showWarranty" /></a>
	</display:column>
  
	
	<c:choose>
   		<c:when test="${row.isFinal != true}">
   	<display:column titleKey="administrator.editWarranty"> 
		<a href="warranty/administrator/edit.do?warrantyId=${row.id}"><spring:message code="administrator.editWarranty" /></a>
	</display:column>
    	</c:when>    
    	<c:otherwise>
        	<display:column titleKey="administrator.editWarranty"/>
    	</c:otherwise>
	</c:choose>
  	
  	<display:column property="title" titleKey="warranty.title"/>
  	<display:column property="terms" titleKey="warranty.terms"/>
  	<display:column property="laws" titleKey="warranty.laws"/>
   	
   	<c:choose>
		<c:when test="${row.isFinal != true}">
			<display:column titleKey="warranty.show.isFinal">
				<spring:message code="warranty.show.isFinal.NO" />
			</display:column>
		</c:when>
		<c:otherwise>
			<display:column titleKey="warranty.show.isFinal">
				<spring:message	code="warranty.show.isFinal.SI" />
			</display:column>
		</c:otherwise>
	</c:choose>
  	</display:table>
  	
	<p class="create"><input type="button" value=<spring:message code="administrator.createWarranty" /> id="buttonWarranty" name="buttonWarranty"  onclick="location.href='warranty/administrator/create.do';"/></p>
  		
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
		
</body>