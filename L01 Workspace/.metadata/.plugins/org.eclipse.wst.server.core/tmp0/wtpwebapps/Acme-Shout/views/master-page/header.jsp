<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="images/logo.png" alt="Acme Shout Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/dashboard.do"><spring:message code="master.page.administrator.action.1" /></a></li>
					<li><a href="administrator/chart.do"><spring:message code="master.page.administrator.action.2" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/shouts.do"><spring:message code="master.page.customer.action.1" /></a></li>
					<li><a href="customer/enter-a-shout.do"><spring:message code="master.page.customer.action.2" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="profile/inspiring-quotes.do"><spring:message code="master.page.profile.action.1" /></a></li>
					<li><a href="profile/calculator.do"><spring:message code="master.page.profile.action.2" /></a></li>
					<li><a href="profile/oops.do"><spring:message code="master.page.profile.action.3" /></a></li>					
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<img class="img"id="img_es" alt="es" src="images/es.png" onClick="changeLangEs()"><img class="img" id="img_en" alt="en" src="images/en.png" onClick="changeLangEn()">
</div>
<script type="text/javascript">
function changeLangEs(){
	var cpage=window.location.href;
		if(cpage.includes("language=en")){
			cpage=cpage.replace("language=en", "language=es");
		}else if(cpage.includes("language=es")){
			
		}else{
			cpage=window.location.href+"?language=es";
		}
		window.location.href = cpage;
		
	
	
	}

</script>
<script type="text/javascript">
function changeLangEn(){
	var cpage=window.location.href;
		if(cpage.includes("language=es")){
			cpage=cpage.replace("language=es", "language=en");
		}else if(cpage.includes("language=en")){
			
		}else{
			cpage=window.location.href+"?language=en";
		}
		window.location.href = cpage;
		
	
	
	}
</script>




