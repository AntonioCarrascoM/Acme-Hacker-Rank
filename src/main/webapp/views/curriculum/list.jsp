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

<spring:message code="curriculum.id" var="id" />
<spring:message code="curriculum.return" var="return" />
<spring:message code="curriculum.details" var="details" />
<spring:message code="curriculum.create" var="msgCreate" />
<spring:message code="curriculum.delete" var="delete" />
<spring:message code="curriculum.confirm.delete" var="confirm" />

<security:authorize access="hasRole('HACKER')">

<%-- Listing grid --%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="curriculums" requestURI="${requestURI}" id="row">
	
	<%-- Attributes --%>
	
	
	<display:column property="id" title="${id}"/>
	
	<%-- Links towards display, apply, edit and cancel views --%>
	
	<spring:url var="displayUrl"
		value="curriculum/hacker/display.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>

	<display:column title="${details}">
	<a href="${displayUrl}"><jstl:out value="${details}" /></a>
	</display:column>
	
	<spring:url var="deleteUrl"
		value="curriculum/hacker/delete.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	
	<display:column title="${delete}">
			<a href="${deleteUrl}" onclick="return confirm('${confirm}')" ><jstl:out value="${delete}" /></a>
		</display:column>

</display:table>


	<spring:url var="createUrl"
		value="curriculum/hacker/create.do">
	</spring:url>
	<a href="${createUrl}"><jstl:out value="${msgCreate}" /></a>
</security:authorize>
