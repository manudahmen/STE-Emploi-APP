<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="steemploi.persistance.TablePEntreprise, com.myapp.struts.events.PEntreprise, steemploi.service.Utilisateur" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session" />
<jsp:useBean id="g" class="com.myapp.struts.Getter" scope="request"></jsp:useBean>
<%
        Object o = session.getAttribute("user");
        if (o == null || !(o instanceof Utilisateur)) 
        {%><logic:redirect  page="/login.jsp"  /><%
        		Long l = (Long) o;
        } 
int id = 0;
if(request.getParameter("id")!=null)
	id= Integer.parseInt(request.getParameter("id"));
PEntreprise pentreprise = new PEntreprise();
if(id!=0)
	pentreprise = new TablePEntreprise().findById(id);
pentreprise.setDate_Debut(String.format("%td/%tm/%tY", pentreprise.getDateDebut(), pentreprise.getDateDebut(), pentreprise.getDateDebut()));

g.setObject(pentreprise);
%>
<bean:define id="entreprise" name="g" property="object" type="com.myapp.struts.events.PEntreprise" scope="request"/>
<h3>Présentation d'entreprise</h3>
  <div id="errorwarning"><strong>Erreurs dans le formulaire!</strong> Corrigez les erreurs en rouge</div> 
<html:form action="/EditPEntreprise.do" onsubmit="return configureValidation1entreprise();" >
<table>
<tr>
<td>
<label for="date_Debut" id="date_DebutL" class="fixedLabel">Date de présentation</label>

</td>
<td><input type="text" name="date_Debut" id="date_Debut" size="11" onblur="dp_dateFormat='dd/mm/yyyy';magicDate(this)" onfocus="if (this.className != 'error') this.select()" value="<%= pentreprise.getDate_Debut() %>" /> <a href="javascript:void(0);" onclick="g_Calendar.show(event, 'date_Debut', 'dd/mm/yyyy')" title="Show popup calendar"><img src="js/cal/calendar.gif" border="0" /></a></td>
</tr>
<tr>
<td>
<label for="nom" id="nomL" class="fixedLabel">Nom de l'entreprise</label>
</td>
<td>
<html:text name="entreprise" property="nom" styleId="nom" onkeydown="return blockEnter(event)" />
</td>
</tr>
<tr>
<td>
<label for="telephone" id="telephoneL" class="fixedLabel">Téléphone</label>
</td>
<td>
<html:text name="entreprise" property="telephone" styleId="telephone" onkeydown="return blockEnter(event)" />
</td>
</tr>
<tr>
<td>
<label for="email" id="emailL" class="fixedLabel">Email</label>
</td>
<td>
<html:text name="entreprise" property="email" styleId="email" onkeydown="return blockEnter(event)" />
</td>
</tr>
<tr>
<td>
<label for="url" id="urlL" class="fixedLabel">Site web</label>
</td>
<td>
<html:text name="entreprise" property="url" styleId="url" onkeydown="return blockEnter(event)" />
</td>
</tr>
<tr>
<td>
<label for="description" id="descriptionL" class="fixedLabel">Description de l'entreprise</label>
</td>
<td>

<html:textarea name="entreprise" property="description" styleId="description" cols="70" rows="15" onkeydown="return blockEnter(event)" >
</html:textarea>
</td>
</tr>
<tr>
<td>
<label for="infoscomplementaires" id="infoscomplementairesL" class="fixedLabel">Informations complémentaires</label>
</td>
<td>

<html:textarea name="entreprise" property="infoscomplementaires" styleId="infoscomplementaires" cols="70" rows="15"  onkeydown="return blockEnter(event)">
</html:textarea>
</td>
</tr>
<tr>
<td>
</td>
<td>
<html:hidden name="entreprise" property="id" />
<input type="hidden" name="type" value="entreprise"/>
<input type="hidden" name="action" value="index_agenda.jsp?page=creerEvenement" />
<a href="#" onclick="checkEntreprise();" class="submitButton" >Ajouter présentation d'entreprise</a>
</td>
</tr>
</table>
</html:form>