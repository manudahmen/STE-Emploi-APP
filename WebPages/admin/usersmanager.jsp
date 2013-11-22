<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.*,java.text.*,steemploi.service.*"%>

<%@page import="steemploi.persistance.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session"/>
<jsp:useBean id="g" scope="request" class="com.myapp.struts.Getter"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Users manager</title>
<style>
<!--
p {margin:5px; padding:0px;}
#main {padding: 10px; height: 500px; overflow: auto; background-color: #ffd;}
#title, #subtitle {overflow: auto; padding: 10px; height: 500px; float: left; width: 200px; font-size: smaller; background-color: #fdd; color: black;}
#formleft { overflow: auto; padding: 10px; float: left; width: 350px; background-color: #336; height: 500px; }
.sessions_anchors {margin-left: 30px; margin-right: 10px; }
.formations_anchors { margin-left: 10px; margin-right: 10px; }
.sessions_anchors a,.sessions_anchors a:visited { font-weight: bold; color: #044;}
.formations_anchors a:visited, .formations_anchors a {font-weight: bold; color: #000;}
.links a, .links a:visited  { font-weight: bold; font-size: larger; color: #011;}
#searchright { float: right;/*width: 250px; height: 80px;*/}
#searchright #searchresponse { margin: 0px; padding: 3px;}
#searchresponse a { margin-top: 5px; background-color: #00b; color: #FFF; padding: 2px;}
a.hidden { display: none; }
#main h2 { background-color: #faa; padding: 5px;}
-->
</style>

<script type="text/javascript">
function search()
{
	var elem = document.getElementById("searchresponse");
	elem.innerHTML="";	
	elem = document.getElementById("search");
	var value = elem.value;
	if(value=="") return;

	var reg=/\\s/g;
	var name1 = value.replace(reg, "_");
	var name=name1.toLowerCase();
	var name="ut"+name;
	if(document.anchors[name]!=null)
	{
		var id = document.anchors[name].innerHTML;
		reg = /(^[0-9]+$)/g;
		if(reg.test(id))
		{
			var name_ = document.anchors["utid"+id];
			name2 = name_.innerHTML;
			alert(name2);
			elem = document.getElementById("searchright");
			elem.style.backgroundColor = "#afa";
			elem = document.getElementById("searchresponse");
			elem.innerHTML="Utilisateur trouvé: <a href='?action=editutilisateur&id="+id+"'>"+name2+"</a>";
			var href = document.location.href.toString();
			if(href.indexOf("#")>0)
			{
				href=href.substring(0, href.indexOf("#"));
			}
			var reg=/\s/g;
			name2 = name2.replace(reg, "_");
			href+="#"+name2;
			document.location.href=href;
		}
	}
	else
	{
		elem = document.getElementById("searchright");
		elem.style.backgroundColor = "red";
		var elem = document.getElementById("searchresponse");
		elem.innerHTML="Utilisateur non trouvé";
		//document.location.href=href;
	}
}
</script>
</head>
<body>
<%
String action = request.getParameter("action");
if(action!=null)
{
%>
<div id="formleft">
<%
if(action.equals("editutilisateur"))
{
String str_id = request.getParameter("id");
String str_formation_id = request.getParameter("formation");
int id = 0;
int formation_id = 0;
Utilisateur u = null;
if(str_id!=null)
	id = Integer.parseInt(str_id);
String type="" ;
if(id==0)
{
	u = new Utilisateur();
	if(str_formation_id!=null)
	{
		formation_id = Integer.parseInt(str_formation_id);
		u.setFormation_id(formation_id);
	}
	type = request.getParameter("type");
	if(type.equals("etudiant"))
	{
		u.setType(TypeUtilisateur.ETUDIANT);
		u.setEtudiant(new Etudiant());
		u.getEtudiant().setId(0);
		u.getEtudiant().setSf(new SessionsFormations());
		u.getEtudiant().getSf().setId(formation_id);
		u.getEtudiant().setFormation_id(formation_id);
	} else if(type.equals("admin"))
	{
		u.setType(TypeUtilisateur.ADMIN);
	} else if(type.equals("formateur"))
	{
		u.setType(TypeUtilisateur.FORMATEUR);
	}
		
}
else
	u = new TableUtilisateurs().findById(id);
g.setObject(u);
	%>
<h1>Editer utilisateur</h1>
<bean:define id="user2" type="steemploi.service.Utilisateur" name="g" property="object"   scope="request" />
<html:form action="/admin/EditUtilisateur3.do">
<table>
<tr>
<td>Type d'utilisateur
</td>
<td>
<html:select name="user2" property="usertype" >
	<option value="etudiant" <%= ("etudiant".equals(type))?"selected=\"true\"":""%> >Etudiant(e)</option>
	<option value="formateur" <%= ("formateur".equals(type))?"selected=\"true\"":""%> >Formateur/trice</option>
	<option value="admin" <%= ("admin".equals(type))?"selected=\"true\"":""%> >Administrateur/trice</option>
</html:select>
</td>
</tr>
<% if(u.getType().equals(TypeUtilisateur.ETUDIANT)) { %>
<tr>
<td>Session de formation
</td>
<% if(formation_id!=-1)
{ %>
<td>
<html:select name="user2" property="formation_id">
<option value="-1">Choisissez une formation</option>
<%
List<SessionsFormations> formations = new TableSessionsFormations().findAll(false);
for(SessionsFormations sf : formations)
{ %>
	<option value="<%= sf.getId() %>" <%= (sf.getId()==u.getEtudiant().getSf().getId())?"selected=\"true\"":""%> ><%= sf.getFormation().getNom()+" " + String.format("%td/%tm/%tY", sf.getDateStart(),sf.getDateStart(),sf.getDateStart()) %></option>
<% } %>
<option value="-1">Formations vides</option>
<%
formations = new TableSessionsFormations().findEmpty(true);
for(SessionsFormations sf : formations)
{ %>
	<option value="<%= sf.getId() %>" <%= (sf.getId()==u.getEtudiant().getSf().getId())?"selected=\"true\"":""%> ><%= sf.getFormation().getNom()+" " + String.format("%td/%tm/%tY", sf.getDateStart(),sf.getDateStart(),sf.getDateStart()) %></option>
<% } %>
</html:select>
</td>
</tr>
<%  
}
}%>
<tr>
<td>Nom d'utilisateur
</td>
<td><html:text  name="user2" property="username" value="<%= u.getUsername() %>"/>
</td>
</tr>
<tr>
<td>Mot de passe
</td>
<td><html:password name="user2" property="password"/>
</td>
</tr>
<tr>
<td>Répéter mot de passe
</td>
<td><html:password name="user2" property="password2" value=""/>
</td>
</tr>
<tr>
<td>Nom
</td>
<td><html:text  name="user2" property="nom" />
</td>
</tr>
<tr>
<td>Prénom
</td>
<td><html:text  name="user2" property="prenom" />
</td>
</tr>
<tr>
<td>Rue
</td>
<td><html:text  name="user2" property="rue" />
</td>
</tr>
<tr>
<td>Boîte
</td>
<td><html:text  name="user2" property="boite" />
</td>
</tr>
<tr>
<td>Numéro
</td>
<td><html:text  name="user2" property="numero" />
</td>
</tr>
<tr>
<td>Code postal
</td>
<td><html:text  name="user2" property="codepostal" />
</td>
</tr>
<tr>
<td>Ville
</td>
<td><html:text  name="user2" property="ville" />
</td>
</tr>
<tr>
<td>Pays
</td>
<td><html:text  name="user2" property="pays" />
</td>
</tr>
<tr>
<td>Téléphone
</td>
<td><html:text  name="user2" property="tel" />
</td>
</tr>
<tr>
<td>GSM
</td>
<td><html:text  name="user2" property="gsm" />
</td>
</tr>
<tr>
<td>Adresse email
</td>
<td><html:text  name="user2" property="email" />
</td>
</tr>
<tr>
<td>
</td>
<td>
<html:submit value="Enregistrer"/>
</td>
</tr>

</table>
<input type="hidden" name="id" id="id" value="<%= u.getId() %>"/>
<input type="hidden" name="action" value="usersmanager.jsp"/>
</html:form>
<%
}
else if(action.equals("deleteutilisateur"))
{
String str_id = request.getParameter("id");
int id = Integer.parseInt(str_id);

%>
<h1>Supprimer utilisateur</h1>
<form name="deleteutilisateur" method="GET" action="DeleteUtilisateur?id=<%= id %>">
<input type="hidden" name="id" value="<%= id %>"/> 
<input type="hidden" name="action" value="usersmanager.jsp"/>
<input type="submit" value="Effacer utilisateur: <%= id %>"/>
</form>
<%
}
else if(action.equals("editformation"))
{
String str_id = request.getParameter("id");
Formation f = null;
int id = 0;
if(str_id!=null)
	id = Integer.parseInt(str_id);
if(id==0)
	f = new Formation();
else
	f = new TableFormations().findById(id);

g.setObject(f);
%>
<h1>Editer formation</h1>
<bean:define id="formation" type="steemploi.service.Formation" name="g" property="object"   scope="request" />
<html:form action="/admin/EditFormation.do">
<table>
<tr>
<td>Nom de la formation</td><td><html:text name="formation" property="nom" styleId="nom" /></td>
</tr>
<tr>
<td>Année de début</td><td><html:text name="formation" property="annee" styleId="annee"  /></td>
</tr>
<tr>
<td></td><td><input type="submit" value="Enregistrer"/></td>
</tr>
</table>
<input type="hidden" name="id" id="id" value="<%= id %>"/>
<input type="hidden" name="action" value="usersmanager.jsp"/>
</html:form>
<%
}
else if(action.equals("deleteformation"))
{
String str_id = request.getParameter("id");
int id = Integer.parseInt(str_id);
%>
<h1>Supprimer formation</h1>
<form name="deleteformation" method="GET" action="DeleteFormation?id=<%= id %>">
<input type="hidden" name="id" value="<%= id %>"/> 
<input type="hidden" name="action" value="usersmanager.jsp"/>
<input type="submit" value="Effacer formation : <%= id %>"/>
</form>
<%
}
else if(action.equals("editsessionformation"))
{
SessionsFormations sf = null;
Formation f = null;
String str_id = request.getParameter("id");
int id = 0;
int formation_id = 0;
String str_formation = request.getParameter("formation");
if(str_id!=null)
	id = Integer.parseInt(str_id);
if(id==0)
{
	sf = new SessionsFormations();
	if(str_formation!=null)
	{
		formation_id = Integer.parseInt(str_formation);
		sf.setFormation(new Formation());
		sf.getFormation().setId(formation_id);
		sf.setFormation_id(formation_id); 
	}
}
else
	sf = new TableSessionsFormations().findById(id);


List<Formation> formations = new TableFormations().findAll();

NumberFormat nf = NumberFormat.getInstance();
nf.setMinimumIntegerDigits(2);

sf.setDateStart(sf.getDateStart()==null ? Calendar.getInstance() :  sf.getDateStart());
sf.setDateEnd(sf.getDateEnd()==null ? Calendar.getInstance() :  sf.getDateEnd());
sf.setDate_Start(
nf.format( 
sf.getDateStart().get(Calendar.DAY_OF_MONTH)) + "/" + nf.format(sf.getDateStart().get(Calendar.MONTH)+1) + "/" + sf.getDateStart().get(Calendar.YEAR)
);

sf.setDate_End(
nf.format( sf.getDateEnd().get(Calendar.DAY_OF_MONTH)) + "/" + nf.format( sf.getDateEnd().get(Calendar.MONTH)+1) + "/"  + sf.getDateEnd().get(Calendar.YEAR)
);
g.setObject(sf);
%>
<h1>Editer session</h1>
<bean:define id="sessionformation" type="steemploi.service.SessionsFormations" name="g" property="object"   scope="request" />
<html:form action="/admin/EditSessionFormation.do" styleId="sessionformation">
<table>
<tr>
<td>Nom</td><td><html:text name="sessionformation" property="name" styleId="name"/></td></tr><tr>
<td>Formation</td><td>
<html:select name="sessionformation" property="formation_id" styleId="formation_id">
<% for(Formation f2 : formations) { %>
	<option value="<%= f2.getId() %>" <%= sf.getFormation().getId()==f2.getId()?"selected=\"true\"":"" %>><%= f2.getNom() %></option>
<% } %>
</html:select>
</td>
</tr>
<tr>
<td>
Date de début</td><td><html:text name="sessionformation" property="date_Start" styleId="date_Start" />(jj/mm/aaaa)
</td>
</tr>
<tr>
<td>
Date de fin</td><td><html:text name="sessionformation" property="date_End" styleId="date_End"/>(jj/mm/aaaa)</td>
</tr>
<tr>
<td>
</td><td><input type="submit" value="Enregistrer"/></td>
</tr>
</table>
<input type="hidden" name="id" id="id" value="<%= sf.getId() %>"/>
<input type="hidden" name="action" value="usersmanager.jsp"/>
</html:form>
<%
}
else if(action.equals("deletesessionformation"))
{
String str_id = request.getParameter("id");
int id = Integer.parseInt(str_id);
%>
<h1>Supprimer session</h1>
<form name="deletesessionformation" method="GET" action="DeleteSessionFormation?id=<%= id %>">
<input type="hidden" name="id" value="<%= id %>"/> 
<input type="hidden" name="action" value="usersmanager.jsp"/>
<input type="submit" value="Effacer session de formation : <%= id %>"/>
</form>
<%

}
%>
</div>
<%
}
%>
<div id="subtitle">
<div id="searchright">
<p>Rechercher un(e) étudiant(e): <input type="text" id="search" name="search" value=""/>
<input type="button" value="OK" onclick="javascript:search()"/></p>
<p id="searchresponse"></p>
</div>
<h1><a name="home"></a><a href="usersmanager.jsp">Users manager</a></h1>
<p><a href="../logout.jsp">Déconnecter</a></p>
<p class="links">
<a href="#etudiants">Etudiants</a>&nbsp;
</p>
<p class="formations_anchors">
<%
//StudentManager manager = new StudentManager();
List<SessionsFormations> list = new TableSessionsFormations().findAll(false);
int formation_id = -1;
for(SessionsFormations sf : list)
{
if(formation_id!=sf.getFormation().getId())
{%>
</p><p class="formations_anchors"><a href="#formation<%= sf.getFormation().getId() %>"><%= sf.getFormation().getNom() %></a>&nbsp;</p><p class="sessions_anchors">
<!--Sessions&nbsp;:&nbsp;-->
<%
}
%>
<a href="#sf<%= sf.getId() %>" title="Formation '<%= sf.getFormation().getNom() %>' du <%= String.format("%td/%tm/%tY", sf.getDateStart(),sf.getDateStart(),sf.getDateStart()) %> au <%= String.format("%td/%tm/%tY", sf.getDateEnd(),sf.getDateEnd(),sf.getDateEnd()) %>"><%= String.format("%td/%tm/%tY", sf.getDateStart(),sf.getDateStart(),sf.getDateStart()) %></a>&nbsp;
<%
formation_id = sf.getFormation().getId();
}
%></p>
<div class="links">
<a href="#autreformations">Autres formations</a><br/>
<a href="#autre">Autres étudiants</a><br/>
<a href="#formateurs">Formateurs</a><br/>
<a href="#admins">Administrateurs</a><br/>
<a href="#activer">Activer/désactiver compte</a><br/>
<a href="#changermotdepasse">Changer de mot de passe</a>
</div>
</div>
<div id="main">
<h2><a name="etudiants"></a>Etudiants et formations</h2>
<h3><a href="?action=editformation&id=0">Ajouter une formation</a></h3>
<h3>
	<a href="?action=editutilisateur&type=etudiant&id=0&formation=0">Ajouter un(e) étudiant(e)</a>
	</h3>
<%
formation_id = -1;
for(SessionsFormations sf : list)
{
if(formation_id!=sf.getFormation().getId())
{%>
<div style="background-color: #afa; padding: 4px;">
<h3><a name="formation<%= sf.getFormation().getId() %>"></a><%= sf.getFormation().getNom() %>&nbsp;<a href="?action=editformation&id=<%= sf.getFormation().getId() %>">Editer</a>
&nbsp;-&nbsp;
<a href="?action=deleteformation&id=<%= sf.getFormation().getId() %>">Supprimer</a>
</h3>
<h4><a href="?action=editsessionformation&formation=<%= sf.getFormation().getId() %>&id=0">Ajouter une session de formation pour <%= sf.getFormation().getNom().toLowerCase() %></a></h4>
</div>
<%}
%>
<h4>
<a name="sf<%= sf.getId() %>"></a>
<%= String.format("%td/%tm/%tY", sf.getDateStart(),sf.getDateStart(),sf.getDateStart()) %>&nbsp;
<%= String.format("%td/%tm/%tY", sf.getDateEnd(), sf.getDateEnd(), sf.getDateEnd()) %>&nbsp;
<%= sf.getFormation().getNom() %>
&nbsp;<a href="?action=editsessionformation&id=<%= sf.getId() %>">Editer</a>
&nbsp;-&nbsp;
<a href="?action=deletesessionformation&id=<%= sf.getId() %>">Supprimer</a>
</h4>		
<h4>
		<a href="?action=editutilisateur&type=etudiant&id=0&formation=<%= sf.getId() %>">Ajouter un(e) étudiant(e)</a>
</h4>
	<%
	if(sf.getEtudiants().size()==0)
	{
	%>
	<p><strong>AUCUN ETUDIANT DANS CETTE FORMATION</strong></p>
	<%
	}
	%>
<table>
<% for(Etudiant et : sf.getEtudiants())
				{%>
						<tr>
							<td>user id : <%= et.getUser_id() %></td>
			<td class="nometudiant">
<a name="<%= et.getPrenom()+"_"+et.getNom() %>"/>
<a class="hidden" name="ut<%= (et.getPrenom()+"_"+et.getNom()).toLowerCase() %>"><%= et.getUser_id() %></a>
<a class="hidden" name="ut<%= (et.getNom()+"_"+et.getPrenom()).toLowerCase() %>"><%= et.getUser_id() %></a>
<a class="hidden" name="ut<%= (et.getNom()).toLowerCase() %>"><%= et.getUser_id() %></a>
<a class="hidden" name="ut<%= (et.getPrenom()).toLowerCase() %>"><%= et.getUser_id() %></a>
<a class="hidden" name="utid<%= et.getUser_id() %>"><%= et.getPrenom() + " " + et.getNom() %></a>
<%= et.getNom() %> <%= et.getPrenom() %>
			</td>
							<td>
							<a href="?action=editutilisateur&id=<%= et.getUser_id() %>&formation=<%= sf.getId() %>&type=etudiant">Editer</a>
										</td><td>
							
							<a href="?action=deleteutilisateur&id=<%= et.getUser_id() %>">Supprimer</a>
							</td><td>
			<a href="?action=changepassword&id=<%= et.getUser_id() %>">Changer mot de passe</a>
			</td>
						</tr>
				<%} %>
</table>
<hr/>
<%
formation_id = sf.getFormation().getId();
}
%>
	<h2><a name="autreformations"></a>Autres formations</h2>
<%
List<Formation> formationsss = new TableSessionsFormations().findFormationsSansSessions();
for(Formation f : formationsss)
{
%>
<p><a href="?action=editsessionformation&formation=<%= f.getId() %>&id=0">Ajouter une session pour </a><strong><%= f.getNom() %></strong></p>
<p><a href="?action=deleteformation&id=<%= f.getId() %>">Supprimer</a></p>
<%
}
%>
	<h2><a name="autre"></a>Autres étudiants</h2>
	<p><a href="?action=editutilisateurs&type=etudiant&id=0&formation=0">Ajouter un(e) étudiant(e)</a></p>
	<%
	List<Utilisateur> utilisateurs = new TableUtilisateurs().getAutresEtudiants();
	if(utilisateurs.size()==0)
	{
	%>
	<p><strong>AUCUN ETUDIANT</strong></p>
	<%
	}
	%>
	<table>
	<% for(Utilisateur u : utilisateurs) {%><tr>
			<td><%= u.getId() %></td>
			<td><%= u.getUsername() %></td>
			<td class="nometudiant">
<a name="<%= (u.getPrenom()+" "+u.getNom()).toLowerCase() %>"/>
<a name="<%= (u.getNom()+" "+u.getPrenom()).toLowerCase() %>"/>
<a name="<%= (u.getNom()).toLowerCase() %>"/>
<a name="<%= (u.getPrenom()).toLowerCase() %>"/>
<%= u.getNom() %> <%= u.getPrenom() %>
			</td>
			<td>
			<a href="?action=editutilisateur&id=<%= u.getId() %>">Editer</a>
			</td><td>
			<a href="?action=deleteutilisateur&id=<%= u.getId() %>">Supprimer</a>
			</td><td>
			<a href="?action=changepassword&id=<%= u.getId() %>">Changer mot de passe</a>
			</td>
		</tr>
		<% } %>
	</table>
	<h2><a name="formateurs"></a>Formateurs</h2>
	<p><a href="?action=editutilisateur&type=formateur&id=0">Ajouter un(e) formateur/formatrice</a></p>
	<%
		utilisateurs = new TableUtilisateurs().getFormateurs();
	%>
	<table>
	<% for(Utilisateur u : utilisateurs) {%>
		<tr>
			<td><%= u.getId() %></td>
			<td><%= u.getUsername() %></td>
			<td><%= u.getNom() %> <%= u.getPrenom() %></td>
			<td>
			<a href="?action=editutilisateur&id=<%= u.getId() %>&type=formateur">Editer</a>
			</td><td>
			<a href="?action=deleteutilisateur&id=<%= u.getId() %>">Supprimer</a>
			</td><td>
			<a href="?action=changepassword&id=<%= u.getId() %>">Changer mot de passe</a>
			</td>
		</tr>
		<% } %>
	</table>
	<h2><a name="admins"></a>Administrateurs</h2>
	<p><a href="?action=editutilisateur&type=admin&id=0">Ajouter un(e) administrateur/administratrice</a></p>
	<%
		utilisateurs = new TableUtilisateurs().getAdministrateurs();
	%>
	<table>
	<% for(Utilisateur u : utilisateurs) {%>
		<tr>
			<td><%= u.getId() %></td>
			<td><%= u.getUsername() %></td>
			<td><%= u.getNom() %> <%= u.getPrenom() %></td>
			<td>
			<a href="?action=editutilisateur&id=<%= u.getId() %>&type=admin">Editer</a>
			</td><td>
			<a href="?action=deleteutilisateur&id=<%= u.getId() %>">Supprimer</a>
			</td>
		</tr>
		<% } %>
	</table>
	<h2><a name="activer"></a>Activer compte</h2>
	<form name="activercompte" method="GET" action="ActiverCompte.servlet">
		<table>
		<tr>
			<td>
				Activer un compte
			</td>
			<td>
				<input type="text" name="activercompte" value=""/>&nbsp;<input type="submit" name="activercompte" value="Activer" />
			</td>
		</tr>
		<tr>
			<td>
				Désactiver un compte
			</td>
			<td>
				<input type="text" name="desactivercompte" value=""/>&nbsp;<input type="submit" name="desactivercompte" value="Désactiver"/>
			</td>
		</tr>
		</table>
	</form>
<h2><a name="#changermotdepasse"></a>Changer de mot de passe</h2>
	<form name="changermotdepasse" method="GET" action="ChangerMDP.servlet">
		<table>
		<tr>
			<td>
				Nom d'utilisateur
			</td>
			<td>
				<input type="text" name="username" value=""/>
			</td>
		</tr>
		<tr>
			<td>
				Mot de passe
			</td>
			<td>
				<input type="text" name="password" value=""/>
			</td>
		</tr>
		<tr>
			<td>
				Mot de passe
			</td>
			<td>
				<input type="text" name="passwordconfirm" value=""/>
			</td>
		</tr>
		<tr>
			<td>
			</td>
			<td>
				<input type="submit" value="Changer le mot de passe"/>
			</td>
		</tr>
		</table>
	</form>
</div>
</body>
</html>
