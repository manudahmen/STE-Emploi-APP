<%--
    Document   : etudiant.jsp
    Created on : 07-avr.-2009, 19:25:46
    Author     : manuel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.myapp.struts.Getter, steemploi.service.*, java.util.Calendar, steemploi.service.TypeUtilisateur"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session"/>
<jsp:useBean id="g" class="com.myapp.struts.Getter" scope="request"/>
<%
g.setObject(user);
%>

<bean:define id="user1" name="g" property="object" type="steemploi.service.Utilisateur" scope="request"/>
<h1 id="titrePage">Changer vos coordonnées</h1>

<html:form action="/EditUtilisateur.do">

<table>
<tr>
<td>
</td>
<td><h2><%= user1.getUsername() %></h2>
</td>
</tr>
<tr>
<td>Nom
</td>
<td><html:text name="user1" property="nom"/>
</td>
</tr>
<tr>
<td>Prénom
</td>
<td><html:text  name="user1" property="prenom"/>
</td>
</tr>
<tr>
<td>Rue
</td>
<td><html:text  name="user1" property="rue"/>
</td>
</tr>
<tr>
<td>Boîte
</td>
<td><html:text  name="user1" property="boite"/>
</td>
</tr>
<tr>
<td>Numéro
</td>
<td><html:text  name="user1" property="numero"/>
</td>
</tr>
<tr>
<td>Code postal
</td>
<td><html:text  name="user1" property="codepostal"/>
</td>
</tr>
<tr>
<td>Ville
</td>
<td><html:text  name="user1" property="ville"/>
</td>
</tr>
<tr>
<td>Pays
</td>
<td><html:text  name="user1" property="pays"/>
</td>
</tr>
<tr>
<td>Téléphone
</td>
<td><html:text  name="user1" property="tel"/>
</td>
</tr>
<tr>
<td>GSM
</td>
<td><html:text  name="user1" property="gsm"/>
</td>
</tr>
<tr>
<td>Adresse email
</td>
<td><html:text  name="user1" property="email"/>
</td>
</tr>
<tr>
<td>
</td>
<td><html:submit  value="Enregistrer"/>
</td>
</tr>
</table>
<html:hidden name="user1" property="id"/>
<input type="hidden" name="action" value="index_agenda.jsp?page=editUtilisateur"/> 
</html:form>