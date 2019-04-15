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
<spring:message code="positionData.edit" var="edit" />
<spring:message code="positionData.create" var="create" />
<spring:message code="positionData.return" var="return" />
<spring:message code="positionData.details" var="details" />

<%-- Listing grid --%>
<security:authorize access="hasRole('HACKER')">

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="positionDatas" requestURI="${requestURI}" id="row">
	
	<%-- Attributes --%>
	
	
	<display:column property="title" title="${title}"/>
	
	<display:column property="description" title="${description}"  />


	<%-- Links towards display, apply, edit and cancel views --%>
	
	<spring:url var="editUrl"
		value="positionData/hacker/edit.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>

	<display:column title="${edit}">
	<a href="${editUrl}"><jstl:out value="${edit}" /></a>
	</display:column>
	
	<spring:url var="displayUrl"
		value="positionData/hacker/display.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>

	<display:column title="${details}">
	<a href="${displayUrl}"><jstl:out value="${details}" /></a>
	</display:column>
	
	

</display:table>

	<spring:url var="createUrl" value="positionData/hacker/create.do">
		<spring:param name="varId" value="${curriculum.id}" />
	</spring:url>
	<a href="${createUrl}"><jstl:out value="${create}"/></a>
	
</security:authorize>
