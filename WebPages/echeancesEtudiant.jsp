<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*, com.myapp.struts.events.Conge, steemploi.persistance.TableConge, java.util.List, steemploi.service.Utilisateur" %>

<%@page import="steemploi.persistance.TableEcheance"%>
<%@page import="steemploi.service.Echeance"%>
<%@page import="steemploi.service.GetAgendaEventsEtudiant"%>
<%@page import="steemploi.service.TypeUtilisateur"%><jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session" />
<%
        Object o = session.getAttribute("user");
        if (o == null || !(o instanceof Utilisateur) || !(((Utilisateur)(o)).getType().equals(TypeUtilisateur.ETUDIANT)))
        {%><logic:redirect  page="/login.jsp"  /><%
        		Long l = (Long) o;
        } %>
<h1 id="titrePage">Echéances</h1>
<table id="events_list">
<tr>
<td>Titre</td>
<td>Description</td>
<td>Date</td>
</tr>
<% 
String qs = request.getQueryString();
Calendar date1 = Calendar.getInstance();
Calendar date2 = Calendar.getInstance();
date1.add(Calendar.YEAR, -5);
date2.add(Calendar.YEAR, 1);

GetAgendaEventsEtudiant get = new GetAgendaEventsEtudiant(date1, date2, user.getEtudiant());
Map<String, ArrayList<Echeance>> echeancesMap  = get.getEcheances();
Iterator<Map.Entry<String, ArrayList<Echeance>>> it = echeancesMap.entrySet().iterator();
while(it.hasNext())
{
	Map.Entry<String, ArrayList<Echeance>> echeanceArray = it.next();
	ArrayList<Echeance> echeances = echeanceArray.getValue();
	for(Echeance echeance : echeances)
	{
%>

<tr>
<td><%= echeance.getTitle() %></td>
<td><%= echeance.getDescription() %></td>
<td><%= String.format("%td/%tm/%tY", echeance.getDate(),echeance.getDate(),echeance.getDate()) %></td>
</tr>
<%}}
if(echeancesMap.size()==0)
{%>
<tr><td colspan="5" style="text-align: center;">Aucune échéance</td></tr>
<%}
%>
</table>
