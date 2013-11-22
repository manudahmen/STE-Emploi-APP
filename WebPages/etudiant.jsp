<%-- 
    Document   : etudiant.jsp
    Created on : 07-avr.-2009, 19:25:46
    Author     : manuel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="steemploi.service.*" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session"/>
<%
	Etudiant e = null;
        if (user.getType() == TypeUtilisateur.ETUDIANT) {
            e = user.getEtudiant();
        }
%>
	<p class="infoUserBox"><%=  user.getUsername() %>&nbsp;(&nbsp;rôle&nbsp;:&nbsp;&nbsp;<%=user.getTypeString() %>&nbsp;)&nbsp;</p>
 	<a class="infoUserBox" href="?page=editUtilisateur" >Mon compte</a>
	<a class="infoUserBox" href="logout.jsp" >Me déconnecter</a>
    <h1><a href="index_agenda.jsp" style="color: black; text-decoration: none; cursor: pointer;">Suivi de recherche d'emploi</a></h1>
    <span style="float: right;"><strong><%= user.getNom() + " " + user.getPrenom() %></strong>&nbsp;-&nbsp;<%= user.getEmail() %></span>
<div id="menu_gauche">
    <h1>Page étudiants</h1>
    <ul>
        <li><a class="menuItem" href="?page=agenda">Agenda</a></li>
        <li><a class="menuItem" href="?page=echeances">Echéances</a></li>
        <li><a class="menuItem" href="?page=candidatures">Candidatures</a></li>
        <li><a class="menuItem" href="https://europass.cedefop.europa.eu/" target="NEW">Créer ou mettre à jour un CV Europass</a></li>
        <li><a class="menuItem" href="?page=entreprises">Entreprises et contacts</a></li>
        <li><a class="menuItem" href="?page=profil">Profil informatisé</a></li>
        <li><a class="menuItem" href="?page=cvlettres">Cv et lettres de motivation</a></li>
    </ul>
</div>
<div id="main">
    <div id="_scroll">
    <%
        String pageQS = request.getParameter("page");
        if (pageQS == null || pageQS.equals("agenda")) {%>
    <jsp:include page="agenda.jsp"/>
    <% } else if (pageQS.equals("task")) {
    %>
    <jsp:include page="task.jsp"/>
    <% } else if (pageQS.equals("echeances")) {
    %>
    <jsp:include page="echeancesEtudiant.jsp"/>
    <%    } else if (pageQS.equals("profil")) {
    %>
    <jsp:include page="profil.jsp"/>
    <%    } else if (pageQS.equals("cvlettres")) {
    %>
    <jsp:include page="cvlettres.jsp"/>
    <%    } else if (pageQS.equals("entreprises")) {
    %>
    <jsp:include page="task.jsp"/>
    <%    } else if (pageQS.equals("candidatures")) {
    %>
    <jsp:include page="candidatures.jsp"/>
    <% } else if (pageQS.equals("editUtilisateur")) { %>
    <jsp:include page="editUtilisateur.jsp"/>
    <% }  %>
    </div>
</div>
