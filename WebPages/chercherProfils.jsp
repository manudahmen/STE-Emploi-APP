<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.myapp.struts.Getter, com.myapp.struts.events.Conge, steemploi.persistance.TableConge, steemploi.service.*" %>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session" />
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="g" class="com.myapp.struts.Getter" scope="request"></jsp:useBean>
<%
        Object o = session.getAttribute("user");
        if (o == null || !(o instanceof Utilisateur) || !(((Utilisateur)(o)).getType().equals(TypeUtilisateur.FORMATEUR)))
        {%><logic:redirect  page="/login.jsp"  /><%
        		Long l = (Long) o;
        } %>
<h1 id="titrePage">Recherche des profils</h1>
<form action="ChercherProfils.servlet">
	<table>
		<tr>
			<td>
				Langages et technologies connus
			</td>
			<td>
				<input type="text" name="nom" maxlength="100" size="100"/>
			</td>
		</tr>
		<tr>
			<td>
				
			</td>
			<td>
				<input type="hidden" name="action" value="index_agenda.jsp?page=chercherProfils"/>
				<input type="submit" value="Chercher des profils correspondants"/>
			</td>
		</tr>
	</table>
</form>
<%

%>