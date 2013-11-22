<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session" />
<%
String idStr=request.getParameter("id");
int id=Integer.parseInt(idStr);
String name = "jours_supp_" + id; 
String li = "li_jours_supp_" + id;
%>
<li id="<%= li %>">
<table>
<tr>
<td>
Date du jour de congé
</td>
<td>
<input type="text" name="<%= name %>" id="<%= name %>" size="11" onblur="dp_dateFormat='dd/mm/yyyy';magicDate(this)" onfocus="if (this.className != 'error') this.select()" /> <a href="javascript:void(0);" onclick="g_Calendar.show(event, '<%= name %>', 'dd/mm/yyyy')" title="Show popup calendar"><img src="js/cal/calendar.gif" border="0" /></a>(jj/mm/aaaa)
</td>
</tr>
<tr><td>Nom du congé</td><td><input type="text" name="name_<%= name %>" /></td></tr>
</table>
<input type="button" value="Supprimer" onclick="supprimerJour('<%= li %>')" />
</li>