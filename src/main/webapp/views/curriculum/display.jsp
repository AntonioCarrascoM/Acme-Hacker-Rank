<%--
 * display.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<%-- Stored message variables --%>

<spring:message code="curriculum.personalData" var="msgPersonalData" />
<spring:message code="curriculum.personalData.fullName" var="msgFullName" />
<spring:message code="curriculum.personalData.statement" var="msgStatement" />
<spring:message code="curriculum.personalData.phone" var="msgPhoneNumber" />
<spring:message code="curriculum.personalData.github" var="msgGitHubProfile" />
<spring:message code="curriculum.personalData.linkedin" var="msgLinkedInProfile" />
<spring:message code="curriculum.personalData.create" var="createPersonalData" />

<spring:message code="curriculum.educationData" var="msgEducationData" />
<spring:message code="curriculum.educationData.degree" var="degree" />
<spring:message code="curriculum.educationData.institution" var="institution" />
<spring:message code="curriculum.educationData.mark" var="mark" />
<spring:message code="curriculum.educationData.create" var="createEducationData" />

<spring:message code="curriculum.positionData" var="msgPositionData" />
<spring:message code="curriculum.positionData.title" var="title" />
<spring:message code="curriculum.positionData.description" var="description" />
<spring:message code="curriculum.positionData.create" var="createPositionData" />

<spring:message code="curriculum.miscellaneousData" var="msgMiscellaneousData" />
<spring:message code="curriculum.miscellaneousData.text" var="text" />
<spring:message code="curriculum.miscellaneousData.attachments" var="attachments" />
<spring:message code="curriculum.miscellaneousData.create" var="createMiscellaneousData" />

<spring:message code="curriculum.createPersonalData" var="msgCreatePersonalData" />
<spring:message code="curriculum.return" var="msgReturn" />
<spring:message code="curriculum.delete" var="delete" />
<spring:message code="curriculum.details" var="details" />
<spring:message code="curriculum.edit" var="edit" />
<spring:message code="curriculum.confirm" var="confirm" />


<%-- For the curriculum in the list received as model, display the following information: --%>

<security:authorize access="hasRole('HACKER')">

	<%-- Personal Data --%>

	<h2><jstl:out value="${msgPersonalData}" /></h2>

	<jstl:if test="${hasPersonalData eq true}">

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
	<a href="${editUrl}"><jstl:out value="${edit}" /></a>
</jstl:if>

	<jstl:if test="${hasPersonalData eq false}">

	<spring:url var="personalDataUrl"
		value="personalData/hacker/create.do">
		<spring:param name="varId" value="${curriculum.id}" />
	</spring:url>
	<a href="${personalDataUrl}"><jstl:out value="${createPersonalData}" /></a>
	<br />
	</jstl:if>

	<!-- Education data list -->
	
	<h2><jstl:out value="${msgEducationData}" /></h2>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="educationDatas" requestURI="${requestURI}" id="row">

		<display:column property="degree" title="${degree}" />
		<display:column property="institution" title="${institution}" />
		<display:column property="mark" title="${mark}" />

		<spring:url var="editUrl" value="educationData/hacker/edit.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${edit}">
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		</display:column>
		
		<spring:url var="deleteUrl" value="educationData/hacker/delete.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${delete}">
			<a href="${deleteUrl}" onclick="return confirm('${confirm}')" ><jstl:out value="${delete}" /></a>
		</display:column>

		<spring:url var="displayUrl" value="educationData/hacker/display.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${details}">
			<a href="${displayUrl}"><jstl:out value="${details}" /></a>
		</display:column>

	</display:table>
	
	<spring:url var="createEducationDataUrl" value="educationData/hacker/create.do">
		<spring:param name="varId" value="${curriculum.id}" />
	</spring:url>
	<a href="${createEducationDataUrl}"><jstl:out value="${createEducationData}"/></a>

	<br>
	
	<!-- Position data list -->
	
	<h2><jstl:out value="${msgPositionData}" /></h2>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="positionDatas" requestURI="${requestURI}" id="row">


		<display:column property="title" title="${title}" />
		<display:column property="description" title="${description}" />


		<spring:url var="editUrl" value="positionData/hacker/edit.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${edit}">
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		</display:column>
		
		<spring:url var="deleteUrl" value="positionData/hacker/delete.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${delete}">
			<a href="${deleteUrl}" onclick="return confirm('${confirm}')" ><jstl:out value="${delete}" /></a>
		</display:column>

		<spring:url var="displayUrl" value="positionData/hacker/display.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${details}">
			<a href="${displayUrl}"><jstl:out value="${details}" /></a>
		</display:column>

	</display:table>
	
	<br>
	
	<spring:url var="createPositionDataUrl" value="positionData/hacker/create.do">
		<spring:param name="varId" value="${curriculum.id}" />
	</spring:url>
	<a href="${createPositionDataUrl}"><jstl:out value="${createPositionData}"/></a>

	<br>

	<!-- Miscellaneous  data list -->
	
	<h2><jstl:out value="${msgMiscellaneousData}" /></h2>
	
	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="miscellaneousDatas" requestURI="${requestURI}" id="row">
		
		<display:column property="text" title="${text}"/>
		<display:column property="attachments" title="${attachments}"/>

		<spring:url var="editUrl" value="miscellaneousData/hacker/edit.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${edit}">
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		</display:column>
		
		<spring:url var="deleteUrl" value="miscellaneousData/hacker/delete.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${delete}">
			<a href="${deleteUrl}" onclick="return confirm('${confirm}')" ><jstl:out value="${delete}" /></a>
		</display:column>
		
		<spring:url var="displayUrl"
			value="miscellaneousData/hacker/display.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${details}">
			<a href="${displayUrl}"><jstl:out value="${details}" /></a>
		</display:column>

	</display:table>

	<br />
	
	<spring:url var="createMiscellaneousDataUrl" value="miscellaneousData/hacker/create.do">
		<spring:param name="varId" value="${curriculum.id}" />
	</spring:url>
	<a href="${createMiscellaneousDataUrl}" onclick="return confirm('${confirm}')" ><jstl:out value="${createMiscellaneousData}"/></a>
	
	<br>

	<a href="curriculum/hacker/list.do"><jstl:out value="${msgReturn}" /></a>

</security:authorize>