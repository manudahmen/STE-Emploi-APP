<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="steemploi.service.Utilisateur" %>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session" />
<%
        Object o = session.getAttribute("user");
        if (o == null || !(o instanceof Utilisateur)) 
        {%><logic:redirect  page="/login.jsp"  /><%
        		Long l = (Long) o;
        } 
 String queryString = request.getQueryString(); 
queryString = queryString.replaceFirst("page=creerEvenement&amp;", "");
%>
<script src="/ste-emploi/js/validator/validator.js" type="text/javascript"></script>
<script src="/ste-emploi/js/validator/conge/configurator_example.js" type="text/javascript"></script>
<script src="/ste-emploi/js/validator/pentreprise/configurator_example.js" type="text/javascript"></script>
<script src="/ste-emploi/js/validator/jpo/configurator_example.js" type="text/javascript"></script>
<script src="/ste-emploi/js/validator/evt/configurator_example.js" type="text/javascript"></script>
<script src="/ste-emploi/event/include.js" type="text/javascript"></script>
<script type="text/javascript">
function check()
{
	if(configureValidation1conge())
	{
		document.forms['conge'].submit();
	}
}
</script>
<h1 id="titrePage">Créer et modifier des évènements</h1>
<p>
<select id="typeevent" name="typeevent" onchange="javascript:updateSelected(false)" style="display: inline;">
	<option value="-1">Sélectionner un type d'évènement</option>
	<option value="1">Echéance pour étudiant ou formation</option>
	<option value="2">JPO</option>
	<option value="3">Présentation d'entreprise</option>
	<option value="4">Congés</option>

	<option value="5">Autre événement</option>

</select>
<select id="action" name="action" onchange="javascript:updateSelected(false)" style="display: inline;">
	<option value="1">Edition</option>
	<option value="2">Liste</option>
</select>
</p>
<p>
	<a class="actionButton" href="#" onclick="javascript:chooseList(1)">Echéance pour étudiant ou formation</a>
	<a class="actionButton" href="#" onclick="javascript:chooseList(2)">JPO</a>
	<a class="actionButton" href="#" onclick="javascript:chooseList(3)">Présentation d'entreprise</a>
	<a class="actionButton" href="#" onclick="javascript:chooseList(4)">Congés</a>
	<a class="actionButton" href="#" onclick="javascript:chooseList(5)">Autres évènements</a>
</p>
<p>
	<a class="actionButton" href="#" onclick="javascript:chooseNew(1)">Nouvelle Echéance pour étudiant ou formation</a>
	<a class="actionButton" href="#" onclick="javascript:chooseNew(2)">Nouvelle JPO</a>
	<a class="actionButton" href="#" onclick="javascript:chooseNew(3)">Nouvelle Présentation d'entreprise</a>
	<a class="actionButton" href="#" onclick="javascript:chooseNew(4)">Nouveau congé</a>
	<a class="actionButton" href="#" onclick="javascript:chooseNew(5)">Nouvel évènement</a>
</p>
<p>
<input type="button" value="GO" onclick="go();" style="display: none;"/> 
<input type="hidden" name="action" value="event/event.jsp" style="display: none;"/>
</p>
 <div id="forward"></div>
<% 
String str_event_type = request.getParameter("eventtype");
String str_event_id = request.getParameter("id");
//out.println(str_event_id + " & " + str_event_type);
if((str_event_id!=null && str_event_type!=null) || (str_event_type!=null))
{
if(str_event_id!=null)
{
	int event_type = Integer.parseInt(str_event_type);
	int event_id = Integer.parseInt(str_event_id);
	%>
	<script language="javascript" type="text/javascript">
		chooseUpdate(<%= event_type %>, <%=str_event_id %>);			
	</script>

	<%
}
else
{
	int event_type = Integer.parseInt(str_event_type);
	%>
	<script language="javascript" type="text/javascript">
		chooseList(<%= event_type %>);			
	</script>

	<%
	
}

}
%>