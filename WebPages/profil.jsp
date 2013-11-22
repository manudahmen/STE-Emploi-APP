<%--
    Document   : etudiant.jsp
    Created on : 07-avr.-2009, 19:25:46
    Author     : manuel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.myapp.struts.Getter, java.util.Calendar, java.util.List, java.util.Date, steemploi.service.*, steemploi.persistance.TableCandidature, steemploi.service.Profil.ProfilDiplome"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%@page import="steemploi.service.Profil.ProfilDiplome"%>
<%@page import="steemploi.service.Profil.ProfilEmployeur"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Locale"%>
<%@page import="steemploi.service.Profil.CategoriesLogiciels"%>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session"/>
<jsp:useBean id="g" class="com.myapp.struts.Getter" scope="request"/>
<jsp:useBean id="etudforma" class="steemploi.service.Profil" scope="session"/>
<%
        Object o = session.getAttribute("user");
        if (o == null || !(o instanceof Utilisateur) || !(((Utilisateur)(o)).getType().equals(TypeUtilisateur.ETUDIANT)))
        {%><logic:redirect  page="/login.jsp"  /><%
        		Long l = (Long) o;
        } %>

<style>
</style>
<script language="javascript" type="text/javascript">
<%

String [] steps 
  = new String[] {"Sommaire", "Etudes et formations", "Expérience professionnelle", 
  "Permis de conduire", "Logiciels connus", "Langages connus", "Langues", "Qualités", "Loisirs"};

int step = 1;
if(request.getParameter("step")!=null)
{
	step = Integer.parseInt(request.getParameter("step"));
}

int nstep=steps.length;
%>
function save(step)
{
	window.location.replace("index_agenda.jsp?page=profil&step="+step);
}
</script>
<h1 id="titrePage">profil informatisé</h1>
<h2 style="text-align: center; background-color: #699; padding: 10px;"><%= steps[step-1] %>
<a href="?page=profil&step=1" class="sommaire">Retour au sommaire</a>
</h2>

<div style="float: left;">
<%
if(step>1)
{
	%>
	<a href="#" onclick="save(<%= step-1 %>);" class="buttonProfilNav">Etape précédente</a>
	<% 
	}
	%>

</div>
<div style="float: right;">
<%
if(step<nstep)
{
%>
<a href="#" onclick="save(<%= step+1 %>);" class="buttonProfilNav">Etape suivante</a>
<% 
}
%>

