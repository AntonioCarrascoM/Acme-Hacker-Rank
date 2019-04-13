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

<spring:message code="positionData.title" var="title" />
<spring:message code="positionData.description" var="description" />

<spring:message code="positionData.return" var="return" />
<spring:message code="positionData.details" var="details" />
<spring:message code="positionData.create" var="msgCreate" />

<%-- Listing grid --%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="positionDatas" requestURI="${requestURI}" id="row">
	
	<%-- Attributes --%>
	
	
	<display:column property="title" title="${title}" sortable="true" />
	
	<display:column property="description" title="${description}" sortable="true" />


	<%-- Links towards display, apply, edit and cancel views --%>
	
	<spring:url var="displayUrl"
		value="positionData/hacker/display.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>

	<display:column title="${details}">
	<a href="${displayUrl}"><jstl:out value="${details}" /></a>
	</display:column>

</display:table>

<security:authorize access="hasRole('HACKER')">
	<spring:url var="createUrl"
		value="positionData/hacker/create.do">
	</spring:url>
	<a href="${createUrl}"><jstl:out value="${msgCreate}" /></a>
</security:authorize>
