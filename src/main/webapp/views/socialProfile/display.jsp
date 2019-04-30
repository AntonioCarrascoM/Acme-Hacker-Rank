<%--
 * display.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>
<spring:message code="socialProfile.nick" var="msgNick" />
<spring:message code="socialProfile.socialNetwork" var="msgSocialNetwork"/>
<spring:message code="socialProfile.profileLink" var="msgProfileLink"/>
<spring:message code="socialProfile.edit" var="edit"/>
<spring:message code="socialProfile.delete" var="delete"/>
<spring:message code="socialProfile.confirm.delete" var="confirm" />



<%-- Display the following information about the Social Profile: --%>
	
	<jstl:out value="${msgNick}" />:
	<jstl:out value="${socialProfile.nick}" />
	<br /> 
	
	<jstl:out value="${msgSocialNetwork}" />:
	<jstl:out value="${socialProfile.socialNetwork}" />
	<br />
	
	<jstl:out value="${msgProfileLink}" />:
	<a href="${socialProfile.profileLink}"><jstl:out value="${socialProfile.profileLink}" /></a>
	<br />
	
	
	<spring:url var="editUrl"
		value="socialProfile/edit.do">
		<spring:param name="varId"
			value="${socialProfile.id}"/>
	</spring:url>
	<a href="${editUrl}"><jstl:out value="${edit}" /></a>
	
	<spring:url var="deleteUrl"
		value="socialProfile/delete.do">
		<spring:param name="varId"
			value="${socialProfile.id}"/>
	</spring:url>
	<a href="${deleteUrl}" onclick="return confirm('${confirm}')"><jstl:out value="${delete}" /></a>
	
	

	
