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
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<%-- Stored message variables --%>

<spring:message code="curriculum.creationMsg" var="creationMsg" />
<spring:message code="curriculum.confirm" var="confirm" />
<spring:message code="curriculum.save" var="save" />
<spring:message code="curriculum.cancel" var="cancel" />

<security:authorize access="hasRole('HACKER')">

	<form:form id="form" action="${requestURI}"
		modelAttribute="curriculum">

	<%-- Forms --%>

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="hacker" />
	

	<jstl:out value="${creationMsg}" />

	<%-- Buttons --%>

	<input type="submit" name="save" value="${save}"
		onclick="return confirm('${confirm}')" />&nbsp;
		
		<acme:cancel url="welcome/index.do" code="curriculum.cancel" />


	</form:form>
</security:authorize>