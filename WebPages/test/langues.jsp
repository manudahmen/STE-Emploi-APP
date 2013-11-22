<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="com.myapp.struts.Getter,com.myapp.struts.events.Conge,steemploi.persistance.TableConge,steemploi.service.*"%>

<%@page import="steemploi.service.Profil.CategoriesLogiciels"%><jsp:useBean
	id="user" class="steemploi.service.Utilisateur" scope="session" />
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:useBean id="g" class="com.myapp.struts.Getter" scope="request">
</jsp:useBean>
<html>
<head>
<title>Liste</title>
	<style type="text/css">
	body{
		font-family: Trebuchet MS, Lucida Sans Unicode, Arial, sans-serif;
		margin:0px;
		padding:0px;
		background-repeat:no-repeat;
		padding-top:85px;					
		overflow:hidden;
		padding-left:10px;
		-moz-user-select:no;
	}
	
	/* Don't change these options */
	#movableNode {
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
		background-color: #00F;
		width: 30%;
	}
	#arrangableNodes li,#movableNode li {
		list-style-type:none;
		cursor:default;
		background-color: #99F;
	}
	#arrangableNodes li.link {
		margin-left: 35%;
		position: relative;
	}

	</style>
	
<script type="text/javascript">

	function openNode(nodeId)
	{
		alert(nodeId);
	}

	function addLanguage()
	{
		var elem = document.getElementById("addlanguage");
		elem.innerHTML="<form onsubmit='addLangue(); document.getElementById(\"addlanguage\").innerHTML=\"\"; return false;'><table><tr><td>Langue</td><td><input name='langue' type='text' id='langue'/></td></tr><tr><td>Abbréviation</td><td><input type='text' name='abbr' id='abbr'></td></tr><td></td><td><input type='submit' value='Enregistrer'></td></tr></table></form>";
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
			if(arrNextSibling)arrParent.insertBefore(arrTarget,arrNextSibling); else arrParent.appendChild(arrTarget);			
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
					return;
				}				
			}
			
			if(tmpY>=topPos && tmpY<=(topPos+tmpHeight)){
				arrInsertDiv.style.top = (topPos+tmpHeight + offsetYInsertDiv) + 'px';
				arrInsertDiv.style.display = 'block';				
				arrNodesDestination = subs[no];
				insertAsFirstNode = false;
				return;
			}				
		}
	}
	
	function arrangeNodeStopMove()
	{
		arrMoveCounter = -1; 
		arrInsertDiv.style.display='none';
		
		if(arrNodesDestination){
			var subs = arrParent.getElementsByTagName('LI');
			if(arrNodesDestination==subs[0] && insertAsFirstNode){
				arrParent.insertBefore(arrTarget,arrNodesDestination);		
			}else{
				if(arrNodesDestination.nextSibling){
					arrParent.insertBefore(arrTarget,arrNodesDestination.nextSibling);
				}else{
					arrParent.appendChild(arrTarget);
				}
			}
		}		
		arrNodesDestination = false;
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
		
		document.forms[0].hiddenNodeIds.value = string;
		
		// Just for testing
		document.getElementById('arrDebug').innerHTML = 'Ready to save these nodes:<br>' + string.replace(/,/g,',<BR>');	
		
		
	}
	
	function initArrangableNodes()
	{
		arrParent = document.getElementById('arrangableNodes');
		arrMoveCont = document.getElementById('movableNode').getElementsByTagName('UL')[0];
		arrInsertDiv = document.getElementById('arrDestIndicator');
		
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

</head>
<body>
<h1>Choisissez l'ordre des langues présentées dans le formulaire</h1>
<p>
	<a href="#" onclick="addLanguage()">Ajouter une langue</a>
</p>
<p id="addlanguage">
</p>
<ul id="arrangableNodes">
	<%
		Profil profil = new Profil();
		profil.loadLangues();
		for (Profil.Langue langue : profil.getLangues())
		{
	%>
	<li id="<%= langue.getId() %>">
		
		<%= langue.getNom() %>
	<div style="float: right; width: 40%;">	
	<a style="text-decoration: underline; color: green; "
		 onclick="openNode('<%= langue.getId() %>')">
		Afficher les détails
	</a>
	
	</div>
	</li>
	<%
		}
	%>
</ul>
<p>
	<a href="#" onclick="saveArrangableNodes();return false">Enregistrer</a>
</p>
<div id="movableNode"><ul></ul></div>	
<div id="arrDestIndicator"><img src="http://www.dhtmlgoodies.com/scripts/arrange-nodes/images/insert.gif"></div>
<div id="arrDebug"></div>
<form method="post" action="????">
	<input type="hidden" name="hiddenNodeIds">
</form>

</body>
</html>
