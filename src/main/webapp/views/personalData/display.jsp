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

<spring:message code="personalData.fullName"  var="msgFullName"/>
<spring:message code="personalData.statement" var="msgStatement" />
<spring:message code="personalData.phoneNumber" var="msgPhoneNumber" />
<spring:message code="personalData.gitHubProfile" var="msgGitHubProfile" />
<spring:message code="personalData.linkedInProfile" var="msgLinkedInProfile" />
<spring:message code="personalData.edit" var="msgEdit" />
<spring:message code="personalData.return" var="msgReturn" />


<security:authorize access="hasRole('HACKER')">
	<%-- For the curriculum in the list received as model, display the following information: --%>
	<jstl:out value="${msgFullName}" />:
	<jstl:out value="${personalData.fullName}" />
	<br />
	
	<jstl:out value="${msgStatement}" />:
	<jstl:out value="${personalData.statement}" />
	<br />
	
	<jstl:out value="${msgPhoneNumber}" />:
	<jstl:out value="${personalData.phoneNumber}" />
	<br />
	
	<jstl:out value="${msgGitHubProfile}" />:
	<jstl:out value="${personalData.gitHubProfile}" />
	<br />
	
	<jstl:out value="${msgLinkedInProfile}" />:
	<jstl:out value="${personalData.linkedInProfile}" />
	<br />
	

	<spring:url var="editUrl"
		value="personalData/hacker/edit.do">
		<spring:param name="varId"
			value="${personalData.id}"/>
	</spring:url>
	
	<br>
	<a href="${editUrl}"><jstl:out value="${msgEdit}" /></a>
</security:authorize>
