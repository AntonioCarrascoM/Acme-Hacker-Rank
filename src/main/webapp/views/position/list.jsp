<%--
 * suspiciousList.jsp
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

<spring:message code="position.title" var="title" />
<spring:message code="position.statement" var="statement" />
<spring:message code="position.hint" var="hint" />
<spring:message code="position.company.commercialName" var="commercialName" />
<spring:message code="position.display" var="display" />
<spring:message code="position.company" var="company" />
<spring:message code="position.return" var="msgReturn" />
<spring:message code="position.edit" var="edit" />
<spring:message code="position.delete" var="delete" />
<spring:message code="position.confirm" var="msgConfirm" />
<spring:message code="position.cancel" var="cancel" />
<spring:message code="position.formatDate" var="formatDate" />

<jsp:useBean id="now" class="java.util.Date"/>

<%-- Listing grid --%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="positions" requestURI="${requestURI}" id="row">
	
	<%-- Attributes --%>
	
	<display:column property="ticker" title="${ticker}" sortable="true" />
	<display:column property="company.commercialName" title="${commercialName}" sortable="true" />
	<display:column property="title" title="${title}" sortable="true" />
	
	<display:column title="${hint}" sortable="true">
		<fmt:formatDate value="${row.deadline}" pattern="${formatDate}"/>
	</display:column>
	
	<%-- Links towards display, apply, edit and cancel views --%>
	
	<spring:url var="editUrl"
		value="position/company/edit.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	
	<spring:url var="deleteUrl"
		value="position/company/delete.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	
	<spring:url var="cancelUrl"
		value="position/company/cancel.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	
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
	
	<security:authorize access="hasRole('COMPANY')">
	<display:column title="${cancel}">
		<jstl:if test="${row.cancelled eq false}">
			<a href="${cancelUrl}"><jstl:out value="${cancel}" /></a>
		</jstl:if>
	</display:column>
	
	<display:column title="${edit}">
		<jstl:if test="${row.finalMode eq false}">
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		</jstl:if>
	</display:column>
	
	<display:column title="${delete}">
		<jstl:if test="${row.finalMode eq false}">
			<a href="${deleteUrl}" onclick="return confirm('${msgConfirm}')"><jstl:out value="${delete}" /></a>
		</jstl:if>
	</display:column>
	</security:authorize>

</display:table>
<a href="welcome/index.do"><jstl:out value="${msgReturn}" /></a>

