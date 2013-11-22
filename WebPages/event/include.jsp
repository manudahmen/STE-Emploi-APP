<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="steemploi.service.Utilisateur" %>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session" />
<%
        Object o = session.getAttribute("user");
        if (o == null || !(o instanceof Utilisateur)) 
        {%><logic:redirect  page="/login.jsp"  /><%
        		Long l = (Long) o;
        } %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>Resource Forwarding</title>
  <meta http-equiv="Content-Type" content="text/html; charset=us-ascii" />
  <script type='text/javascript' src='../dwr/tabs/tabs.js'> </script>
  <script type='text/javascript' src='../dwr/dwr/engine.js'> </script>
  <script type='text/javascript' src='../dwr/dwr/util.js'> </script>
  <script type='text/javascript' src='../dwr/dwr/interface/Include.js'> </script>
  <script type="text/javascript" src='include.js'> </script>
  <link rel="stylesheet" type="text/css" href="../dwr/tabs/tabs.css" />
  <link rel="stylesheet" type="text/css" href="../dwr/generic.css" />
</head>
<body>

<h1>Création d'un événement</h1>
<form action="/AjouterEvenement.jsp" method="get" accept-charset="UTF-8">
<select id="typeevent" name="typeevent" >
	<option onclick="forward(this)" value="-1">Sélectionner un type d'évènement</option>
	<option onclick="forward(this)" value="1">Echéance pour étudiant ou formation</option>
	<option onclick="forward(this)" value="2">JPO</option>
	<option onclick="forward(this)" value="3">Présentation d'entreprise</option>
	<option onclick="forward(this)" value="4">Congés</option>

</select>
</form>
 <div id="forward"></div>

</body>
</html>
