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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<%-- Stored message variables --%>

<spring:message code="application.save" var="save" />
<spring:message code="application.cancel" var="cancel" />
<spring:message code="application.problem" var="problem" />
<spring:message code="application.problem.title" var="problemTitle" />
<spring:message code="application.problem.statement" var="problemStatement" />
<spring:message code="application.problem.hint" var="problemHint" />
<spring:message code="application.problem.attachments" var="problemAttachments" />
<spring:message code="application.answerMoment" var="answerMoment" />
<spring:message code="application.answer" var="answer" />

<form:form action="${requestURI}" modelAttribute="application">

	<%-- Hidden attributes --%>

	<form:hidden path="id" />
	
	<jstl:if test="${application.id != 0}">
	
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
	</jstl:if>
	
	<%-- Hidden attributes --%>
	
	<security:authorize access="hasRole('HACKER')">
	
	<jstl:if test="${application.id == 0}">
		<acme:select code="application.curriculum" path="curriculum"
			items="${curriculums}" itemLabel="id" id="curriculums" />
		<br />
		<acme:select code="application.position" path="position"
			items="${positions}" itemLabel="ticker" id="positions" />
		<br />
	</jstl:if>
	<br />
	
	<jstl:if test="${application.id != 0}">
	<fieldset>
		<legend><jstl:out value="${answer}"/></legend>
	<acme:textarea code="application.answerDescription" path="answerDescription"/>
		 <br />
	<acme:textbox code="application.answerLink" path="answerLink"/>
		
	</fieldset>
	</jstl:if>

	</security:authorize>
 <br />
	<%-- Buttons --%>
		
		<acme:submit code="application.save" name="save"/>
		
		<acme:cancel code="application.cancel" url ="/application/hacker/list.do" />

</form:form>