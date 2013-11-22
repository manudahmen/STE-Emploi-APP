<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap, java.util.Map, steemploi.service.Echeance, steemploi.ui.DateFormat, steemploi.persistance.TableConge, java.util.ArrayList, java.util.Date,java.lang.Long,java.util.Calendar,java.util.Locale,steemploi.ui.*,steemploi.service.*, steemploi.persistance.*,com.myapp.struts.events.Conge" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session"/>
<%
        Object o = session.getAttribute("user");
        if (o == null || !(o instanceof Utilisateur)) 
        {%><logic:redirect  page="/login.jsp"  /><%
        		Long l = (Long) o;
        }
%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Congé</title>
<link type="text/css" href="../stylex/style_evt.css" media="screen" rel="stylesheet" />
</head>
<body>
<%
int id=Integer.parseInt(request.getParameter("id"));
String type = request.getParameter("type");

Conge e = null;
e = new TableConge().findById(id); 


String day_str = request.getParameter("day");
int day =0;
if(day_str!=null)
{
	day = Integer.parseInt(day_str);
	Calendar date = Calendar.getInstance();
	date.setTimeInMillis(day);
	e.setDateDebut(date);
	e.setDateFin(date);
}


%>




<h1>Congé</h1>
<p>du 
<%= String.format("%td/%tm/%tY", e.getDateDebut(),e.getDateDebut(),e.getDateDebut())%>

au 
<%= String.format("%td/%tm/%tY", e.getDateFin(),e.getDateFin(),e.getDateFin())%>
</p>
<h3><%= e.getNom() %></h3>
<% 
if(user.getFormateur()!=null) 
{
%>
<a onclick="top.frames[1].close();document.location.href=this.href;" href="../index_agenda.jsp?page=creerEvenement&eventtype=4&id=<%= e.getId() %>"><h3>Edition de l'évènement</h3></a>
<% 
}%>
</body>
</html>