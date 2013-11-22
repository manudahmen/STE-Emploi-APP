<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.myapp.struts.Getter, com.myapp.struts.events.Conge, steemploi.persistance.*, steemploi.service.*" %>

<%@page import="java.net.URLEncoder"%><jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session" />
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="g" class="com.myapp.struts.Getter" scope="request"></jsp:useBean>
<script type="text/javascript">
function check()
{
			document.forms['cv'].submit(); 
}
</script>
<%
        Object o = session.getAttribute("user");
        if (o == null || !(o instanceof Utilisateur) || !(((Utilisateur)(o)).getType().equals(TypeUtilisateur.ETUDIANT)))
        {%><logic:redirect  page="/login.jsp"  /><%
        		Long l = (Long) o;
        } 
CV cv1 = null;
cv1 = new CVRepos().findByEtudiantId(user.getEtudiant().getId());
if(cv1==null) cv1 = new CV();
g.setObject(cv1);
 %>
<h1 id="titrePage">CV et Lettres</h1>
<p>Vous pouvez stocker votre cv ici.</p>
<script type="text.javascript">
function verifieFormat()
{
}
</script>
<html:form action="/PostCV.do" method="POST" enctype="multipart/form-data">
<bean:define id="cv" type="steemploi.service.CV" name="g" property="object"/>
<%
if(cv.getId()!=0)
{
%>
<p>Nom du cv: <strong><%= cv.getFilenameOriginal() %></strong></p>
<p><a class="actionButton" href="DownloadCV">Télécharger CV</a></p>
<p><a class="actionButton" href="DeleteCV.do?id=<%= cv.getId() %>&action=<%= URLEncoder.encode("index_agenda.jsp?page=cvlettres") %>">Effacer CV</a></p>
<hr/>
<p>Mettre à jour votre cv (l'ancienne version sera effacée) :  </p>
<p><html:file  name="cv" property="cvFile" onchange="verifieFormat()"/> (2M maximum) </p>
<%
} else
{
%>
<p>Pas de cv enregistré</p>
<p>Télécharger votre cv :</p>
<p><html:file  name="cv" property="cvFile" onchange="verifieFormat()"/> (2M maximum) </p>
<%
}
%>
<p><html:text name="cv" property="name" onchange="verifieFormat()"/></p>
<input type="hidden" name="action" value="index_agenda.jsp?page=cvlettres" />
<a href="#" onclick="javascript:check()" class="submitButton">Enregistrer</a>
</html:form>
