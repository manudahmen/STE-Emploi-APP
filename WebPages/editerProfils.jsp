<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.myapp.struts.Getter, com.myapp.struts.events.Conge, steemploi.persistance.TableConge, steemploi.service.*" %>

<%@page import="steemploi.service.Profil.CategoriesLogiciels"%><jsp:useBean id="user" class="steemploi.service.Utilisateur" scope="session" />
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="g" class="com.myapp.struts.Getter" scope="request"></jsp:useBean>
<script type="text/javascript">
function loadPage(page, div)
{
	Include.setPage(page);
	Include.getInclude(function(data) {
    dwr.util.setValue(div, data, { escapeHtml:false});
 	});
}
function loadCategorie(categorieId)
{
	var url = "/LoadCategorie.servlet?categorieId="+categorieId;
	var div = document.getElementById("catInfoForm");
	loadPage(url, "catInfoForm");
	dwr.util.setValue("catInfoForm", "", { escapeHtml:false});
}

function loadListeLogiciels(categorieId)
{
	var url = "/LoadListeLogiciels.servlet?categorieId="+categorieId;
	var div = document.getElementById("");
	dwr.util.setValue("catInfoForm", div, { escapeHtml:false});
	loadPage(url, "liste2");
}

function deleteCategorie(categorieId)
{
	var url = "/DeleteCategorie.servlet?categorieId="+categorieId;
	//alert(url);
	loadPage(url, "info");
	document.location.reload();
}
function loadLogiciels(categorie_id)
{
	var url = "/Logiciels.servlet?categorie_id="+categorie_id;
	//alert(url);
	loadPage(url, "selectLogiciels");
}
function loadLogiciels2(categorie_id)
{
	var url = "/Logiciels.servlet?categorie_id="+categorie_id;
	//alert(url);
	loadPage(url, "catInfoForm");
}
function loadLogiciel(id, cat)
{
	var url = "/Logiciel.servlet?id="+id+"&categorieId="+cat;
	//alert(url);
	loadPage(url, "logicielEdit");
}
function loadLogiciel2(id, cat)
{
	var url = "/Logiciel.servlet?id="+id+"&categorieId="+cat;
	//alert(url);
	loadPage(url, "logicielEdit");
}
function deleteLogiciel(id)
{
	var url = "/DeleteLogiciel.servlet?id="+id;
	//alert(url);
	loadPage(url, "info");
	document.location.reload();
}
function saveLangue()
{
	saveArrangableNodes();
	return true;
}

function loadLangue(id)
{
	var url = "/LoadLangue.servlet?id="+id;
	if(id>=0)
		{
			loadPage(url, "langueFormDiv");
		}
}
function loadLangage2(id)
{
	var url = "/LoadLangage.servlet?id="+id;
	if(id>=0)
		{
			loadPage(url, "langagesForm");
		}
}


function loadLangue2(id)
{
	var url = "/LoadLangue.servlet?id="+id;
	if(id>=0)
		{
			loadPage(url, "languesForm");
		}
}
function deleteLangue(id)
{
	var url = "/DeleteLangue.servlet?id="+id;
	//alert(url);
	loadPage(url, "langageFormDiv");
	document.location.reload();
}
function deleteLangue2(id)
{
	document.location.href="DeleteLangue.servlet?id="+id;
}
function loadLangage(id)
{
	var url = "/LoadLangage.servlet?id="+id;
	//alert(url);
	loadPage(url, "langageFormDiv");
}
function languesOnChange(select)
{
	var id = -1;
	for(var i=0; i<select.options.length; i++)
	{
		var text = select.options[i].text;
		
		if(select.options[i].selected)
		{
			id = select.options[i].value;
		}
	}
	loadLangue(id);
}
function deleteLangueOnClick()
{
	var select = document.getElementById("languesSelect");
var id = -1;
for(var i=0; i<select.options.length; i++)
{
	var text = select.options[i].text;
	
	if(select.options[i].selected)
	{
		id = select.options[i].value;
	}
}
deleteLangue(id);
}

function selectLangagesOnChange(select)
{
	var id = -1;
	for(var i=0; i<select.options.length; i++)
	{
		var text = select.options[i].text;
		
		if(select.options[i].selected)
		{
			id = select.options[i].value;
		}
	}
-	loadLangage(id);
}
function catInfoOnChange(select)
{
	//var elem = document.getElementById("nomCategorieLogiciel");
	//var elemId = document.getElementById("idCategorieLogiciel");
	var id = 0;
	for(var i=0; i<select.options.length; i++)
	{
		var text = select.options[i].text;
		
		if(select.options[i].selected)
		{
			//elem.value = text;
			id = select.options[i].value;
			//elemId.value = id;
			
		}
	}
	if(id==0)
	{
		//loadCategorie(id);
	}
	else
	{
		loadLogiciels(id);
		//loadCategorie(id);
	}
}
function catInfoEdit()
{
	//var elem = document.getElementById("nomCategorieLogiciel");
	var select = document.getElementById("categorieInfo");
	var id = 0;
	for(var i=0; i<select.options.length; i++)
	{
		var text = select.options[i].text;
		
		if(select.options[i].selected)
		{
			//elem.value = text;
			id = select.options[i].value;
			//elemId.value = id;
			
		}
	}
	if(id==0)
	{
		loadCategorie(id);
	}
	else
	{
		//loadLogiciels(id);
		loadCategorie(id);
	}
}
function saveCatInfo()
{
	for(var i=0; i<select.options.length; i++)
	{
		var text = select.options[i].text;
		
		if(select.options[i].selected)
		{
			elem.value = text;
			id = select.options[i].value;
		}
	}
	var form = document.forms['catInfo'];
	form.submit();
}
function logicielsOnChange2(select, cat)
{
	var id = -1;
	for(var i=0; i<select.options.length; i++)
	{
		var text = select.options[i].text;
		
		if(select.options[i].selected)
		{
			id = select.options[i].value;
		}
	}
	loadLogiciel2(id, cat);
}
function logicielsOnChange(select, cat)
{
	var elem = document.getElementById("nomCategorieLogiciel");
	var elemId = document.getElementById("idCategorieLogiciel");
	var id = -1;
	for(var i=0; i<select.options.length; i++)
	{
		var text = select.options[i].text;
		
		if(select.options[i].selected)
		{
			id = select.options[i].value;
		}
	}
	loadLogiciel(id, cat);
}
function deleteCategorieLink()
{
	var select = document.getElementById("categorieInfo");
	var id = -1;
	for(var i=0; i<select.options.length; i++)
	{
		var text = select.options[i].text;
		
		if(select.options[i].selected)
		{
			id = select.options[i].value;
		}
	}
	deleteCategorie(id);
}
function deleteLogicielLink()
{
	var select = document.getElementById("selectLogiciels1");
	var id = -1;
	for(var i=0; i<select.options.length; i++)
	{
		var text = select.options[i].text;
		
		if(select.options[i].selected)
		{
			id = select.options[i].value;
		}
	}
	deleteLogiciel(id);
}

</script>
