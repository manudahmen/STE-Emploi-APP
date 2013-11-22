<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.myapp.struts.Getter, com.myapp.struts.events.Conge, steemploi.persistance.TableConge, steemploi.service.Utilisateur" %>
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
        } %>
<%
int id = Integer.parseInt(request.getParameter("id"));
Conge conge1 = new Conge();
if(id!=0) conge1 = new TableConge().findById(id);
conge1.setDate_Debut(String.format("%td/%tm/%tY", conge1.getDateDebut(), conge1.getDateDebut(), conge1.getDateDebut()));
conge1.setDate_Fin(String.format("%td/%tm/%tY", conge1.getDateFin(), conge1.getDateFin(), conge1.getDateFin()));
g.setObject(conge1);
%>
<bean:define id="conge" name="g" property="object" type="com.myapp.struts.events.Conge" scope="request"/>
<h3>Congé</h3>
  <div id="errorwarning"><strong>Erreurs dans le formulaire!</strong> Corrigez les erreurs en rouge</div> 
<html:form action="/EditPEntreprise.do" onsubmit="return configureValidation1conge();" >
<table>
<tr><td>
<label for="nom" id="nomL" class="fixedLabel">Nom du congé</label>
</td><td><html:text name="conge" property="nom" styleId="nom"/></td></tr>
<tr>
<td>
<label for="date_Debut" id="date_DebutL" class="fixedLabel">Date de début</label>
</td>
<td>
<input type="text" name="date_Debut" id="date_Debut" size="11" onblur="dp_dateFormat='dd/mm/yyyy';magicDate(this)" onfocus="if (this.className != 'error') this.select()"> <a href="javascript:void(0);" onclick="g_Calendar.show(event, 'date_Debut', 'dd/mm/yyyy')" title="Show popup calendar"><img src="js/cal/calendar.gif" border="0"></a>(jj/mm/aaaa)
</td>
</tr>
<tr>
<td>
<label for="date_Fin" id="date_FinL" class="fixedLabel">Date de fin</label>
</td>
<td>
<input type="text" name="date_Fin" id="date_Fin" size="11" onblur="dp_dateFormat='dd/mm/yyyy';magicDate(this)" onfocus="if (this.className != 'error') this.select()"> <a href="javascript:void(0);" onclick="g_Calendar.show(event, 'date_Fin', 'dd/mm/yyyy')" title="Show popup calendar"><img src="js/cal/calendar.gif" border="0"></a>(jj/mm/aaaa)
</td>
</tr>

</table>

<html:hidden name="conge" property="id" />
<input type="hidden" name="action" value="index_agenda.jsp?page=creerEvenement" />
<input type="submit" name="conge" value="Ajouter Congé"/>
</html:form>