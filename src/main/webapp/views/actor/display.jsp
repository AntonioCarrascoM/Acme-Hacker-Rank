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

<spring:message code="actor.name" var="name" />
<spring:message code="actor.surnames" var="surnames" />
<spring:message code="actor.vatNumber" var="vatNumber" />
<spring:message code="actor.creditCard" var="creditCard" />
<spring:message code="actor.creditCard.holder" var="holder" />
<spring:message code="actor.creditCard.make" var="make" />
<spring:message code="actor.creditCard.expMonth" var="expMonth" />
<spring:message code="actor.creditCard.expYear" var="expYear" />
<spring:message code="actor.creditCard.cvv" var="cvv" />
<spring:message code="actor.photo" var="photo" />
<spring:message code="actor.email" var="email" />
<spring:message code="actor.phone" var="phone" />
<spring:message code="actor.address" var="address" />
<spring:message code="actor.spammer" var="spammer" />
<spring:message code="actor.Tspammer" var="Tspammer" />
<spring:message code="actor.Fspammer" var="Fspammer" />

<spring:message code="actor.return" var="returnMsg" />

	<security:authorize access="hasRole('ADMIN')" >
	
	<%-- Displays the information of the selected actor --%>
	
	<jstl:out value="${name}" />:
	<jstl:out value="${actor.name}"/>
	<br />
	
	<jstl:out value="${surnames}" />:
	<jstl:out value="${actor.surnames}"/>
	<br />
	
	<jstl:out value="${vatNumber}" />:
	<jstl:out value="${actor.vatNumber}"/>
	<br />
	
	<jstl:out value="${photo}" />:
	<a href="${actor.photo}"><jstl:out value="${actor.photo}"/></a>
	<br />
	
	<jstl:out value="${email}" />:
	<jstl:out value="${actor.email}"/>
	<br />
	
	<jstl:out value="${phone}" />:
	<jstl:out value="${actor.phone}"/>
	<br />
	
	<jstl:out value="${address}" />:
	<jstl:out value="${actor.address}"/>
	<br />
	

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
  	
	
	<br />
	
	<fieldset>
	<legend><jstl:out value="${creditCard}" /></legend>
	
	<jstl:out value="${holder}" />:
	<jstl:out value="${actor.creditCard.holder}"/>
	<br />
	
	<jstl:out value="${make}" />:
	<jstl:out value="${actor.creditCard.make}"/>
	<br />
	
	<jstl:out value="${expMonth}" />:
	<jstl:out value="${actor.creditCard.expMonth}"/>
	<br />
	
	<jstl:out value="${expYear}" />:
	<jstl:out value="${actor.creditCard.expYear}"/>
	<br />
	
	<jstl:out value="${cvv}" />:
	<jstl:out value="${actor.creditCard.cvv}"/>
	<br />
	</fieldset>
  		
	<a href="administrator/bannableList.do"><jstl:out value="${returnMsg}" /></a>

</security:authorize>