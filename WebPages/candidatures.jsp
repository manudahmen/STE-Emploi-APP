<%--
    Document   : etudiant.jsp
    Created on : 07-avr.-2009, 19:25:46
    Author     : manuel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.myapp.struts.Getter, java.util.Calendar, java.util.List, steemploi.service.Candidature, steemploi.persistance.TableCandidature"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session"/>
<jsp:useBean id="g" class="com.myapp.struts.Getter" scope="request"/>
<script type="text/javascript">
function check()
{
	if(configureValidation1()) { 
			document.forms['ecandidature'].submit(); 
		}
}
</script>
<% 

int user_id = user.getEtudiant().getId();
int id = 0;
if(request.getParameter("id")!=null)
	id= Integer.parseInt(request.getParameter("id"));
Candidature candidature1 = new Candidature();

if(id!=0)
	candidature1 = new TableCandidature().findById(id, user_id);
else
	candidature1.setId(id);
	candidature1.setDate_(String.format("%td/%tm/%tY", candidature1.getDate(), candidature1.getDate(),candidature1.getDate()));
	candidature1.setEtudiantId(user_id);

g.setObject(candidature1);

List<Candidature> candidatures = new TableCandidature().findByEtudiant(user_id);

%>
<bean:define id="candidature" name="g" property="object" type="steemploi.service.Candidature" scope="request"/>
<script src="js/validator/validator.js" type="text/javascript"></script>
<script src="js/validator/candidatures/configurator_example.js" type="text/javascript"></script>
<h1 id="titrePage">Candidatures</h1>
  <div id="errorwarning"><strong>Erreurs dans le formulaire!</strong> Corrigez les erreurs en rouge</div> 
<html:form action="/EditCandidature.do" onsubmit="return configureValidation1();">
<p><a class="actionButton" href="?page=candidatures&id=0">Nouvelle candidature</a></p>
<table>
<tr>
<td>
<label for="tache" id="tacheL" class="fixedLabel">Tache effectuée(envoi cv, contact tél, ...):</label>
</td>
<td>
<html:text maxlength="100" size="100"  maxlength="100" size="100"  name="candidature" property="tache" styleId="tache" onkeydown="return blockEnter(event)"/>
</td>
</tr>
<tr>
<td>
<label for="date_" id="date_L" class="fixedLabel">Date:</label>

</td>
<td>
<input type="text" name="date_" id="date_" size="11" onkeydown="return blockEnter(event)" onblur="dp_dateFormat='dd/mm/yyyy';magicDate(this)" onfocus="if (this.className != 'error') this.select()" value="<%= candidature.getDate_() %>">
<a href="javascript:void(0);" onclick="g_Calendar.show(event, 'date_', 'dd/mm/yyyy')" title="Show popup calendar">
	<img src="js/cal/calendar.gif" border="0">
</a>(jj/mm/aaaa)
</td>
</tr>
<tr>
<td>
<label for="entreprise" id="entrepriseL" class="fixedLabel">Entreprise:</label>

</td>
<td>
<html:text maxlength="100" size="100"  name="candidature" property="entreprise" styleId="entreprise" onkeydown="return blockEnter(event)"/>
</td>
</tr>
<tr>
<td>
<label for="fonction" id="fonctionL" class="fixedLabel">Fonction:</label>

</td>
<td><html:text maxlength="100" size="100"   name="candidature" property="fonction" styleId="fonction" onkeydown="return blockEnter(event)"/>
</td>
</tr>
<tr>
<td>
<label for="reponse" id="reponseL" class="fixedLabel">Réponse:</label>

</td>
<td><html:text maxlength="100" size="100"  name="candidature" property="reponse" styleId="reponse" onkeydown="return blockEnter(event)"/>
</td>
</tr>
<tr>
<td>
<label for="suite" id="suiteL" class="fixedLabel">Suite à donner:</label>

</td>
<td><html:text maxlength="100" size="100"  name="candidature" property="suite" styleId="suite" onkeydown="return blockEnter(event)"/>
</td>
</tr>
<tr>
<td>
<label for="infos" id="infosL" class="fixedLabel">Autres informations:</label>

</td>
<td><html:text maxlength="100" size="100"   name="candidature" property="infos" styleId="infos" onkeydown="return blockEnter(event)"/>
</td>
</tr>
<tr>
<td>
<label for="contact" id="contactL" class="fixedLabel">Contact, adresse, email, tél:</label>

</td>
<td><html:text maxlength="100" size="100"   name="candidature" property="contact" styleId="contact" onkeydown="return blockEnter(event)"/>
</td>
</tr>
<tr>
<td>
</td>
<td>
<input type="hidden" name="action" value="index_agenda.jsp?page=candidatures" />
<input type="hidden" name="etudiantId" value="<%= user_id %>"/>
<input type="hidden" name="id" value="<%= candidature1.getId() %>"/>
<a href="#" onclick="javascript:check()" class="submitButton">Enregistrer</a>
<%--<logic:equal name="candidature" property="id" value="0">
<html:submit value="Ajouter candidature"/>
</logic:equal>
<logic:notEqual name="candidature" property="id" value="0">
<html:submit value="Mettre à jour candidature"/>
</logic:notEqual>
--%>
</td>
</tr>
</table>


</html:form>
<hr/>
<table id="candidature_table"> 
<tr>
<th>Tache
</th>
<th>Date
</th>
<th>Entreprise
</th>
<th>Fonction
</th>
<th>Réponse
</th>
<th>Suite à donner
</th>
<th>Autres informations
</th>
<th>Contact
</th>
<th>Action
</th>
<th>Action
</th>
</tr>
<% for(Candidature c : candidatures)
{
c.setDate_(String.format("%td/%tm/%tY", c.getDate(), c.getDate(),c.getDate()));
%>
<tr>
<td><%= c.getTache()%></td>
<td><%= c.getDate_()%></td>
<td><%= c.getEntreprise()%></td>
<td
><%= c.getFonction()%></td>
<td><%= c.getReponse()%></td>
<td><%= c.getSuite()%></td>
<td><%= c.getInfos()%></td>
<td><%= c.getContact()%></td>
<td><a class="actionButton" href='?page=candidatures&amp;id=<%= c.getId() %>'>Editer</a></td>
<td><a class="actionButton" href='DeleteCandidature.do?id=<%= c.getId() %>'>Supprimer</a></td>
</tr>

<%}
%>
</table>
