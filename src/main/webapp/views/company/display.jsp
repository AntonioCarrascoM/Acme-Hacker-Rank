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

<spring:message code="company.commercialName" var="commercialName" />
<spring:message code="company.name" var="name" />
<spring:message code="company.surnames" var="surnames" />
<spring:message code="company.vatNumber" var="vatNumber" />
<spring:message code="company.creditCard" var="creditCard" />
<spring:message code="company.creditCard.holder" var="holder" />
<spring:message code="company.creditCard.make" var="make" />
<spring:message code="company.creditCard.expMonth" var="expMonth" />
<spring:message code="company.creditCard.expYear" var="expYear" />
<spring:message code="company.creditCard.cvv" var="cvv" />
<spring:message code="company.photo" var="photo" />
<spring:message code="company.email" var="email" />
<spring:message code="company.phone" var="phone" />
<spring:message code="company.address" var="address" />
<spring:message code="company.spammer" var="spammer" />
<spring:message code="company.Tspammer" var="Tspammer" />
<spring:message code="company.Fspammer" var="Fspammer" />

<spring:message code="company.return" var="returnMsg" />


	
	<%-- Displays the information of the selected company --%>
	
	<jstl:out value="${commercialName}" />:
	<jstl:out value="${company.commercialName}" />
	<br />
	
	<jstl:out value="${name}" />:
	<jstl:out value="${company.name}"/>
	<br />
	
	<jstl:out value="${surnames}" />:
	<jstl:out value="${company.surnames}"/>
	<br />
	
	<jstl:out value="${vatNumber}" />:
	<jstl:out value="${company.vatNumber}"/>
	<br />
	
	<jstl:out value="${photo}" />:
	<a href="${company.photo}"><jstl:out value="${company.photo}"/></a>
	<br />
	
	<jstl:out value="${email}" />:
	<jstl:out value="${company.email}"/>
	<br />
	
	<jstl:out value="${phone}" />:
	<jstl:out value="${company.phone}"/>
	<br />
	
	<jstl:out value="${address}" />:
	<jstl:out value="${company.address}"/>
	<br />
	
	<security:authorize access="hasRole('ADMIN')" >
	<jstl:if test="${actor.evaluated eq true}">
	<jstl:out value="${spammer}" />:
	<jstl:if test="${actor.spammer eq true}">
	<jstl:out value="${Tspammer}"/>
	</jstl:if>
	<jstl:if test="${actor.spammer eq false}">
	<jstl:out value="${Fspammer}"/>
	</jstl:if>
	<br />
	</jstl:if>
	<jstl:if test="${actor.evaluated eq false}">
	<jstl:out value="${spammer}" />:
	<jstl:out value="N/A"/>
	<br />
	</jstl:if>
  	</security:authorize>
	
	<br />
	
	<fieldset>
	<legend><jstl:out value="${creditCard}" /></legend>
	
	<jstl:out value="${holder}" />:
	<jstl:out value="${company.creditCard.holder}"/>
	<br />
	
	<jstl:out value="${make}" />:
	<jstl:out value="${company.creditCard.make}"/>
	<br />
	
	<jstl:out value="${expMonth}" />:
	<jstl:out value="${company.creditCard.expMonth}"/>
	<br />
	
	<jstl:out value="${expYear}" />:
	<jstl:out value="${company.creditCard.expYear}"/>
	<br />
	
	<jstl:out value="${cvv}" />:
	<jstl:out value="${company.creditCard.cvv}"/>
	<br />
	</fieldset>
  		
	<a href="welcome/index.do"><jstl:out value="${returnMsg}" /></a>

