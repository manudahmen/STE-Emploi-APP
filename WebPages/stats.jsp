<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="steemploi.persistance.*,steemploi.service.*,java.util.*, java.text.DateFormat"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.myapp.struts.*,steemploi.service.*,steemploi.persistance.*,java.util.List,java.util.ArrayList,java.util.Calendar,java.util.Locale,java.util.Collection"%>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session" />
<% 
String type="tableau";
if(request.getParameter("type")!=null && request.getParameter("type")!="")
{
	type=request.getParameter("type");
}
Calendar date = Calendar.getInstance();
if(request.getParameter("date")!=null && request.getParameter("date")!="")
{
	date.setTime(DateFormat.getDateInstance(DateFormat.SHORT,
            Locale.FRANCE).parse(request.getParameter("date")));
}
%>
<style type="text/css">
<!--
<% for(int i=0;i<16;i++)
{%>th.class<%=i%>{
background-color:#<%=Integer.toHexString(15-i)+Integer.toHexString(i)%>2;
font-weight:bold;
}
<%}
%>
}
#stat td {	
}

/*
#line1 th
{
	background-color: #fef;
	font-weight: bold;
}
#line2 th
{
	background-color: #fdf;
	font-weight: bold;
}*/
#stat .formaname td {
	background-color: #fdd;
	font-weight: bold;
}

#stat .etudiant td {
	background-color: #dfd;
	text-align: center;
}
-->
</style>
<script type="text/javascript">
	function openChart(etudiantId)
	{
		
	}
</script>

<h1 id="titrePage">Statistiques</h1>
<form name="inputStats" method="get">
<input type="hidden" name="page" value="statistiques"/>
Date:&nbsp;
<input type="text" name="date" id="date"
	onkeydown="return blockEnter(event)" size="11"
	onblur="dp_dateFormat='dd/mm/yyyy';magicDate(this)"
	onfocus="if (this.className != 'error') this.select(); document.forms['inputStats'].submit();"
	value="<%= String.format("%2d/%2d/%4d", date.get(Calendar.DATE), date.get(Calendar.MONTH)+1, date.get(Calendar.YEAR)) %>" 
	onchange="document.forms['inputStats'].submit();"
	/>
<a href="javascript:void(0);"
	onclick="g_Calendar.show(event, 'date', 'dd/mm/yyyy')"
	title="Show popup calendar"><img src="js/cal/calendar.gif"
	border="0" /></a>
(jj/mm/aaaa)
Type:&nbsp;
<select name="type"
	onchange="document.forms['inputStats'].submit();">
	<option value="tableau"
		<%= "tableau".equals(type)?"selected='true'":""%>
		>Tableau</option>
	<option value="graphique"
		<%= "graphique".equals(type)?"selected='true'":""%>
		>Graphique</option>
</select>
</form>


<%
if("tableau".equals(type))
{
%>
<table border="1" id="stat">
	<%
TableStat ts = new TableStat();
TableSessionsFormations tsf = new TableSessionsFormations();

TableCodeCategorieTache tcodes = new TableCodeCategorieTache();
List<CategoriesTache> codes =  tcodes.findAll();
List<SessionsFormations> sfs = tsf.findAll(false);
out.println("<tr id='line1'><th>Sessions de formation</th><th>Etudiants</th>");
int i = 0;
for(CategoriesTache ct : codes)
{
	out.println("<th class='class"+i+"'>"+ct.getTitle().substring(0, 1).toUpperCase()+ct.getTitle().substring(1)+"</th>");
	i++;
}
out.println("</tr>");
out.println("<tr id='line2'><th></th><th></th>");
i = 0;
for(CategoriesTache ct : codes)
{
	out.println("<th class='class"+i+"'>"+ct.getCode()+"</th>");
	i++;
}
out.println("</tr>");
for(SessionsFormations sf : sfs)
{
	
	out.println("<tr class='formaname'><td>"+sf.getFormation().getNom()+" session de "+String.format("%02d/%02d/%04d", sf.getDateStart().get(Calendar.DAY_OF_MONTH), sf.getDateStart().get(Calendar.MONTH)+1, sf.getDateStart().get(Calendar.YEAR))+"</td>");
	int sf_id = sf.getId();
	HashMap<Etudiant, ArrayList<TacheStatItem>> map =  ts.getStats(sf, date);
	Set<Etudiant> etudiants = map.keySet();
	int [] total = new int[codes.size()]; 
	out.println("<td colspan='"+(codes.size()+1)+"' "+((etudiants.size()==0)?"style='color: gray;' ":"")+">");
	if(etudiants.size()==0)
	{
	out.println("<h4>Aucune statistique pour cette formation</h4>");
	}
	out.println("</td>");
	out.println("</tr>");
	for(Etudiant et : etudiants)
	{
		out.println("<tr class='etudiant'>");
		out.println("<td><!--<a href='javascript:openChart("+et.getId()+")'>Graphique</a>--></td><td>"+et.getNom()+" "+et.getPrenom()+"</td>");
		ArrayList<TacheStatItem> items = map.get(et);
		int j = 0;
		for(CategoriesTache ct : codes)
		{
			boolean ex = false; 
			for( TacheStatItem item : items)
			{
				if(ct.getCode().equals(item.getCode()))
				{
					out.println("<td>"+item.getCount()+"</td>");
					total[j++] +=  item.getCount();
					ex = true;
				}
			}
			if(!ex)
			{
				out.println("<td>0</td>");
				j++;
			}
		}
		out.println("</tr>");
	}
	if(etudiants.size()>0)
	{
		int j=0;
		out.println("<tr class='etudiant'><td></td><td><strong>Total</strong></td>");
		for(CategoriesTache ct : codes)
		{
			out.println("<td>"+total[j]+"</td>");
			j++;
		}
		out.println("</tr>");
	}
}

%>
</table>
<%
}
else if("graphique".equals(type))
{
%>
<%
}
%>
