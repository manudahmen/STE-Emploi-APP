<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.myapp.struts.events.Conge, steemploi.persistance.TableConge, java.util.List, steemploi.service.Utilisateur" %>

<%@page import="steemploi.persistance.TableEcheance"%>
<%@page import="steemploi.service.Echeance"%><jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session" />
<%
        Object o = session.getAttribute("session_id");
        if (o == null || !(o instanceof Utilisateur)) 
        {%><logic:redirect  page="/login.jsp"  /><%
        		Long l = (Long) o;
        } %>
<h3>Echeances</h3>
<table id="events_list">
<tr>
<td>Id</td>
<td>Titre</td>
<td>Date</td>
<td>Description</td>
<td>Action</td>
<td>Action</td>
</tr>
<% 
String qs = request.getQueryString();
List<Echeance> echeances = new TableEcheance().findAll();
for(Echeance echeance: echeances)
{%>

<tr>
<td><%= echeance.getId() %></td>
<td><%= echeance.getTitle() %></td>
<td><%= echeance.getDescription() %></td>
<td><%= String.format("%td/%tm/%tY", echeance.getDate(),echeance.getDate(),echeance.getDate()) %></td>
<td><a class="actionButton" href="javascript:;" onclick="editSelected(<%= echeance.getId() %>)">Editer</a></td>
<td><a class="actionButton" href="javascript:;" onclick="removeSelected(<%= echeance.getId() %>)">Supprimer</a></td>
</tr>
<%}
if(echeances.size()==0)
{%>
<tr><td colspan="5" style="text-align: center;">Aucune échéance</td></tr>
<%}
%>
<tr><td colspan="5" style="text-align: center;">
<a class="actionButton" href="javascript:;" onclick="editSelected(0, <%=  qs %>)">
    Nouvelle échéance
    </a></td></tr>
</table>
