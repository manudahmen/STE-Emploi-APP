<%-- 
    Document   : login
    Created on : 27-avr.-2009, 8:11:03
    Author     : manuel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.myapp.struts.Getter, java.util.Calendar, steemploi.service.Utilisateur,steemploi.service.TypeUtilisateur" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" href="styles/styles.css" media="screen" rel="stylesheet" />
        <title><bean:message key="welcome.page" /></title>
    </head>
    <body>
		<h1><bean:message key="welcome.title" /></h1> 
        <h2 style="text-align: center"><bean:message key="welcome.page" /></h2>
  		<p style="text-align: right;"><%= (session.getAttribute("session_id")==null ? "Aucune session ouverte"  : "<a href='"+(((Utilisateur)session.getAttribute("user")).getType().equals(TypeUtilisateur.ADMIN)?"admin/usersmanager.jsp":"index_agenda.jsp")+"'>Session ouverte: " + ((Utilisateur)session.getAttribute("user")).getUsername()+"</a>")%></p>
        <html:form action="/Login" styleClass="loginform">
		<table id="login_form">
        <tr><td colspan="2" class="title">Entrez votre nom d'utilisateur et votre mot de passe</td></tr>
		<tr><td class="property"><html:errors/></td><td></td></tr>
        <tr><td>Nom d'utilisateur</td><td class="property"><html:text property="username"/></td></tr>
        <tr><td>Mot de passe</td><td class="property"><html:password property="password"/></td></tr>
		<tr><td></td><td class="property">
		<input type="hidden" name="action" value="<%= new Getter().encode("index_agenda.jsp", new String[] {"page", "agenda", "millis", ""+Calendar.getInstance().getTimeInMillis()}) %>"/>
        <html:submit value="Login" styleClass="submit"  />
		</td></tr>
        </table>
        
        </html:form>

    </body>
</html>
