<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="application.status" var="status" />
<spring:message code="application.position" var="position" />
<spring:message code="application.moment" var="moment" />
<spring:message code="application.edit" var="edit" />
<spring:message code="application.accept" var="accept" />
<spring:message code="application.reject" var="reject" />
<spring:message code="application.position.ticker" var="ticker" />
<spring:message code="application.hacker" var="hacker" />
<spring:message code="application.problem" var="problem" />
<spring:message code="application.create" var="create" />
<spring:message code="application.display" var="display" />
<spring:message code="application.problem.title" var="problemTitle" />
<spring:message code="application.formatDate" var="formatDate" />

<%-- List view --%>

<display:table pagesize="5" class="displaytag" name="applications"
	requestURI="${requestURI}" id="row">

	<%-- Attributes --%>
	
	<display:column title="${moment}" sortable="true" >
		<fmt:formatDate value="${row.moment}" pattern="${formatDate}" />
	</display:column>
	<display:column property="status" title="${status}" sortable="true" />
	<display:column property="position.ticker" title="${ticker}"/>
	<display:column property="position.title" title="${position}"/>
	<display:column property="problem.title" title="${problemTitle}"/>
	
	<security:authorize access="hasRole('COMPANY')"> 
	<display:column property="hacker.userAccount.username" title="${hacker}"/>
	</security:authorize>
	
	<%-- Edition button --%>

	<security:authorize access="hasRole('HACKER')"> 
	
	<spring:url var="editUrl" value="application/hacker/edit.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	
	<display:column title="${edit}">
	<jstl:if test="${row.status.name == 'PENDING' || row.status.name == 'SUBMITTED'}">
		<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		</jstl:if>
	</display:column> 
	
	<spring:url var="displayUrl" value="application/hacker/display.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column title="${display}">
		<a href="${displayUrl}"><jstl:out value="${display}" /></a>
	</display:column>
	
	</security:authorize>
	
	<security:authorize access="hasRole('COMPANY')">
	
	<spring:url var="acceptUrl" value="application/company/accept.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	
	<display:column title="${accept}">
	<jstl:if test="${row.status.name == 'SUBMITTED'}">
		<a href="${acceptUrl}"><jstl:out value="${accept}" /></a>
		</jstl:if>
	</display:column> 
	
	<spring:url var="rejectUrl" value="application/company/reject.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>
	
	<display:column title="${reject}">
	<jstl:if test="${row.status.name == 'SUBMITTED'}">
		<a href="${rejectUrl}"><jstl:out value="${reject}" /></a>
		</jstl:if>
	</display:column>
	
		
	<spring:url var="displayUrl" value="application/company/display.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column title="${display}">
		<a href="${displayUrl}"><jstl:out value="${display}" /></a>
	</display:column> 
	
	</security:authorize>
	
</display:table>

<security:authorize access="hasRole('HACKER')"> 

<spring:url var="createUrl" value="application/hacker/create.do" />
	<a href="${createUrl}"><jstl:out value="${create}" /></a>

</security:authorize>