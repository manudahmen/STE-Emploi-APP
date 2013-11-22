<%-- 
    Document   : listeFormations.jsp
    Created on : 05-mai-2009, 15:58:52
    Author     : manuel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page
	import="steemploi.service.*,steemploi.persistance.*,java.util.List,java.util.Calendar,java.util.Locale,java.util.regex.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session" />
<%

String escape="\'";
String formation_id_str = request.getParameter("formation_id");
String etudiant_id_str=request.getParameter("etudiant_id");
if(formation_id_str==null) formation_id_str="-1";
if(etudiant_id_str==null || etudiant_id_str=="-1") etudiant_id_str="-2";
	int formation_id = Integer.parseInt(formation_id_str);
	int etudiant_id = Integer.parseInt(etudiant_id_str);

	List<SessionsFormations> sessions;
	 
	boolean current = (request.getParameter("passed") == null || request.getParameter("passed").equals("false"));
	sessions = new TableSessionsFormations().findAll(current);

	SessionsFormations sf0 = new TableSessionsFormations()
			.findById(formation_id);
%>
<script type="text/javascript" language="JavaScript">
    <%int i = 0;
	int j = 0;%>
    formations = new Array();
    etudiants = new Array();
<%for (SessionsFormations sf : sessions) {%>
            formations[<%=i%>] = new Array();
            formations[<%=i%>][0] = <%=sf.getId()%>;
            formations[<%=i%>][1] = '<%= Pattern.compile(escape).matcher(sf.getFormation().getNom()).replaceAll("&quot;") %>';
            formations[<%=i%>][2]= '<%=  Pattern.compile(escape).matcher(sf.getDateStart().get(Calendar.YEAR) + " "+ sf.getDateStart().getDisplayName(Calendar.MONTH, 1,Locale.getDefault())).replaceAll("&quot;") %>';
    <%i++;
				for (Etudiant e : sf0.getEtudiants()) {%>
                    etudiants[<%=j%>] = new Array();
                    etudiants[<%=j%>][0]= <%=sf.getId()%>;
                    etudiants[<%=j%>][1] = <%=e.getId()%>;
                    etudiants[<%=j%>][2]= '<% if(e.getNom()!=null) out.print(Pattern.compile(escape).matcher(e.getNom()).replaceAll("&quot;")); %>';
                    etudiants[<%=j%>][3]= '<% if(e.getPrenom()!=null) out.print(Pattern.compile(escape).matcher(e.getPrenom()).replaceAll("&quot;")); %>';
                    etudiants[<%=j%>][5]= true;
            <%j++;
				}
			}%>
	function checkSelect(select)
	{
			document.getElementById("etud_opt_tous").selected=true;
			document.forms["listeform"].submit();
	}
</script>
<form action="index_agenda.jsp"  method="get" id="listeform" name="listeform">
 <input type="hidden" name="page" value="agenda" />
<input type="hidden" name="millis" value="<%= request.getParameter("millis")!=null ? request.getParameter("millis") : Calendar.getInstance().getTimeInMillis() %>" />
<p><input type="checkbox" name="passed" onchange="javascript:submit()"  
<%= ("on".equals(request.getParameter("passed")) ? " value='on' checked='checked'" : " value='on' ") %>  />
<span style="color: white;">Inclure les formations terminées</span>
<p><select name="formation_id" onchange="javascript:checkSelect(this)"  class="droiteselect">
	<option value="-1" style="font-weight: bold;">Choisissez une formation</option>
	<%
		for (SessionsFormations sf : sessions) {
	%>
	<option value="<%=sf.getId()%>" onclick="javascript:checkSelect(this)"
		<%if (sf.getId() == formation_id) {
					out.print(" selected='true' ");
				}%>>
	<%=sf.getDateStart().get(Calendar.YEAR)
						+ " "
						+ sf.getDateStart().getDisplayName(Calendar.MONTH, 1,
								Locale.getDefault()) + " " + sf.getFormation().getNom()%>
	</option>
	<%
		}
	%>
</select></p>
<p> <select id="etudiant_id_select"  name="etudiant_id" multiple="false" size="5" class="droiteselect"
	onchange="javascript:submit()" id="etudiant_id_select">
	<option value="-1" id="etud_opt_aucun" style="visibility: hidden; display: none;"></option> 
	<option value="-2" id="etud_opt_tous" <% if(etudiant_id==-2) out.print(" selected='true' ");  %>>Tous</option>
	
	<%
		if (formation_id != -1) {
			for (i = 0; i < sf0.getEtudiants().size(); i++) {					
		Etudiant e = sf0.getEtudiants().get(i);
	%>
	<option value="<%=e.getId()%>"
		<%if (etudiant_id == e.getId()) {
								out.println(" selected='true' ");
							}%>>
	<%=e.getPrenom() + " " + e.getNom()%></option>
	<%
		}
	}
		
	%>

</select>
</p>
 <%
 	if (request.getAttribute("include") == null) {
 %>
 </form>
<%
	}
%>
