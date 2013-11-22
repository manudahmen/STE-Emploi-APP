<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap, java.util.Map, com.myapp.struts.events.Evt, steemploi.ui.DateFormat, steemploi.persistance.TableEvt, java.util.ArrayList, java.util.Date,java.lang.Long,java.util.Calendar,java.util.Locale,steemploi.ui.*,steemploi.service.*, steemploi.persistance.*" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session"/>
<?xml version="1.0" encoding="UTF-8" ?>
<%
        Object o = session.getAttribute("user");
        if (o == null || !(o instanceof Utilisateur)) 
        {%><logic:redirect  page="/login.jsp"  /><%
        		Long l = (Long) o;
        } 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Evènement</title>
<link type="text/css" href="../stylex/style_evt.css" media="screen" rel="stylesheet" />
</head>
<body>
<%
int id=Integer.parseInt(request.getParameter("id"));
String type = request.getParameter("type");

Evt e = null;
if (type.equals("evt")) {
    e = new TableEvt().findById(id); %>
<h1>Evènement</h1>
    <p>Date: <%= String.format("%td/%tm/%tY", e.getDateDebut(),e.getDateDebut(),e.getDateDebut()) %></p>
<table id="eventview">
    <tr><td>Nom</td><td><%= e.getName() %></td></tr>
    <tr><td>Lieu</td><td><%= e.getLocation()%></td></tr>
    <tr><td>Description</td><td><%= e.getDescription()%></td></tr>
    <tr><td>Lien</td><td>
    
    <% 
    	if(e.getLink()==null || e.getLink().equals("") || !e.getLink().startsWith("http://")) { %>
    -
    <% } else { %>
    <a href="<%= e.getLink()%>" target="NEW"><%= e.getLink()%></a>
    <% } %>
    
    </td></tr>
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