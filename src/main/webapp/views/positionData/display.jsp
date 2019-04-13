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

<spring:message code="positionData.title"  var="msgTitle"/>
<spring:message code="positionData.description" var="msgDescription" />
<spring:message code="positionData.startDate" var="msgStartDate" />
<spring:message code="positionData.endDate" var="msgEndDate" />
<spring:message code="positionData.edit" var="msgEdit" />
<spring:message code="positionData.return" var="msgReturn" />
<spring:message code="positionData.formatDate" var="formatDate"/>



	<%-- For the curriculum in the list received as model, display the following information: --%>
	<jstl:out value="${msgTitle}" />:
	<jstl:out value="${positionData.title}" />
	<br />
	
	<jstl:out value="${msgDescription}" />:
	<jstl:out value="${positionData.description}" />
	<br />
	
	<jstl:out value="${msgStartDate}" />:
	<fmt:formatDate value="${positionData.startDate}" pattern="${formatDate}"/>
	<br />
	
	<jstl:out value="${msgEndDate}" />:
	<fmt:formatDate value="${positionData.endDate}" pattern="${formatDate}"/>
	<br />
	
	
	
	
<security:authorize access="hasRole('HACKER')">
	<spring:url var="editUrl"
		value="positionData/hacker/edit.do">
		<spring:param name="positionDataId"
			value="${positionData.id}"/>
	</spring:url>
	<a href="${editUrl}"><jstl:out value="${msgEdit}" /></a>
</security:authorize>

<a href="#"><jstl:out value="${msgReturn}" /></a>