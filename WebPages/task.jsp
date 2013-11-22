<%-- 
    Document   : task
    Created on : 22-avr.-2009, 17:49:20
    Author     : Manuel Dahmen
--%>

<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@page import="steemploi.persistance.*, java.util.*, steemploi.service.*,com.myapp.struts.*" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session"/>
<jsp:useBean id="g" scope="request" class="com.myapp.struts.Getter"/>
<style>
ul#onglets
{
	margin:0px;
	margin-top: 4px; 
	padding:0px;
	padding-bottom: 10px;
	list-style: none;
	border-bottom: thick dotted gray; 
}
ul#onglets a
{
padding: 2px; border: medium dotted gray; background-color: #aaa; color: maroon; text-decoration: none;
}
ul#onglets li
{
	display: inline; 
	margin-left: 4px; 
	margin-right: 4px; 
	margin-bottom: 2px;
}
</style>
<script type="text/javascript">
	function openTab(i)
	{
		var subs = document.getElementsByClassName("ongletDivs");
		for(var no=0; no<subs.length; no++)
		{
			subs[no].style.display="none";
		}
		if(i!=-1)
		{
			subs[i].style.display="block";
		}
	}
	function openTab0()
	{
			openTab(0);
	}
	<%if("entreprisedetails".equals(request.getParameter("form"))) { %>
	window.onload=openTab0;
	<% } %>
</script>
<%
int etudiant_id = user.getEtudiant().getId();

        String form = request.getParameter("form");
        if (form == null || form.equals("")) {
            form = "none";
        }
        String entreprise_idStr = request.getParameter("id");
        int entreprise_id = 0;
        if (entreprise_idStr == null || form.equals("")) {
        } else {
            entreprise_id = Integer.parseInt(entreprise_idStr);
        }

        TableCodeCategorieTache codesTaches = new TableCodeCategorieTache();
       List<CategoriesTache> codes = codesTaches.findAll();

        TableEntreprise entrepriseTable = new TableEntreprise();
        List<Entreprise> es = entrepriseTable.findByEtudiantId(user.getEtudiant().getId());

%>
<h1 id="titrePage">Entreprises, contacts et tâches</h1>
	<div class="tasks">
        <h2>Entreprises et contacts</h2>
		<script type="text/javascript">
	function searchEntreprise(text)
	{
		document.getElementById("searchresult").innerHTML="";
		if(text.length<3)
		{
			return true;
		}
		text = text.toLowerCase();
		var names = document.getElementsByClassName("item");
		var paragraphs = document.getElementsByClassName("linkEntrepriseA");
		for(var no =0; no<names.length; no++)
		{
			var name = names[no].innerHTML;
			nameL = name.toLowerCase().trim();
			if(nameL.indexOf(text)>=0 && text.length>=3)
			{
				var p2 = paragraphs[no].cloneNode(true);
				p2.className="";
				p2.firstChild.className="searchResult";
				var li = document.createElement("LI");
				li.appendChild(p2);
				document.getElementById("searchresult").appendChild(li);
			}
		}
	}
