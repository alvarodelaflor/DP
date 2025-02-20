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


<header id="main-header">
    
        <a title="AcmeTitle" href="">${system}    		    	<img  src="${logo}" id="main-header" >
    <!--<img src="images/logo.png" alt="Logo" />--></a>
 
    <nav>
		<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/list.do"><spring:message code="master.page.administrator.action.1" /></a></li>
					<li><a href="referee/administrator/list.do"><spring:message code="master.page.administrator.action.3" /></a></li>
					<li><a href="complaint/administrator/showD.do"><spring:message code="master.page.administrator.action.2" /></a></li>
					<li><a href="endorsement/administrator/list.do"><spring:message code="master.page.administrator.endorsement" /></a></li>
										<li><a href="administrator/create.do"><spring:message code="master.page.administrator.create" /></a></li>		
					<li><a href="category/administrator/list.do"><spring:message code="master.page.category.list" /></a></li>																
					<li><a href="warranty/administrator/list.do"><spring:message code="master.page.warranty.list" /></a></li>																
					<li><a href="administrator/statistics.do"><spring:message code="master.page.administrator.statistics" /></a></li>																
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="fixUp/customer/list.do"><spring:message code="master.page.customer.action.1" /></a></li>
					<li><a href="application/customer/list.do"><spring:message code="master.page.customer.list.application" /></a></li>
					<li><a href="complaint/customer/list.do"><spring:message code="master.page.customer.complaint" /></a></li>
					<li><a href="endorsement/customer/show.do"><spring:message code="master.page.customer.endorsement" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('HANDYWORKER')">
			<li><a class="fNiv"><spring:message	code="master.page.handyWorker" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="fixUp/handyWorker/list.do"><spring:message code="master.page.customer.action.1" /></a></li>
					<li><a href="finder/handyWorker/list.do"><spring:message code="master.page.handyWorker.finder" /></a></li>
					<li><a href="application/handyWorker/list.do"><spring:message code="master.page.handyWorker.list.application" /></a></li>
					<li><a href="tutorial/handyWorker/list.do"><spring:message code="master.page.handyWorker.tutorial" /></a></li>	
					<li><a href="complaint/handyWorker/list.do"><spring:message code="master.page.handyWorker.complaint" /></a></li>
					<li><a href="endorsement/handyWorker/show.do"><spring:message code="master.page.handyWorker.endorsement" /></a></li>									
										
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('REFEREE')">
			<li><a class="fNiv"><spring:message	code="master.page.referee" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="complaint/referee/list.do"><spring:message code="master.page.complaints.referee.list" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a href="tutorial/list.do"><spring:message code="master.page.tutorialListing" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.createUser" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/create.do"><spring:message code="master.page.createUser.customer" /></a></li>
					<li><a href="handyWorker/create.do"><spring:message code="master.page.createUser.handyWorker" /></a></li>
					<li><a href="sponsor/create.do"><spring:message code="master.page.createUser.sponsor" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>					
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
					<li><a href="mailBox/list.do"><spring:message code="master.page.mailBox.list" /></a></li>																
					<security:authorize access="hasRole('CUSTOMER')">
						<li><a href="actor/edit.do"><spring:message code="master.page.editProfile" /></a></li>
						<li><a href="actor/show.do"><spring:message code="master.page.showProfile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('ADMIN')">
						<li><a href="administrator/edit.do"><spring:message code="master.page.editProfile" /></a></li>
						<li><a href="actor/show.do"><spring:message code="master.page.showProfile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('REFEREE')">
						<li><a href="referee/edit.do"><spring:message code="master.page.editProfile" /></a></li>
						<li><a href="referee/show.do"><spring:message code="master.page.showProfile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('SPONSOR')">
						<li><a href="sponsor/edit.do"><spring:message code="master.page.editProfile" /></a></li>
						<li><a href="actor/show.do"><spring:message code="master.page.showProfile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('HANDYWORKER')">			
						<li><a href="handyWorker/edit.do"><spring:message code="master.page.editProfile" /></a></li>
						<li><a href="handyWorker/show.do"><spring:message code="master.page.showProfile" /></a></li>
						<li><a href="curriculum/handyWorker/show.do"><spring:message code="master.page.showCurriculum" /></a></li>
					</security:authorize>
				</ul>
			</li>
		</security:authorize>
	</ul>
    </nav><!-- / nav -->
 
  </header><!-- / #main-header -->

<div class=language>
	<img width="20px" src="images/uk.png" alt="en" onClick="changeLangEn()"/><img width="20px" src="images/spain.png" alt="es" onClick="changeLangEs()"/>
</div>

<script type="text/javascript">
function changeLangEs(){
	var cpage=window.location.href;
		if(cpage.includes("language=en")){
			cpage=cpage.replace("language=en", "language=es");
		}else if(cpage.includes("language=es")){
			
		}else if(cpage.includes("?")){
			cpage=window.location.href+"&language=es";
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
			
		}else if(cpage.includes("?")){
			cpage=window.location.href+"&language=en";
			
		}else{
			cpage=window.location.href+"?language=en";
		}
		window.location.href = cpage;
		
	
	
	}
</script>



