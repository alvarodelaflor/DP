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
        	<spring:message code="handyWorker.edit.professionalProfile" />
        </h2>
      </header>
      
      <div class="content">
    		
    	<form:form class="formularioEdicion" method="POST" modelAttribute="professionalRecord" action="professionalRecord/handyWorker/edit.do">
          	<form:hidden path="id"/>
          	<form:hidden path="version"/>
          	
          	<form:label path="company"><spring:message code="curriculum.company" /></form:label>
			<form:input path="company" required="required"/>
			<form:errors cssClass="error" path="company"/><br>
			
			<form:label path="role"><spring:message code="curriculum.role" /></form:label>
			<form:input path="role" required="required"/>
			<form:errors cssClass="error" path="role"/><br>
			
			<form:label path="link"><spring:message code="curriculum.link" /></form:label>
			<form:input path="link" required="required"/>
			<form:errors cssClass="error" path="link"/><br>

			<form:label path="comments"><spring:message code="curriculum.comments" /></form:label>
			<form:input path="comments" required="required"/>
			<form:errors cssClass="error" path="comments"/><br>

			<form:label path="start"><spring:message code="curriculum.start" /></form:label>
			<form:input type="date" path="start" required="required" placeholder="yyyy/MM/dd HH:mm"/>
			<form:errors cssClass="error" path="start"/><br>
			
			<form:label path="end"><spring:message code="curriculum.end" /></form:label>
			<form:input type="date" path="end" required="required" placeholder="yyyy/MM/dd HH:mm"/>
			<form:errors cssClass="error" path="end"/><br>
					
			<input type="submit" name="save" value=<spring:message code="send" />/>
		</form:form>
      </div>
      
 	</article>

  
  </section>
