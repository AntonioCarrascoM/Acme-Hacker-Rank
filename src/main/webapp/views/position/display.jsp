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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>

<spring:message code="position.ticker" var="ticker" />
<spring:message code="position.title" var="title" />
<spring:message code="position.description" var="description" />
<spring:message code="position.deadline" var="deadline" />
<spring:message code="position.requiredProfile" var="requiredProfile" />
<spring:message code="position.requiredSkills" var="requiredSkills" />
<spring:message code="position.requiredTech" var="requiredTech" />
<spring:message code="position.offeredSalary" var="offeredSalary" />
<spring:message code="position.cancelled" var="cancelled" />
<spring:message code="position.finalMode" var="finalMode" />
<spring:message code="position.finalMode.false" var="msgFalse" />
<spring:message code="position.finalMode.true" var="msgTrue" />
<spring:message code="position.company" var="company" />
<spring:message code="position.return" var="returnMsg" />
<spring:message code="position.formatDate" var="formatDate" />

<security:authorize access="hasRole('COMPANY')">
	
	<%-- Displays the information of the selected position --%>
	
	<jstl:out value="${ticker}" />:
	<jstl:out value="${position.ticker}" />
	<br />
	
	<jstl:out value="${title}" />:
	<jstl:out value="${position.title}"/>
	<br />
	
	<jstl:out value="${description}" />:
	<jstl:out value="${position.description}"/>
	<br />
	
	<jstl:out value="${deadline}" />:
	<fmt:formatDate value="${position.deadline}" pattern="${formatDate}"/>
	<br />
	
	<jstl:out value="${requiredProfile}" />:
	<jstl:out value="${position.requiredProfile}"/>
	<br />
	
	<jstl:out value="${requiredSkills}" />:
	<jstl:out value="${position.requiredSkills}"/>
	<br />
	
	<jstl:out value="${requiredTech}" />:
	<jstl:out value="${position.requiredTech}"/>
	<br />
	
	<jstl:out value="${offeredSalary}" />:
	<jstl:out value="${position.offeredSalary}"/>
	<br />
	
	<jstl:out value="${cancelled}" />:
	<jstl:if test="${position.cancelled eq false}">
		<jstl:out value="${msgFalse}" />
	</jstl:if>
	<jstl:if test="${position.cancelled eq true}">
		<jstl:out value="${msgTrue}" />
	</jstl:if>
	<br>
	
	<jstl:out value="${finalMode}" />:
	<jstl:if test="${position.finalMode eq false}">
		<jstl:out value="${msgFalse}" />
	</jstl:if>
	<jstl:if test="${position.finalMode eq true}">
		<jstl:out value="${msgTrue}" />
	</jstl:if>
	<br>
	
	<jstl:out value="${company}" />:
	<jstl:out value="${position.company.commercialName}"/>
	<br />
	<br>
	
	<a href="welcome/index.do"><jstl:out value="${returnMsg}" /></a>

</security:authorize>