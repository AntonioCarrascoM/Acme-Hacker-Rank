
<%--
 * create.jsp
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Stored message variables --%>
<security:authorize access="hasRole('HACKER')">

	<spring:message code="finder.keyWord" var="msgKeyWord" />
	<spring:message code="finder.minSalary" var="msgMinSalary" />
	<spring:message code="finder.maxSalary" var="msgMaxSalary" />
	<spring:message code="finder.specificDeadline" var="msgSpecificDeadline" />
	<spring:message code="finder.save" var="msgSave" />
	<spring:message code="finder.cancel" var="msgCancel" />
	<spring:message code="finder.clear" var="msgClear" />
	
	<spring:message code="finder.position.ticker" var="ticker" />
	<spring:message code="finder.position.title" var="title" />
	<spring:message code="finder.position.deadline" var="deadline" />
	<spring:message code="finder.company.commercialName" var="commercialName" />
	<spring:message code="finder.position.display" var="display" />
	<spring:message code="finder.position.company" var="company" />
	
	<spring:message code="finder.position.formatDate" var="formatDate" />


	<form:form action="${requestURI}" modelAttribute="finder">

	<%-- Form fields --%>
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="positions" />
	<form:hidden path="moment" />

	
	<acme:textbox code="finder.keyWord" path="keyWord" />
	
	<acme:textbox code="finder.specificDeadline" path="specificDeadline" placeholder="finder.ph"/>
	
	<acme:textnumber code="finder.minSalary" path="minSalary" placeholder="sal.ph" />
	
	<acme:textnumber code="finder.maxSalary" path="maxSalary" placeholder="sal.ph" />
	
	<br />
	
	<%-- Buttons --%>
	
	<acme:submit name="save" code="finder.save" />
	<acme:submit name="clear" code="finder.clear" />
	<acme:cancel url="welcome/index.do" code="finder.cancel" />

	</form:form>
	<br />
	
	
	<%--   ---------------------------------RESULTS-----------------------------------------  --%>
	
	<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="positions" requestURI="${requestURI}" id="row">
	
	<%-- Attributes --%>
	
	<display:column property="ticker" title="${ticker}" sortable="true" />
	<display:column property="company.commercialName" title="${commercialName}" sortable="true" />
	<display:column property="title" title="${title}" sortable="true" />
	
	<display:column title="${deadline}" sortable="true">
		<fmt:formatDate value="${row.deadline}" pattern="${formatDate}"/>
	</display:column>
	
	<%-- Links towards display, apply, edit and cancel views --%>

	<spring:url var="companyUrl"
		value="company/display.do">
		<spring:param name="varId"
			value="${row.company.id}"/>
	</spring:url>
	
	<spring:url var="displayUrl"
		value="position/display.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>

	<display:column title="${company}">
			<a href="${companyUrl}"><jstl:out value="${company}" /></a>
	</display:column>
	
	<display:column title="${display}">
			<a href="${displayUrl}"><jstl:out value="${display}" /></a>
	</display:column>
	
	</display:table>
	
</security:authorize>