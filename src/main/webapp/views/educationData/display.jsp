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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>

<spring:message code="educationData.degree"  var="msgDegree"/>
<spring:message code="educationData.institution" var="msgInstitution" />
<spring:message code="educationData.mark" var="msgMark" />

<spring:message code="educationData.startDate" var="msgStartDate" />
<spring:message code="educationData.endDate" var="msgEndDate" />
<spring:message code="educationData.edit" var="msgEdit" />
<spring:message code="educationData.return" var="msgReturn" />
<spring:message code="educationData.formatDate" var="formatDate"/>



	<%-- For the curriculum in the list received as model, display the following information: --%>
	<jstl:out value="${msgDegree}" />:
	<jstl:out value="${educationData.degree}" />
	<br />
	
	<jstl:out value="${msgInstitution}" />:
	<jstl:out value="${educationData.institution}" />
	<br />
	
	<jstl:out value="${msgMark}" />:
	<jstl:out value="${educationData.mark}" />
	<br />
	
	<jstl:out value="${msgStartDate}" />:
	<fmt:formatDate value="${educationData.startDate}" pattern="${formatDate}"/>
	<br />
	
	<jstl:out value="${msgEndDate}" />:
	<fmt:formatDate value="${educationData.endDate}" pattern="${formatDate}"/>
	<br />
	
	
	
	
<security:authorize access="hasRole('HACKER')">
	<spring:url var="editUrl"
		value="educationData/hacker/edit.do">
		<spring:param name="educationDataId"
			value="${educationData.id}"/>
	</spring:url>
	<a href="${editUrl}"><jstl:out value="${msgEdit}" /></a>
</security:authorize>

<a href="#"><jstl:out value="${msgReturn}" /></a>