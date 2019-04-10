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

<spring:message code="problem.title" var="title" />
<spring:message code="problem.statement" var="statement" />
<spring:message code="problem.hint" var="hint" />
<spring:message code="problem.company.commercialName" var="commercialName" />
<spring:message code="problem.display" var="display" />
<spring:message code="problem.return" var="msgReturn" />
<spring:message code="problem.confirm" var="msgConfirm" />
<spring:message code="problem.edit" var="edit" />
<spring:message code="problem.delete" var="delete" />

<jsp:useBean id="now" class="java.util.Date"/>

<%-- Listing grid --%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="problems" requestURI="${requestURI}" id="row">
	
	<%-- Attributes --%>
	
	<display:column property="title" title="${title}" sortable="true" />
	<display:column property="statement" title="${statement}" sortable="true" />
	<display:column property="hint" title="${hint}" sortable="true" />
	<display:column property="company.commercialName" title="${commercialName}" sortable="true" />

	<%-- Links towards display, apply, edit and cancel views --%>
	
	<spring:url var="editUrl"
		value="problem/company/edit.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	
	<spring:url var="deleteUrl"
		value="problem/company/delete.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	
	<spring:url var="displayUrl"
		value="problem/display.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	
	<display:column>
		<jstl:if test="${row.finalMode eq false}">
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		</jstl:if>
	</display:column>
	
	<display:column>
		<jstl:if test="${row.finalMode eq false}">
			<a href="${deleteUrl}" onclick="return confirm('${msgConfirm}')"><jstl:out value="${delete}" /></a>
		</jstl:if>
	</display:column>
	
	<display:column>
			<a href="${displayUrl}"><jstl:out value="${display}" /></a>
	</display:column>

</display:table>
<a href="welcome/index.do"><jstl:out value="${msgReturn}" /></a>

