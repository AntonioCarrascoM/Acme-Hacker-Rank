<%--
 * display.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%-- Stored message variables --%>

<spring:message code="configuration.systemName" var="systemName" />
<spring:message code="configuration.banner" var="banner" />
<spring:message code="configuration.welcomeEN" var="welcomeEN" />
<spring:message code="configuration.welcomeES" var="welcomeES" />
<spring:message code="configuration.spamWords" var="spamWords" />
<spring:message code="configuration.countryCode" var="countryCode" />
<spring:message code="configuration.expireFinderMinutes" var="expireFinderMinutes" />
<spring:message code="configuration.maxFinderResults" var="maxFinderResults" />

<spring:message code="configuration.return" var="returnMsg" />
<spring:message code="configuration.edit" var="edit" />

<security:authorize access="hasRole('ADMIN')">

	<%-- For the selected task in the list received as model, display the following information: --%>
	
	<jstl:out value="${systemName}" />:
	<jstl:out value="${configuration.systemName}" />
	<br />
		
	<jstl:out value="${banner}" />:
	<a href="${configuration.banner}"><jstl:out value="${configuration.banner}" /></a>
	<br />
		
	<jstl:out value="${welcomeEN}" />:
	<jstl:out value="${configuration.welcomeEN}" />
	<br />
	
	<jstl:out value="${welcomeES}" />:
	<jstl:out value="${configuration.welcomeES}" />
	<br />
	
	<jstl:out value="${countryCode}" />:
	<jstl:out value="${configuration.countryCode}" />
	<br />
	
	<jstl:out value="${expireFinderMinutes}" />:
	<jstl:out value="${configuration.expireFinderMinutes}" />:
	<br />

	
	<jstl:out value="${maxFinderResults}" />:
	<jstl:out value="${configuration.maxFinderResults}" />
	<br />
	
	<jstl:out value="${spamWords}" />:
	<jstl:out value="${configuration.spamWords}" />
	<br />
	
	<br />
	
	<spring:url var="editUrl"
		value="configuration/administrator/edit.do">
		<spring:param name="varId"
			value="${configuration.id}"/>
	</spring:url>

	<a href="${editUrl}"><jstl:out value="${edit}" /></a>&nbsp;

	<a href="welcome/index.do"><jstl:out value="${returnMsg}" /></a>

</security:authorize>