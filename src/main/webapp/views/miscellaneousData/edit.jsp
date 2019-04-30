<%--
 * edit.jsp
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
	


<%-- Stored message variables --%>

<spring:message code="miscellaneousData.text" var="text" />
<spring:message code="miscellaneousData.attachments" var="attachments" />
<spring:message code="miscellaneousData.save" var="save" />
<spring:message code="miscellaneousData.delete" var="delete" />
<spring:message code="miscellaneousData.cancel" var="cancel" />
<spring:message code="miscellaneousData.confirm" var="confirm" />

<security:authorize access="hasRole('HACKER')">

	<form:form action="${requestURI}" modelAttribute="miscellaneousData">

		<%-- Forms --%>

		<form:hidden path="id" />
		<form:hidden path="curriculum" />
		
		
		<acme:textarea code="miscellaneousData.text" path="text"/>
		<acme:textarea code="miscellaneousData.attachments" path="attachments"/>
		
		<%-- Buttons --%>

		<acme:submit code="miscellaneousData.save" name="save" />

		
	<jstl:if test="${miscellaneousData.id!=0}">
			<input type="submit" name="delete" value="${delete}"
				onclick="return confirm('${confirm}')" />&nbsp;
	</jstl:if>

	<acme:cancel url="curriculum/hacker/list.do" code="miscellaneousData.cancel" />


</form:form>

</security:authorize>


