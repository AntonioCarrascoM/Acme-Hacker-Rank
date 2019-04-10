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

<spring:message code="company.commercialName" var="commercialName" />
<spring:message code="company.email" var="email" />
<spring:message code="company.phone" var="phone" />
<spring:message code="company.address" var="address" />
<spring:message code="company.positions" var="positions" />
<spring:message code="company.display" var="display" />
<spring:message code="company.return" var="msgReturn" />

<jsp:useBean id="now" class="java.util.Date"/>

<%-- Listing grid --%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="companies" requestURI="${requestURI}" id="row">
	
	<%-- Attributes --%>
	
	<display:column property="commercialName" title="${commercialName}" sortable="true" />
	<display:column property="email" title="${email}" sortable="true" />
	<display:column property="phone" title="${phone}" sortable="true" />
	<display:column property="address" title="${address}" sortable="true" />

	<%-- Links towards display, apply, edit and cancel views --%>
	
	<spring:url var="positionsUrl"
		value="position/listByCompany.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	
	<spring:url var="displayUrl"
		value="company/display.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	

	<display:column>
			<a href="${positionsUrl}"><jstl:out value="${positions}" /></a>
	</display:column>
	
	<display:column>
			<a href="${displayUrl}"><jstl:out value="${display}" /></a>
	</display:column>



</display:table>
<a href="welcome/index.do"><jstl:out value="${msgReturn}" /></a>

