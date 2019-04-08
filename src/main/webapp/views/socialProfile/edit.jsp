<%--
 * edit.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<%-- Stored message variables --%>

<spring:message code="socialProfile.create" var="create" />
<spring:message code="socialProfile.edit" var="edit" />
<spring:message code="socialProfile.profileLink" var="profileLink" />
<spring:message code="socialProfile.nick" var="nick" />
<spring:message code="socialProfile.socialNetwork" var="socialNetwork" />
<spring:message code="socialProfile.profileLink" var="profileLink" />
<spring:message code="socialProfile.save" var="save" />
<spring:message code="socialProfile.delete" var="delete" />
<spring:message code="socialProfile.confirm.delete" var="confirm" />
<spring:message code="socialProfile.cancel" var="cancel" />

<security:authorize access="isAuthenticated()">

	<form:form action="${requestURI}" modelAttribute="socialProfile">

		<%-- Forms --%>

		<form:hidden path="id" />


		<acme:textbox code="socialProfile.nick" path="nick" />
		<acme:textbox code="socialProfile.socialNetwork" path="socialNetwork" />
		<acme:textbox placeholder="socialProfile.ph" code="socialProfile.profileLink" path="profileLink" />
		<br />

		<%-- Buttons --%>

		<acme:submit code="socialProfile.save" name="save" />

		<jstl:if test="${socialProfile.id!=0}">
			<input type="submit" name="delete" value="${delete}"
				onclick="return confirm('${confirm}')" />&nbsp;
	</jstl:if>

		<acme:cancel code="socialProfile.cancel" url="/socialProfile/list.do" />
	</form:form>
</security:authorize>