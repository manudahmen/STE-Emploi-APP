<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="steemploi.service.*, steemploi.persistance.*, com.myapp.struts.*, java.util.*, steemploi.service.Utilisateur"%>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session" />
<jsp:useBean id="g" class="com.myapp.struts.Getter" scope="request" />
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<% 
String prefix = request.getParameter("prefix");
String label = request.getParameter("label");

Calendar c = Calendar.getInstance();
try
{
long day = Long.parseLong(request.getParameter("day"));
c.setTimeInMillis(day);
}
catch(Exception ex)
{}
ArrayList<SelectOptions> months = new ArrayList<SelectOptions>();
			for (int i = 0; i < 12; i++) {
				Calendar m = Calendar.getInstance();
				m.set(Calendar.MONTH, i);
				SelectOptions mso = new SelectOptions();
				mso.setLabel(m.getDisplayName(Calendar.MONTH, 1, Locale
						.getDefault()));
				mso.setValue(i);
				months.add(mso);

			}
			
			g.setObject(months);
    %>
    <bean:define id="mois" name="g" property="object" type="List<com.myapp.struts.SelectOptions>" />
	<!-- <div class="listeChoixDiv" id="div3"> -->
<tr>
	<td><%= label %></td>
	<td><select name="<%= prefix %>_date_j">
		<% for(int i=1; i<32; i++) {%>
		<option value="<%= i-1 %>"><%= i %></option>
		<% }%>
		</select>/ 
	<select name="<%= prefix %>_date_m" name="event" property="date_m">
		<% for(SelectOptions so : months){ %>
		<option value="<%= so.getValue() %>"><%= so.getLabel()%></option>
		<% } %>
	</select> / 
	<select name="<%= prefix %>_date_a">
		<% for(int i=1990; i<2038; i++) {%>
		<option value="<%= i %>"><%= i %></option>
		<% }%>
		</select>
		(jj/mm/aaaa)
	</td>
	</tr>	
    <!-- </div> -->