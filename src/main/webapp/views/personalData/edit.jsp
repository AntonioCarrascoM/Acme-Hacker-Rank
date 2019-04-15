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
<spring:message code="personalData.linkedInProfile"
	var="linkedInProfile" />
	<spring:message code="personalData.phone.pattern1" var="phonePattern1" />
<spring:message code="personalData.phone.pattern2" var="phonePattern2" />
<spring:message code="personalData.phone.warning" var="phoneWarning" />
<spring:message code="personalData.save" var="save" />
<spring:message code="personalData.delete" var="delete" />
<spring:message code="personalData.cancel" var="cancel" />
<spring:message code="personalData.confirm" var="confirm" />

<security:authorize access="hasRole('HACKER')">

	<form:form action="${requestURI}" modelAttribute="personalData">

		<%-- Forms --%>

		<form:hidden path="id" />
		<form:hidden path="curriculum" />
		
		<acme:textbox code="personalData.fullName" path="fullName" />
		<br />
		
		<acme:textarea code="personalData.statement" path="statement" />
		<br />

		<acme:textbox placeholder="personalData.phnumber"
			code="personalData.phoneNumber" path="phoneNumber" />*
		<br />


		<acme:textbox code="personalData.gitHubProfile" path="gitHubProfile" />
		<br />

		<acme:textbox code="personalData.linkedInProfile"
			path="linkedInProfile" />
		<br />
		
		<jstl:out value="${phoneWarning}" />
		<br />
		<jstl:out value="${phonePattern1}" />
		<br />
		<jstl:out value="${phonePattern2}" />
		<br />
		<br />

		<%-- Buttons --%>

		<acme:submit  name="save" code="personalData.save"/>

		<jstl:if test="${personalData.id!=0}">
			<input type="submit" name="delete" value="${delete}"
				onclick="return confirm('${confirm}')" />&nbsp;
	</jstl:if>

		<acme:cancel url="curriculum/hacker/list.do" code="personalData.cancel" />

	</form:form>

</security:authorize>


