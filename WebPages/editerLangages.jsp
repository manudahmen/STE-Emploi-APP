<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="com.myapp.struts.Getter,com.myapp.struts.events.Conge,steemploi.persistance.TableConge,steemploi.service.*"%>

<%@page import="steemploi.service.Profil.CategoriesLogiciels"%><jsp:useBean
	id="user" class="steemploi.service.Utilisateur" scope="session" />
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%
int id=-1;
String strId=request.getParameter("id");
if(strId!=null)
	id=Integer.parseInt(strId);

%>
<jsp:useBean id="g" class="com.myapp.struts.Getter" scope="request">
</jsp:useBean>
<style type="text/css">
	h1{
		margin-bottom:5px;
	}
	
	/* Don't change these options */
	#movableNode{
		position:absolute;
	}
	
	#arrDestIndicator{
		position:absolute;
		display:none;
		width:100px;
	}
	/* End options that shouldn't be changed */

	
	#arrangableNodes,#movableNode ul{
		padding-left:0px;
		margin-left:0px;
		margin-top:0px;
		padding-top:0px;
		width:300px;
	}
	
	#arrangableNodes li,#movableNode li{
		list-style-type:none;
		cursor:default;
		border:1px solid #999;
		padding:5px;
		background-color:#EEE;
		margin-top:2px;

	}
	img{
		border:0px;
	}
	</style>
	
