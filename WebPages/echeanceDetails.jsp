<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@page
	import="java.util.Calendar, java.util.Locale, com.myapp.struts.Getter, steemploi.persistance.*, steemploi.service.*"%><%@ page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="steemploi.persistance.TableFormations"%>
<%@page import="steemploi.persistance.TableEtudiants"%><jsp:useBean
	id="g" class="com.myapp.struts.Getter" scope="request" />
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session" />

<%
	int formation_id = -1;
	int etudiant_id = -1;
	if (user.getType() == TypeUtilisateur.FORMATEUR) {
		String formation_id_str = request
		.getParameter("formation_id");
		String etudiant_id_str = request
		.getParameter("etudiant_id");
		if(formation_id_str!=null)
			formation_id = Integer.parseInt(formation_id_str);
		if(etudiant_id_str!=null)
		etudiant_id = Integer.parseInt(etudiant_id_str);
		request.setAttribute("formation_id", formation_id);
		request.setAttribute("etudiant_id", etudiant_id);
	}
%>

<%
String idStr = request.getParameter("id");
Echeance e = new Echeance();
if(idStr!=null)
{
	idStr = (String) request.getParameter("id");
	int id = Integer.parseInt(idStr);
	e = new TableEcheance().findById(id);
}
for(int i=0; i<e.getFormations().size(); i++)
{
	SessionsFormations f = e.getFormations().get(i);
	int id=f.getId();
	e.getFormations().set(i, new TableSessionsFormations().findById(id));
}

for(int i=0; i<e.getEtudiants().size(); i++)
{
	Etudiant et = e.getEtudiants().get(i);	
	e.getEtudiants().set(i, new TableEtudiants().findById(et.getId()));
}

g.setObject(e);
%>
<bean:define id="echeance" name="g" property="object" />


<table>


	<tr>
		<td>ID</td>
		<td><bean:write name="echeance" property="id" /></td>
	</tr>
	<tr>
		<td>Code</td>
		<td><bean:write name="echeance" property="code" /></td>
	</tr>
	<tr>
		<td>Titre</td>
		<td><bean:write name="echeance" property="title" /></td>
	</tr>
	<tr>
		<td>Description</td>
		<td><bean:write name="echeance" property="description" /></td>
	</tr>
	<tr>
		<td>Date</td>
		<td><bean:write name="echeance" property="date_j" /> <%= e.getDate().getDisplayName(Calendar.MONTH,  1, Locale.getDefault()) %>
		<bean:write name="echeance" property="date_a" /></td>
	</tr>
	<% for(Etudiant et : e.getEtudiants()) { %>
	<tr>
		<td>Etudiant</td>
		<td><%= et.getNom() + " " +et.getPrenom() %></td>
	</tr>
	<% } %>
	<% for(SessionsFormations f : e.getFormations()) { %>
	<tr>
		<td>Formation</td>
		<td><%= f.getFormation().getNom()+" | " + f.getDateStart().get(Calendar.YEAR) + "/" + (f.getDateStart().get(Calendar.MONTH)+1) %></td>
	</tr>
	<% } %>
	<tr>
		<td>Edition</td>
		<td><a class="actionButton"
			href="?page=echeance&id=<bean:write name="echeance" property="id"/>&amp;formation_id=<%= formation_id %>&amp;etudiant_id=<%= etudiant_id %>">Editer</a></td>
	</tr>
	<tr>
		<td>Suppression</td>
		<td><a class="actionButton"
			href="index_agenda.jsp?<%= request.getQueryString().replace("page=echeanceDetails", "page=deleteEcheance") %>">Supprimer</a></td>
	</tr>
	<tr>
		<td></td>
		<td><a class="actionButton"
			href="?page=agenda&amp;<%= request.getQueryString().replace("?page=echeanceDetails", "page=deleteEcheance") %>">Retour</a></td>
	</tr>
</table>