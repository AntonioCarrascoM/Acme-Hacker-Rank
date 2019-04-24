<%--
 * create.jsp
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

<spring:message code="company.edit" var="edit" />
<spring:message code="company.userAccount.username" var="username" />
<spring:message code="company.userAccount.password" var="password" />
<spring:message code="company.name" var="name" />
<spring:message code="company.surnames" var="surnames" />
<spring:message code="company.vatNumber" var="vatNumber"/>
<spring:message code="company.creditCard" var="creditCard" />
<spring:message code="company.creditCard.holder" var="holder" />
<spring:message code="company.creditCard.make" var="make" />
<spring:message code="company.creditCard.number" var="number" />
<spring:message code="company.creditCard.expMonth" var="expMonth" />
<spring:message code="company.creditCard.expYear" var="expYear" />
<spring:message code="company.creditCard.cvv" var="cvv" />
<spring:message code="company.photo" var="photo" />
<spring:message code="company.email" var="email" />
<spring:message code="company.phone" var="phone" />
<spring:message code="company.address" var="address" />
<spring:message code="company.save" var="save" />
<spring:message code="company.cancel" var="cancel" />
<spring:message code="company.confirm" var="confirm" />
<spring:message code="company.phone.pattern1" var="phonePattern1" />
<spring:message code="company.phone.pattern2" var="phonePattern2" />
<spring:message code="company.phone.warning" var="phoneWarning" />
<spring:message code="company.phone.note" var="phoneNote" />
<spring:message code="company.terms" var="terms" />
<spring:message code="company.acceptedTerms" var="acceptedTerms" />
<spring:message code="company.secondPassword" var="secondPassword" />


<security:authorize access="isAnonymous() or hasRole('COMPANY')">
	<form:form id="form" action="${requestURI}"
		modelAttribute="foc">

		<%-- Forms --%>

			<form:label path="username">
				<jstl:out value="${username}" />:
		</form:label>
			<form:input path="username" />
			<form:errors cssClass="error" path="username" />
			<br />

			<form:label path="password">
				<jstl:out value="${password}" />:
		</form:label>
			<form:password path="password" />
			<form:errors cssClass="error" path="password" />
			<br />
			
			<form:label path="secondPassword">
				<jstl:out value="${secondPassword}" />:
		</form:label>
			<form:password path="secondPassword" />
			<form:errors cssClass="error" path="secondPassword" />
			<br />
		
		<acme:textbox code="company.commercialName" path="commercialName"/>
		<acme:textbox code="company.name" path="name"/>
		<acme:textbox code="company.surnames" path="surnames"/>
		<acme:textbox code="company.vatNumber" path="vatNumber"/>
		<fieldset>
		<legend><jstl:out value="${creditCard}"/></legend>
		<acme:textbox code="company.creditCard.holder" path="creditCard.holder" />
		<acme:textbox code="company.creditCard.make" path="creditCard.make" />
		<acme:textbox code="company.creditCard.number" path="creditCard.number" />
		<acme:textbox code="company.creditCard.expMonth" path="creditCard.expMonth" placeholder="month.ph"/>
		<acme:textbox code="company.creditCard.expYear" path="creditCard.expYear" placeholder="year.ph"/>
		<acme:textbox code="company.creditCard.cvv" path="creditCard.cvv" placeholder="cvv.ph"/>
		</fieldset>
		<acme:textbox code="company.photo" path="photo" placeholder="link"/>
		<acme:textbox code="company.email" path="email" placeholder="company.mail.ph"/>
		<acme:textbox code="company.phone" path="phone" placeholder="phone.ph"/>
		<acme:textbox code="company.address" path="address"/>
		<br>

		<form:label path="acceptedTerms" >
        	<jstl:out value="${acceptedTerms}" />:
    </form:label>
    <a href="welcome/terms.do" target="_blank"><jstl:out value="${terms}" /></a>
    <form:checkbox path="acceptedTerms" required="required"/>
    <form:errors path="acceptedTerms" cssClass="error" />
    <br/>
	<br/>
		<jstl:out value="${phoneWarning}" />
		<br />
		<jstl:out value="${phonePattern1}" />
		<br />
		<jstl:out value="${phonePattern2}" />
		<br />
		<br />

		<%-- Buttons --%>

		<input type="submit" name="create" value="${save}"
			onclick="return confirm('${confirm}')" />&nbsp;
		
	<acme:cancel url="welcome/index.do" code="company.cancel" />
	</form:form>
</security:authorize>