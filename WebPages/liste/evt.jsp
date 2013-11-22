<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.myapp.struts.events.Evt, steemploi.persistance.TableEvt, java.util.List, steemploi.service.Utilisateur" %>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session" />
<%
        Object o = session.getAttribute("session_id");
        if (o == null || !(o instanceof Utilisateur)) 
        {%><logic:redirect  page="/login.jsp"  /><%
        		Long l = (Long) o;
        } %>
<h3>Evènements</h3>
<table id="events_list">
<tr>
<td>Id</td>
<td>Nom</td>
<td>Lieu</td>
<td>Date de début</td>
<td>Date de fin</td>
<td>Action</td>
<td>Action</td>
</tr>
<% 
String qs = request.getQueryString();
List<Evt> evts = new TableEvt().findAll();
for(Evt evt: evts)
{%>
<tr>
<td><%= evt.getId() %></td>
<td><%= evt.getName() %></td>
<td><%= evt.getLocation() %></td>
<td><%= String.format("%td/%tm/%tY", evt.getDateDebut(),evt.getDateDebut(),evt.getDateDebut()) %></td>
<td><%= String.format("%td/%tm/%tY", evt.getDateFin(),evt.getDateFin(),evt.getDateFin())  %></td>
<td><a class="actionButton" href="javascript:;" onclick="editSelected(<%= evt.getId() %>, 5, '<%=  qs %>')">Editer</a></td>
<td><a class="actionButton" href="javascript:;" onclick="removeSelected(<%= evt.getId() %>, 5, '<%=  qs %>')">Supprimer</a></td>
</tr>
<%}
if(evts.size()==0)
{%>
<tr><td colspan="5" style="text-align: center;">Aucun évènements</td></tr>
<%}
%>
<tr><td colspan="5" style="text-align: center;"><a class="actionButton" href="javascript:;" onclick="editSelected(0, <%=  qs %>)">Nouvel évènement</a></td></tr>
</table>
