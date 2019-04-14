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

<spring:message code="position.title" var="title" />
<spring:message code="position.description" var="description" />
<spring:message code="position.deadline" var="deadline" />
<spring:message code="position.requiredProfile" var="requiredProfile" />
<spring:message code="position.requiredSkills" var="requiredSkills" />
<spring:message code="position.requiredTech" var="requiredTech" />
<spring:message code="position.offeredSalary" var="offeredSalary" />
<spring:message code="position.finalMode" var="finalMode" />
<spring:message code="position.formatDate" var="formatDate" />
<spring:message code="position.problems" var="problemsMsg" />
<spring:message code="position.save" var="save" />
<spring:message code="position.return" var="returnMsg" />

<security:authorize access="hasRole('COMPANY')">

	<form:form id="form" action="${requestURI}"
		modelAttribute="position">

		<%-- Forms --%>

		<form:hidden path="id" />
		
		<acme:textbox code="position.title" path="title"/>
		<acme:textbox code="position.description" path="description"/>
		<acme:textbox code="position.deadline" path="deadline" placeholder="position.formatDate"/>
		<acme:textbox code="position.requiredProfile" path="requiredProfile" />
		<acme:textbox code="position.requiredSkills" path="requiredSkills" />
		<acme:textbox code="position.requiredTech" path="requiredTech" />
		
		<form:label path="offeredSalary">
			<jstl:out value="${offeredSalary}"/>
		</form:label>	
		<form:input path="offeredSalary" pattern="(\d*)|(\d*.\d*)" placeholder="num."/>
		<form:errors path="offeredSalary" cssClass="error" />
		<br>
		
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
		<br>
		<br>
		<form:label path="problems">
			<jstl:out value="${problemsMsg}" />:
		</form:label>
		<jstl:forEach items="${problems}" var = "item">
          <form:checkbox path="problems" value="${item}"/><jstl:out value="${item.title}"/>
      	</jstl:forEach>
		<br><br>

		<%-- Buttons --%>

		<input type="submit" name="save" value="${save}"/>&nbsp;
		
		<acme:cancel url="welcome/index.do" code="position.cancel" />
	</form:form>
</security:authorize>