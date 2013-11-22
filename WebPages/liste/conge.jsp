<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.myapp.struts.events.Conge, steemploi.persistance.TableConge, java.util.List, steemploi.service.Utilisateur" %>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session" />
<%
        Object o = session.getAttribute("session_id");
        if (o == null || !(o instanceof Utilisateur)) 
        {%><logic:redirect  page="/login.jsp"  /><%
        		Long l = (Long) o;
        } %>
<h3>Congés</h3>
<table id="events_list">
<tr>
<td>Id</td>
<td>Nom</td>
<td>Date de début</td>
<td>Date de fin</td>
<td>Action</td>
<td>Action</td>
</tr>
<% 
String qs = request.getQueryString();
List<Conge> conges = new TableConge().findAll();
for(Conge conge: conges)
{%>

<tr>
<td><%= conge.getId() %></td>
<td><%= conge.getNom() %></td>
<td><%= String.format("%td/%tm/%tY", conge.getDateDebut(),conge.getDateDebut(),conge.getDateDebut()) %></td>
<td><%= String.format("%td/%tm/%tY", conge.getDateFin(),conge.getDateFin(),conge.getDateFin())  %></td>
<td><a class="actionButton" href="javascript:;" onclick="editSelected(<%= conge.getId() %>, 4, '<%=  qs %>')">Editer</a></td>
<td><a class="actionButton" href="javascript:;" onclick="removeSelected(<%= conge.getId() %>, 4, '<%=  qs %>')">Supprimer</a></td>
</tr>
<%}
if(conges.size()==0)
{%>
<tr><td colspan="5" style="text-align: center;">Aucun congé</td></tr>
<%}
%>
<tr><td colspan="5" style="text-align: center;">
<a class="actionButton" href="javascript:;" onclick="editSelected(0, <%=  qs %>)">
    Nouveau congé
    </a></td></tr>
</table>
