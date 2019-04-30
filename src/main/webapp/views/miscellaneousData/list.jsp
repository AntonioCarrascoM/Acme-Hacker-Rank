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

<spring:message code="miscellaneousData.text" var="text" />
<spring:message code="miscellaneousData.attachments" var="attachments" />

<spring:message code="miscellaneousData.return" var="return" />
<spring:message code="miscellaneousData.details" var="details" />
<spring:message code="miscellaneousData.create" var="msgCreate" />

<%-- Listing grid --%>

<security:authorize access="hasRole('HACKER')">

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="miscellaneousDatas" requestURI="${requestURI}" id="row">
	
	<%-- Attributes --%>
	
	
	<display:column property="text" title="${text}" sortable="true" />
	<display:column property="attachments" title="${attachments}" sortable="true" />
	
	<%-- Links towards display, apply, edit and cancel views --%>
	
	<spring:url var="displayUrl"
		value="miscellaneousData/hacker/display.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>

	<display:column title="${details}">
	<a href="${displayUrl}"><jstl:out value="${details}" /></a>
	</display:column>

</display:table>

	<spring:url var="createUrl"
		value="miscellaneousData/hacker/create.do">
		<spring:param name="varId" value="${curriculum.id}" />
	</spring:url>
	<a href="${createUrl}"><jstl:out value="${msgCreate}" /></a>
</security:authorize>
