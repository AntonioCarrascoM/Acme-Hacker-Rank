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
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Stored message variables --%>

<spring:message code="problem.title" var="title" />
<spring:message code="problem.statement" var="statement" />
<spring:message code="problem.hint" var="hint" />
<spring:message code="problem.attachments" var="attachments" />
<spring:message code="problem.finalMode" var="finalMode" />
<spring:message code="problem.company" var="company" />
<spring:message code="problem.positions" var="positions" />
<spring:message code="problem.return" var="returnMsg" />
<spring:message code="problem.save" var="save" />

<security:authorize access="hasRole('COMPANY')">

	<form:form id="form" action="${requestURI}"
		modelAttribute="problem">

		<%-- Forms --%>

		<form:hidden path="id" />
		
		<acme:textbox code="problem.title" path="title"/>
		<acme:textbox code="problem.statement" path="statement"/>
		<acme:textbox code="problem.hint" path="hint" />
		<acme:textarea code="problem.attachments" path="attachments" placeholder="problem.attachments.warning"/>
		
		<form:label path="finalMode">
			<jstl:out value="${finalMode}" />:
		</form:label>
			<form:select path="finalMode" >
				<form:option
					label="NO"
					value="false" />
				<form:option
					label="YES"
					value="true" />
			</form:select>
		<br><br>
		
		<%-- Buttons --%>

		<input type="submit" name="save" value="${save}"/>&nbsp;
		
		<acme:cancel url="welcome/index.do" code="problem.cancel" />
	</form:form>
</security:authorize>