<script type="text/javascript">
	var savedLangue = true;
	function openNode(nodeId)
	{
		loadLangage2(nodeId);
	}
	function openNode2()
	{
		document.forms['langagesForm'].submit();
	}
	function openNode3()
	{
		document.forms['langagesForm'].id.value=0;
		document.forms['langagesForm'].submit();
	}

	function addLanguage()
	{
		loadLangage2(0);
	}
	function addLangue()
	{
		var elem = document.getElementById('langue');
		var langue = elem.value;
		elem = document.getElementById('abbr');
		var abbr = elem.value;
		if(langue!=null && langue!='' && abbr!=null && abbr!='')
		{
			alert('Langue: ' + langue + '\nAbbr; ' + abbr);
			//ajaxAddLangue(langue, abbr, 'arrangableNodes');
			alert('Non effectué');
			}	
		else
		{
			alert('Données de formulaire invalides');
		}
	}
	var save = false;
	var arrParent = false;
	var arrMoveCont = false;
	var arrMoveCounter = -1;
	var arrTarget = false;
	var arrNextSibling = false;
	var leftPosArrangableNodes = false;
	var widthArrangableNodes = false;
	var nodePositionsY = new Array();
	var nodeHeights = new Array();
	var arrInsertDiv = false;
	var offsetYInsertDiv = -4; // Y offset for the little arrow indicating where the node should be inserted.
	if(!document.all)offsetYInsertDiv = offsetYInsertDiv - 7; 	// No IE
	var arrNodesDestination = false;
	var insertAsFirstNode = false;
	var trash=false;
	var nodeIdDAD = false;
	function cancelEvent()
	{
		return false;
	}
	function getTopPos(inputObj)
	{
		
	  var returnValue = inputObj.offsetTop;
	  while((inputObj = inputObj.offsetParent) != null){
	  	returnValue += inputObj.offsetTop;
	  }
	  return returnValue;
	}
	
	function getLeftPos(inputObj)
	{
	  var returnValue = inputObj.offsetLeft;
	  while((inputObj = inputObj.offsetParent) != null)returnValue += inputObj.offsetLeft;
	  return returnValue;
	}
		
	function clearMovableDiv()
	{
		if(arrMoveCont.getElementsByTagName('LI').length>0){
			if(arrNextSibling)
				{
				arrParent.insertBefore(arrTarget,arrNextSibling);
				}
			else 
			{
				arrParent.appendChild(arrTarget);
			}			
		}
	}
		
	function initMoveNode(e)
	{
		clearMovableDiv();
		if(document.all)e = event;
		arrMoveCounter = 0;
		arrTarget = this;
		if(this.nextSibling)arrNextSibling = this.nextSibling; else arrNextSibling = false;
		timerMoveNode();
		arrMoveCont.parentNode.style.left = e.clientX + 'px';
		arrMoveCont.parentNode.style.top = (e.clientY + Math.max(document.body.scrollTop,document.documentElement.scrollTop)) + 'px';
		nodeIdDAD=arrTarget.id;
		return false;
		
	}
	function timerMoveNode()
	{
		if(arrMoveCounter>=0 && arrMoveCounter<10){
			arrMoveCounter = arrMoveCounter +1;
			setTimeout('timerMoveNode()',20);
		}
		if(arrMoveCounter>=10){
			arrMoveCont.appendChild(arrTarget);
		}
	}
		
	function arrangeNodeMove(e)
	{
		if(document.all)e = event;
		if(arrMoveCounter<10)return;
		if(document.all && arrMoveCounter>=10 && e.button!=1 && navigator.userAgent.indexOf('Opera')==-1){
			arrangeNodeStopMove();
		}
		
		arrMoveCont.parentNode.style.left = e.clientX + 'px';
		arrMoveCont.parentNode.style.top = (e.clientY + Math.max(document.body.scrollTop,document.documentElement.scrollTop)) + 'px';	
		
		var tmpY = e.clientY + Math.max(document.body.scrollTop,document.documentElement.scrollTop);
		arrInsertDiv.style.display='none';
		arrNodesDestination = false;
		

		if(e.clientX<leftPosArrangableNodes || e.clientX>leftPosArrangableNodes + widthArrangableNodes)return; 
			
		var subs = arrParent.getElementsByTagName('LI');
		for(var no=0;no<subs.length;no++){
			var topPos =getTopPos(subs[no]);
			var tmpHeight = subs[no].offsetHeight;
			
			if(no==0){
				if(tmpY<=topPos && tmpY>=topPos-5){
					arrInsertDiv.style.top = (topPos + offsetYInsertDiv) + 'px';
					arrInsertDiv.style.display = 'block';				
					arrNodesDestination = subs[no];	
					insertAsFirstNode=true;
					trash=false;
					arrTarget.style.backgroundColor='#99F';
					return;
				}
			}
			if(tmpY>=topPos && tmpY<=(topPos+tmpHeight)){
				arrInsertDiv.style.top = (topPos+tmpHeight + offsetYInsertDiv) + 'px';
				arrInsertDiv.style.display = 'block';				
				arrNodesDestination = subs[no];
				insertAsFirstNode = false;
				if(no==subs.length-1)
				{
					trash=true;
					arrTarget.style.backgroundColor='red';
				}
				else
				{
					trash=false;
					arrTarget.style.backgroundColor='#99F';
				}
				return;
			}				
		}
	}
	
	function arrangeNodeStopMove()
	{
		var subs = arrParent.getElementsByTagName('LI');
		arrMoveCounter = -1; 
		arrInsertDiv.style.display='none';
		if(arrNodesDestination==subs[subs.length-1] && trash==true){
			trash=false;
			savedChange = false;
			arrNodesDestination = false;
			document.location.replace("DeleteLangage.servlet?id="+arrTarget.id);
			return;
		}
		if(arrNodesDestination){
			if(arrNodesDestination==subs[0] && insertAsFirstNode){
				arrParent.insertBefore(arrTarget,arrNodesDestination);
				save=true;
			}else{
				if(arrNodesDestination.nextSibling){
					arrParent.insertBefore(arrTarget,arrNodesDestination.nextSibling);
					save=true;
				}else{
					arrParent.appendChild(arrTarget);
					save=true;
				}
			}
		}		
		arrNodesDestination = false;
		if(save)
		{
			save=false;
			saveArrangableNodes();
			clearMovableDiv();
		}
		clearMovableDiv();
		
	}	
	
	function saveArrangableNodes()
	{
		var nodes = arrParent.getElementsByTagName('LI');
		var string = "";
		for(var no=0;no<nodes.length;no++){
			if(string.length>0)string = string + ',';
			string = string + nodes[no].id;		
		}
		
		document.forms['langages'].hiddenNodeIds.value = string;
		
		// Just for testing
		//document.getElementById('arrDebug').innerHTML = 'Ready to save these nodes:<br><p><a onclick="document.forms[\'langues\'].submit();">Confirmer</a></p>' + string.replace(/,/g,',<BR>');
		document.forms['langages'].submit();	
	}
	
	function initArrangableNodes()
	{
		trash=false;
		arrParent = document.getElementById('arrangableNodes');
		arrMoveCont = document.getElementById('movableNode').getElementsByTagName('UL')[0];

		/*arrInsertDiv = document.createElement("div");
		arrInsertDiv.id='arrDestIndicator';
		arrInsertDiv.style.height='6px';
		arrInsertDiv.style.width='20px';
		arrInsertDiv.style.backgroundColor = 'gray';
		arrInsertDiv.style.color="blue";
*/
		arrInsertDiv = document.getElementById("arrDestIndicator");
		leftPosArrangableNodes = getLeftPos(arrParent);
		arrInsertDiv.style.left = leftPosArrangableNodes - 5 + 'px';
		widthArrangableNodes = arrParent.offsetWidth;
		
		
		var subs = arrParent.getElementsByTagName('LI');
		for(var no=0;no<subs.length;no++){
			subs[no].onmousedown = initMoveNode;
			subs[no].onselectstart = cancelEvent;	
		}
	
		document.documentElement.onmouseup = arrangeNodeStopMove;
		document.documentElement.onmousemove = arrangeNodeMove;
		document.documentElement.onselectstart = cancelEvent;
		
	}	
	
	window.onload = initArrangableNodes;
	
	</script>

