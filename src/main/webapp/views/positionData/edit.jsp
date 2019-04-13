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

<spring:message code="positionData.title" var="title" />
<spring:message code="positionData.description" var="description" />
<spring:message code="positionData.startDate" var="startDate" />
<spring:message code="positionData.endDate" var="endDate" />
<spring:message code="positionData.save" var="save" />
<spring:message code="positionData.delete" var="delete" />
<spring:message code="positionData.cancel" var="cancel" />
<spring:message code="positionData.confirm" var="confirm" />

<security:authorize access="hasRole('HACKER')">

	<form:form action="${requestURI}" modelAttribute="positionData">

		<%-- Forms --%>

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="curriculum" />
		
		
		
		<acme:textbox code="positionData.title" path="title"/>
		
		<acme:textarea
		 code="positionData.description" 
		 path="description"/>
		 <br />
		 
		 		
		 <acme:textbox 
	     placeholder="positionData.ph"
		 code = "positionData.startDate" 
		 path="startDate"/>
		 <br/>
		 
		  <acme:textbox 
	     placeholder="positionData.ph"
		 code = "positionData.endDate" 
		 path="endDate"/>
		 <br/>
	

		<%-- Buttons --%>

		<acme:submit code="positionData.save" name="save" />

		
	<jstl:if test="${positionData.id!=0}">
			<input type="submit" name="delete" value="${delete}"
				onclick="return confirm('${confirm}')" />&nbsp;
	</jstl:if>

	<acme:cancel url="welcome/index.do" code="positionData.cancel" />


</form:form>

</security:authorize>


