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

<spring:message code="miscellaneousData.text"  var="msgText"/>
<spring:message code="miscellaneousData.attachments" var="msgAttachments" />
<spring:message code="miscellaneousData.edit" var="msgEdit" />
<spring:message code="miscellaneousData.return" var="msgReturn" />

<security:authorize access="hasRole('HACKER')">

	<%-- For the curriculum in the list received as model, display the following information: --%>
	<jstl:out value="${msgText}" />:
	<jstl:out value="${miscellaneousData.text}" />
	<br />
	
	<jstl:out value="${msgAttachments}" />:
	<jstl:out value="${miscellaneousData.attachments}" />
	<br />
	
</security:authorize>
