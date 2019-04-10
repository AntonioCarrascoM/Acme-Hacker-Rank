<%--
 * display.jsp
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
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<%-- Stored message variables --%>

<spring:message code="problem.title" var="title" />
<spring:message code="problem.statement" var="statement" />
<spring:message code="problem.hint" var="hint" />
<spring:message code="problem.attachments" var="attachments" />
<spring:message code="problem.finalMode" var="finalMode" />
<spring:message code="problem.finalMode.false" var="msgFalse" />
<spring:message code="problem.finalMode.true" var="msgTrue" />
<spring:message code="problem.company" var="company" />
<spring:message code="problem.return" var="returnMsg" />

<security:authorize access="hasRole('COMPANY')">
	
	<%-- Displays the information of the selected problem --%>
	
	<jstl:out value="${title}" />:
	<jstl:out value="${problem.title}" />
	<br />
	
	<jstl:out value="${statement}" />:
	<jstl:out value="${problem.statement}"/>
	<br />
	
	<jstl:out value="${hint}" />:
	<jstl:out value="${problem.hint}"/>
	<br />
	
	<jstl:out value="${attachments}" />:
	<jstl:out value="${problem.attachments}"/>
	<br />
	
	<jstl:out value="${finalMode}" />:
	<jstl:if test="${problem.finalMode eq false}">
		<jstl:out value="${msgFalse}" />
	</jstl:if>
	<jstl:if test="${problem.finalMode eq true}">
		<jstl:out value="${msgTrue}" />
	</jstl:if>
	<br>
	
	<jstl:out value="${company}" />:
	<jstl:out value="${problem.company.commercialName}"/>
	<br />
	<br>
	
	<a href="welcome/index.do"><jstl:out value="${returnMsg}" /></a>

</security:authorize>