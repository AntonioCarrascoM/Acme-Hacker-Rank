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

<security:authorize access="hasRole('ADMIN')">

	<form:form id="form" action="${requestURI}"
		modelAttribute="administrator">

		<%-- Forms --%>

		<form:hidden path="id" />
		
		<acme:textbox code="administrator.name" path="name"/>
		<acme:textbox code="administrator.surnames" path="surnames"/>
		<acme:textbox code="administrator.vatNumber" path="vatNumber"/>
		<fieldset>
		<legend><jstl:out value="${creditCard}"/></legend>
		<acme:textbox code="company.creditCard.holder" path="creditCard.holder" />
		<acme:textbox code="company.creditCard.make" path="creditCard.make" />
		<acme:textbox code="company.creditCard.number" path="creditCard.number" />
		<acme:textbox code="company.creditCard.expMonth" path="creditCard.expMonth" placeholder="month.ph"/>
		<acme:textbox code="company.creditCard.expYear" path="creditCard.expYear" placeholder="year.ph"/>
		<acme:textbox code="company.creditCard.cvv" path="creditCard.cvv" />
		</fieldset>
		<acme:textbox code="administrator.photo" path="photo" placeholder="link"/>
		<acme:textbox code="administrator.email" path="email" placeholder="mail.ph"/>
		<acme:textbox code="administrator.phone" path="phone" placeholder="phone.ph"/>
		<acme:textbox code="administrator.address" path="address"/>
		
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
		
		<acme:cancel url="welcome/index.do" code="administrator.cancel" />
	</form:form>
</security:authorize>

<script type="text/javascript">
	function phoneCheck() {
		var form = document.getElementById("form");
		var phone = form["phone"].value;
		var regx1 = "^$|^\\+([1-9][0-9]{0,2}) (\\([1-9][0-9]{0,2}\\)) ([a-zA-Z0-9 -]{4,})$";
		var regx2 = "^$|^\\+([1-9][0-9]{0,2}) ([a-zA-Z0-9 -]{4,})$";
		var regx3 = "^$|^\\([a-zA-Z0-9 -]{4,})$";
		if (!phone.match(regx1) && !phone.match(regx2) && !phone.match(regx3))
			confirm('${check}');
	}
</script>