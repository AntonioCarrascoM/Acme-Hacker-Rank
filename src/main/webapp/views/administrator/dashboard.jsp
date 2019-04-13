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


<spring:message code="administrator.queryc1" var="queryc1" />
<spring:message code="administrator.queryc2" var="queryc2" />
<spring:message code="administrator.queryc3" var="queryc3" />
<spring:message code="administrator.queryc4" var="queryc4" />
<spring:message code="administrator.queryc5" var="queryc5" />
<spring:message code="administrator.queryc6" var="queryc6" />
<spring:message code="administrator.queryb1" var="queryb1" />
<spring:message code="administrator.queryb2" var="queryb2" />
<spring:message code="administrator.queryb3" var="queryb3" />

<spring:message code="administrator.return" var="returnMsg" />

<security:authorize access="hasRole('ADMIN')" >
	
	<%-- Displays the result of all required database queries --%>
	
	<table style="width:100%">
 		
  		<tr>
    		<td><jstl:out value="${queryc1}" /></td>
    		<td><jstl:out value="${avgMinMaxStddevPositionsPerCompany}" /></td> 
  		</tr>
  		
  		<tr>
    		<td><jstl:out value="${queryc2}" /></td>
    		<td><jstl:out value="${avgMinMaxStddevApplicationsPerHacker}" /></td> 
  		</tr>
  		
  		<tr>
    		<td><jstl:out value="${queryc3}" /></td>
    		<td><jstl:out value="${companiesWithMoreOfferedPossitions}" /></td> 
  		</tr>

  		<tr>
    		<td><jstl:out value="${queryc4}" /></td>
    		<td><jstl:out value="${hackersWithMoreApplications}" /></td> 
  		</tr>
  		
  		<tr>
    		<td><jstl:out value="${queryc5}" /></td>
    		<td><jstl:out value="${avgMinMaxStddevOfferedSalaries}" /></td> 
  		</tr>
  		
  		<tr>
    		<td><jstl:out value="${queryc6}" /></td>
    		<td><jstl:out value="${bestAndWorstPositions}" /></td> 
  		</tr>
  		
  		
    	<tr>
    		<td><jstl:out value="${queryb1}" /></td>
    		<td><jstl:out value="${minMaxAvgStddevCurriculaPerHacker}" /></td> 
  		</tr>
  		
  		<tr>
    		<td><jstl:out value="${queryb2}" /></td>
    		<td><jstl:out value="${minMaxAvgStddevResultsFinders}" /></td> 
  		</tr>
  		
  		<tr>
    		<td><jstl:out value="${queryb3}" /></td>
    		<td><jstl:out value="${ratioEmptyVersusNonEmptyFinders}" /></td> 
  		</tr>
  		
  		
	</table>
	
	<a href="welcome/index.do"><jstl:out value="${returnMsg}" /></a>

</security:authorize>