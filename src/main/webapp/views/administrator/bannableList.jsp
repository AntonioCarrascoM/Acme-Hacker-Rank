<%--
 * bannableList.jsp
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

<spring:message code="administrator.name" var="name" />
<spring:message code="administrator.surnames" var="surnames" />
<spring:message code="administrator.vatNumber" var="vatNumber" />
<spring:message code="administrator.reminderMsg" var="msgReminder" />
<spring:message code="administrator.return" var="msgReturn" />
<spring:message code="administrator.actor.display" var="msgDisplay" />
<spring:message code="administrator.ban.title" var="msgBanUnban" />
<spring:message code="administrator.ban.text" var="msgBan" />
<spring:message code="administrator.unban.text" var="msgUnban" />
<spring:message code="administrator.selfBan.error" var="msgSelfBan"/>


<jsp:useBean id="now" class="java.util.Date"/>

<security:authorize access="hasRole('ADMIN')">

<%-- Listing grid --%>

<h3><jstl:out value="${msgReminder}"/></h3>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="actors" requestURI="${requestURI}" id="row">
	
	<%-- Attributes --%>
	
	<display:column property="name" title="${name}" sortable="true" />
	
	<display:column property="surnames" title="${surnames}" sortable="true" />
	
	<display:column property="vatNumber" title="${vatNumber}" sortable="true" />

	<%-- Links towards display, apply, edit and cancel views --%>
	
	<spring:url var="banUrl"
		value="administrator/ban.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	
	<spring:url var="displayUrl"
		value="administrator/actorDisplay.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	
	<display:column title="${msgDisplay}">
			<a href="${displayUrl}"><jstl:out value="${msgDisplay}" /></a>
	</display:column>

	<display:column title="${msgBanUnban}">
		<jstl:if test="${row.userAccount.banned eq false}">
			<a href="${banUrl}"><jstl:out value="${msgBan}" /></a>
		</jstl:if>
		<jstl:if test="${row.userAccount.banned eq true}">
			<a href="${banUrl}"><jstl:out value="${msgUnban}" /></a>
		</jstl:if>
	</display:column>



</display:table>
<a href="welcome/index.do"><jstl:out value="${msgReturn}" /></a>

</security:authorize>