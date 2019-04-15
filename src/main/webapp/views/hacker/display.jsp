<%--
 * dashboard.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<%-- Stored message variables --%>

<spring:message code="hacker.name" var="name" />
<spring:message code="hacker.surnames" var="surnames" />
<spring:message code="hacker.vatNumber" var="vatNumber" />
<spring:message code="hacker.creditCard" var="creditCard" />
<spring:message code="hacker.creditCard.holder" var="holder" />
<spring:message code="hacker.creditCard.make" var="make" />
<spring:message code="hacker.creditCard.expMonth" var="expMonth" />
<spring:message code="hacker.creditCard.expYear" var="expYear" />
<spring:message code="hacker.creditCard.cvv" var="cvv" />
<spring:message code="hacker.photo" var="photo" />
<spring:message code="hacker.email" var="email" />
<spring:message code="hacker.phone" var="phone" />
<spring:message code="hacker.address" var="address" />
<spring:message code="hacker.spammer" var="spammer" />

<spring:message code="hacker.return" var="returnMsg" />


	
	<%-- Displays the information of the selected hacker --%>
	
	<jstl:out value="${name}" />:
	<jstl:out value="${hacker.name}"/>
	<br />
	
	<jstl:out value="${surnames}" />:
	<jstl:out value="${hacker.surnames}"/>
	<br />
	
	<jstl:out value="${vatNumber}" />:
	<jstl:out value="${hacker.vatNumber}"/>
	<br />
	
	<jstl:out value="${photo}" />:
	<a href="${hacker.photo}"><jstl:out value="${hacker.photo}"/></a>
	<br />
	
	<jstl:out value="${email}" />:
	<jstl:out value="${hacker.email}"/>
	<br />
	
	<jstl:out value="${phone}" />:
	<jstl:out value="${hacker.phone}"/>
	<br />
	
	<jstl:out value="${address}" />:
	<jstl:out value="${hacker.address}"/>
	<br />
	
	<security:authorize access="hasRole('ADMIN')" >
	<jstl:out value="${spammer}" />:
	<jstl:out value="${hacker.spammer}"/>
	<br />
  	</security:authorize>
	
	<br />
	
	<fieldset>
	<legend><jstl:out value="${creditCard}" /></legend>
	
	<jstl:out value="${holder}" />:
	<jstl:out value="${hacker.creditCard.holder}"/>
	<br />
	
	<jstl:out value="${make}" />:
	<jstl:out value="${hacker.creditCard.make}"/>
	<br />
	
	<jstl:out value="${expMonth}" />:
	<jstl:out value="${hacker.creditCard.expMonth}"/>
	<br />
	
	<jstl:out value="${expYear}" />:
	<jstl:out value="${hacker.creditCard.expYear}"/>
	<br />
	
	<jstl:out value="${cvv}" />:
	<jstl:out value="${hacker.creditCard.cvv}"/>
	<br />
	</fieldset>
  		
	<a href="welcome/index.do"><jstl:out value="${returnMsg}" /></a>

