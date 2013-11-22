<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.myapp.struts.Getter, com.myapp.struts.events.JPO, steemploi.persistance.TableJPO, steemploi.service.Utilisateur" %>
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
JPO jpo1 = new JPO();
if(id!=0) jpo1 = new TableJPO().findById(id);
jpo1.setDate_Debut(String.format("%td/%tm/%tY", jpo1.getDateDebut(), jpo1.getDateDebut(), jpo1.getDateDebut()));
g.setObject(jpo1);
%>
<bean:define id="jpo" name="g" property="object" type="com.myapp.struts.events.JPO" scope="request"/>
<h3>JPO</h3>
  <div id="errorwarning"><strong>Erreurs dans le formulaire!</strong> Corrigez les erreurs en rouge</div> 
<html:form action="/EditJPO.do" onsubmit="return configureValidation1jpo();" >
<table>
<caption>Edition des données de la JPO</caption>
<thead><tr><td>Champ</td><td>Valeur</td></tr>
</thead>
<tbody>
<tr>
<td>
<label for="name" id="nameL" class="fixedLabel">Nom</label>
</td>
<td>
<html:text name="jpo" property="name" styleId="name"  onkeydown="return blockEnter(event)" size="30"/>
</td>
</tr>
<tr>
<td>
<label for="date_Debut" id="date_DebutL" class="fixedLabel">Date de la JPO</label>
</td>
<td>
<input  onkeydown="return blockEnter(event)" type="text" name="date_Debut" id="date_Debut" size="11" onblur="dp_dateFormat='dd/mm/yyyy';magicDate(this)" onfocus="if (this.className != 'error') this.select()" value="<%= jpo1.getDate_Debut() %>"/> <a href="javascript:void(0);" onclick="g_Calendar.show(event, 'date_Debut', 'dd/mm/yyyy')" title="Show popup calendar"><img src="js/cal/calendar.gif" style="border: 0px;" /></a>(jj/mm/aaaa)
</td>
</tr>
<tr>
<td><label for="description" id="descriptionL" class="fixedLabel">Description</label>
</td>
<td>
<html:textarea name="jpo" property="description" styleId="description"  onkeydown="return blockEnter(event)" cols="70" rows="15">
<bean:write name="jpo" property="description" />
</html:textarea>
</td>
</tr>
<tr>
<td>
</td>
<td>
<input type="hidden" name="action" value="index_agenda.jsp?page=creerEvenement" />
<html:hidden name="jpo" property="id" />
<a href="#" onclick="checkJpo();" class="submitButton" >Ajouter JPO</a>
</td>
</tr>
</tbody>
</table>

</html:form>