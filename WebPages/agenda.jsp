<%-- 
    Document   : agenda.jsp
    Created on : 08-avr.-2009, 11:39:36
    Author     : Manuel Dahmen
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap, java.util.Map, steemploi.service.Echeance, steemploi.ui.DateFormat, steemploi.persistance.TableEcheance, java.util.ArrayList, java.util.Date,java.lang.Long,java.util.Calendar,java.util.Locale,steemploi.ui.*,steemploi.service.*, steemploi.persistance.*, com.myapp.struts.events.*" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session"/>
<%
int d=0;
int [] count = new int[42]; 
Locale.setDefault(Locale.FRANCE);
String passed = request.getParameter("passed");
if(passed==null) passed = "false";
int formation_id = -1;
        int etudiant_id = -1;
        if (user.getType() == TypeUtilisateur.FORMATEUR) {
            String s_formation_id = request.getParameter("formation_id");
            String s_etudiant_id = request.getParameter("etudiant_id");
            if (s_formation_id != null) {
                formation_id = Integer.parseInt(s_formation_id);
            }
            if (s_etudiant_id != null) {
                etudiant_id = Integer.parseInt(s_etudiant_id);
            }
            request.setAttribute("formation_id", formation_id);
            request.setAttribute("etudiant_id", etudiant_id);
            request.setAttribute("include", true);
        }

        else if(user.getType()==TypeUtilisateur.ETUDIANT)
        {
        	etudiant_id = user.getEtudiant().getId();
        	formation_id=user.getEtudiant().getFormation_id();
        }


        long millis = (request.getParameter("millis") == null) ? Calendar.getInstance().getTimeInMillis() : Long.parseLong((String) request.getParameter("millis"));
		request.setAttribute("millis", millis);
        String etendue = request.getParameter("etendue");

            etendue = "mois";

		DateFormat dateFormat = new DateFormat(millis, etendue);
        String[] days = dateFormat.getDays();
        long[] millisA = dateFormat.getMillisA();
        Calendar[] dateA = dateFormat.getDateA();
        String[] dateYMD = dateFormat.getDateYMD();
        int min = dateFormat.getMin();
        int max = dateFormat.getMax();
        Calendar prev = dateFormat.getPrev();
        Calendar next = dateFormat.getNext();
        String courant = dateFormat.getCourant();

        for(int i=0; i<42; i++) out.println("<!--"+ dateA[i].get(Calendar.DAY_OF_MONTH)+"-->");       
        /*GetAgendaEvents get = new GetAgendaEvents(dateA[0], dateA[41]);
        Map<String, ArrayList<Echeance>> events = null;
        Map<String, Integer> count[d]s = null;
        if (user.getType() == UserType.FORMATEUR) {
            if (formation_id != -1) {	
                get.getFormationPourPourFormateurRole(formation_id);
            }
            if (etudiant_id != -1) {
            	get.getEtudiantPourFormateurRole(etudiant_id);
            }
        } else {
            Etudiant e = new TableEtudiants().findByUserId(user.getId());
            events = get.getEtudiantRole(etudiant_id);
           
        }
        */


        int gettype=-1;
        SessionsFormations sessionFormation =null;
        Etudiant etudiant =null;
		GetAgendaEventsFormateur2 getForm =null;
		//GetAgendaEventsEtudiant getEtud =null;
		ArrayList<Object> objects =null;
        Map<String, ArrayList<Echeance>> events=null ;
        if (user.getType() == TypeUtilisateur.FORMATEUR) 
        {    if (formation_id != -1 && etudiant_id!=-1) {	
                gettype=0;
            }
            if (etudiant_id != -1) {
            	gettype=1;
            }
            if(etudiant_id==-2)
            {
            	gettype=2;
            }
            sessionFormation = new TableSessionsFormations().findById(formation_id);
    		etudiant = new TableEtudiants().findById(etudiant_id);        
            getForm = new GetAgendaEventsFormateur2(dateA[0], dateA[41]);
            events = getForm.find(gettype, formation_id, etudiant_id);
        } else if (user.getType() == TypeUtilisateur.ETUDIANT)
        {
		gettype=1;
        	Etudiant et = new TableEtudiants().findById(etudiant_id);
            getForm = new GetAgendaEventsFormateur2(dateA[0], dateA[41]);
            events = getForm.find(1, et.getSf().getId(), et.getId());
        }
        
    	if(user.getType()==TypeUtilisateur.FORMATEUR) {
        
        out.println("<p id='infos' style='display: none;'>");
		out.println("Utilisateurs:&nbsp;"+user.getUsername()+" | "+ user.getType()+"&nbsp;|&nbsp;");
		if(etudiant_id!=-1 && etudiant_id!=-2) out.println("Etudiant:&nbsp;"+etudiant.getPrenom()+" " +etudiant.getNom()+"&nbsp;|&nbsp;");
		if(formation_id!=-1 && etudiant_id==-2) out.println("&nbsp;Tous les étudiants&nbsp;");
		if(formation_id!=-1) out.println("Formation&nbsp;:&nbsp;"+sessionFormation.getName()+"&nbsp;(&nbsp;commence le : "+sessionFormation.getDateStart().get(Calendar.DAY_OF_MONTH)+""+sessionFormation.getDateStart().get(Calendar.MONTH)+""+sessionFormation.getDateStart().get(Calendar.YEAR)+"&nbsp;)");
        out.println("</p>");

    	}else if(user.getType()==TypeUtilisateur.ETUDIANT) {
    		Calendar date1 =user.getEtudiant().getSf().getDateStart(); 
 			String dateDebutForma = String.format("%td/%tm/%tY", date1,date1,date1);
 			Calendar date2 =user.getEtudiant().getSf().getDateEnd(); 
    		String dateFinForma = String.format("%td/%tm/%tY", date2,date2,date2);
    			
            %>
            <p id='infos' style="display: none;">
    		User:&nbsp;<%= user.getUsername() %> | <%= user.getType() %>&nbsp;|&nbsp;
    		Etudiant:&nbsp;<%= user.getEtudiant().getPrenom() %>&nbsp;<%= user.getEtudiant().getNom() %>&nbsp;|&nbsp;
    		
    		Session de formation : <%=user.getEtudiant().getSf().getFormation().getNom() %>
    		(
    		<%= dateDebutForma %>
    		à
    		<%= dateFinForma %>
    		)
    		</p>
    		<%

        	}

