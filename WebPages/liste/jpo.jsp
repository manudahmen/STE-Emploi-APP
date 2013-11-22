<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.myapp.struts.events.JPO, steemploi.persistance.TableJPO, java.util.List, steemploi.service.Utilisateur" %>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session" />
<%
        Object o = session.getAttribute("session_id");
        if (o == null || !(o instanceof Utilisateur)) 
        {%><logic:redirect  page="/login.jsp"  /><%
        } %>
<h3>JPOs</h3>
<table id="events_list">
<tr>
<td>Id</td>
<td>Nom</td>
<td>Date</td>
<td>Action</td>
<td>Action</td>
</tr>
<% 
String qs = request.getQueryString();
List<JPO> jpos = new TableJPO().findAll();
for(JPO jpo: jpos)
{%>
<tr>
<td><%= jpo.getId() %></td>
<td><%= jpo.getName() %></td>
<td><%= String.format("%td/%tm/%tY", jpo.getDateDebut(),jpo.getDateDebut(),jpo.getDateDebut()) %></td>
<td><a class="actionButton" href="javascript:;" onclick="editSelected(<%= jpo.getId() %>, 2, '<%=  qs %>')">Editer</a></td>
<td><a class="actionButton" href="javascript:;" onclick="removeSelected(<%= jpo.getId() %>, 2, '<%=  qs %>')">Supprimer</a></td>
</tr>
<%}
if(jpos.size()==0)
{%>
<tr><td colspan="5" style="text-align: center;">Aucune JPO</td></tr>
<%}
%>
<tr><td colspan="5" style="text-align: center;"><a class="actionButton" href="javascript:;" onclick="editSelected(0, <%=  qs %>)">Nouvelle JPO</a></td></tr>
</table>
