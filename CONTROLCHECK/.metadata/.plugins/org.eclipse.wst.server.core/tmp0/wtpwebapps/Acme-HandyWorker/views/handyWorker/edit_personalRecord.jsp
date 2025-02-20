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
        	<spring:message code="handyWorker.edit.personalRecord" />
        </h2>
      </header>
      
      <div class="content">
    		
    	<form:form class="formularioEdicion" method="POST" modelAttribute="personalRecord" action="personalRecord/handyWorker/edit.do">
          	<form:hidden path="id"/>
          	<form:hidden path="version"/>
          	
          	<form:label path="name"><spring:message code="handyWorker.name" /></form:label>
			<form:input path="name" required="required"/>
			<form:errors cssClass="error" path="name"/><br>
			
			<form:label path="photo"><spring:message code="curriculum.photo" /></form:label>
			<form:input path="photo" required="required"/>
			<form:errors cssClass="error" path="photo"/><br>
			
			<form:label path="phone"><spring:message code="handyworker.phone" /></form:label>
			<form:input path="phone" required="required"/>
			<form:errors cssClass="error" path="phone"/><br>
			
			<form:label path="linkedIn"><spring:message code="curriculum.linkedIn" /></form:label>
			<form:input path="linkedIn" required="required"/>
			<form:errors cssClass="error" path="linkedIn"/><br>
					
			<input type="submit" name="save" value=<spring:message code="send" />/>
		</form:form>
      </div>
      
 	</article>

  
  </section>