%>
<script type="text/javascript">
    dates = new Array();
    datesDisplays = new Array();
    count = new Array();
    <%int i;
    for (i = 0; i < 42; i++) {
            out.println("dates["+i+"] = '" + dateYMD[i] + "';");
        }
    for (i = 0; i < 42; i++) {
            out.println("count["+i+"] = '" + ((events.get(dateYMD[i]) == null) ? 0 : ((Integer) (events.get(dateYMD[i]).size()))) + "';");
        }
    for (i = 0; i < 42; i++) {
           out.println("datesDisplays["+i+"] = '" + days[i] + "';");
        }%>

     function afficherEvents(d)
     {
         // Ã  remplacer par LightBox
         details_div = document.getElementById("details_event");
         details_div.innerHTML="<h2>Evèements de " + datesDisplays[d] + "</h2>";
         details_div.innerHTML+=document.getElementById(""+d).innerHTML;
     }
         
</script>
<h1 id="titrePage">Agenda</h1>
<div id="agenda_nav">
    <a href="?page=agenda&amp;etendue=mois&amp;millis=<%=  prev.getTimeInMillis()%>&amp;passed=<%= passed %>&amp;formation_id=<%=formation_id%>&amp;etudiant_id=<%=etudiant_id%>" class="gauche">
        <img src="images/arrow_left.png" width="100px" height="30px" title="Mois précédent" />
        <p>Mois précédent</p>
    </a>
    <a href="?page=agenda&amp;etendue=mois&amp;millis=<%=next.getTimeInMillis()%>&amp;passed=<%= passed %>&amp;formation_id=<%=formation_id%>&amp;etudiant_id=<%=etudiant_id%>" class="droite">
        <img src="images/arrow_right.png" width="100px" height="30px" title="Mois suivant" />
        <p>Mois suivant</p>
        
    </a>
    
    
    
    
    <hr class="clearNav"/>
</div>
<div id="agenda_mois">
<h2><%=courant%></h2>
<div id="panel_right">
<%
	if (user.getType() == TypeUtilisateur.FORMATEUR) {
%>
    <div class="choix_formation">
    <% request.setAttribute("etudiant_id", etudiant_id); %>
    <% request.setAttribute("formation_id", formation_id); %>
    
        <jsp:include page="listeFormations.jsp"/>
    </div>
    <% } %>
