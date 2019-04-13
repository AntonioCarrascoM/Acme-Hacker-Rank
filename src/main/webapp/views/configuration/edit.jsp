<%--
 * edit.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Stored message variables --%>

<spring:message code="configuration.systemName" var="systemName" />
<spring:message code="configuration.banner" var="banner" />
<spring:message code="configuration.welcomeEN" var="welcomeEN" />
<spring:message code="configuration.welcomeES" var="welcomeES" />
<spring:message code="configuration.spamWords" var="spamWords" />
<spring:message code="configuration.countryCode" var="countryCode" />
<spring:message code="configuration.expireFinderMinutes" var="expireFinderMinutes" />
<spring:message code="configuration.maxFinderResults" var="maxFinderResults" />
<spring:message code="configuration.commasMessage" var="commasMessage" />
<spring:message code="configuration.noteBelow" var="noteBelow" />

<spring:message code="configuration.save" var="save" />
<spring:message code="configuration.cancel" var="cancel" />

<security:authorize access="hasRole('ADMIN')">

<form:form action="${requestURI}" modelAttribute="configuration">

	<%-- Form fields --%>
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
		
	<acme:textbox code="configuration.systemName" path="systemName"/>
		 <br />
	<acme:textbox code="configuration.banner" path="banner"/>
		 <br />
	<acme:textarea code="configuration.welcomeEN" path="welcomeEN"/>
		 <br />
	<acme:textarea code="configuration.welcomeES" path="welcomeES"/>
		 <br />
	
	<acme:textbox code="configuration.countryCode" path="countryCode"/>
		 <br />
	<acme:textbox code="configuration.expireFinderMinutes" path="expireFinderMinutes"/>
		 <br />
	<acme:textbox code="configuration.maxFinderResults" path="maxFinderResults"/>
		 <br />
	<acme:textarea code="configuration.spamWords" path="spamWords" placeholder="configuration.noteBelow"/>
		 <br />
	<%-- Buttons --%>
	
	<jstl:out value="${commasMessage}" />
		<br /><br/>
	
		<%--<input type="submit" name="save" value="${save}"> --%>

	<acme:submit name="save" code="configuration.save" />
		
	<%--<input type="button" name="cancel" value="${cancel}"
		onclick="javascript: relativeRedir('configuration/administrator/display.do');" />--%>

	<acme:cancel url="configuration/administrator/display.do" code="configuration.cancel" />

</form:form>

</security:authorize>