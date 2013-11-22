<%--
    Document   : etudiant.jsp
    Created on : 07-avr.-2009, 19:25:46
    Author     : manuel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="steemploi.service.*, java.util.Calendar"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session"/>
<script type="text/javascript">
</script>

<%
	Formateur f = null;
        if (user.getType() == TypeUtilisateur.FORMATEUR) {
            f = user.getFormateur();
        }
%>
	<p class="infoUserBox"><bean:write name="user" property="username"/>&nbsp;(&nbsp;rôle&nbsp;:&nbsp;&nbsp;<bean:write name="user" property="typeString"/>&nbsp;)&nbsp;</p>
 	<a class="infoUserBox" href="?page=editUtilisateur" >Mon compte</a>
	<a class="infoUserBox" href="logout.jsp" >Me déconnecter</a>
    <h1><a href="index_agenda.jsp" style="color: black; text-decoration: none; cursor: pointer;">Suivi de recherche d'emploi</a></h1>
<div  id = "menu_gauche" >
    <h1>Page formateurs</h1>
    <ul>
    	<li><a class="menuItem" href="?page=agenda" > Agenda </a></li>
    	<li><a class="menuItem" href="?page=echeance" > Ajouter une échéance</a></li>
    	<li><a class="menuItem" href="?page=creerEvenement" >Créer des évènements</a></li>
    	<li><a class="menuItem" href="?page=statistiques" >Statistiques</a></li>
		<li onmouseover="document.getElementById('sousMenuEditerProfils').style.display='block';" onmouseout="document.getElementById('sousMenuEditerProfils').style.display='none';" ><a class="menuItem" href="?page=editerProfils">Paramètres du formulaire de profils</a>
				<ul class="sousMenu" id="sousMenuEditerProfils" style="display: none;" >
					<li>
						<a class="sousMenuItem" href="?page=langues" >Langues</a>
					</li>
					<li>
						<a class="sousMenuItem" href="?page=langages" >Langages</a>
					</li>
					<li>
						<a class="sousMenuItem" href="?page=logiciels" >Connaissances</a>
					</li>
				</ul>
			
		</li>
		<li><a class="menuItem" href="?page=chercherProfils" >Recherche de profils</a></li>
    </ul> 
 </div>
<div id="main">

	<div id="_scroll">
    <%
        String pageQS = request.getParameter("page");
        String qs1 = request.getQueryString();
        if(qs1==null) qs1="";
    	if (pageQS == null || pageQS.equals("agenda")) {%>
    <jsp:include page="agenda.jsp"/>
    <% } else if (pageQS.equals("echeances2")) {
    %>
    <jsp:include page="echeances.jsp"/>
    <% } else if (pageQS.equals("echeance")) {
    %>
    <jsp:include page="echeances2.jsp"/>
    <% } else if (pageQS.equals("echeanceDetails")) {
    %>
    <jsp:include page="echeanceDetails.jsp"/>
    <% } else if (pageQS.equals("deleteEcheance")) {
    %>
    <jsp:include page="deleteEcheance.jsp"/>
    <% } else if (pageQS.equals("login")) { %>
        <jsp:include page="login.jsp"/>
    <% } else if (pageQS.equals("echeanceDetails")) { %>
    <jsp:include page="echeanceDetails.jsp"/>
    <% } else if (pageQS.equals("logout")) { %>
    <jsp:include page="logout.jsp"/>
    <% } else if (pageQS.equals("creerEvenement")) { %>
    <jsp:include page="event/event.jsp"/>
    <% } else if (pageQS.equals("langues")) { %>
    <jsp:include page="editerProfils.jsp"/>
    <jsp:include page="langues.jsp"/>
    <% } else if (pageQS.equals("langages")) { %>
    <jsp:include page="editerProfils.jsp"/>
    <jsp:include page="editerLangages.jsp"/>
    <% } else if (pageQS.equals("logiciels")) { %>
    <jsp:include page="editerProfils.jsp"/>
    <jsp:include page="editerLogiciels.jsp"/>
    <% } else if (pageQS.equals("statistiques")) { %>
    <jsp:include page="stats.jsp"/>
    <% } else if (pageQS.equals("editerProfils")) { %>
<jsp:include page="descriptionEditerProfils.jsp"></jsp:include>
    <% } else if (pageQS.equals("chercherProfils")) { %>
    <jsp:include page="chercherProfils.jsp"/>
    <% } else if (pageQS.equals("editUtilisateur")) { %>
    <jsp:include page="editUtilisateur.jsp"/>
    <% }  %>
    </div>
</div><!-- End of div main (right) -->
