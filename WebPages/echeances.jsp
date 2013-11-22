<%-- 
    Document   : echeances
    Created on : 26-avr.-2009, 21:31:25
    Author     : manuel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.myapp.struts.*,steemploi.service.*,steemploi.persistance.*,java.util.List,java.util.ArrayList,java.util.Calendar,java.util.Locale,java.util.Collection"%>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session" />
<%
        Object o = session.getAttribute("user");
        if (o == null || !(o instanceof Utilisateur)) 
        {%><logic:redirect  page="/login.jsp"  /><%
        		Long l = (Long) o;
        } 
	int formation_id = -1;
	int etudiant_id = -1;
	if (user.getType() == TypeUtilisateur.FORMATEUR) {
		formation_id = Integer.parseInt(request
		.getParameter("formation_id")==null?"-1":request
		.getParameter("formation_id"));
		etudiant_id = Integer.parseInt(request
		.getParameter("etudiant_id")==null?"-1":request
		.getParameter("etudiant_id"));
		request.setAttribute("formation_id", formation_id);
		request.setAttribute("etudiant_id", etudiant_id);
	}
%>
	<jsp:useBean id="g" class="com.myapp.struts.Getter" scope="request" />
	<%
		Echeance e = new Echeance();
			String idStr = request.getParameter("id");
			int id = 0;

			if (idStr != null && !idStr.equals("")) {
				id = Integer.parseInt(idStr);
				e = new TableEcheance().findById(id, true);
				e.setDate_a(e.getDate().get(Calendar.YEAR));
				e.setDate_m(e.getDate().get(Calendar.MONTH));
				e.setDate_j(e.getDate().get(Calendar.DATE));
				out.println("<script type='text/javascript'>");
					/*for(Formation ff : e.getEtudiants())
						out.println("";*/
				for(Etudiant et : e.getEtudiants())
					//out.println("<p>et:" +et.getId()+"</p>");
					out.println("ajouterEtudiantParId("+et.getId()+");");
				for(SessionsFormations f : e.getFormations())
					out.println("ajouterFormationParId("+f.getId()+")");
				out.println("</script>");
			} else {

				Calendar c = Calendar.getInstance();
				String qsDay = request.getParameter("day");
				long day = 1;
				if(qsDay!=null)
				{
					day = Long.parseLong(qsDay);
					c.setTimeInMillis(day);
				}
				e.setDate(c);
			}

			ArrayList<SelectOptions> months = new ArrayList<SelectOptions>();
			for (int i = 0; i <= 11; i++) {
				Calendar m = Calendar.getInstance();
				m.set(Calendar.MONTH, i);
				SelectOptions mso = new SelectOptions();
				mso.setLabel(m.getDisplayName(Calendar.MONTH, 1, Locale
						.getDefault()));
				mso.setValue(i);
				months.add(mso);

			}
			List<Formation> formations = null;
			g.setObject(e);
	%>
	<bean:define id="echeance" name="g" property="object" toScope="request"
		type="steemploi.service.Echeance" />
	<%
		g.setObject(months);
	%>
<html:form action="/EditEcheance" styleId="formid" method="get" acceptCharset="UTF-8">
<%
	if (user.getType() == TypeUtilisateur.FORMATEUR) 
{
			formations = new TableFormations().findAll();
			request.getRequestDispatcher("formationsList1.jsp")
					.include(request, response);
}
%>
<bean:define id="mois" name="g" property="object" type="List<com.myapp.struts.SelectOptions>" />
	<div class="listeChoixDiv" id="div3">
	<p>Date</p>
	<p><html:select name="echeance" property="date_j" styleClass="datelargeur" 
		styleId="form_date_j_id" onmouseout="valide(validateFrame, 2, 'div3')">
		<% for(int i=1; i<=31; i++) {%>
		<option value="<%= i %>" <% if(e.getDate_j()==i) out.println(" selected "); %> ><%= i %></option>
		<% }%>
		</html:select>/ 
	<html:select styleId="form_date_m_id"  styleClass="datelargeur"
		name="echeance" property="date_m" >
		<html:optionsCollection name="mois"/>
	</html:select> / 
	<html:select name="echeance" property="date_a"  styleClass="datelargeur"
		styleId="form_date_a_id" >
		<% for(int i=1990; i<2038; i++) {%>
		<option value="<%= i %>" <% if(e.getDate_a()==i) out.println(" selected "); %>><%= i %></option>
		<% }%>
		</html:select>
	</p>
	<p>Code de l'échéance</p>
	<p><html:text name="echeance" property="code" styleId="form_code_id" styleClass="largeur" onmouseover="valide(validateFrame, 2, 'div3')"/></p>
	<p>Titre</p>
	<p><html:text name="echeance" property="title" styleId="form_title_id" styleClass="largeur" onmouseover="valide(validateFrame, 2, 'div3')"/></p>
</div>
	<div class="listeChoixDiv">
		<a href="#" onclick="javascript:valideTout();">Valider tout</a>
		<ol  id="errors_ul" style="overflow: scroll;"></ol>
	</div>
	<div class="listeChoixDiv" id="div4">
	<p>Description</p>
	<p>
		<html:textarea styleId="form_description_id" cols="20" rows="10" 
						styleClass="largeur" 
						name="echeance" property="description"
	 				onmouseover="valide(validateFrame, 3, 'div4')">
	 	</html:textarea>
	 </p>
</div>
	<div class="listeChoixDiv">
	<p><html:submit disabled="false" property="d" value="Enregistrer" onclick="submit_echeance_form();return submit();" styleClass="largeurSubmit" styleId="submit_button"/></p>
	<p><html:button disabled="false" property="d" value="Réinitialiser" onclick="reset();" styleClass="largeurSubmit"/></p>
	<p><html:button disabled="false" property="d" value="Annuler" onclick="cancel();" styleClass="largeurSubmit"/></p>
</div>
	<input type="hidden" name="id" id="id" value="<%= echeance.getId() %>" />
	<input type="hidden" name="action"	value="<% if(e.getId()>0) out.print("index_agenda.jsp?page=echeanceDetails&amp;id="+e.getId()+"&amp;millis="+e.getDate().getTimeInMillis()+"&amp;formation_id=" + formation_id + "&amp;etudiant_id="+etudiant_id); else out.print("index_agenda.jsp?page=echeanceDetails&millis=" +e.getDate().getTimeInMillis()+"&amp;formation_id=" + formation_id + "&amp;etudiant_id="+etudiant_id + "&amp;id=attr"); %>"/>
	<html:hidden styleId="_etudforma" name="echeance" property="_etudforma" value=""/>
</html:form>
<%
out.println("<script type='text/javascript'>");
out.println("valideTout();");
				/*out.println("valide(validateFrame, 1, 'selected_div');");
					out.println("valide(validateFrame, 2, 'div3');");
					out.println("valide(validateFrame, 3, 'div4');");*/
out.println("</script>");
%>