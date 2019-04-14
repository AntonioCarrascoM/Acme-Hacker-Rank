<%--
 * index.jsp
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

<spring:message code="welcome.formatDate" var="formatDate" />

<jstl:set var="localeCode" value="${pageContext.response.locale}"/>

<img src="${configuration.banner}" width="410" height="123">
<br/>

<jstl:if test="${localeCode == 'en'}">
	<jstl:out value="${configuration.welcomeEN}" />
</jstl:if>
<jstl:if test="${localeCode == 'es'}">
	<jstl:out value="${configuration.welcomeES}" />
</jstl:if>

<p><spring:message code="welcome.greeting.prefix" /><spring:message code="welcome.greeting.suffix" /></p>

<p><spring:message code="welcome.greeting.current.time" /> ${moment}</p> 