</div>
<hr style="clear: both; visibility: hidden"/>
<% if(step!=1) {%>
<h2 style="text-align: center;">Etape <%= step %> sur <%= steps.length %></h2>
<% } %>
<%
if(step==1)
{

etudforma.clearDiplome();
etudforma.setIdDiplome(0);
etudforma.clearEmployeur();
etudforma.setIdEmploi(0);
%>
<ol>
	<li class="sommaireItem"><a href="?page=profil&step=2">Etudes et diplômes</a></li>
	<li class="sommaireItem"><a href="?page=profil&step=3">Expérience professionnelle</a></li>
	<li class="sommaireItem"><a href="?page=profil&step=4">Permis de conduire et voiture</a></li>
	<li class="sommaireItem"><a href="?page=profil&step=5">Logiciels connus</a></li>
	<li class="sommaireItem"><a href="?page=profil&step=6">Langages connus</a></li>
	<li class="sommaireItem"><a href="?page=profil&step=7">Langues</a></li>
	<li class="sommaireItem"><a href="?page=profil&step=8">Atouts</a></li>
	<li class="sommaireItem"><a href="?page=profil&step=9">Loisirs</a></li>
</ol>
<%--  
<html:form action="/EditProfilStep1.do" method="POST" styleClass="formProfil" >

	<h3>Données d'identité</h3>
<div id="personne" class="formProfil">
	<fieldset>
		<label>Nom</label>
		<span class="input"><html:text name="etudforma" property="nom"/></span>
	</fieldset>
	<fieldset>
		<label>Prénom</label>
		<span class="input"><html:text name="etudforma" property="prenom"/></span>
	</fieldset>
	<fieldset>
		<label>Rue</label>
		<span class="input"><html:text name="etudforma" property="rue"/></span>
	</fieldset>
	<fieldset>
		<label>Boîte</label>
		<span class="input"><html:text name="etudforma" property="boite"/></span>
	</fieldset>
	<fieldset>
		<label>Code postal</label>
		<span class="input"><html:text name="etudforma" property="codepostal"/></span>
	</fieldset>
	<fieldset>
		<label>Téléphone fixe</label>
		<span class="input"><html:text name="etudforma" property="telfixe"/></span>
	</fieldset>
	<fieldset>
		<label>Téléphone portable</label>
		<span class="input"><html:text name="etudforma" property="mobile"/></span>
	</fieldset>
	<fieldset>
		<label>Adresse email</label>
		<span class="input"><html:text name="etudforma" property="email"/></span>
	</fieldset>
	<fieldset>
		<label>&nbsp;</label>
		<span class="input">
		<input type="hidden" name="action" value="index_agenda.jsp?page=profil&step=1" />
		<a href="#" onclick="document.forms['profil1'].submit();" class="submitButton">Enregistrer</a>
		</span>
	</fieldset>
</div>

</html:form>
--%>
<%}
else if(step==2)
{%>
<html:form action="/EditProfilStep2.do" method="POST" styleClass="formProfil">
<div id="liste">
<fieldset id="right">
<h2>Etudes et formations</h2>
<ol>
<% 
etudforma.loadDiplomes(user.getEtudiant().getId());
etudforma.clearDiplome();
int i=0;
for(ProfilDiplome d : etudforma.getDiplomes())
{
i++;
int idDiplome1=d.getId();
String href="/EditProfilStep2.do?id="+idDiplome1+"&amp;delete=";
%>
	<li>
		<span class="itemListe">
			<a href="?page=profil&step=2&id=<%= d.getId() %>">Diplôme <%= d.getIntitule() %></a>
		</span>
		<html:link styleClass="actionButton" action="<%= href %>">Effacer</html:link>
	</li>
<%
}
%>
</ol>
</fieldset>
</div>
<div>
<div id="etudforma">
<%
String idDiplomeStr = request.getParameter("id");
int idDiplome=0;
if(idDiplomeStr!=null)
{
	idDiplome = Integer.parseInt(idDiplomeStr);
}

for(ProfilDiplome d : etudforma.getDiplomes())
{
	if(d.getId()==idDiplome) etudforma.copyDiplome(d);
}

String date_Debut_Formation = String.format("%td/%tm/%tY", etudforma.getDateDebutFormation(),etudforma.getDateDebutFormation(),etudforma.getDateDebutFormation());
String date_Fin_Formation = String.format("%td/%tm/%tY", etudforma.getDateFinFormation(),etudforma.getDateFinFormation(),etudforma.getDateFinFormation());

%>
<h2>Diplômes</h2>
	<fieldset>
		<a href="EditProfilStep2.do?reset=true" class="actionButton">Nouveau diplôme ou formation</a>
	</fieldset>
	<fieldset>
		<label>Intitulé</label>
		<span class="input"><html:text name="etudforma" property="intitule"/></span>
	</fieldset>
	<fieldset>
		<label>Orientation</label>
		<span class="input"><html:text name="etudforma" property="orientation"/></span>
	</fieldset>
	<fieldset>
		<label>Ecole</label>
		<span class="input"><html:text name="etudforma" property="etablissement"/></span>
	</fieldset>
	<fieldset>
		<label>Niveau</label>
		<span class="input">
			<html:select name="etudforma" property="niveau">
				<html:option value="1">Secondaire inférieur</html:option>
				<html:option value="2">Secondaire supérieur</html:option>
				<html:option value="3">Supérieur de type court (graduat/bachelor)</html:option>
				<html:option value="4">Supérieur de type long (licence/master)</html:option>
				<html:option value="5">Formation professionnelle</html:option>
			</html:select> 
		</span>
	</fieldset>
	<fieldset>
		<label>Commentaires</label>
		<span class="input"><html:textarea name="etudforma" property="commentaires"></html:textarea></span>
	</fieldset>
	<fieldset>
		<label>Diplôme obtenu</label>
		<span class="input">
		<html:select name="etudforma" property="annee">
	<%
		for(i=2009; i>1950; i--)
		{%>
		<html:option value="<%= new Integer(i).toString() %>"><%= i %></html:option>
		<%}%>
		</html:select>
		</span>
	</fieldset>
	<fieldset>
		<label>Obtenu</label>
		<span class="input"><html:checkbox name="etudforma" property="obtenu" value="on"/></span>
	</fieldset>
	<fieldset>
		<label>Date de début</label>
		<span class="input">
			<input type="text" name="date_Debut_Formation" id="date_Debut_Formation" size="11" onblur="dp_dateFormat='dd/mm/yyyy';magicDate(this)" onfocus="if (this.className != 'error') this.select()" value="<%= date_Debut_Formation %>" /> <a href="javascript:void(0);" onclick="g_Calendar.show(event, 'date_Debut_Formation', 'dd/mm/yyyy')" title="Show popup calendar"><img src="js/cal/calendar.gif" border="0"/></a>(jj/mm/aaaa)
		</span>
	</fieldset>
	<fieldset>
		<label>Date de fin</label>
		<span class="input">
			<input type="text" name="date_Fin_Formation" id="date_Fin_Formation" size="11" onblur="dp_dateFormat='dd/mm/yyyy';magicDate(this)" onfocus="if (this.className != 'error') this.select()" value="<%= date_Fin_Formation %>"/> <a href="javascript:void(0);" onclick="g_Calendar.show(event, 'date_Fin_Formation', 'dd/mm/yyyy')" title="Show popup calendar"><img src="js/cal/calendar.gif" border="0"/></a>(jj/mm/aaaa)		
		</span>
	</fieldset>
	<fieldset>
		<label></label>
		<span class="input">
		<html:hidden name="etudforma" property="idDiplome" />
		<a href="#" onclick="document.forms['profil2'].submit();" class="submitButton">Enregistrer</a>
		</span>
	</fieldset>
</div>
</div>

<input type="hidden" name="action" value="index_agenda.jsp?page=profil&step=2" />
</html:form>
<%
}
else if(step==3)
{
etudforma.setEtudiantId(user.getEtudiant().getId());
etudforma.loadEmplois(user.getEtudiant().getId());
etudforma.clearEmployeur();
%>
<html:form action="/EditProfilStep3.do" method="POST" styleClass="formProfil" >
<div id="liste">
<fieldset id="right">
<h2>Expérience professionnelle</h2>
<ol>
<% 
int i = 0;
for(Profil.ProfilEmployeur d : etudforma.getEmployeurs())
{
i++;
int idEmploi1=d.getId();
String href="/EditProfilStep3.do?id="+idEmploi1+"&amp;delete=";
%>
	<li>
		<span class="itemListe">
			<a href="?page=profil&step=3&id=<%= idEmploi1 %>">Emploi <%= d.getIntitulePoste() %></a>
		</span>
		<html:link styleClass="actionButton" action="<%= href %>">Effacer</html:link>
	</li>
<%
}
%>
</ol>
</fieldset>
</div>
<%
	String idEmploiStr = request.getParameter("id");
	int idEmploi=0;
	if(idEmploiStr!=null)
	{
		idEmploi = Integer.parseInt(idEmploiStr);
	}
	for(Profil.ProfilEmployeur d : etudforma.getEmployeurs())
	{
		if(d.getId()==idEmploi) 
			etudforma.copyEmployeur(d);
	}
	
	
etudforma.setDate_Debut_Emploi(String.format("%td/%tm/%tY", etudforma.getDateDebutEmploi(), etudforma.getDateDebutEmploi(), etudforma.getDateDebutEmploi()));
etudforma.setDate_Fin_Emploi(String.format("%td/%tm/%tY", etudforma.getDateDebutEmploi(), etudforma.getDateDebutEmploi(), etudforma.getDateDebutEmploi()));

%>	
<h2>Expérience professionnelle</h2>
	<fieldset>
		<a href="EditProfilStep3.do?reset=true" class="actionButton">Nouvelle expérience professionnelle</a>
		
	</fieldset>
	<fieldset>
		<label>Employeur</label>
		<span class="input"><html:text name="etudforma" property="employeur" /></span>
	</fieldset>
	<fieldset>
		<label>Date de début de contrat</label>
		<span class="input">
			<input type="text" name="date_Debut_Emploi" id="date_Debut_Emploi" size="11" onblur="dp_dateFormat='dd/mm/yyyy';magicDate(this)" onfocus="if (this.className != 'error') this.select()" value="<%= etudforma.getDate_Debut_Emploi() %>" /> <a href="javascript:void(0);" onclick="g_Calendar.show(event, 'date_Debut_Emploi', 'dd/mm/yyyy')" title="Show popup calendar"><img src="js/cal/calendar.gif" border="0"/></a>(jj/mm/aaaa)
		</span>
	</fieldset>
	<fieldset>
		<label>Date de fin de contrat</label>
		<span class="input">
			<input type="text" name="date_Fin_Emploi" id="date_Fin_Emploi" size="11" onblur="dp_dateFormat='dd/mm/yyyy';magicDate(this)" onfocus="if (this.className != 'error') this.select()" value="<%= etudforma.getDate_Fin_Emploi() %>"/> <a href="javascript:void(0);" onclick="g_Calendar.show(event, 'date_Fin_Emploi', 'dd/mm/yyyy')" title="Show popup calendar"><img src="js/cal/calendar.gif" border="0"/></a>(jj/mm/aaaa)
		</span>
	</fieldset>
	<fieldset>
		<label>Intitulé du poste</label>
		<span class="input"><html:text name="etudforma" property="intitulePoste" /></span>
	</fieldset>
	<fieldset>
		<label>Type de contrat</label>
		<span class="input"><html:text name="etudforma" property="typeContrat" /></span>
	</fieldset>
	<fieldset>
		<label>Fin du contrat</label>
		<span class="input"><html:checkbox name="etudforma" property="finContrat" value="true"/></span>
	</fieldset>
	<fieldset>
		<label>Motif de fin du contrat</label>
		<span class="input"><html:textarea name="etudforma" property="motifFinContrat" /></span>
	</fieldset>
	<fieldset>
		<label></label>
		<span class="input">
		<a href="#" onclick="document.forms['profil3'].submit();" class="submitButton">Enregistrer</a>
		<html:hidden name="etudforma" property="idEmploi" />
		</span>
	</fieldset>
</html:form>
<%}
else if(step==4)
{
etudforma.loadPermis(user.getEtudiant().getId());

%>	
<html:form action="/EditProfilStep4.do" method="GET"  styleClass="formProfil">
<h2>Permis de conduire</h2>
<input type="hidden" name="action" value="index_agenda.jsp?page=profil&step=4" />
	<fieldset>
		<label>Permis de conduire [B]</label>
		<span class="input"><html:checkbox name="etudforma" property="permisDeConduire"/></span>
	</fieldset>
	<fieldset>
		<label>Licence</label>
		<span class="input"><html:checkbox name="etudforma" property="licence"/></span>
	</fieldset>
	<fieldset>
		<label>Voiture</label>
		<span class="input"><html:checkbox name="etudforma" property="voiture"/></span>
	</fieldset>
	<fieldset>
		<label></label>
		<span class="input"></span>
		<input type="hidden" name="action" value="index_agenda.jsp?page=profil&step=3" />
		<a href="#" onclick="document.forms['profil4'].submit();" class="submitButton">Enregistrer</a>
	</fieldset>
</html:form>
<%}
else if(step==5)
{%>	
<html:form action="/EditProfilStep5.do" method="POST" styleClass="formProfil" >
<h2>Logiciels connus</h2>
<input type="hidden" name="action" value="index_agenda.jsp?page=profil&step=5" />
<%

etudforma.loadCategories();
etudforma.loadNiveauxConnaissancesLogiciels(user.getEtudiant().getId());
String strCatId = request.getParameter("catid");
int catid = 0;
if(strCatId!=null)
{
	catid = Integer.parseInt(strCatId);
}
ArrayList<Profil.CategoriesLogiciels> cats = null;
if(catid==0)
{
	out.println("<h2>Choisissez une catégorie de logiciels</h2>");
	cats = etudforma.getCategories();
	for(Profil.CategoriesLogiciels cat : cats)
	{ 
		int i = 0;
	%>
	<fieldset>
		<label></label>
		<span class="input"><a href="?page=profil&step=5&catid=<%= cat.getIdCategorie() %>"><%= cat.getNomCategorie() %></a></span>
	</fieldset>
	<% 
	i++;
	}
}
else
{
%>
	<fieldset>
		<label></label>
		<span class="input"><a href="?page=profil&step=5&catid=0">Retour à l'index des catégories</a></span>
	</fieldset>
<%
	Profil.CategoriesLogiciels cat = etudforma.getCategorie(catid);
%>
	<h2><%= cat.getNomCategorie() %></h2>
<%
	ArrayList<Profil.Logiciels> catlog = etudforma.getLogicielsCategories(cat);
	for(Profil.Logiciels log : catlog)
	{ 
		int i = 0;
		int level = etudforma.getNiveauConnaissanceLogiciel(log.getIdLogiciel());
		ArrayList<String> versions = etudforma.getNiveauConnaissanceLogicielVersions(log.getIdLogiciel());
		String versionsStr = "";
		int j=0;
		for(String s : versions)
		{
			if(j>0) versionsStr+=";"; 
			versionsStr+=s;
			j++;
		}
		//versionsStr="|"+versionsStr+"|";
	%>
	<fieldset>
		<label><%= log.getNomLogiciel() %></label>
		<span class="input">
			<select name="logiciel_<%= log.getIdLogiciel() %>" >
				<option value="0" <%= level==0?"selected='selected'":""%>>0</option>
				<option value="1" <%= level==1?"selected='selected'":""%>>1</option>
				<option value="2" <%= level==2?"selected='selected'":""%>>2</option>
				<option value="3" <%= level==3?"selected='selected'":""%>>3</option>
			</select>
			<% if(log.getVersion()!=null && !log.getVersion().equals(""))
					for(String s : log.getVersion().split(";"))
				{
				if(s!=null&&!s.equals(""))
				{
			%>
				<input type="checkbox" name="langageversion_<%= log.getIdLogiciel() %>_<%= s %>" <% if(versionsStr.contains(s)) out.print("checked=''true"); %> /><%= s %>
			<% }}%>
		</span>
	</fieldset>
	<% 
	i++;
	}
}
%>
	<fieldset>
		<label></label>
		<span class="input">
		<input type="hidden" name="catid" value="<%= catid %>" />
		<input type="hidden" name="action" value="index_agenda.jsp?page=profil&step=5" />
		<a href="#" onclick="document.forms['profil5'].submit();" class="submitButton">Enregistrer</a>
		</span>
	</fieldset>
</html:form>
<%}
else if(step==6)
{

%>	
<html:form action="/EditProfilStep6.do" method="POST" styleClass="formProfil" >
<h2>Langages connus</h2>
<input type="hidden" name="action" value="index_agenda.jsp?page=profil&step=6" />
<% 
etudforma.loadLangages();
etudforma.loadNiveauxConnaissancesLangages(user.getEtudiant().getId());
for(Profil.Langage langage :  etudforma.getLangages())
{
int level = etudforma.getNiveauconnaissanceLangage(langage.getId());
ArrayList<String> versions = etudforma.getNiveauconnaissanceLangageVersions(langage.getId());
		String versionsStr = "";
		int j=0;
		for(String s : versions)
		{
			if(j>0) versionsStr+=";"; 
			versionsStr+=s;
			j++;
		}
%>
	<fieldset>
		<label title="<%= langage.getDescription() %>">
		<%= langage.getNom() %>
		</label>
		<span class="input">
			<select name="langage_<%= langage.getId() %>" >
				<option value="0" <%= level==0?"selected='selected'":""%>>0</option>
				<option value="1" <%= level==1?"selected='selected'":""%>>1</option>
				<option value="2" <%= level==2?"selected='selected'":""%>>2</option>
				<option value="3" <%= level==3?"selected='selected'":""%>>3</option>
			</select>
			<% if(langage.getVersion()!=null && !langage.getVersion().equals(""))
					for(String s : langage.getVersion().split(";"))
				{
				if(s!=null&&!s.equals(""))
				{
			%>
				<input type="checkbox" name="langageversion_<%= langage.getId() %>_<%= s %>" <% if(versionsStr.contains(s)) out.print("checked=''true"); %> /><%= s %>
			<% }} %>
		</span>
	</fieldset>
<% } %>
	<fieldset>
		<label></label>
		<span class="input">
		<a href="#" onclick="document.forms['profil6'].submit();" class="submitButton">Enregistrer</a>
		</span>
	</fieldset>
</html:form>
<%}
else if(step==7)
{
etudforma.loadLangues();
etudforma.loadNiveauxConnaissancesLangues(user.getEtudiant().getId());
%>	
<html:form action="/EditProfilStep7.do" method="POST" styleClass="formProfil" >
<input type="hidden" name="action" value="index_agenda.jsp?page=profil&step=7" />
<%

%>
<h2>Langues</h2>
 <% 
 int i=0;
 for(Profil.Langue langueCl : etudforma.getLangues())
	 {
	 String langue = langueCl.getNomCourt();
	 Locale l = new Locale(langue);
	 String dl = langueCl.getNom();
	 
	 int [] niveau = new int[3];
	 
	 niveau[0]=etudforma.getNiveauConnaissanceLangue(langue, 'l');
	 niveau[1]=etudforma.getNiveauConnaissanceLangue(langue, 'p');
	 niveau[2]=etudforma.getNiveauConnaissanceLangue(langue, 'e');
	 %>
	 <h4><%= dl %></h4>
	<fieldset>
		<label>Lu</label>
		<span class="input">
			<select name="langue_<%= langue %>_l" >
				<option value="0" <%= niveau[0]==0?"selected='selected'":""%>>0</option>
				<option value="1" <%= niveau[0]==1?"selected='selected'":""%>>1</option>
				<option value="2" <%= niveau[0]==2?"selected='selected'":""%>>2</option>
				<option value="3" <%= niveau[0]==3?"selected='selected'":""%>>3</option>
			</select>		
		</span>
	</fieldset>
	<fieldset>
		<label>Parlé</label>
		<span class="input">
			<select name="langue_<%= langue %>_p" >
				<option value="0" <%= niveau[1]==0?"selected='selected'":""%>>0</option>
				<option value="1" <%= niveau[1]==1?"selected='selected'":""%>>1</option>
				<option value="2" <%= niveau[1]==2?"selected='selected'":""%>>2</option>
				<option value="3" <%= niveau[1]==3?"selected='selected'":""%>>3</option>
			</select>		
		</span>
	</fieldset>
	<fieldset>
		<label>Ecrit</label>
		<span class="input">
			<select name="langue_<%= langue %>_e" >
				<option value="0" <%= niveau[2]==0?"selected='selected'":""%>>0</option>
				<option value="1" <%= niveau[2]==1?"selected='selected'":""%>>1</option>
				<option value="2" <%= niveau[2]==2?"selected='selected'":""%>>2</option>
				<option value="3" <%= niveau[2]==3?"selected='selected'":""%>>3</option>
			</select>		
		</span>
	</fieldset> 
			
 <% } %>
<fieldset>
		<label></label>
		<span class="input">
			<a href="#" onclick="document.forms['profil7'].submit();" class="submitButton">Enregistrer</a>
		</span>
</fieldset>
</html:form>
<%}
else if(step==8)
{

	etudforma.loadAtouts(user.getEtudiant().getId());

%>	
<html:form action="/EditProfilStep8.do" method="POST" styleClass="formProfil" >
<input type="hidden" name="action" value="index_agenda.jsp?page=profil&step=8" />
<h2>Atouts</h2>
<fieldset>
		<label>Expliquez ici vos atouts</label>
		<span class="input"></span>
		<html:textarea name="etudforma" property="atouts" rows="10" cols="70"><bean:write name="etudforma" property="atouts"/></html:textarea>
</fieldset>
<fieldset>
		<label></label>
		<span class="input"></span>
	<a href="#" onclick="document.forms['profil8'].submit();" class="submitButton">Enregistrer</a>
</fieldset>
</html:form>
<%}
else if(step==9)
{

	etudforma.loadLoisirs(user.getEtudiant().getId());

%>	
<html:form action="/EditProfilStep9.do" method="POST" styleClass="formProfil" >
<input type="hidden" name="action" value="index_agenda.jsp?page=profil&step=9" />
<h2>Loisirs</h2>
<fieldset>
		<label>Expliquez ici vos loisirs</label>
		<span class="input"></span>
		<html:textarea name="etudforma" property="loisirs" rows="10" cols="70"><bean:write name="etudforma" property="loisirs"/></html:textarea>
</fieldset>
<fieldset>
		<label></label>
		<span class="input">
		<a href="#" onclick="document.forms['profil9'].submit();" class="submitButton">Enregistrer</a>
		</span>
</fieldset>
</html:form>
<%}%>


<br style="clear: both;"/>
