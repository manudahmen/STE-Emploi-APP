<%@page import="steemploi.persistance.TableEcheance"%><%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="steemploi.persistance.*"%>
<%
String idStr = request.getParameter("id");
int id = Integer.parseInt(idStr);
if(new TableEcheance().delete(id))
{%>
<h3>L'écheance n°<%= id %> a été supprimée</h3>
<%}
else
{
	out.println("ERREUR");
}
%>
<h4><a href="index_agenda.jsp?<%= request.getQueryString().replace("page=deleteEcheance", "page=agenda").replace("&id="+id, "") %>">Retour à l'agenda</a></h4>
