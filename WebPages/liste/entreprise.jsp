<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.myapp.struts.events.PEntreprise, steemploi.persistance.TablePEntreprise, java.util.List, steemploi.service.Utilisateur" %>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session" />
<%
        Object o = session.getAttribute("session_id");
        if (o == null || !(o instanceof Utilisateur)) 
        {%><logic:redirect  page="/login.jsp"  /><%
        		Long l = (Long) o;
        } %>
<h3>Présentations d'entreprises</h3>
<table id="events_list">
<tr>
<td>Id</td>
<td>Nom</td>
<td>Date de présentation</td>
<td>Téléphone</td>
<td>Site web</td>
<td>Email</td>
<td>Action</td>
<td>Action</td>
</tr>
<% 
String qs = request.getQueryString();
List<PEntreprise> entreprises = new TablePEntreprise().findAll();
for(PEntreprise entreprise: entreprises)
{%>
<tr>
<td><%= entreprise.getId() %></td>
<td><%= entreprise.getNom() %></td>
<td><%= String.format("%td/%tm/%tY", entreprise.getDateDebut(),entreprise.getDateDebut(),entreprise.getDateDebut()) %></td>
<td><%= entreprise.getTelephone() %></td>
<td><%= entreprise.getUrl() %></td>
<td><%= entreprise.getEmail() %></td>
<td><a class="actionButton" href="javascript:;" onclick="editSelected(<%= entreprise.getId() %>, 3, '<%=  qs %>')">Editer</a></td>
<td><a class="actionButton" href="javascript:;" onclick="removeSelected(<%= entreprise.getId() %>, 3, '<%=  qs %>')">Supprimer</a></td>
</tr>
<%}
if(entreprises.size()==0)
{%>
<tr><td colspan="5" style="text-align: center;">Aucune présentation d'entreprise</td></tr>
<%}
%>
<tr><td colspan="5" style="text-align: center;">
    <a class="actionButton" href="javascript:;" onclick="editSelected(0, <%=  qs %>)">
        Nouvel évènement
    </a></td></tr>
</table>
