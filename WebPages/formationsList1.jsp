<%-- 
    Document   : formationsList1
    Created on : 05-mai-2009, 15:58:52
    Author     : manuel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page
	import="steemploi.service.*, steemploi.persistance.*, java.util.List, java.util.Calendar, java.util.Locale"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session" />
<%
        List<SessionsFormations> sessions;
        sessions = new TableSessionsFormations().findAll(false);
%>
<script type="text/javascript" language="JavaScript">
    <%
        int i = 0;
        int j = 0;

%>
    var formations = new Array();
    var etudiants = new Array();
<%
        for (SessionsFormations sf : sessions) {
        %>
            formations[<%= i%>] = new Array();
            formations[<%= i%>][0] = <%= sf.getId()%>;
            formations[<%= i%>][1] = '<%= sf.getName()%>';
            formations[<%= i%>][2]= '<%= String.format("%td/%tm/%tY",  sf.getDateStart(),  sf.getDateStart() ,  sf.getDateStart()) + " " + sf.getFormation().getNom() + " - " + sf.getName()%>';
            formations[<%= i%>][3]= true;
    <%
            i++;
            for (Etudiant e : sf.getEtudiants()) {
                %>
                    etudiants[<%= j%>] = new Array();
                    etudiants[<%= j%>][0]= <%= sf.getId()%>;
                    etudiants[<%= j%>][1] = <%= e.getId()%>;
                    etudiants[<%= j%>][2]= '<%= e.getNom()%>';
                    etudiants[<%= j%>][3]= '<%= e.getPrenom()%>';
                    etudiants[<%= j%>][4]= '<%= e.getEmail()%>';
                    etudiants[<%= j%>][5]= true;
            <%
                j++;
            }
        }%>
            var f_id=0;
            function formationChanged()
            {
				var sel = document.getElementById('formations_sel');
                for(i=0; i<sel.options.length; i++)
                {
                    if(sel.options[i].selected==true)
                    {
                        id=sel.options[i].value;
                     }
                }
                select_e = document.getElementById('etudiants_sel');
                select_e.innerHTML='';
                j=0;
                for(i = 0; i<etudiants.length; i++)
                {
                    if(etudiants[i][0]==id && etudiants[i][5]==true)
                    {
                        select_e.innerHTML += "<option value='" + etudiants[i][1] +"' selected='true'>"+etudiants[i][3] + ' ' + etudiants[i][2]+"</option>";
                    }
                }
                f_id=id;
            }
            function ajouterFormation()
            {
                var sel = document.getElementById('formations_sel');
                var selected = document.getElementById('selected');
                var selEtudiants = document.getElementById('etudiants_sel');
                var deleted = new Array();
                d=0;
                for(j=0; j<sel.options.length; j++)
                {
                    option = sel.options[j];
                    if(option.selected==true && option.value!="-1")
                    {
                        var elem = document.createElement("option");
                        //var index = selected.options.length;
                        var value = option.value;
                        var newValue = 'f:'+option.value;
                        elem.value = newValue;
                        var text = option.text;
                        elem.text = text;
						elem.title= text;
                        selected.appendChild(elem);
                        deleted[d]=option;
                        d++;
                        for(i=0; i<formations.length; i++)
                        {
                            if(formations[i][0]==value)
                            {
                                formations[i][3]=false;
                            }
                        }
                        for(i=0; i<etudiants.length; i++)
                        {
                            if(etudiants[i][0]==value && etudiants[i][5]==true)
                            {
                             	etudiants[i][5]=false;
                             	
                            }
                        }
                        if(f_id==value)
                        {
                        	iN = selEtudiants.options.length-1;
                            for(i=iN; i>=0; i--)
                            {
                                selEtudiants.removeChild(selEtudiants.options[i]);
                           }
                       }
                }
                }


                for(i=0; i<deleted.length; i++)
                {
                    var elemSrc = deleted[i];
                    sel.removeChild(elemSrc);
                }

                f_id=0;

            }
            function ajouterEtudiant()
            {
                var selSelect = document.getElementById('etudiants_sel');
                var selected = document.getElementById('selected');
                var deleted = new Array();
                d=0;
                for(j=0; j<selSelect.length; j++)
                {
                    option = selSelect.options[j];
                    if(option.selected==true)
                    {
                        var elem = document.createElement("option");
                        var index = selected.options.length;
                        var value = option.value;
                        var newValue = 'e:'+option.value;
                        elem.value = newValue;
                        var text = option.text;
                        elem.text = text;
						elem.title= text;
                        selected.appendChild(elem);
                        deleted[d]=option;
                        d++;
                        for(i=0; i<etudiants.length; i++)
                        {
                            if(etudiants[i][1]==value)
                            {
                                etudiants[i][5]=false;
                            }
                        }
                    }
                }



                for(i=0; i<deleted.length; i++)
                {
                    var elemSrc = deleted[i];
                    selSelect.removeChild(elemSrc);
                }

            }
            function supprimer()
            {
                var selected = document.getElementById('selected');
                var selEtudiants = document.getElementById('etudiants_sel');
                var selFormations = document.getElementById('formations_sel');
                var deleted = new Array();
                var d=0;
                var a=0;
                var elem ;
                for(i=0;i< selected.options.length; i++)
                {
                    if(selected.options[i].selected==true)
                    {
                        var value=selected.options[i].value;
                        var text =selected.options[i].text;
                        
                        if(value.charAt(0)=='e')
                        {
                        	//alert(value.substring(2));
                        	//alert(etudiants[0][0]);
						  	var j;
                                for(j=0; j<etudiants.length;j++)
                            	{
                                    
                                if(etudiants[j][1]==value.substring(2)){
                                	
                                    etudiants[j][5]=true;
                                    if(etudiants[j][0]==f_id)
                                    {
                                    	
                                        elem = document.createElement("option");
                                        elem.value = value.substring(2);
                                        elem.text = text;
                						elem.title= text;
                                        selEtudiants.appendChild(elem);
                                    }
                                }
                            }
                        }
                        else if(value.charAt(0)=='f')
                        {
                            for(j=0; j<formations.length;j++)
                            {
                                if(formations[j][0]==value.substring(2))


                                {
										//alert(value);
                                       	formations[j][3]=true;
                                        elem = document.createElement("option");
                                        elem.value = value.substring(2);
                                        elem.text = text;
                						elem.title= text;
                                        //alert(value.substring(2));
			for(var e = 0 ; e<etudiants.length; e++)
			{
				if(etudiants[e][0]==value.substring(2)) 
				{
					etudiants[e][5] = true;
				}
			}
										selFormations.appendChild(elem);
                                    }
                                }
                            }
                        deleted[d]=selected.options[i];
                        d++;
                    }
                 }
                
                for(i=0; i<deleted.length; i++)
                {
                    var elemSrc = deleted[i];
                    if(elemSrc!=null)
                    	selected.removeChild(elemSrc);
                }
				formationChanged();

            }
            function ajouterEtudiantParId(etid)
            {
                for(i=0; i<etudiants.length; i++)
                {
                    if(etudiants[i][1]==etid)
                    {
                        etudiants[i][5]=false;
                        index0 = i;
                    }
                }
                selected = document.getElementById("selected");
                var elem = document.createElement("option");
                var index = selected.options.length;
                var newValue = 'e:'+etid;
                elem.value = newValue;
				elem.title= text;
                var text = etudiants[index0][3] + ' ' + etudiants[index0][2];
                elem.text = text;
                selected.appendChild(elem);
            }
            function ajouterFormationParId(fid)
            {
                for(i=0; i<formations.length; i++)
                {
                    if(formations[i][0]==fid && formations[i][3]==true)
                    {
                        formations[i][3]=false;
                        index0 = i;
                    }
                }
                selected = document.getElementById("selected");
                var elem = document.createElement("option");
                var index = selected.options.length;
                var newValue = 'f:'+fid;
                elem.value = newValue;
                var text = formations[index0][2];
                elem.text = text;
				elem.title= text;
                selected.appendChild(elem);
            }
                
            function selectAll()
            {
                selected = document.getElementById("selected");
                for(i=0; i<selected.options.length; i++)
                    selected.options[i].selected=true;
			}
        </script>

