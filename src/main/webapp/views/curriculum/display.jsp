<%--
 * display.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<%-- Stored message variables --%>

<spring:message code="curriculum.personalData" var="msgPersonalData" />
<spring:message code="curriculum.positionData" var="msgPositionData" />
<spring:message code="curriculum.educationData" var="msgEducationData" />
<spring:message code="curriculum.miscellaneousData" var="msgMiscellaneousData" />
<spring:message code="curriculum.createPersonalData" var="msgCreatePersonalData" />
<spring:message code="curriculum.return" var="msgReturn" />
<spring:message code="curriculum.delete" var="msgDelete" />
<spring:message code="curriculum.confirm" var="msgConfirm" />


	<%-- For the curriculum in the list received as model, display the following information: --%>
	
	
	<spring:url var="personalDataUrl"
		value="personalData/hacker/display.do">
		<spring:param name="varId"
			value="${curriculum.id}"/>
		</spring:url>
	<a href="${personalDataUrl}"><jstl:out value="${msgPersonalData}" /></a>
	<br />
	
	<spring:url var="positionDataUrl"
		value="positionData/hacker/list.do">
		<spring:param name="varId"
			value="${curriculum.id}"/>
		</spring:url>

	<a href="${positionDataUrl}"><jstl:out value="${msgPositionData}" /></a>
	<br />
	
	<spring:url var="educationDataUrl"
		value="educationData/hacker/list.do">
		<spring:param name="varId"
			value="${curriculum.id}"/>
		</spring:url>

	<a href="${educationDataUrl}"><jstl:out value="${msgEducationData}" /></a>
	<br />
	
	<spring:url var="miscellaneousDataUrl"
		value="miscellaneousData/hacker/list.do">
		<spring:param name="varId"
			value="${curriculum.id}"/>
		</spring:url>

	<a href="${miscellaneousDataUrl}"><jstl:out value="${msgMiscellaneousData}" /></a>
	<br />

	

	
	
<security:authorize access="hasRole('HACKER')">

	<spring:url var="deleteUrl" value="curriculum/hacker/delete.do">
			<spring:param name="curriculumId" value="${curriculum.id}" />
		</spring:url>
		<a href="${deleteUrl}" onclick="return confirm('${msgConfirm}')" ><jstl:out value="${msgDelete}" /></a>
	
</security:authorize>
<br />
<a href="#"><jstl:out value="${msgReturn}" /></a>