</script>
<p>Chercher une entreprise
<input type="text" name="searchentreprise" id="searchentreprise" onchange="searchEntreprise(this.value);" onkeydown="searchEntreprise(this.value);" onkeyup="searchEntreprise(this.value);"/>
</p>
<ul id="searchresult"></ul>
        <ul class="entreprise">
            <li><a href="?page=task&form=editentreprise&entreprise_id=0" class="actionButton">Nouvelle entreprise</a></li>
            <% for (int i = 0; i < es.size(); i++) {
            Entreprise e = es.get(i);%>
            <li>
				<p class="linkEntrepriseA"><a class="item" href="index_agenda.jsp?page=task&amp;form=entreprisedetails&amp;id=<%= e.getId()%>" onclick="javascript:selectEntreprise(this.name)" title="Voir les informations sur l'entreprise">
						<%= e.getNom()%>
					</a></p>
            </li>
            <% }%>
        </ul>
    </div>
    <div id="droiteentreprises">
        <% 
        if (form.equals("tasks")) {

        entreprise_idStr = request.getParameter("entreprise_id");
        entreprise_id = Integer.parseInt(entreprise_idStr);
        TableEntreprise tableEntreprise = new TableEntreprise();
        Entreprise entreprise = tableEntreprise.findById(entreprise_id);
	     int task_id = Integer.parseInt(request.getParameter("id"));
    	 Tache tache1 = new Tache();
     if (task_id == 0) {
         tache1 = new Tache();
         tache1.setId(0);
         tache1.setPercent(100);
     } else {
         TableTaches table= new TableTaches();
         tache1 = table.findById(task_id);
     }
tache1.setEtudiant_id(etudiant_id);
tache1.setEntreprise_id(entreprise_id);
tache1.setContact_id(0);
ArrayList<Contact> contacts = (ArrayList<Contact>) new TableContact().findByEntreprise(etudiant_id, entreprise_id);
tache1.setDateCompleted_(String.format("%td/%tm/%tY", tache1.getDateCompleted(),tache1.getDateCompleted(),tache1.getDateCompleted()));
%>
<jsp:useBean id="t" scope="request" class="com.myapp.struts.Getter"/>
  	<%   
  		t.setObject(tache1);
	%>
        <bean:define id="tache" type="steemploi.service.Tache" name="t" property="object"  scope="request" />
        <h2>Tâche pour : <%= entreprise.getNom() %></h2>
		<p><a href="?page=task&form=listtask&entreprise_id=<%= entreprise_id %>" title="Retour à la liste des tâches" class="actionButton">Retour</a></p>
<script src="js/validator/validator.js" type="text/javascript"></script>
<script src="js/validator/edittask/configurator_example.js" type="text/javascript"></script>
  <div id="errorwarning"><strong>Erreurs dans le formulaire!</strong> Corrigez les erreurs en rouge</div> 
       <html:form action="/Task.do" onsubmit="checkTask();">
            <table class="entreprise">
            <tr><td colspan="2">
             <logic:equal name="tache" property="id" value="0">
                <h2>Nouvelle tâche</h2>
            </logic:equal>
            <logic:notEqual name="tache" property="id" value="0">
                <h2>Editer une tâche</h2>
            </logic:notEqual>
            </td></tr>    
            <tr><td>
            <label for="code" id="codeL" class="fixedLabel">Code:</label>
            </td><td>
                <html:select name="tache" property="code"  onkeydown="return blockEnter(event)" styleId="code">
                    <option value="-1">Choisissez un type de tâche</option>
                    <% for (int i = 0; i < codes.size(); i++) {%>
                    <option value="<%= codes.get(i).getId()%>" <%= ((Tache) t.getObject()).getCode() == codes.get(i).getId() ? " selected " : ""%> >
                    	<%= codes.get(i).getTitle()%>
                    </option>
                            <% }%>
                        </html:select>
                        </td></tr>
                <tr><td>
                <label for="title" id="titleL" class="fixedLabel">Titre:</label>
                </td>
                <td><html:text name="tache" property="title" onkeydown="return blockEnter(event)" styleId="title"/></td></tr>
                <tr><td>
                <label for="description" id="descriptionL" class="fixedLabel">Description:</label>
                </td>
                <td><html:textarea name="tache" property="description" onkeydown="return blockEnter(event)" styleId="description">
                    <bean:write name="tache" property="description"/>
                </html:textarea></td></tr>
				<!-- <tr><td>
                <label for="percent" id="percentL" class="fixedLabel">Pourcentage:</label>
				</td> -->
                <!-- <td> --><html:hidden name="tache" property="percent" onkeydown="return blockEnter(event)" styleId="percent" value="100"/><!-- 
                </td></tr> -->
                <tr><td>
                <label for="dateCompleted_" id="dateCompleted_L" class="fixedLabel">Date d'achèvement:</label>
				</td>
                <td><html:text name="tache" property="dateCompleted_" onkeydown="return blockEnter(event)" styleId="dateCompleted_"/>
</td></tr>
				<% if(contacts.size()>0) { %>
				<tr><td>Contact:</td><td>
                <select name="contact_id" >
                	<% for(Contact c : contacts)
                		{
                	%>
                	<option value="<%=c.getId() %>" <%= c.getId()==tache1.getId()?"selected='true'":"" %> >
                		<%= c.getPrenom()+ " " + c.getNom() %>
                	</option>
                	<% } %>
                </select>
                </td></tr>
            	<% } %>
				<tr><td></td><td><a href="#" onclick="checkTask();"  class="submitButton">Enregistrer tâche</a>
				</td></tr>
</table>
                <html:hidden name="tache" property="id"/>
                <html:hidden name="tache" property="etudiant_id"/>
                <html:hidden name="tache" property="entreprise_id"/>
                <input type="hidden" name="action" value="index_agenda.jsp?page=task&form=listtask&entreprise_id=<%= entreprise_id %>"/>
</html:form>
        <% 
        
        
        
        } else if (form.equals("editentreprise")) {%>
<script src="js/validator/validator.js" type="text/javascript"></script>
<script src="js/validator/task/configurator_example.js" type="text/javascript"></script>
  <div id="errorwarning"><strong>Erreurs dans le formulaire!</strong> Corrigez les erreurs en rouge</div> 
<h2>Modifier les coordonnées d'entreprise<%= entreprise_id == 0 ? " ( nouvelle entreprise ) " : ""%></h2>
<form action="index_agenda.jsp" method="get" name="e">
<input type="hidden" name="page" value="entreprises"/>
<input type="hidden" name="form" value="entreprisedetails"/>
<input type="hidden" name="id" value="<%= entreprise_id %>"/>
</form>
       <html:form action="/Entreprise" styleId="EntrepriseForm" onsubmit="return checkEntreprise();">
            <%
     int e_id = 0;
     Entreprise enB;
     if (entreprise_id != 0) {
         e_id = entreprise_id;
         enB = new TableEntreprise().findById(e_id);
     } else {
         enB = new Entreprise();
     }
	 entreprise_id = enB.getId();
     g.setObject(enB);
            %>
            <% if(e_id!=0){ %>
            <p><a href="/SupprimerEntreprise.do" class="submitButton">Supprimer cette entreprise</a></p>
            <% } %>
            <% if(e_id==0){ %>
			<p><a href="index_agenda.jsp?page=task" title="Liste des entreprises" class="actionButton">Retour</a></p>
            <% } else { %>
			<p><a href="index_agenda.jsp?page=task&amp;form=entreprisedetails&amp;id=<%= enB.getId()%>" onclick="javascript:selectEntreprise(this.name)" title="Voir les informations sur l'entreprise" class="actionButton">Retour</a></p> 
            <% } %>
            <bean:define id="entreprise" type="steemploi.service.Entreprise" name="g" property="object"   scope="request" />
				<table class="entreprise" id="table_entreprise_form">
                <tr><td>
                	<label for="nom" id="nomL" class="fixedLabel"><span class="required"></span>Nom:</label>
                </td><td>
                	<html:text name="entreprise" property="nom" onkeydown="return blockEnter(event)" styleId="nom"/>
                </td></tr>
                <tr><td>
                
				<label for="rue" id="rueL" class="fixedLabel"><span class="required"></span>Rue:</label>
                </td><td><html:text name="entreprise" property="rue" onkeydown="return blockEnter(event)" styleId="rue"/></td></tr>
                <tr><td>
                <label for="numero" id="numeroL" class="fixedLabel"><span class="required"></span>Numéro:</label>
                </td><td><html:text name="entreprise" property="numero"  onkeydown="return blockEnter(event)" styleId="numero"/></td></tr>
                <tr><td>
                <label for="boite" id="boiteL" class="fixedLabel"><span class="optional"></span>Boite:</label>
                </td><td><html:text name="entreprise" property="boite"  onkeydown="return blockEnter(event)" styleId="boite"/></td></tr>
                <tr><td>
                <label for="codepostal" id="codepostalL" class="fixedLabel"><span class="required"></span>Code postal:</label>
                </td><td><html:text name="entreprise" property="codepostal"  onkeydown="return blockEnter(event)" styleId="codepostal"/></td></tr>
                <tr><td>
                <label for="ville" id="villeL" class="fixedLabel"><span class="required"></span>Ville:</label>
                </td><td><html:text name="entreprise" property="ville" onkeydown="return blockEnter(event)" styleId="ville" /></td></tr>
                <tr><td>
                <label for="pays" id="paysL" class="fixedLabel"><span class="required"></span>Pays:</label>
                </td><td><html:text name="entreprise" property="pays" onkeydown="return blockEnter(event)" styleId="pays" /></td></tr>
                <tr><td>Carte</td><td><a href="http://maps.google.com/maps?f=q&hl=fr&q=<bean:write name="entreprise" property="rue"/> <bean:write name="entreprise" property="numero"/>,<bean:write name="entreprise" property="codepostal"/>,<bean:write name="entreprise" property="pays"/>" target="_blank">Carte</a>
		</td></tr>
                <tr><td>
                <label for="tel" id="telL" class="fixedLabel">Numéro de tél bureau:</label>
                </td><td><html:text name="entreprise" property="tel"  onkeydown="return blockEnter(event)" styleId="tel"/></td></tr>
                <tr><td>
                <label for="gsm" id="gsmL" class="fixedLabel">Numéro de tél. mobile:</label>
                </td><td><html:text name="entreprise" property="gsm"  onkeydown="return blockEnter(event)" styleId="gsm"/></td></tr>
                <tr><td>
                <label for="email" id="emailL" class="fixedLabel">Adresse email:</label>
                </td><td><html:text name="entreprise" property="email"  onkeydown="return blockEnter(event)" styleId="email"/></td></tr>
                <tr><td>
                <label for="url" id="urlL" class="fixedLabel">Site web:</label>
                </td><td><html:text name="entreprise" property="url"  onkeydown="return blockEnter(event)" styleId="url"/>&nbsp;<a href="<bean:write name="entreprise" property="url" />" target="_blank">Se rendre sur le site</a></td></tr>
                <tr><td>
                <label for="infocomplementaires" id="infocomplementairesL" class="fixedLabel">Informations complémentaires:</label>
                </td><td><html:textarea name="entreprise" property="infocomplementaires"  onkeydown="return blockEnter(event)" styleId="infocomplementaires"/></td></tr>
                <tr><td>
                <label for="commentaires" id="commentairesL" class="fixedLabel">Commentaires:</label>
                </td><td><html:textarea name="entreprise" property="commentaires"  onkeydown="return blockEnter(event)" styleId="commentaires"/></td></tr>
                <tr><td>
                <label for="secteur" id="secteurL" class="fixedLabel">Secteur d'activité:</label>
                </td><td><html:text name="entreprise" property="secteur"  onkeydown="return blockEnter(event)" styleId="secteur"/></td></tr>
		        <tr><td>Contacts</td><td>

				<%
				List<Contact> contacts = new TableContact().findByEntreprise(etudiant_id, entreprise.getId());
				if(contacts.size()==0)
				{%>
				<em>Aucun contact</em>
				<% }else{
					%>				<select name="contacts" id="contacts">
					<% 
				for(Contact c : contacts){ %>
				<option value="<%= c.getId() %>"><%= c.getNom()+" " +c.getPrenom() %></option>
				<% } %>
				</select>
<script>
function openEditContact_<%= entreprise_id %>()
{
	id = document.entreprise.contacts.value;
	window.open('index_agenda.jsp?page=task&form=editcontact&id=<%= entreprise_id %>&cid='+id);
}
</script>

				<a href="#" name="editcontact"  onclick="openEditContact_<%= entreprise_id %>();" class="actionButton">Editer contact</a>
				<br/>				
<%} %>
			<a href="index_agenda.jsp?page=task&form=editcontact&amp;id=<%= entreprise_id %>&amp;cid=0" target="_NEW" class="actionButton">Ajouter un  contact</a>  
                </td></tr>
                <tr><td colspan="2" style="text-align: center;"><a href="#" onclick="checkEntreprise();" class="submitButton">Enregistrer</a></td></tr>

</table>
<html:hidden  name="entreprise" property="id" />
<input type="hidden" name="owner" value="<%= etudiant_id %>"/>
<input type="hidden" name="action"	value="<% out.print("index_agenda.jsp?page=task&amp;form=editentreprise&id="+(entreprise_id==0?"attr":""+entreprise_id)); %>"/>
        </html:form>
        <% 
        
        
        
        
        } else if (form.equals("listtask")) {
            entreprise_idStr = request.getParameter("entreprise_id");
            entreprise_id = Integer.parseInt(entreprise_idStr);
        	
     TableTaches tableTaches = new TableTaches();
     List<Tache> taches = tableTaches.findByEtudiantAndEntreprise(etudiant_id, entreprise_id);

     TableEntreprise tableEntreprise = new TableEntreprise();
     Entreprise entreprise = tableEntreprise.findById(entreprise_id);%>
 		<p><strong><a href="index_agenda.jsp?page=task&form=entreprisedetails&id=<%= entreprise_id %>" title="Retour aux coordonnées de l'entreprise" class="actionButton">Retour à la page: <%= entreprise.getNom() %></a></strong></p>
        <h2>Tâches effectuées</h2>
        <p><a href="index_agenda.jsp?page=task&amp;form=tasks&amp;id=0&amp;entreprise_id=<%= request.getParameter("entreprise_id") %>&amp;etudiant_id=1" class="actionButton">Ajouter une nouvelle tâche</a></p>
        <table class="entreprise">
        <%
     for (int i = 0; i < taches.size(); i++) {%>
            <jsp:useBean id="tD" scope="request" class="com.myapp.struts.Getter"/>
            <%
            tD.setObject(taches.get(i));
            request.getParameterMap().put("nextUrl", new String[] {new Integer(i).toString()});

            %>
            <bean:define id="tache1" type="steemploi.service.Tache" name="tD" property="object" scope="request" toScope="request"/>

            <jsp:useBean id="d" scope="request" class="com.myapp.struts.Getter"/>
            <jsp:useBean id="e" scope="request" class="com.myapp.struts.Getter"/>
            <%
            d.setObject(d.encode("index_agenda.jsp", new String[]{"page", "task", "form", "listtask", "entreprise_id", request.getParameter("entreprise_id")}));
            e.setObject(e.encode("index_agenda.jsp", new String[]{"page", "task", "form", "tasks", "id", String.valueOf(taches.get(i).getId())}));
            %>
            <bean:define id="dR" type="String" name="d" property="object" scope="request" toScope="request"/>
            <bean:define id="eR" type="String" name="e" property="object" scope="request" toScope="request"/>
            <bean:define id="tache1" type="steemploi.service.Tache" name="tD" property="object" scope="request" toScope="request"/>

            <tr><td colspan="2">Tâche
                        </td></tr>    

            <tr><td>Code</td>
            <td><%
            CategoriesTache code = new TableCodeCategorieTache().findById(taches.get(i).getCode());
            out.println(""+code.getTitle() + "");%>
                        </td></tr>
                <tr><td>Titre</td>
                <td><bean:write name="tache1" property="title"/></td></tr>
                <tr><td>Description
                </td>
                <td><bean:write name="tache1" property="description" filter="false" />
</td></tr>
				<tr><td>

				Pourcentage</td>
                <td><bean:write name="tache1" property="percent"/>%
                </td></tr>
                <tr><td>
                Date d'achèvement</td>
                <td><%= String.format("%td/%tm/%tY", tache1.getDateCompleted(),tache1.getDateCompleted(),tache1.getDateCompleted()) %>
</td></tr>
				<logic:notEqual name="tache1" property="contact_id" value="0">
				<tr><td>Contacts</td><td>
                <% 
                int cid = tache1.getContact_id();
                steemploi.service.Contact c = new TableContact().findById(cid); 
                %>
				<p>Identité: <%= c.getNom()+" "+c.getPrenom() %></p>
				<% if(c.getTel()!=null && !c.getTel().equals("")) { %>
				<p>Téléphone: <%= c.getTel() %></p>
				<% } %>
				<% if(c.getGsm()!=null && !c.getGsm().equals("")) { %>
				<p>GSM: <%= c.getGsm() %></p>
				<% } %>
				<% if(c.getEmail()!=null && !c.getEmail().equals("")) { %>
				<p>Email: <%= c.getEmail() %></p>
				<% } %>
				<!-- <a href="index_agenda.jsp?page=task&form=editcontact&amp;id=<%= entreprise_id%>&amp;cid=0" class="actionButton">Editer contact</a> -->
				</td></tr>
				</logic:notEqual>
				<tr><td></td><td>
            <html:link action="/DeleteTask.do" paramId="action" paramName="d" paramProperty="object" styleClass="submitButton">Supprimer tâche</html:link>
                
                <a href="index_agenda.jsp?page=task&amp;form=tasks&amp;id=<%= taches.get(i).getId() %>&amp;entreprise_id=<%= request.getParameter("entreprise_id") %>&amp;etudiant_id=<%= etudiant_id %>" class="submitButton">Editer</a>
        <% }%>
				</td></tr>
				</table>
    <%
 } else if(form.equals("editcontact"))
 {
	 
int cid=Integer.parseInt(request.getParameter("cid")); // Contact ID


steemploi.service.Contact c = null;
int id;
if(cid==0)
{
	c = new steemploi.service.Contact();
	id = Integer.parseInt(request.getParameter("id")); // Entreprise ID
}
else
{ 
	c = new TableContact().findById(cid);	
	id=c.getEntreprise_id();//Integer.parseInt(request.getParameter("id")); // Entreprise ID
} 

Entreprise entreprise = new Entreprise();
if(id!=0)
	entreprise = new TableEntreprise().findById(id);
else
{
	
}
g.setObject(c);
c.setEntreprise_id(id);
%>
    <h2>Edition d'un contact</h2>
<bean:define id="contact" type="steemploi.service.Contact" name="g" property="object"  scope="request" />
<script src="js/validator/validator.js" type="text/javascript"></script>
<script src="js/validator/contact/configurator_example.js" type="text/javascript"></script>
<div id="errorwarning"><strong>Erreurs dans le formulaire!</strong> Corrigez les erreurs en rouge</div> 
<form action="index_agenda.jsp" method="get">
<input type="hidden" name="page" value="entreprises"/>
<input type="hidden" name="form" value="contactdetails"/>
<input type="hidden" name="cid" value="<%= cid %>"/>
<input type="hidden" name="id" value="<%= id %>" />
</form>
			<p><a href="index_agenda.jsp?page=task&amp;form=entreprisedetails&amp;id=<%= entreprise.getId()%>" onclick="javascript:selectEntreprise(this.name)" title="Voir les informations sur l'entreprise" class="actionButton">Retour</a></p>
<html:form action="/EditContact.do" styleId="EntrepriseForm" onsubmit="return checkContact();">
<table class="entreprise">
                <tr><td>
                	<label for="prenom" id="prenomL" class="fixedLabel"><span class="required"></span>Prénom:</label>
                </td><td>
                	<html:text name="contact" property="prenom" onkeydown="return blockEnter(event)" styleId="prenom"/>
                </td></tr>
                <tr><td>
                	<label for="nom" id="nomL" class="fixedLabel"><span class="required"></span>Nom:</label>
                </td><td>
                	<html:text name="contact" property="nom" onkeydown="return blockEnter(event)" styleId="nom"/>
                </td></tr>
                <tr><td>
                
				<label for="rue" id="rueL" class="fixedLabel"><span class="required"></span>Rue:</label>
                </td><td><html:text name="contact" property="rue" onkeydown="return blockEnter(event)" styleId="rue"/></td></tr>
                <tr><td>
                <label for="numero" id="numeroL" class="fixedLabel"><span class="required"></span>Numéro:</label>
                </td><td><html:text name="contact" property="numero"  onkeydown="return blockEnter(event)" styleId="numero"/></td></tr>
                <tr><td>
                <label for="boite" id="boiteL" class="fixedLabel"><span class="optional"></span>Boite:</label>
                </td><td><html:text name="contact" property="boite"  onkeydown="return blockEnter(event)" styleId="boite"/></td></tr>
                <tr><td>
                <label for="codepostal" id="codepostalL" class="fixedLabel"><span class="required"></span>Code postal:</label>
                </td><td><html:text name="contact" property="codepostal"  onkeydown="return blockEnter(event)" styleId="codepostal"/></td></tr>
                <tr><td>
                <label for="ville" id="villeL" class="fixedLabel"><span class="required"></span>Ville:</label>
                </td><td><html:text name="contact" property="ville" onkeydown="return blockEnter(event)" styleId="ville" /></td></tr>
                <tr><td>
                <label for="pays" id="paysL" class="fixedLabel"><span class="required"></span>Pays:</label>
                </td><td><html:text name="contact" property="pays" onkeydown="return blockEnter(event)" styleId="pays" /></td></tr>
                <tr><td>Carte</td><td><a href="http://maps.google.com/maps?f=q&hl=fr&q=<bean:write name="contact" property="rue"/> <bean:write name="contact" property="numero"/>,<bean:write name="contact" property="codepostal"/>,<bean:write name="contact" property="pays"/>" target="_blank">Carte</a>
		</td></tr>
                <tr><td>
                <label for="tel" id="telL" class="fixedLabel">Numéro de tél bureau:</label>
                </td><td><html:text name="contact" property="tel"  onkeydown="return blockEnter(event)" styleId="tel"/></td></tr>
                <tr><td>
                <label for="gsm" id="gsmL" class="fixedLabel">Numéro de tél. mobile:</label>
                </td><td><html:text name="contact" property="gsm"  onkeydown="return blockEnter(event)" styleId="gsm"/></td></tr>
                <tr><td>
                <label for="email" id="emailL" class="fixedLabel">Adresse email:</label>
                </td><td><html:text name="contact" property="email"  onkeydown="return blockEnter(event)" styleId="email"/></td></tr>
                <tr><td>
                <label for="url" id="urlL" class="fixedLabel">Site web:</label>
                </td><td><html:text name="contact" property="url"  onkeydown="return blockEnter(event)" styleId="url"/>&nbsp;<a href="<bean:write name="contact" property="url" />" target="_blank">Se rendre sur le site</a></td></tr>
                <tr><td>
                <label for="infocomplementaires" id="infocomplementairesL" class="fixedLabel">Informations complémentaires:</label>
                </td><td><html:textarea name="contact" property="infocomplementaires"  onkeydown="return blockEnter(event)" styleId="infocomplementaires"/></td></tr>
                <tr><td>
                <label for="commentaires" id="commentairesL" class="fixedLabel">Commentaires:</label>
                </td><td><html:textarea name="contact" property="commentaires"  onkeydown="return blockEnter(event)" styleId="commentaires"/></td></tr>
<%-- 
                <tr><td>Nom</td><td><html:text name="contact" property="nom" /></td></tr>
                <tr><td>Prénom</td><td><html:text name="contact" property="prenom" /></td></tr>
                <tr><td>Rue</td><td><html:text name="contact" property="rue" /></td></tr>
                <tr><td>Numéro</td><td><html:text name="contact" property="numero" /></td></tr>
                <tr><td>Boite</td><td><html:text name="contact" property="boite" /></td></tr>
                <tr><td>Code postal</td><td><html:text name="contact" property="codepostal" /></td></tr>
                <tr><td>Ville</td><td><html:text name="contact" property="ville" /></td></tr>
                <tr><td>Pays</td><td><html:text name="contact" property="pays" /></td></tr>
                <tr><td>Carte</td><td><a href="http://http://maps.google.com/maps?f=q&hl=fr&q=<bean:write name="contact" property="rue"/> <bean:write name="contact" property="numero"/>,<bean:write name="contact" property="codepostal"/>,<bean:write name="contact" property="pays"/>">Carte sur Google Maps</a></td></tr>
                <tr><td>Numéro de tél bureau</td><td><html:text name="contact" property="tel" /></td></tr>
                <tr><td>Numéro de gsm</td><td><html:text name="contact" property="gsm" /></td></tr>
                <tr><td>Adresse email</td><td><html:text name="contact" property="email" /></td></tr>
                <tr><td>Site web</td><td><html:text name="contact" property="url" /></td></tr>
                <tr><td>Informations complémentaires</td><td><html:textarea name="contact" property="infocomplementaires" /></td></tr>
                <tr><td>Commentaires</td><td><html:textarea name="contact" property="commentaires" /></td></tr>
--%>
                <tr><td>Entreprise</td><td>
                	<table class="entreprise">
                		<tr><td>Nom</td><td><%= entreprise.getNom() %></td></tr>
                		<tr><td>Site web</td><td><%= entreprise.getUrl() %></td></tr>
                		<tr><td>Email</td><td><%= entreprise.getEmail() %></td></tr>
                		<tr><td>Tél</td><td><%= entreprise.getTel() %></td></tr>
                		<tr><td>Mobile</td><td><%= entreprise.getGsm() %></td></tr>
                	</table>
                </td></tr>
                <tr><td colspan="2" style="text-align: center;"><a href="#" onclick="checkContact();" class="submitButton">Enregistrer</a></td></tr>
</table>
<html:hidden  name="contact" property="id" />
<input type="hidden" name="owner" value="<%= etudiant_id %>"/>
<html:hidden name="contact" property="entreprise_id" />
<input type="hidden" name="action"	value="index_agenda.jsp?page=task&amp;form=editcontact&amp;etudiant_id=<%= etudiant_id %>&amp;cid=<%= cid %>&amp;id=<%= id %>" />

</html:form>
<%
}
else if(form.equals("entreprisedetails"))
{
TableEntreprise tableEntreprise = new TableEntreprise();
Entreprise entreprise1 = tableEntreprise.findById(entreprise_id);
%>
<jsp:useBean id="g1" scope="request" class="com.myapp.struts.Getter"/>
<%
g1.setObject(entreprise1);
%>
<bean:define id="entreprise2" type="steemploi.service.Entreprise" name="g1" property="object"   scope="request" />
<h2>Entreprise</h2>
<H3><bean:write name="entreprise2" property="nom"/></H3>
     <ul id="onglets">
    	<li>
    		<a href="#" onclick="openTab(0);">Résumé</a> 
    	</li>
    	<li>
    		<a href="#" onclick="openTab(1);">Carte</a> 
    	</li>
    	<li>
    		<a href="#" onclick="openTab(2);">Contacts</a> 
    	</li>
    	<li>
    		<a href="#" onclick="openTab(3);">Tâches</a> 
    	</li>
    	<li>
    		<a href="#" onclick="openTab(4);">Commentaires</a> 
    	</li>
    </ul>

    <div class="ongletDivs" id="onglet1">
   		<h2>Résumé</h2>
<h3>Description de l'enrepirse</h3>
<div><bean:write name="entreprise2" property="infocomplementaires" filter="false"/></div>
<h3>Secteur</h3>
<p><bean:write name="entreprise2" property="secteur"/></p>
<h3>Liens</h3>
<table>
<tr><td>Site web</td><td>
<a href="<bean:write name="entreprise2" property="url"/>"><bean:write name="entreprise2" property="url"/></a>
</td></tr>
<tr><td>Adresse email</td><td>
<a href="mailto:<bean:write name="entreprise2" property="email"/>"><bean:write name="entreprise2" property="email"/></a>
</td></tr>
</table>
<h3>Coordonnées</h3>
<table id="entrepriseResume">
<tr><td>Nom</td><td><bean:write name="entreprise2" property="nom"/></td></tr>
<tr><td>Adresse</td><td>
<p><bean:write name="entreprise2" property="rue"/>,<bean:write name="entreprise2" property="numero"/></p>
<p><bean:write name="entreprise2" property="codepostal"/>&nbsp;<bean:write name="entreprise2" property="ville"/></p>
<p><bean:write name="entreprise2" property="pays"/></p>
</td></tr>
<tr><td>Numéro de téléphone</td><td>
<bean:write name="entreprise2" property="tel"/>
</td></tr>
<tr><td>Mobile</td><td>
<bean:write name="entreprise2" property="gsm"/>
</td></tr>
</table>
<form action="index_agenda.jsp" method="get" name="modifierCoordonnees">
<a href="#" onclick="document.forms['modifierCoordonnees'].submit();" class="actionButton">Modifier les coordonnées de l'entreprise</a>
 <input type="hidden" name="page" value="task"/>
<input type="hidden" name="form" value="editentreprise"/>
<input type="hidden" name="id" value="<%= entreprise_id %>"/>
<html:hidden name="entreprise2" property="id" />
</form>
<form action="DeleteEntreprise.do" method="get" name="supprimerEntreprise">
<p><a href="#" onclick="document.forms['supprimerEntreprise'].submit()" class="actionButton">Supprimer les coordonnées de l'entreprise</a></p>
<input type="hidden" name="action" value="index_agenda.jsp?page=task"/>
<html:hidden name="entreprise2" property="id" />
</form>
    </div>

    <div class="ongletDivs" id="onglet2">
    	<h2>Carte</h2>
<table id="entrepriseCarte">
<tr><td>Carte</td><td>
<!-- ++Begin Map Search Control Wizard Generated Code++ -->
  <!--
  // Created with a Google AJAX Search Wizard
  // http://code.google.com/apis/ajaxsearch/wizards.html
  -->

  <!--
  // The Following div element will end up holding the map search control.
  // You can place this anywhere on your page
  -->
  <div id="mapsearch">
    <span style="color:#676767;font-size:11px;margin:10px;padding:4px;">Loading...</span>
  </div>

  <!-- Maps Api, Ajax Search Api and Stylesheet
  // Note: If you are already using the Maps API then do not include it again
  //       If you are already using the AJAX Search API, then do not include it
  //       or its stylesheet again
  //
  // The Key Embedded in the following script tags is designed to work with
  // the following site:
  // http://localhost:8080
  -->
  <script src="http://maps.google.com/maps?file=api&v=2&key=ABQIAAAAMcvam0N-bMFf8r3cuPWp6xRB4nrBOd15Q9ov68K7sUHf5JKZLxQVzJf1ii5umrIJAbXQ_Vy97cVt2Q"
    type="text/javascript"></script>
  <script src="http://www.google.com/uds/api?file=uds.js&v=1.0&source=uds-msw&key=ABQIAAAAMcvam0N-bMFf8r3cuPWp6xRB4nrBOd15Q9ov68K7sUHf5JKZLxQVzJf1ii5umrIJAbXQ_Vy97cVt2Q"
    type="text/javascript"></script>
  <style type="text/css">
    @import url("http://www.google.com/uds/css/gsearch.css");
  </style>

  <!-- Map Search Control and Stylesheet -->
  <script type="text/javascript">
    window._uds_msw_donotrepair = true;
  </script>
  <script src="http://www.google.com/uds/solutions/mapsearch/gsmapsearch.js?mode=new"
    type="text/javascript"></script>
  <style type="text/css">
    @import url("http://www.google.com/uds/solutions/mapsearch/gsmapsearch.css");
  </style>

  <style type="text/css">
    .gsmsc-mapDiv {
      height : 275px;
    }

    .gsmsc-idleMapDiv {
      height : 275px;
    }

    #mapsearch {
      width : 365px;
      margin: 10px;
      padding: 4px;
    }
  </style>
  <script type="text/javascript">
    function LoadMapSearchControl() {

      var options = {
            zoomControl : GSmapSearchControl.ZOOM_CONTROL_ENABLE_ALL,
            title : "Googleplex",
            url : "http://www.google.com/corporate/index.html",
            idleMapZoom : GSmapSearchControl.ACTIVE_MAP_ZOOM,
            activeMapZoom : GSmapSearchControl.ACTIVE_MAP_ZOOM
            }

      new GSmapSearchControl(
            document.getElementById("mapsearch"),
            "<bean:write name="entreprise2" property="rue"/> <bean:write name="entreprise2" property="numero"/>,<bean:write name="entreprise2" property="codepostal"/> <bean:write name="entreprise2" property="ville"/>,<bean:write name="entreprise2" property="pays"/>",
            options
            );

    }
    // arrange for this function to be called during body.onload
    // event processing
    GSearch.setOnLoadCallback(LoadMapSearchControl);
  </script>
