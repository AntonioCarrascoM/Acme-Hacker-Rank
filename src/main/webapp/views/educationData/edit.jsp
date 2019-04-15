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

<spring:message code="educationData.degree" var="degree" />
<spring:message code="educationData.institution" var="institution" />
<spring:message code="educationData.mark" var="mark" />
<spring:message code="educationData.startDate" var="startDate" />
<spring:message code="educationData.endDate" var="endDate" />
<spring:message code="educationData.save" var="save" />
<spring:message code="educationData.delete" var="delete" />
<spring:message code="educationData.confirm" var="confirm" />

<security:authorize access="hasRole('HACKER')">

	<form:form action="${requestURI}" modelAttribute="educationData">

		<%-- Forms --%>

		<form:hidden path="id" />
		<form:hidden path="curriculum" />
		
		<acme:textbox code="educationData.degree" path="degree"/>
		<br>
		<acme:textbox code="educationData.institution" path="institution"/>
		<br>
		<acme:textbox code="educationData.mark" path="mark"/>
		<br>
		
		 <acme:textbox 
	     placeholder="educationData.ph"
		 code = "educationData.startDate" 
		 path="startDate"/>
		 <br/>
		 
		  <acme:textbox 
	     placeholder="educationData.ph"
		 code = "educationData.endDate" 
		 path="endDate"/>
		 <br/>

		<%-- Buttons --%>

		<acme:submit code="educationData.save" name="save" />

		
	<jstl:if test="${educationData.id!=0}">
			<input type="submit" name="delete" value="${delete}"
				onclick="return confirm('${confirm}')" />&nbsp;
	</jstl:if>

	<acme:cancel url="curriculum/hacker/list.do" code="educationData.cancel" />


</form:form>

</security:authorize>


