<%--
 * display.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>

<spring:message code="message.sent" var="sent" />
<spring:message code="message.sender" var="sender" />
<spring:message code="message.recipient" var="recipient" />
<spring:message code="message.subject" var="subject" />
<spring:message code="message.tags" var="tags" />
<spring:message code="message.body" var="body" />
<spring:message code="message.return" var="returnMsg" />
<spring:message code="message.formatDate" var="formatDate" />

	<%-- For the selected message, display the following information: --%>
	
	<jstl:out value="${sent}" />:
	<fmt:formatDate value="${msg.sent}" pattern="${formatDate}"/>
	<br />
	
	<jstl:out value="${sender}" />:
	<jstl:out value="${msg.sender.name}" />&nbsp;<jstl:out value="${msg.sender.surnames}" />
	<br />
		
	<jstl:out value="${recipient}" />:
	<jstl:out value="${msg.recipient.name}" />&nbsp;<jstl:out value="${msg.recipient.surnames}" />
	<br />
	
	<jstl:out value="${subject}" />:
	<jstl:out value="${msg.subject}" />
	<br />
	
	<jstl:out value="${body}" />:
	<jstl:out value="${msg.body}" />
	<br />
	
	<jstl:out value="${tags}" />:
	<jstl:out value="${msg.tags}" />
	<br />
	
	