<!-- ++End Map Search Control Wizard Generated Code++ -->
            
<a href="http://maps.google.com/maps?f=q&hl=fr&q=<bean:write name="entreprise2" property="rue"/> <bean:write name="entreprise2" property="numero"/>,<bean:write name="entreprise2" property="codepostal"/> <bean:write name="entreprise2" property="ville"/>,<bean:write name="entreprise2" property="pays"/>" target="_blank">Carte</a>
</table>
    </div>
    <div class="ongletDivs" id="onglet3">
        	<h2>Contacts</h2>
<table id="entrepriseContacts">
<% for(Contact c : (ArrayList<Contact>) new TableContact().findByEntreprise(etudiant_id, entreprise_id))
{%>
<p><a class="editLink" href="index_agenda.jsp?page=task&form=contactdetails&cid=<%= c.getId() %>">Contact: <%= c.getPrenom() +" "+ c.getNom() %></a></p>
<% } %>
<p><a href="index_agenda.jsp?page=task&form=editcontact&amp;id=<%= entreprise_id %>&amp;cid=0" target="_NEW" class="actionButton">Ajouter un  contact</a></p>
</table>
</div>
    <div class="ongletDivs" id="onglet4">
        	<h2>Tâches</h2>
<h3>Tâches</h3>
<% 
TableTaches tt = new TableTaches();
List<Tache> taches = tt.findByEtudiantAndEntreprise(etudiant_id, entreprise_id);
%>
<ol>
<% for(Tache t : taches ) {%>
	<li><a href="index_agenda.jsp?page=task&form=listtask&amp;entreprise_id=<%= entreprise_id %>&task=0">Le <%= String.format("%2d/%2d/%4d",  t.getDateCompleted().get(Calendar.DATE), t.getDateCompleted().get(Calendar.MONTH)+1, t.getDateCompleted().get(Calendar.YEAR)) %> : <%= t.getTitle() %></a></li>
<% } %>
</ol>
<p><a href="index_agenda.jsp?page=task&form=listtask&amp;entreprise_id=<%= entreprise_id %>&task=0" class="actionButton">Tâches</a></p>
    </div>
    <div class="ongletDivs" id="onglet5">
        	<h2>Commentaires</h2>
			<div>
				<bean:write name="entreprise2" property="commentaires" filter="false" />
			</div>        	
        	<p><a href="?page=task&amp;form=commentaires&amp;entreprise_id=<%= entreprise_id %>" class="actionButton">Modifier le commentaire</a></p>
    </div>

