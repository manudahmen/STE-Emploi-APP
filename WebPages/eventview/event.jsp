<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap, java.util.Map, steemploi.service.Echeance, steemploi.ui.DateFormat, steemploi.persistance.TableEcheance, java.util.ArrayList, java.util.Date,java.lang.Long,java.util.Calendar,java.util.Locale,steemploi.ui.*,steemploi.service.*, steemploi.persistance.*" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session"/>
<%
        Object o = session.getAttribute("user");
        if (o == null || !(o instanceof Utilisateur)) 
        {%><logic:redirect  page="/login.jsp"  /><%
        		Long l = (Long) o;
        } %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Evènement</title>
<link type="text/css" href="../stylex/style_evt.css" media="screen" rel="stylesheet" />
</head>
<body>
<%
int etudiant_id = -1;
int sf_id = -1;
if(user.getType().equals(TypeUtilisateur.ETUDIANT))
{
		etudiant_id = user.getEtudiant().getId();
		sf_id = user.getEtudiant().getSf().getId();
}
int id=Integer.parseInt(request.getParameter("id"));
String type = request.getParameter("type");

Echeance e = null;
if (type.equals("echeance")) {
    e = new TableEcheance().findById(id);
    if(user.getType().equals(TypeUtilisateur.ETUDIANT))
    {
    	boolean ok = false;
    	for(SessionsFormations sf : e.getFormations())
    	{
    		if(sf.getId()==sf_id)
    			ok=true;
    	}
    	for(Etudiant et : e.getEtudiants())
    	{
    		if(et.getId()==etudiant_id)
    			ok=true;
    	}
    
    	if(!ok) return;
    }
    %>
<h1>Echéance</h1>
    <p>Date: <%= String.format("%td/%tm/%tY", e.getDate(),e.getDate(),e.getDate()) %></p>
<table id="eventview">
    <tr><td>Nom</td><td><strong> <%= e.getTitle() %> </strong></td></tr>
<% 
if(user.getFormateur()!=null) 
{
%>
	<tr><td colspan="2"> Etudiants et/ou formations concernés </td></tr>
	<% for(SessionsFormations f : e.getFormations()) { 
	f = new TableSessionsFormations().findById(f.getId());
	%>
	<tr><td>Formation</td><td><%= f.getDateStart().get(Calendar.YEAR)+" " + f.getDateStart().getDisplayName(Calendar.MONTH, 1, Locale.getDefault()) + " | " +f.getName() %></td></tr>
	<%} %>
	<% for(Etudiant et: e.getEtudiants()) { 
	et = new TableEtudiants().findById(et.getId());
	%>
	<tr><td>Etudiant</td><td><%= et.getPrenom() + " " +et.getNom() %></td></tr>
	<%} %>
<% } %>
    <tr><td>Description</td><td><span style="font-size: larger;"><%= e.getDescription()%></span></td></tr>
    <tr><td>Code</td><td><%= e.getCode()%></td></tr>
</table>
    <%}%>
<% 
if(user.getFormateur()!=null) 
{
%>
<a href="../index_agenda.jsp?page=creerEvenement&eventtype=5&id=<%= e.getId() %>"><h3>Edition de l'évènement</h3></a>
<% 
}%>
</body>
</html>