<h2>Echéance</h2>
<h3>Choisissez un étudiant ou une formation</h3>
<div id="formation_sel_div" class="listeChoixDiv">
<p>Formations</p> 

<p><select id="formations_sel" size="8" multiple="true" class="largeur"
onchange="javascript:formationChanged()"
>
	<option value="-1" style="font-weight: bold;">Choisissez une formation</option>
	<% for (SessionsFormations sf: sessions) {%>
	<option  
		value="<%= sf.getId()%>" 
		title="<%= String.format("%td/%tm/%tY",  sf.getDateStart(),  sf.getDateStart() ,  sf.getDateStart()) + " " + sf.getFormation().getNom() + " - " + sf.getName()%>">
	<%= String.format("%td/%tm/%tY",  sf.getDateStart(),  sf.getDateStart() ,  sf.getDateStart()) + " " + sf.getFormation().getNom() + " - " + sf.getName()%></option>
	<% }%>
</select></p>

<p>
<input type="button" value="Ajouter" class="largeur"
	onclick="javascript:ajouterFormation()" />
</p>
</div>
<div id="etudiant_sel_div" class="listeChoixDiv" >
<p>Etudiants</p>
<p> <select id="etudiants_sel" multiple="true" size="8" class="largeur">
</select></p> 
<p><input type="button" value="Ajouter" class="largeur"
	onclick="javascript:ajouterEtudiant()" /></p>
</div>
<div id="selected_div" class="listeChoixDiv">
<p id="selected_p">Formations et étudiants sélectionnés</p>
<p>
<select id="selected" multiple="true" size="8" class="largeur" onmouseover="valideTout();" onmouseout="valideTout();">
</select>
</p>
<p><input type="button" value="Supprimer" onclick="javascript:supprimer()" class="largeur"/></p>
</div>
<hr style="clear: left;"/>
