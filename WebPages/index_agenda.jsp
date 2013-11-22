<%-- 
    Document   : index.jsp
    Created on : 07-avr.-2009, 19:20:00
    Author     : Manuel Dahmen
                 manuel.dahmen@gmail.com
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="steemploi.service.*,steemploi.persistance.DBConnection" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%
response.setContentType("text/html; charset=UTF-8");
request.setCharacterEncoding("UTF-8");

Object o = session.getAttribute("session_id");
		String pageP = request.getParameter("page");
String QS = request.getQueryString();
        if (session==null || session.getAttribute("user") == null || 
        		!(((Utilisateur)session.getAttribute("user")) instanceof Utilisateur)) 
        {
        %>
        	<logic:redirect  page="/login.jsp"  />
        <% }
        else if (((Utilisateur)session.getAttribute("user")).getType() == TypeUtilisateur.ADMIN) {
        %><logic:redirect  page="admin/usersmanager.jsp"  /><%
        } else  { %>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr" dir="ltr">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link type="text/css" href="styles/styles.css" media="screen" rel="stylesheet" />
        <title>Travail de fin d'études - Application 'Suivi de recherche d'emploi'</title>
        <script type="text/javascript" language="JavaScript">
            var user_id = '<%=user.getType() == TypeUtilisateur.FORMATEUR ? "formateur" : "etudiant"%>';
            var session = '<%=session.getAttribute("session_id")%>';
        </script>
  <script type='text/javascript' language='javascript' src='/ste-emploi/dwr/dwr/engine.js'> </script>
  <script type='text/javascript' language='javascript' src='/ste-emploi/dwr/dwr/util.js'> </script>
<script type='text/javascript' src='/ste-emploi/dwr/dwr/interface/Include.js'></script>
  <script type="text/javascript" language='javascript' src='event/include.js'> </script>
  <script type="text/javascript" language='javascript' src="event/validateEcheance.js"> </script>
  <link rel="stylesheet" type="text/css" href="js/cal/calpopup.css" />
  <script type="text/javascript" language='javascript' src='shadowbox/shadowbox.js'> </script>
<link rel="stylesheet" type="text/css" href="shadowbox/shadowbox.css" />

  <script type="text/javascript" language='javascript'>
  function closeFrame(href)
	{
		top.frames[0].close();
		document.href = href;
	} 
  </script>


<style>
<!--
-->
</style>
<script type="text/javascript">
Shadowbox.init({
    // let's skip the automatic setup because we don't have any
    // properly configured link elements on the page
    skipSetup: true,
    // include the html player because we want to display some html content
    players: ["iframe"]
});

window.onload = function(){

    // open a welcome message as soon as the window loads
	for(i=0; i<42; i++)
	{
			Shadowbox.setup("a.shadowbox", {
        		height:     350,
        		width:      350,
	  			gallery: 	"Evenements du mois"
	  		});
	}
};
</script>
<script src="js/cal/calpopup.js" type="text/javascript"></script>
<script type="text/javascript" src="js/cal/dateparse.js"></script>
    </head>
    <body>
         <%
        	if (user.getType() == TypeUtilisateur.ETUDIANT) {
        %><jsp:include page="etudiant.jsp"/><%
        	} else if (user.getType() == TypeUtilisateur.FORMATEUR) {
        %><jsp:include page="formateur.jsp"/><%
        	} else if (user.getType() == TypeUtilisateur.COORDINATRICE) {
        %><%--<jsp:include page="coordinatrice.jsp"/>--%><%
			}%>
<div id="footer"></div>
    </body>
</html>
<%}%>
