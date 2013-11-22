<%-- 
    Document   : formationsList1
    Created on : 05-mai-2009, 15:58:52
    Author     : manuel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page
	import="steemploi.service.*, steemploi.persistance.*, java.util.List, java.util.Calendar, java.util.Locale, steemploi.service.Utilisateur"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session" />
<%
        Object o = session.getAttribute("user");
        if (o == null || !(o instanceof Utilisateur)) 
        {%><logic:redirect  page="/login.jsp"  /><%
        		Long l = (Long) o;
        } 

        List<SessionsFormations> sessions;
        sessions = new TableSessionsFormations().findAll(true);
%>

<div id="formation_sel_div" class="listeChoixDiv">
<p>Formations</p> 

<p><select id="formations_sel" size="8" multiple="true" class="largeur"
onchange="javascript:formationChanged()"
>
	<option value="-1" style="font-weight: bold;">Choisissez une formation</option>
	<% for (SessionsFormations sf: sessions) {%>
	<option  
		value="<%= sf.getId()%>" 
		title="<%= String.format("%td/%tm/%tY",  sf.getDateStart(),  sf.getDateStart() ,  sf.getDateStart()) + " " + sf.getFormation().getNom() + " - " + sf.getName()%>">
	<%= String.format("%td/%tm/%tY",  sf.getDateStart(),  sf.getDateStart() ,  sf.getDateStart()) + " " + sf.getFormation().getNom() + " - " + sf.getName()%></option>
	<% }%>
</select></p>

<p>
<input type="button" value="Ajouter" class="largeur"
	onclick="javascript:ajouterFormation()">
</p>
</div>
<div id="etudiant_sel_div" class="listeChoixDiv" >
<p>Etudiants</p>
<p> <select id="etudiants_sel" multiple="true" size="8" class="largeur">
</select></p> 
<p><input type="button" value="Ajouter" class="largeur"
	onclick="javascript:ajouterEtudiant()"></p>
</div>
<div id="selected_div" class="listeChoixDiv">
<p>Formations et étudiants sélectionnés</p>
<p>
<select id="selected" multiple="true" size="8" class="largeur" onmouseover="valide(validateFrame, 1, 'selected_div')">
</select>
</p>
<p><input type="button" value="Supprimer" onclick="javascript:supprimer()" class="largeur"/></p>
</div>
<hr style="clear: left;"/>