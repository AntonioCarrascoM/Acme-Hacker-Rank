<%--
 * list.jsp
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%-- Stored message variables --%>

<spring:message code="socialProfile.create" var="create" />
<spring:message code="socialProfile.edit" var="edit" />
<spring:message code="socialProfile.nick" var="nick" />
<spring:message code="socialProfile.socialNetwork" var="socialNetwork" />
<spring:message code="socialProfile.profileLink" var="profileLink" />
<spring:message code="socialProfile.save" var="save" />
<spring:message code="socialProfile.delete" var="delete" />
<spring:message code="socialProfile.create" var="create" />
<spring:message code="socialProfile.confirm.delete" var="confirm" />
<spring:message code="socialProfile.cancel" var="cancel" />

<%-- Listing grid --%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="socialProfiles" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column property="nick" title="${nick}" sortable="true" />

	<display:column property="socialNetwork" title="${socialNetwork}" sortable="true" />
	
	<display:column property="profileLink" title="${profileLink}" sortable="true" />

	<%-- Links towards display, apply, edit and cancel views --%>

		<spring:url var="editUrl" value="socialProfile/edit.do">
			<spring:param name="socialProfileId" value="${row.id}" />
		</spring:url>

		<display:column title="${edit}">
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		</display:column>
		
		<spring:url var="deleteUrl" value="socialProfile/delete.do">
			<spring:param name="socialProfileId" value="${row.id}" />
		</spring:url>

		<display:column title="${delete}">
			<a href="${deleteUrl}" onclick="return confirm('${confirm}')" ><jstl:out value="${delete}" /></a>
		</display:column>
	
</display:table>

<spring:url var="createUrl" value="socialProfile/create.do">
<spring:param name="varId" value="${curriculum.id}" />
</spring:url>
	<a href="${createUrl}"><jstl:out value="${create}"/></a>
		