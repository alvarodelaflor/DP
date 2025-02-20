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

    <style type="text/css">
        .oculta{
            display:none;
        }
    </style>
 
    <script type= "text/javascript"> 
        function mostrar(value_elemento){
            var elemento;
                elemento = document.getElementById('capa0');
                if ("true" == value_elemento){
                    elemento.style.display="block";
                }else{
                    elemento.style.display="none";
                }
        }
    </script> 

<body>
	<form:form action="application/customer/edit.do" method="POST"
		modelAttribute="application">
		
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="version" />
		<form:hidden path="applier" />
		<form:hidden path="fixUp"/>
		<form:hidden path="moment"/>
		<form:hidden path="comments"/>
		<!--
		<form:label path="comments">
			<spring:message code="application.comments" />
		</form:label>
		<form:input path="comments" />
		<form:errors cssClass="error" path="comments" /><br/>
		-->
		<form:hidden path="offered.quantity"/>
		<form:label path="commentsCus"><spring:message code="application.comments" /></form:label>
		<form:input type="input" path="commentsCus" />
		<!--
		<form:label path="offered.quantity"><spring:message code="application.offered.quantity" /></form:label>
		<form:input type="number" path="offered.quantity" required="required"/>
		<form:errors cssClass="error" path="offered.quantity" />
		-->
		<form:hidden path="offered.currency"/>
		<form:hidden path="offered.quantity"/>
		<!--
		<form:label path="offered.currency"><spring:message code="application.offered.currency" /></form:label>
		<form:select path="offered.currency" >
			<form:option value="EUR"></form:option>
			<form:option value="DOL"></form:option>
		</form:select>
		<form:errors cssClass="error" path="offered.currency" /><br />
		-->	
		<form:errors cssClass="error" path="creditCard.brand" /><br/>	
		<form:errors cssClass="error" path="creditCard.name" /><br/>	
		<form:errors cssClass="error" path="creditCard.number" /><br/>	
		<form:errors cssClass="error" path="creditCard.cvv" /><br/>
		<form:errors cssClass="error" path="creditCard.year" /><br/>		
		<form:label path="state"><spring:message code="application.state" /></form:label>
		<form:select path="state" onchange="mostrar(this.value);">
			<form:option value="false"  selected="true"><spring:message code="application.denied" /></form:option>
			<form:option value="true"><spring:message code="application.accepted" /></form:option>
		</form:select><br/>	
		<form:errors cssClass="error" path="state" />
		<div class="oculta" id="capa0">
		<spring:message code="application.creditCard.info" /><br/>
		<form:label path="creditCard.name"><spring:message code="application.creditCard.name" /></form:label>
		<form:input type="input" path="creditCard.name" />
		<form:label path="creditCard.brand"><spring:message code="application.creditCard.brand" /></form:label>
			<form:select path="creditCard.brand" >
					<form:options items="${brand}"/>
			</form:select>
		
		<form:label path="creditCard.number"><spring:message code="application.creditCard.number" /></form:label>
		<form:input type="number" path="creditCard.number"/>
		
		<form:label path="creditCard.cvv"><spring:message code="application.creditCard.cvv" /></form:label>
		<form:input type="number" path="creditCard.cvv" />
		
		<form:label path="creditCard.month"><spring:message code="application.creditCard.month" /></form:label>
		<form:select path="creditCard.month" >
			<form:option value="JANUARY"><spring:message code="JANUARY" /></form:option>
			<form:option value="FEBRUARY"><spring:message code="FEBRUARY" /></form:option>
			<form:option value="MARCH"><spring:message code="MARCH" /></form:option>
			<form:option value="APRIL"><spring:message code="APRIL" /></form:option>
			<form:option value="MAY"><spring:message code="MAY" /></form:option>
			<form:option value="JUNE"><spring:message code="JUNE" /></form:option>
			<form:option value="JULY"><spring:message code="JULY" /></form:option>
			<form:option value="AUGUST"><spring:message code="AUGUST" /></form:option>
			<form:option value="SEPTEMBER"><spring:message code="SEPTEMBER" /></form:option>
			<form:option value="OCTOBER"><spring:message code="OCTOBER" /></form:option>
			<form:option value="NOVEMBER"><spring:message code="NOVEMBER" /></form:option>
			<form:option value="DECEMBER"><spring:message code="DECEMBER" /></form:option>
		</form:select>		
		
		<form:label path="creditCard.year"><spring:message code="application.creditCard.year" /></form:label>
		<form:input type="number" path="creditCard.year" />
		</div>
		
		<input type="submit" name="save" value="<spring:message code="customer.editApplication" />" />
		<input type="button" name="cancel" value="<spring:message code="application.cancel"/>" onclick="javascript:relativeRedir('application/customer/list.do');"/>

	</form:form>
</body>