<%}
else if(form.equals("contactdetails"))
{
	int cid = 0;
	String cid_Str = request.getParameter("cid");
	cid = Integer.parseInt(cid_Str);
	
	Contact contact1 = new TableContact().findById(cid);
	entreprise_id = contact1.getEntreprise_id();

	TableEntreprise te = new TableEntreprise();
	Entreprise entreprise = te.findById(entreprise_id);
%>
<jsp:useBean id="g2" scope="request" class="com.myapp.struts.Getter"/>
<% 
g2.setObject(contact1);
%>
<bean:define id="contact2" type="steemploi.service.Contact" name="g2" property="object"   scope="request" />
<h2>Contact</h2>
<bean:write name="contact2" property="prenom"/>
<bean:write name="contact2" property="nom"/>
<h3>Coordonnées</h3>
<table id="entreprise">
<tr><td>&nbsp;</td><td>
</td></tr>
<tr><td>Adresse</td><td>
<p><bean:write name="contact2" property="rue"/>,<bean:write name="contact2" property="numero"/></p>
<p><bean:write name="contact2" property="codepostal"/>&nbsp;<bean:write name="contact2" property="ville"/></p>
<p><bean:write name="contact2" property="pays"/></p>
</td></tr>
<tr><td>Numéro de téléphone</td><td>
<bean:write name="contact2" property="tel"/>
</td></tr>
<tr><td>Mobile</td><td>
<bean:write name="contact2" property="gsm"/>
</td></tr>
<tr><td>Site web</td><td>
<a href="<bean:write name="contact2" property="url"/>"><bean:write name="contact2" property="url"/></a>
</td></tr>
<tr><td>Adresse email</td><td>
<a href="mailto:<bean:write name="contact2" property="email"/>"><bean:write name="contact2" property="email"/></a>
</td></tr>
</table>
<h3>Entreprise</h3>
<p><strong><a href="index_agenda.jsp?page=task&form=entreprisedetails&id=<%= entreprise_id %>" title="Retour aux coordonnées de l'entreprise" class="actionButton"><%= entreprise.getNom() %></a></strong></p>
<h3>Edition et suppression</h3>
<p><a href="index_agenda.jsp?page=task&form=editcontact&cid=<%= cid %>" class="actionButton">Modifier contact</a></p>
<p><a href="/DeleteContact.do?id=<%= entreprise_id %>" class="actionButton">Suppression du contact</a></p>
<%
}
else if(form.equals("commentaires"))
{
%>
       <form action="EntrepriseComments.servlet" name="EntrepriseForm">
            <%
     int e_id = 0;
     Entreprise enB;
     entreprise_id = Integer.parseInt(request.getParameter("entreprise_id"));
     if (entreprise_id != 0) {
         e_id = entreprise_id;
         enB = new TableEntreprise().findById(e_id);
     } else {
         enB = new Entreprise();
     }
	 entreprise_id = enB.getId();
     g.setObject(enB);
            %>
            <bean:define id="entreprise3" type="steemploi.service.Entreprise" name="g" property="object"   scope="request" />
				<table class="entreprise" id="table_entreprise_form">
                <label for="commentaires" id="commentairesL" class="fixedLabel">Commentaires:</label>
                </td><td><html:textarea name="entreprise3" property="commentaires"  styleId="commentaires" cols="50" rows="30"/></td></tr>
                <tr><td colspan="2" style="text-align: center;"><a href="#" onclick="document.forms['EntrepriseForm'].submit();" class="submitButton">Enregistrer</a></td></tr>

</table>
<html:hidden  name="entreprise3" property="id" />
<input type="hidden" name="owner" value="<%= etudiant_id %>"/>
<input type="hidden" name="action"	value="<% out.print("index_agenda.jsp?page=task&amp;form=entreprisedetails&id="+(entreprise_id==0?"attr":""+entreprise_id)); %>"/>
				</form>

<%
}
else
{
%>
    <h2>Choissisez une entreprise pour voir les tâches.</h2>
    <hr style="clear: left" />
<%
}
%>
	<hr style="border: 0px; clear: both;" />
</div>
<script type="text/javascript">
   /* initMCE();*/
</script>
<script language="JavaScript" type="text/JavaScript"> 
 
//stop form submisson when enter key is used. Should be added to onload event handler
function blockEnter(evt) {
    evt = (evt) ? evt : event;
    var charCode = (evt.charCode) ? evt.charCode :
        ((evt.which) ? evt.which : evt.keyCode);
    if (charCode == 13) {
        return false;
    } else {
        return true;
    }
}
 
</script> 