</div>

<div id="jours_conteneur">

<%
	Calendar auj=Calendar.getInstance();
	for (d = 0; d < 42; d++) {
		ArrayList<String> a = new ArrayList<String>();
		ArrayList<String> editLink = new ArrayList<String>();
		ArrayList<String> deleteLink = new ArrayList<String>();
		ArrayList<Integer> ids = new ArrayList<Integer>();
            String day = days[d];
            String class0 = "";
           if (d < min || d > max) {
                class0 = "jourhorsmois";
            } else {
                class0 = "jourdumois";
 		  		if (dateA[d].get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || dateA[d].get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) 
 		  		{
                    class0 = "weekend";
                }

            }
		if(dateA[d].get(Calendar.DATE)==auj.get(Calendar.DATE)&&
			dateA[d].get(Calendar.MONTH)==auj.get(Calendar.MONTH) &&
			dateA[d].get(Calendar.YEAR)==auj.get(Calendar.YEAR))
				class0="aujourdhui";
            objects = getForm.getByDay(dateA[d]);
%>
<%
	count[d] = 0;
	int color=0;
      ArrayList<Echeance> os = new ArrayList<Echeance>();
  	if(events.containsKey(dateYMD[d])) 
	{	
		os = events.get(dateYMD[d]);
	}
	if(dateA[d].compareTo(Calendar.getInstance())<0) { color=1;} 
      if(os!=null && os.size()>0)
	  objects.addAll(0, os);
  	count[d] = objects.size();
	if(count[d]>0)
{
		
    int j = 0;
    for (j = 0; j < count[d]; j++) {
        Object o = objects.get(j);
        Echeance e = null;
        Tache t = null;
        
        if (o instanceof Echeance) {
            e = (Echeance) o;
            e = new TableEcheance().findById(e.getId()); %>
<%
a.add(
"<a class='shadowbox' id='a_evt_"+d+"_"+j+"'"  
+    	"rel='shadowbox' title='Evenements du "+ day +"' " 
+    	"href='eventview/event.jsp?id="+ e.getId() +"&amp;type=echeance'>"
+   		"Echéance"
+    	"</a>");
if(user.getType().equals(TypeUtilisateur.FORMATEUR))
	editLink.add("<a class='link' title='Editer l&apos;évènement' style='font-size: smaller; color: green;' id='edit_a_evt_"+d+"_"+j+"'  href='?page=echeanceDetails&amp;id="+ e.getId() +"&amp;formation_id="+ formation_id +"&amp;etudiant_id="+ etudiant_id +"'>●</a>");
	deleteLink.add("<a class='link' title='Supprimer l&apos;évènement' style='font-size: smaller; color: red;' id='delete_a_evt_"+d+"_"+j+"'  href='DeleteEvt?id="+ e.getId() +"&amp;eventtype=1&amp;formation_id="+ formation_id +"&amp;etudiant_id="+ etudiant_id +"'>x</a>");
        } else if (o instanceof Tache) {
            t = (Tache) o;
    	    } else if(o instanceof JPO)
{
JPO jpo = (JPO) o;
a.add("<a class='shadowbox' id='a_evt_"+d+"_"+j+"'" 
+    	"rel='shadowbox' "
+    	"title='Evenements du "+ day +"' " 
+    	"href='eventview/jpo.jsp?id="+ jpo.getId() +"&amp;type=jpo'>"
+    		"JPO"
+    	"</a>");
if(user.getType().equals(TypeUtilisateur.FORMATEUR))
	editLink.add("<a class='link' title='Editer l&apos;évènement' style='font-size: smaller; color: green;' id='edit_a_evt_"+d+"_"+j+"'  href='?page=creerEvenement&amp;id="+ jpo.getId() +"&eventtype=2'>●</a>");
	deleteLink.add("<a class='link' title='Supprimer l&apos;évènement' style='font-size: smaller; color: red;' id='delete_a_evt_"+d+"_"+j+"'  href='DeleteEvt?id="+ jpo.getId() +"&amp;eventtype=2&amp;formation_id="+ formation_id +"&amp;etudiant_id="+ etudiant_id +"'>x</a>");

} else if(o instanceof Evt)
{
Evt ev= (Evt) o;
a.add("<a  class='shadowbox' id='a_evt_"+d+"_"+j+"' rel='shadowbox' title='Evenements du "+ day +"' href='eventview/evt.jsp?id="+ ev.getId() +"&amp;type=evt'>Evènement</a>");
if(user.getType().equals(TypeUtilisateur.FORMATEUR))
	editLink.add("<a class='link' title='Editer l&apos;évènement' style='font-size: smaller; color: green;' id='edit_a_evt_"+d+"_"+j+"'  href='?page=creerEvenement&amp;id="+ ev.getId() +"&eventtype=5'>●</a>");
	deleteLink.add("<a class='link' title='Supprimer l&apos;évènement' style='font-size: smaller; color: red;' id='delete_a_evt_"+d+"_"+j+"'  href='DeleteEvt?id="+ ev.getId() +"&amp;eventtype=5&amp;formation_id="+ formation_id +"&amp;etudiant_id="+ etudiant_id +"'>x</a>");
} else if(o instanceof PEntreprise)
{
PEntreprise pe = (PEntreprise)o;
a.add("<a class='shadowbox' id='a_evt_"+d+"_"+j+"' rel='shadowbox' title='Evenements du "+ day +"' href='eventview/pentreprise.jsp?id="+ pe.getId() +"&amp;type=pentreprise'>Entreprise</a>");
if(user.getType().equals(TypeUtilisateur.FORMATEUR))
	editLink.add("<a class='link' title='Editer l&apos;évènement' style='font-size: smaller; color: green;' id='edit_a_evt_"+d+"_"+j+"'  href='?page=creerEvenement&amp;id="+ pe.getId() +"&eventtype=3'>●</a>");
	deleteLink.add("<a class='link' title='Supprimer l&apos;évènement' style='font-size: smaller; color: red;' id='delete_a_evt_"+d+"_"+j+"'  href='DeleteEvt?id="+ pe.getId() +"&amp;eventtype=3&amp;type=&amp;formation_id="+ formation_id +"&amp;etudiant_id="+ etudiant_id +"'>x</a>");

} else if(o instanceof Conge)
{
Conge c= (Conge)o;
a.add("<a class='shadowbox' id='a_evt_"+d+"_"+j+"' rel='shadowbox' title='Evenements du "+ day +"' href='eventview/conge.jsp?id="+c.getId() +"&amp;type=conge'>Congé</a>");
if(user.getType().equals(TypeUtilisateur.FORMATEUR))
	editLink.add("<a class='link' title='Editer l&apos;évènement' style='font-size: smaller; color: green;' id='edit_a_evt_"+d+"_"+j+"' href='?page=creerEvenement&amp;id="+ c.getId() +"&eventtype=4'>●</a>");
	deleteLink.add("<a class='link' title='Supprimer l&apos;évènement' style='font-size: smaller; color: red;' id='delete_a_evt_"+d+"_"+j+"' href='DeleteEvt?id="+ c.getId() +"&amp;eventtype=4'>x</a>");

}
}
}
/* END FOR ECHEANCES IN DAY */
%>
<div class="<%= class0 %>">
<% 
if(a.size()>1)
{
%>
<a href="#" class="evt1navLeft" onclick="javascript:prev_2<%= d %>();">◄</a>
<%
}
int j=0;
if(a.size()>1)
{
%>
<a href="#" class="evt1navRight" onclick="javascript:next_2<%= d %>();">►</a>
<% 
}
%>


<%
	if(user.getType().equals(TypeUtilisateur.FORMATEUR)) {
%>

<p style="text-align: center; padding: 0px; margin: 2px;"><a  title="Ajouter une échéance" 
href="?page=creerEvenement&amp;day=<%=millisA[d]%>&amp;formation_id=<%=formation_id%>&amp;etudiant_id=<%=etudiant_id%>">
    <%= dateA[d].get(Calendar.DATE) %></a></p>

<%
	} else if(user.getType().equals(TypeUtilisateur.ETUDIANT)) {
%>

<p style="text-align: center; padding: 0px; margin: 2px;">
    <%= dateA[d].get(Calendar.DATE) %></p>
<% }  %>
<div class="jour_events" id="d<%= d %>">
<div id="showEvt<%= d %>" class="showEvt">
<%
for (j = 0; j<a.size(); j++)
{
	out.println(a.get(j)); 
	if(user.getType().equals(TypeUtilisateur.FORMATEUR))
	{	
		out.println(editLink.get(j));
		out.println(deleteLink.get(j));
	}
}
%>
<% if(a.size()>1){ %>
<span id="a_evt_<%= d %>_index"></span>/<%= count[d] %>
<% } %>

</div> 
</div>

<%   /* END OF HIDDEN DIV*/ %>
    </div><% /* END OF jour_events DIV */ %>
    <% /* END OF class0 DAY MAIN DIV*/ %>
    <% if ((((d + 1) % 7) == 0) && (d > 0)) {%>
  		<div style="clear: left"></div>
    <% }
        
}%>
</div><% /* END OF jour_conteneur DIV*/ %>
<%/** END OF panel_right DIV */%>
</div>



