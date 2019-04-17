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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>

<spring:message code="application.status" var="status" />
<spring:message code="application.position" var="position" />
<spring:message code="application.moment" var="moment" />
<spring:message code="application.hacker" var="hacker" />
<spring:message code="application.answer" var="answer" />
<spring:message code="application.answerDescription" var="answerDescription" />
<spring:message code="application.answerLink" var="answerLink" />
<spring:message code="application.answerMoment" var="answerMoment" />
<spring:message code="application.problem" var="problem" />
<spring:message code="application.problem.title" var="problemTitle" />
<spring:message code="application.problem.statement" var="problemStatement" />
<spring:message code="application.problem.hint" var="problemHint" />
<spring:message code="application.problem.attachments" var="problemAttachments" />
<spring:message code="application.formatDate" var="formatDate" />


<%-- For the selected application, display the following information: --%>


	<jstl:out value="${status}" />:
	<jstl:out value="${application.status}"/>
	<br />
	
	<jstl:out value="${position}" />:
	<jstl:out value="${application.position.title}"/>
	<br />
	
	<jstl:out value="${moment}" />:
	<fmt:formatDate value="${application.moment}" pattern="${formatDate}" />
	<br />
	
	<jstl:out value="${hacker}" />:
	<jstl:out value="${application.hacker.name} "/><jstl:out value="${application.hacker.surnames}"/>
	<br />
	<br />
	
	<fieldset>
		<legend><jstl:out value="${problem}"/></legend>
	
	<jstl:out value="${problemTitle}" />:
	<jstl:out value="${application.problem.title}"/>
	<br />
	
	<jstl:out value="${problemStatement}" />:
	<jstl:out value="${application.problem.statement}"/>
	<br />
	
	<jstl:out value="${problemHint}" />:
	<jstl:out value="${application.problem.hint}"/>
	<br />
	
	<jstl:out value="${problemAttachments}" />:
	<jstl:out value="${application.problem.attachments}"/>
	<br />
	</fieldset>
	<br />
	
	<fieldset>
		<legend><jstl:out value="${answer}"/></legend>
		
	<jstl:if test="${application.status != 'PENDING'}" >
	
	<jstl:out value="${answerDescription}" />:
	<jstl:out value="${application.answerDescription}"/>
	<br />
	
	<jstl:out value="${answerLink}" />:
	<jstl:out value="${application.answerLink}"/>
	<br />
	
	<jstl:out value="${answerMoment}" />:
	<fmt:formatDate value="${application.answerMoment}" pattern="${formatDate}" />
	<br />
	
	</jstl:if>
</fieldset>