<h1 id="titrePage">Langages de programmation</h1>
<h2>Choisissez l'ordre des langages présentées dans le formulaire</h2>
<div style="float: right; margin-left: 40%; width: 40%; height: 400px; border: 6px solid gray;">
<form action="index_agenda.jsp" method="get" name="langagesForm">
<fieldset>
<input type="hidden" name="page" value="langages"/>
<select style="width: 100%;" onchange="openNode2();" name="id">
		<option value="-1" <%= (id==-1)?"selected='selected'":"" %>">----</option>
		<option value="0" <%= (id==0)?"selected='selected'":"" %>">Ajouter un langage</option>
		<%
		Profil profil = new Profil();
		profil.loadLangages();
		for (Profil.Langage langage : profil.getLangages())
		{
		%>
			<option value="<%= langage.getId() %>" <%= (id==langage.getId())?"selected='selected'":"" %>"><%= langage.getNom() %></option>
		<%
		} 
		%>
</select>
</fieldset>
</form>
<div style="float: right; width: 100%; height: 300px; border: 3px solid gray;">
<form name='langageForm' id='langageForm' action='SaveLangage.servlet' method='post'>
<div id="langagesForm">
	<%
	out.flush();
	if(id!=-1)
	pageContext.getServletContext().getRequestDispatcher("/LoadLangage.servlet?id="+id).include(request, response);
	out.flush();

	%>

</div>
<input type="hidden" name="action" value="index_agenda.jsp?page=langues"/>
</form>
</div>

</div>
<p>
	<a href="#" onclick="javascript:openNode3()">Ajouter un langage</a>
</p>
<ul id="arrangableNodes">
	<%
		for (Profil.Langage langage: profil.getLangages())
		{
	%>
	<li id="<%= langage.getId() %>">
		
		<%= langage.getNom() %>
	</li>
	<%
		}
	%>
<li id="trash" style="text-decoration: underline; background-color: gray; color: black;">Corbeille</li>
</ul>
<p>
	<a href="#" onclick="saveArrangableNodes();return false">Enregistrer</a>
</p>
<form method="post" action="SaveOrdreLangages.servlet" name='langages' method="post">
	<input type="hidden" name="hiddenNodeIds"/>
	<input type="hidden" name="action" value="index_agenda.jsp?page=langages"/>
</form>

<div id="movableNode"><ul></ul></div>	
<div id="arrDestIndicator"><img src="images/insert.gif"/></div>
<div id="arrDebug"></div>