<script type="text/javascript">
<% for(d=0; d<42; d++)
{
%>
	var index<%= d %> = 0;
	var length<%= d %> = <%= count[d] %>;
	function prev_2<%= d %>()
	{
		if(length<%= d %>==0)
		{
			return;
		}
		var changed = false;
		if(index<%= d %>>0) 
			{
				index<%= d %>--; 
				changed=true; 
			}
		var evt = document.getElementById('a_evt_<%= d %>_'+index<%= d %>);
		evt.style['display']='inline'; 
		<% if(user.getType().equals(TypeUtilisateur.FORMATEUR)){ %>
				evt = document.getElementById('edit_a_evt_<%= d %>_'+index<%= d %>);
				evt.style['display']='inline';
				evt = document.getElementById('delete_a_evt_<%= d %>_'+index<%= d %>);
				evt.style['display']='inline';
		<%} %>
		if(changed)
		{
			evt = document.getElementById('a_evt_<%= d %>_'+(index<%= d %>+1));
			evt.style['display']='none';
			<% if(user.getType().equals(TypeUtilisateur.FORMATEUR)){ %>
					evt = document.getElementById('edit_a_evt_<%= d %>_'+(index<%= d %>+1));
					evt.style['display']='none';
					evt = document.getElementById('delete_a_evt_<%= d %>_'+(index<%= d %>+1));
					evt.style['display']='none';
			<% } %>
		}
		set_index<%= d %>(); 
	}
	function next_2<%= d %>()
	{
		if(length<%= d %>==0)
		{
			return;
		}
		var changed = false;
		if(index<%= d %><length<%=d %>-1) 
			{
				index<%= d %>++; 
				changed=true; 
			}
		var evt = document.getElementById('a_evt_<%= d %>_'+index<%= d %>);
		evt.style['display']='inline'; 
		<% if(user.getType().equals(TypeUtilisateur.FORMATEUR)){ %>
		evt = document.getElementById('edit_a_evt_<%= d %>_'+index<%= d %>);
		evt.style['display']='inline'; 
		evt = document.getElementById('delete_a_evt_<%= d %>_'+index<%= d %>);
		evt.style['display']='inline'; 
		<% } %>
		if(changed)
		{
			evt = document.getElementById('a_evt_<%= d %>_'+(index<%= d %>-1));
			evt.style['display']='none';
			<% if(user.getType().equals(TypeUtilisateur.FORMATEUR)){ %>
			evt = document.getElementById('edit_a_evt_<%= d %>_'+(index<%= d %>-1));
			evt.style['display']='none';
			evt = document.getElementById('delete_a_evt_<%= d %>_'+(index<%= d %>-1));
			evt.style['display']='none';			<%} %>
		} 
		set_index<%= d %>(); 
	}
	function show_2<%= d %>()
	{
		if(length<%= d %>==0)
		{
			return;
		}
		var evt = document.getElementById('a_evt_<%= d %>_'+(index<%= d %>));
		evt.style['display']='inline'; 
		<% if(user.getType().equals(TypeUtilisateur.FORMATEUR)){ %>
		evt = document.getElementById('edit_a_evt_<%= d %>_'+(index<%= d %>));
		evt.style['display']='inline'; 
		evt = document.getElementById('delete_a_evt_<%= d %>_'+(index<%= d %>));
		evt.style['display']='inline'; 
		<% } %>
		set_index<%= d %>(); 
	}
	function set_index<%= d %>()
	{
		if(length<%= d %><2)
		{
			return;
		}
		var evt = document.getElementById('a_evt_<%= d %>_index');
		evt.innerHTML=index<%= d %>+1;
	}
<% } 
for(d=0; d<42; d++)
{
	if(count[d]>0)
	{
	%>
		show_2<%= d %>();
	<%
	}
}
%>
</script>
