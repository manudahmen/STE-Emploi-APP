<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.myapp.struts.events.*, steemploi.persistance.*, steemploi.service.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edition d'évènements</title>
</head>
<body>
<%
        Object o = session.getAttribute("user");
        if (o == null || !(o instanceof Utilisateur) || ((Utilisateur)o).getType()!=TypeUtilisateur.FORMATEUR) 
        {%><logic:redirect  page="/login.jsp"  /><%
        		Long l = (Long) o;
        } 
%>
<%


String evtType = request.getParameter("evtType");
String evt_idStr = request.getParameter("evt_id");
int evt_id = Integer.parseInt(evt_idStr);

if(evtType.equals("jpo"))
{
JPO jpo = new TableJPO().findById(evt_id);
%>
  	<a class="shadowbox" 
    	rel="shadowbox"
    	href="eventview/jpo.jsp?id=<%= jpo.getId() %>&amp;type=jpo">
    		JPO
    	</a>
<%
} else if(evtType.equals("evt"))
{
Evt ev = new TableEvt().findById(evt_id);
%>  	<a class="shadowbox" 
    	rel="shadowbox"
    	href="eventview/evt.jsp?id=<%= ev.getId() %>&amp;type=evt">
    		Evènement
    	</a>
<%
} else if(evtType.equals("pentreprise"))
{
PEntreprise pe = new TablePEntreprise().findById(evt_id);
%>  	<a class="shadowbox" 
    	rel="shadowbox"
    	href="eventview/pentreprise.jsp?id=<%= pe.getId() %>&amp;type=pentreprise">
    		Entreprise
    	</a>
<%
} else if(evtType.equals("conge"))
{
Conge c = new TableConge().findById(evt_id);
%>  	<a class="shadowbox" 
    	rel="shadowbox"
    	href="eventview/conge.jsp?id=<%=c.getId() %>&amp;type=conge">
    		Congés
    	</a>
<%

}
%>
</body>
</html>