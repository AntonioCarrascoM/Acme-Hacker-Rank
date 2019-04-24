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

<spring:message code="hacker.edit" var="edit" />
<spring:message code="hacker.userAccount.username" var="username" />
<spring:message code="hacker.userAccount.password" var="password" />
<spring:message code="hacker.name" var="name" />
<spring:message code="hacker.surnames" var="surnames" />
<spring:message code="hacker.vatNumber" var="vatNumber"/>
<spring:message code="hacker.creditCard" var="creditCard" />
<spring:message code="hacker.creditCard.holder" var="holder" />
<spring:message code="hacker.creditCard.make" var="make" />
<spring:message code="hacker.creditCard.number" var="number" />
<spring:message code="hacker.creditCard.expMonth" var="expMonth" />
<spring:message code="hacker.creditCard.expYear" var="expYear" />
<spring:message code="hacker.creditCard.cvv" var="cvv" />
<spring:message code="hacker.photo" var="photo" />
<spring:message code="hacker.email" var="email" />
<spring:message code="hacker.phone" var="phone" />
<spring:message code="hacker.address" var="address" />
<spring:message code="hacker.save" var="save" />
<spring:message code="hacker.cancel" var="cancel" />
<spring:message code="hacker.confirm" var="confirm" />
<spring:message code="hacker.phone.pattern1" var="phonePattern1" />
<spring:message code="hacker.phone.pattern2" var="phonePattern2" />
<spring:message code="hacker.phone.warning" var="phoneWarning" />
<spring:message code="hacker.phone.note" var="phoneNote" />
<spring:message code="cvv.ph" var="cvvPH" />
<spring:message code="month.ph" var="monthPH" />
<spring:message code="year.ph" var="yearPH" />


<security:authorize access="hasRole('HACKER')">

	<form:form id="form" action="${requestURI}"
		modelAttribute="hacker">

		<%-- Forms --%>

		<form:hidden path="id" />
		
		<acme:textbox code="hacker.name" path="name"/>
		<acme:textbox code="hacker.surnames" path="surnames"/>
		<acme:textbox code="hacker.vatNumber" path="vatNumber"/>
		<fieldset>
		<legend><jstl:out value="${creditCard}"/></legend>
		<acme:textbox code="hacker.creditCard.holder" path="creditCard.holder" />
		<acme:textbox code="hacker.creditCard.make" path="creditCard.make" />
		<form:label path="creditCard.number">
			<jstl:out value="${number}"/>
		</form:label>	
		<form:input path="creditCard.number" pattern="\d*" placeholder="num."/>
		<form:errors path="creditCard.number" cssClass="error" />
		<br>
		<form:label path="creditCard.expMonth">
			<jstl:out value="${expMonth}"/>
		</form:label>	
		<form:input path="creditCard.expMonth" pattern="\d{1,2}" placeholder="${monthPH}"/>
		<form:errors path="creditCard.expMonth" cssClass="error" />
		<br>
		
		<form:label path="creditCard.expYear">
			<jstl:out value="${expYear}"/>
		</form:label>	
		<form:input path="creditCard.expYear" pattern="\d{4}" placeholder="${yearPH}"/>
		<form:errors path="creditCard.expYear" cssClass="error" />
		<br>
		<form:label path="creditCard.cvv">
			<jstl:out value="${cvv}"/>
		</form:label>	
		<form:input path="creditCard.cvv" pattern="\d{3}" placeholder="${cvvPH}"/>
		<form:errors path="creditCard.cvv" cssClass="error" />
		<br>
		</fieldset>
		<acme:textbox code="hacker.photo" path="photo" placeholder="link"/>
		<acme:textbox code="hacker.email" path="email" placeholder="hacker.mail.ph"/>
		<acme:textbox code="hacker.phone" path="phone" placeholder="phone.ph"/>
		<acme:textbox code="hacker.address" path="address"/>
		
		<br>
		<jstl:out value="${phoneWarning}" />
		<br />
		<jstl:out value="${phonePattern1}" />
		<br />
		<jstl:out value="${phonePattern2}" />
		<br />

		<%-- Buttons --%>

		<input type="submit" name="save" value="${save}"
			onclick="return confirm('${confirm}')" />&nbsp;
		
		<acme:cancel url="welcome/index.do" code="hacker.cancel" />
	</form:form>
</security:authorize>