<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.myapp.struts.Getter, com.myapp.struts.events.Evt, steemploi.persistance.TableEvt, steemploi.service.Utilisateur" %>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session" />
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="g" class="com.myapp.struts.Getter" scope="request"></jsp:useBean>
<%
        Object o = session.getAttribute("user");
        if (o == null || !(o instanceof Utilisateur)) 
        {%><logic:redirect  page="/login.jsp"  /><%
        		Long l = (Long) o;
        } 

int id = Integer.parseInt(request.getParameter("id"));
Evt evt1 = new Evt();
if(id!=0) evt1 = new TableEvt().findById(id);
evt1.setDate_Debut(String.format("%td/%tm/%tY", evt1.getDateDebut(), evt1.getDateDebut(), evt1.getDateDebut()));
evt1.setDate_Fin(String.format("%td/%tm/%tY", evt1.getDateFin(), evt1.getDateFin(), evt1.getDateFin()));
g.setObject(evt1);
%>
<bean:define id="evt" name="g" property="object" type="com.myapp.struts.events.Evt" scope="request"/>
<h3>Evènement</h3>
  <div id="errorwarning"><strong>Erreurs dans le formulaire!</strong> Corrigez les erreurs en rouge</div> 
<html:form action="/EditEvt.do" onsubmit="return configureValidation1conge();" >
<table>
<caption>Edition des données de l'évènement</caption>
<thead><tr><td>Champ</td><td>Valeur</td></tr>
</thead>
<tbody>
<tr>
<td>
<label for="name" id="nameL" class="fixedLabel">Nom</label>

</td>
<td>
<html:text name="evt" property="name" size="30" styleId="name" onkeydown="return blockEnter(event)" />
</td>
</tr>
<tr>
<td>
<label for="location" id="locationL" class="fixedLabel">Lieu</label>
</td>
<td>
<html:text name="evt" property="location" size="30" styleId="location" onkeydown="return blockEnter(event)" />
</td>
</tr>
<tr>
<td>
<label for="date_Debut" id="date_DebutL" class="fixedLabel">Date de début</label>
</td>
<td>
<input type="text" name="date_Debut" id="date_Debut" onkeydown="return blockEnter(event)" size="11" onblur="dp_dateFormat='dd/mm/yyyy';magicDate(this)" onfocus="if (this.className != 'error') this.select()" value="<%= evt1.getDate_Debut() %>"/> <a href="javascript:void(0);" onclick="g_Calendar.show(event, 'date_Debut', 'dd/mm/yyyy')" title="Show popup calendar"><img src="js/cal/calendar.gif" style="border: 0px;" /></a>(jj/mm/aaaa)
</td>
</tr>
<tr>
<td>
<label for="date_Fin" id="date_FinL" class="fixedLabel">Date de fin</label>
</td>
<td>
<input type="text" name="date_Fin" id="date_Fin" onkeydown="return blockEnter(event)" size="11" onblur="dp_dateFormat='dd/mm/yyyy';magicDate(this)" onfocus="if (this.className != 'error') this.select()" value="<%= evt1.getDate_Fin() %>"/> <a href="javascript:void(0);" onclick="g_Calendar.show(event, 'date_Fin', 'dd/mm/yyyy')" title="Show popup calendar"><img src="js/cal/calendar.gif" style="border: 0px;" /></a>(jj/mm/aaaa)
</td>
</tr>
<tr>
<td>
<label for="description" id="descriptionL" class="fixedLabel">Description</label>
</td>
<td>
<html:textarea name="evt" property="description" cols="70" rows="15" styleId="description" onkeydown="return blockEnter(event)" >
<bean:write name="evt" property="description" />
</html:textarea>
</td>
</tr>
<tr>
<td>
<label for="link" id="linkL" class="fixedLabel">Lien</label>
</td>
<td>
<html:text name="evt" property="link" size="30" styleId="link" onkeydown="return blockEnter(event)" />
</td>
</tr>
</tbody>
</table>
<html:hidden name="evt" property="id" />
<input type="hidden" name="action" value="index_agenda.jsp?page=creerEvenement" />
<input type="submit" name="evt" value="Ajouter Evènement"/>
<html:cancel value="Annuler"></html:cancel>

</html:form>