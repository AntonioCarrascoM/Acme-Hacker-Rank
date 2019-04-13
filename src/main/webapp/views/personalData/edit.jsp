<%--
 * edit.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
	


<%-- Stored message variables --%>

<spring:message code="personalData.fullName" var="fullName" />
<spring:message code="personalData.statement" var="statement" />
<spring:message code="personalData.phoneNumber" var="phoneNumber" />
<spring:message code="personalData.gitHubProfile" var="gitHubProfile" />
<spring:message code="personalData.linkedInProfile" var="linkedInProfile" />
<spring:message code="personalData.save" var="save" />
<spring:message code="personalData.delete" var="delete" />
<spring:message code="personalData.cancel" var="cancel" />
<spring:message code="personalData.confirm" var="confirm" />

<security:authorize access="hasRole('HACKER')">

	<form:form action="${requestURI}" modelAttribute="personalData">

		<%-- Forms --%>

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="curriculum" />
		
		
		<acme:textbox code="personalData.fullName" path="fullName"/>
		<acme:textbox code="personalData.statement" path="statement"/>
		
		<acme:textbox 
	     placeholder="personalData.phnumber"
		 code = "personalData.phoneNumber" 
		 path="phoneNumber"/>
		 <br/>
		 
		 
		 <acme:textbox 
	     placeholder="personalData.phgithub"
		 code = "personalData.gitHubProfile" 
		 path="gitHubProfile"/>
		 <br/>
		 
		 <acme:textbox 
	     placeholder="personalData.phlinkedIn"
		 code = "personalData.linkedInProfile" 
		 path="linkedInProfile"/>
		 <br/>
		
			

	



		<%-- Buttons --%>

		<acme:submit code="personalData.save" name="save" />

		
	<jstl:if test="${personalData.id!=0}">
			<input type="submit" name="delete" value="${delete}"
				onclick="return confirm('${confirm}')" />&nbsp;
	</jstl:if>

	<acme:cancel url="welcome/index.do" code="personalData.cancel" />

		

</form:form>

</security:authorize>


