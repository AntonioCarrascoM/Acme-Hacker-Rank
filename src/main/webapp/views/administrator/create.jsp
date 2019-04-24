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

<spring:message code="administrator.edit" var="edit" />
<spring:message code="administrator.userAccount.username" var="username" />
<spring:message code="administrator.userAccount.password" var="password" />
<spring:message code="administrator.name" var="name" />
<spring:message code="administrator.surnames" var="surnames" />
<spring:message code="administrator.vatNumber" var="vatNumber"/>
<spring:message code="administrator.creditCard" var="creditCard" />
<spring:message code="administrator.creditCard.holder" var="holder" />
<spring:message code="administrator.creditCard.make" var="make" />
<spring:message code="administrator.creditCard.number" var="number" />
<spring:message code="administrator.creditCard.expMonth" var="expMonth" />
<spring:message code="administrator.creditCard.expYear" var="expYear" />
<spring:message code="administrator.creditCard.cvv" var="cvv" />
<spring:message code="administrator.photo" var="photo" />
<spring:message code="administrator.email" var="email" />
<spring:message code="administrator.phone" var="phone" />
<spring:message code="administrator.address" var="address" />
<spring:message code="administrator.save" var="save" />
<spring:message code="administrator.cancel" var="cancel" />
<spring:message code="administrator.confirm" var="confirm" />
<spring:message code="administrator.phone.pattern1" var="phonePattern1" />
<spring:message code="administrator.phone.pattern2" var="phonePattern2" />
<spring:message code="administrator.phone.warning" var="phoneWarning" />
<spring:message code="administrator.phone.note" var="phoneNote" />
<spring:message code="administrator.terms" var="terms" />
<spring:message code="administrator.acceptedTerms" var="acceptedTerms" />
<spring:message code="administrator.secondPassword" var="secondPassword" />


<security:authorize access="hasRole('ADMIN')">

	<form:form id="form" action="${requestURI}"
		modelAttribute="foa">

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
		
		<acme:textbox code="administrator.name" path="name"/>
		<acme:textbox code="administrator.surnames" path="surnames"/>
		<acme:textbox code="administrator.vatNumber" path="vatNumber"/>
		<fieldset>
		<legend><jstl:out value="${creditCard}"/></legend>
		<acme:textbox code="administrator.creditCard.holder" path="creditCard.holder" />
		<acme:textbox code="administrator.creditCard.make" path="creditCard.make" />
		<acme:textbox code="administrator.creditCard.number" path="creditCard.number" />
		<acme:textbox code="administrator.creditCard.expMonth" path="creditCard.expMonth" placeholder="month.ph"/>
		<acme:textbox code="administrator.creditCard.expYear" path="creditCard.expYear" placeholder="year.ph"/>
		<acme:textbox code="administrator.creditCard.cvv" path="creditCard.cvv" placeholder="cvv.ph"/>
		</fieldset>
		<acme:textbox code="administrator.photo" path="photo" placeholder="link"/>
		<acme:textbox code="administrator.email" path="email" placeholder="mail.ph"/>
		<acme:textbox code="administrator.phone" path="phone" placeholder="phone.ph"/>
		<acme:textbox code="administrator.address" path="address"/>
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
		
	<acme:cancel url="welcome/index.do" code="administrator.cancel" />
	</form:form>
</security:authorize>