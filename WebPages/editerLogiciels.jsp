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
Profil profil = new Profil();
int catId=-1;
String strCatId=request.getParameter("catId");
if(strCatId!=null)
	catId=Integer.parseInt(strCatId);
int logId=-1;
String strLogId=request.getParameter("logId");
if(strLogId!=null)
	logId=Integer.parseInt(strLogId);
%>
<jsp:useBean id="g" class="com.myapp.struts.Getter" scope="request">
</jsp:useBean>
<style type="text/css">
/* Don't change these options */
#movableNode,#movableNode2 {
	position: absolute;
}

#arrDestIndicator,#arrDestIndicator2 {
	position: absolute;
	display: none;
	width: 100px;
}

/* End options that shouldn't be changed */
#arrangableNodes,#movableNode ul,#arrangableNodes2,#movableNode2 ul {
	padding-left: 0px;
	margin-left: 0px;
	margin-top: 0px;
	padding-top: 0px;
	width: 300px;
}

#arrangableNodes li,#movableNode li,#arrangableNodes2 li,#movableNode2 li
	{
	list-style-type: none;
	cursor: default;
	border: 1px solid #999;
	padding: 5px;
	background-color: #EEE;
	margin-top: 2px;
}

img {
	border: 0px;
}
</style>

<script type="text/javascript">
	var activeList=0;

	var savedLangue = true;
	function openNode(nodeId)
	{
		loadCategorie(nodeId);
	}
	function openNodeC1(nodeId)
	{
		document.location.replace("?page=logiciels&catId="+nodeId);
	}
	function openNodeL1(nodeId, catId)
	{
		document.location.replace("?page=logiciels&catId="+catId+"&logId="+nodeId);
	}

	function addLanguage()
	{
		loadCategorie(0);
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
	var save2 = false;
	var arrParent2 = false;
	var arrMoveCont2 = false;
	var arrMoveCounter2 = -1;
	var arrTarget2 = false;
	var arrNextSibling2 = false;
	var leftPosArrangableNodes2 = false;
	var widthArrangableNodes2 = false;
	var nodePositionsY2 = new Array();
	var nodeHeights2 = new Array();
	var arrInsertDiv2 = false;
	var offsetYInsertDiv2 = -4; // Y offset for the little arrow indicating where the node should be inserted.
	if(!document.all)offsetYInsertDiv2 = offsetYInsertDiv2 - 7; 	// No IE
	var arrNodesDestination2 = false;
	var insertAsFirstNode2 = false;
	var trash2=false;
	var nodeIdDAD2 = false;
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
		
		

	var savedLangue2 = true;
	function openNode2(nodeId)
	{
		loadListeLogiciels(nodeId);
	}

	function cancelEvent2()
	{
		return false;
	}
	function getTopPos2(inputObj)
	{
		
	  var returnValue = inputObj.offsetTop;
	  while((inputObj = inputObj.offsetParent) != null){
	  	returnValue += inputObj.offsetTop;
	  }
	  return returnValue;
	}
	
	function getLeftPos2(inputObj)
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
		activeList=0;
	}
		
	function clearMovableDiv2()
	{
		if(arrMoveCont2.getElementsByTagName('LI').length>0){
			if(arrNextSibling2)
				{
				arrParent2.insertBefore(arrTarget2,arrNextSibling2);
				}
			else 
			{
				arrParent2.appendChild(arrTarget2);
			}			
		}
		activeList=0;
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
	function initMoveNode2(e)
	{
		clearMovableDiv2();
		if(document.all)e = event;
		arrMoveCounter2 = 0;
		arrTarget2 = this;
		if(this.nextSibling)arrNextSibling2 = this.nextSibling; else arrNextSibling2 = false;
		timerMoveNode2();
		arrMoveCont2.parentNode.style.left = e.clientX + 'px';
		arrMoveCont2.parentNode.style.top = (e.clientY + Math.max(document.body.scrollTop,document.documentElement.scrollTop)) + 'px';
		nodeIdDAD2=arrTarget2.id;
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
	function timerMoveNode2()
	{
		if(arrMoveCounter2>=0 && arrMoveCounter2<10){
			arrMoveCounter2 = arrMoveCounter2 +1;
			setTimeout('timerMoveNode2()',20);
		}
		if(arrMoveCounter2>=10){
			arrMoveCont2.appendChild(arrTarget2);
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
	
	
	
	
	
	function arrangeNodeMove2(e)
	{
		if(document.all)e = event;
		if(arrMoveCounter2<10)return;
		if(document.all && arrMoveCounter2>=10 && e.button!=1 && navigator.userAgent.indexOf('Opera')==-1){
			arrangeNodeStopMove();
		}
		
		arrMoveCont2.parentNode.style.left = e.clientX + 'px';
		arrMoveCont2.parentNode.style.top = (e.clientY + Math.max(document.body.scrollTop,document.documentElement.scrollTop)) + 'px';	
		
		var tmpY = e.clientY + Math.max(document.body.scrollTop,document.documentElement.scrollTop);
		arrInsertDiv2.style.display='none';
		arrNodesDestination2 = false;
		

		if(e.clientX<leftPosArrangableNodes2 || e.clientX>leftPosArrangableNodes2 + widthArrangableNodes2)return; 
			
		var subs = arrParent2.getElementsByTagName('LI');
		for(var no=0;no<subs.length;no++){
			var topPos =getTopPos(subs[no]);
			var tmpHeight = subs[no].offsetHeight;
			
			if(no==0){
				if(tmpY<=topPos && tmpY>=topPos-5){
					arrInsertDiv2.style.top = (topPos + offsetYInsertDiv2) + 'px';
					arrInsertDiv2.style.display = 'block';				
					arrNodesDestination2= subs[no];	
					insertAsFirstNode2=true;
					trash2=false;
					arrTarget2.style.backgroundColor='#99F';
					return;
				}
			}
			if(tmpY>=topPos && tmpY<=(topPos+tmpHeight)){
				arrInsertDiv2.style.top = (topPos+tmpHeight + offsetYInsertDiv) + 'px';
				arrInsertDiv2.style.display = 'block';				
				arrNodesDestination2 = subs[no];
				insertAsFirstNode2 = false;
				if(no==subs.length-1)
				{
					trash2=true;
					arrTarget2.style.backgroundColor='red';
				}
				else
				{
					trash2=false;
					arrTarget2.style.backgroundColor='#99F';
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
			document.location.replace("DeleteCategorie.servlet?categorieId="+arrTarget.id);
			return;
		}
		if(arrNodesDestination){
			if(arrNodesDestination==subs[0] && insertAsFirstNode){
				arrParent.insertBefore(arrTarget,arrNodesDestination);
				saveArrangableNodes();
				save=true;
			}else{
				if(arrNodesDestination.nextSibling){
					arrParent.insertBefore(arrTarget,arrNodesDestination.nextSibling);
					saveArrangableNodes();
					save=true;
				}else{
					arrParent.appendChild(arrTarget);
					saveArrangableNodes();
					save=true;
				}
			}
		}		
		arrNodesDestination = false;
		if(save)
		{
			save=false;
			clearMovableDiv();
		}
	}	
	
	function arrangeNodeStopMove2()
	{
		var subs = arrParent2.getElementsByTagName('LI');
		arrMoveCounter2 = -1; 
		arrInsertDiv2.style.display='none';
		if(arrNodesDestination2==subs[subs.length-1] && trash2==true){
			trash2=false;
			savedChange2 = false;
			arrNodesDestination2 = false;
			document.location.replace("DeleteLogiciel.servlet?id="+arrTarget2.id);
			return;
		}
		if(arrNodesDestination2){
			if(arrNodesDestination2==subs[0] && insertAsFirstNode2){
				arrParent2.insertBefore(arrTarget2,arrNodesDestination2);
				saveArrangableNodes2();
				save2=true;
			}else{
				if(arrNodesDestination2.nextSibling){
					arrParent2.insertBefore(arrTarget2,arrNodesDestination2.nextSibling);
					saveArrangableNodes2();
					save2=true;
				}else{
					arrParent2.appendChild(arrTarget2);
					saveArrangableNodes2();
					save2=true;
				}
			}
		}		
		arrNodesDestination2 = false;
		if(save2)
		{
			save2=false;
			clearMovableDiv2();
		}
	}	
	
	function saveArrangableNodes()
	{
		var nodes = arrParent.getElementsByTagName('LI');
		var string = "";
		for(var no=0;no<nodes.length;no++){
			if(string.length>0)string = string + ',';
			if(nodes[no].id!='trash')
			{
				string = string + nodes[no].id.substring(1);
			}
			else
			{
				string = string + 'trash';
			}		
		}
		
		document.forms['soLogiciels'].hiddenNodeIds.value = string;
		
		// Just for testing
		//document.getElementById('arrDebug').innerHTML = 'Ready to save these nodes:<br><p><a onclick="document.forms[\'langues\'].submit();">Confirmer</a></p>' + string.replace(/,/g,',<BR>');
		document.forms['soLogiciels'].submit();	
	}
	function saveArrangableNodes2()
	{
		var nodes = arrParent2.getElementsByTagName('LI');
		var string = "";
		for(var no=0;no<nodes.length;no++){
			if(string.length>0)string = string + ',';
			if(nodes[no].id!='trash')
			{
				string = string + nodes[no].id.substring(1);
			}
			else
			{
				string = string + 'trash';
			}		
		
		}
		
		document.forms['soLogiciels2'].hiddenNodeIds.value = string;
		
		// Just for testing
		//document.getElementById('arrDebug').innerHTML = 'Ready to save these nodes:<br><p><a onclick="document.forms[\'langues\'].submit();">Confirmer</a></p>' + string.replace(/,/g,',<BR>');
		document.forms['soLogiciels2'].submit();	
	}
	
	function liOnmouseup()
	{
		if(activeList==1)
		{
			arrangeNodeStopMove();
		}
		else if(activeList==2)
		{
			arrangeNodeStopMove2();
		}
	}
	function liOnmousemove(e)
	{
		if(activeList==0)
		{
			if(e.target.parentNode.id=='arrangableNodes')
			{
				activeList=1;
				arrangeNodeMove(e);
				return;
			}
			else 
			if(e.target.parentNode.id=='arrangableNodes2')
			{
//				alert(a.target.parentNode.tagName);
				activeList=2;
				arrangeNodeMove2(e);
				return;
			}
		}
		else
		{
			if(activeList==1)
			{	arrangeNodeMove(e);
				return;
			}
			else 
			if(activeList==2)
			{
				arrangeNodeMove2(e);
				return;
			}
		}
	}
	function liSelectstart(e)
	{
		//alert(e.target.tagName);
		if(activeList==0)
		{
			cancelEvent(e);
		}
		else if(activeList==1)
		{
			cancelEvent();
		}
		else if(activeList==2)
		{
			cancelEvent2();
		}
		activeList=0;
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
		arrInsertDiv.style.left = (leftPosArrangableNodes - 5) + 'px';
		widthArrangableNodes = arrParent.offsetWidth;
		
		
		var subs = arrParent.getElementsByTagName('LI');
		for(var no=0;no<subs.length;no++){
			subs[no].onmousedown = initMoveNode;
			subs[no].onselectstart = cancelEvent;	
		}
		document.documentElement.onmouseup = liOnmouseup;
		document.documentElement.onselectstart = liSelectstart;
		document.documentElement.onmousemove = liOnmousemove;
	}	
	function initArrangableNodes2()
	{
		trash2=false;
		arrParent2 = document.getElementById('arrangableNodes2');
		arrMoveCont2 = document.getElementById('movableNode2').getElementsByTagName('UL')[0];
		arrInsertDiv2 = document.getElementById("arrDestIndicator2");
		leftPosArrangableNodes2 = getLeftPos2(arrParent2);
		arrInsertDiv2.style.left = (leftPosArrangableNodes2 - 5) + 'px';
		widthArrangableNodes2 = arrParent2.offsetWidth;
		
		
		var subs = arrParent2.getElementsByTagName('LI');
		for(var no=0;no<subs.length;no++){
			subs[no].onmousedown = initMoveNode2;
			subs[no].onselectstart = cancelEvent2;	
		}


		document.documentElement.onmouseup = liOnmouseup;
		document.documentElement.onmousemove = liOnmousemove;
		document.documentElement.onselectstart = liSelectstart;
	}
	function openNode3(nodeId, catId)
	{
		loadLogiciel(nodeId, catId);
		//initArrangableNodes2();
	}
	/*function openNode4(nodeId, catId)
	{
		document.location.href='?page=logiciels&catId='+catId;
		//initArrangableNodes2();
	}*/
	function winonload()
	{
		initArrangableNodes();
		initArrangableNodes2();
		//openNode(<%= catId %>);
	}
	window.onload = winonload;
	
	
	</script>

<h1 id="titrePage">Logiciels</h1>

<h2>Choisissez l'ordre des logiciels présentés dans le formulaire</h2>
<%--<div style="float: right; margin-left: 60%; width: 40%; border: 6px solid gray;">--%>
<div style="float: right; border: 3px solid gray;">
<form name='catInfo' id='catInfo' action='SaveCatInfo.do' method='post'>
<div id="catInfoForm">
<%
out.flush();
if(catId!=-1)
	pageContext.getServletContext().getRequestDispatcher("/LoadCategorie.servlet?categorieId="+catId).include(request, response);
out.flush();
%>
</div>
<input type="hidden" name="action"
	value="index_agenda.jsp?page=logiciels&catId=<%= catId %>&logId=<%= logId %>" />
</form>
<form name="logiciels" action="saveLogiciel.servlet"
	accept-charset="UTF-8">
<div id="logicielEdit">
<%
out.flush();
if(logId!=-1 && catId!=-1)
	pageContext.getServletContext().getRequestDispatcher("/Logiciel.servlet?id="+logId+"&categorieId="+catId).include(request, response);
out.flush();
%>
</div>
<input type="hidden" name="action"
	value="index_agenda.jsp?page=logiciels&catId=<%= catId %>&logId=<%= logId %>" />
</form>
</div>
<%--</div>--%>


<ul id="arrangableNodes">
	<%
		profil.loadCategories();
		for (Profil.CategoriesLogiciels cat : profil.getCategories())
		{
	%>
	<li id="c<%= cat.getIdCategorie() %>"><%= cat.getNomCategorie() %>
	</li>
	<%
		}
	%>
	<li id="trash"
		style="text-decoration: underline; background-color: gray; color: black;">Corbeille</li>
</ul>
<%
out.flush();
%>
<h3>Sélectionnez une catégorie</h3>
<form action="index_agenda.jsp" method="get" name="categoriesSelect">
<p>
<select style="width: 50%;" id="logicielsSelect" name="catId" onchange="document.forms['categoriesSelect'].submit()">
	<option value="-1"
		<%= (-1==catId)?"selected":"" %>>----</option>
	<option value="0" 
		<%= (0==catId)?"selected":"" %>>Ajouter une catégorie de logiciel</option>
	<%
		for (Profil.CategoriesLogiciels cat : profil.getCategories())
		{
		%>
	<option value="<%= cat.getIdCategorie() %>" <%= (cat.getIdCategorie()==catId)?"selected":"" %>
		><%= cat.getNomCategorie() %></option>
		<%} %>
</select>
</p>
<input type="hidden" name="page" value="logiciels" />
</form>
<p><a href="#" onclick="saveArrangableNodes();return false">Enregistrer</a>
</p>
<div id="liste2">
<%
out.flush();
if(catId!=-1)
	pageContext.getServletContext().getRequestDispatcher("/LoadListeLogiciels.servlet?categorieId="+catId+"&logId="+logId).include(request, response);
out.flush();
%>
</div>
<p><a href="#" onclick="saveArrangableNodes2();return false">Enregistrer</a>
</p>
<form method="post" action="SaveOrdreLogiciels.servlet"
	name='soLogiciels' method="post">
<input type="hidden" name="hiddenNodeIds" />
<input type="hidden" name="action"
	value="index_agenda.jsp?page=logiciels&catId=<%= catId %>&logId=<%= logId %>" />
</form>
<form method="post" action="SaveOrdreLogiciels2.servlet"
	name='soLogiciels2' method="post">
<input type="hidden" name="hiddenNodeIds" />
<input type="hidden" name="action"
	value="index_agenda.jsp?page=logiciels&catId=<%= catId %>&logId=<%= logId %>" />
</form>

<div id="movableNode">
<ul></ul>
</div>
<div id="arrDestIndicator"><img src="images/insert.gif" /></div>
<div id="arrDebug"></div>
<div id="movableNode2">
<ul></ul>
</div>
<div id="arrDestIndicator2"><img src="images/insert.gif" /></div>
<div id="arrDebug2"></div>
