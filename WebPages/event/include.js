var id=0;


var page = "";


function forwardCreateEvent(p_id) {
	var value = eventtype; 
	if(value==-1 || value==0)
	{
		return;
	}
	if(value==1)
	{
		document.location.href='index_agenda.jsp?page=echeanceDetails&id='+p_id;
		//page= "/echeances2.jsp";
 	}
	else if(value==2)
	{
		page="/event/jpo.jsp";
	}
	else if(value==3)
	{
		page="/event/entreprise.jsp";
	} else if(value==4)
	{
		page="/event/conge.jsp";
	} else if(value==5)
	{
		page="/event/evt.jsp";
	} else{
		dwr.util.setValue("forward", "Choisissez un type d'évènement");
		return;
	}
	page=page + "?id=" + p_id;

	Include.setPage(page);
	Include.getInclude(function(data) {
    dwr.util.setValue("forward", data, { escapeHtml:false});
 	});
}
function forwardListe()
{
	var value = eventtype;
	if(value==-1 || value==0)
		return;
	if(value==1)
	{
		page= "/liste/echeances.jsp";
 	}
	else if(value==2)
	{
		page="/liste/jpo.jsp";
	}
	else if(value==3)
	{
		page="/liste/entreprise.jsp";
	} else if(value==4)
	{
		page="/liste/conge.jsp";
	} else if(value==5)
	{
		page="/liste/evt.jsp";
	}

	Include.setPage(page);
	Include.getInclude(function(data) {
    dwr.util.setValue("forward", data, { escapeHtml:false});
	});
}
var eventtype=0;
var action = 1;
function updateSelected(go)
{
	var neweventtype = 0; 
	var options = document.getElementById("typeevent").options;
	for(i=0; i<options.length; i++)
	{
		if(options[i].selected==true)
		{
			neweventtype = options[i].value;
		}
	}
	var options = document.getElementById("action").options
	for(i=0; i<options.length; i++)
	{
		if(options[i].selected==true)
		{
			newaction = options[i].value;
		}
	}
	if(neweventtype!=eventtype || newaction!=action || go==true)
	{
		if(!confirm("Vous avez changé le type d'évènement. Voulez-vous quitter ce formulaire?"))
		{
			return;
		}
		eventtype = neweventtype;
		action = newaction;
		if(action==1)
		{
			forwardCreateEvent(0);
		}
		else if(action==2)
		{
			forwardListe();
		}
	}
}
function updateSelectForm()
{
	var options = document.getElementById("typeevent").options;
	for(i=0; i<options.length; i++)
	{
		if(options[i].value==eventtype)
		{
			options[i].selected=true;
		}
	}
	var options = document.getElementById("action").options
	for(i=0; i<options.length; i++)
	{
		if(options[i].value==action)
		{
			options[i].selected=true;
		}
	}
}
function chooseList(list_id)
{
	var options = document.getElementById("typeevent").options;
	options.value=list_id;
	eventtype=list_id;
	for(i=0; i<options.length; i++)
	{
		if(options[i].value==list_id)
		{
			options[i].selected=true;
		}
	}
	options = document.getElementById("action").options
	options.value=2;
	action=2;
	for(i=0; i<options.length; i++)
	{
		if(options[i].value==action)
		{
			options[i].selected=true;
		}
	}
	forwardListe();
}
function chooseUpdate(list_id, id)
{
	var options = document.getElementById("typeevent").options;
	options.value=list_id;
	eventtype=list_id;
	for(i=0; i<options.length; i++)
	{
		if(options[i].value==list_id)
		{
			options[i].selected=true;
		}
	}
	options = document.getElementById("action").options
	options.value=1;
	action=1;
	for(i=0; i<options.length; i++)
	{
		if(options[i].value==action)
		{
			options[i].selected=true;
		}
	}
	forwardCreateEvent(id);
}
function chooseNew(list_id)
{
	var options = document.getElementById("typeevent").options;
	options.value=list_id;
	eventtype=list_id;
	for(i=0; i<options.length; i++)
	{
		if(options[i].value==list_id)
		{
			options[i].selected=true;
		}
	}
	options = document.getElementById("action").options
	options.value=2;
	action=1;
	for(i=0; i<options.length; i++)
	{
		if(options[i].value==action)
		{
			options[i].selected=true;
		}
	}
	forwardCreateEvent(0);
}
function editSelected(row_id)
{
	id = row_id;
	action=1;
	updateSelectForm();
	//alert(id);
	forwardCreateEvent(id);
}
function removeSelected(row_id)
{
	if(confirm("Voulez-vous vraiment supprimer cet objet? (Oui/Non)"))
	{
		alert("Supprimer " + eventtype + "("+row_id+")");
		document.location.href="/ste-emploi/DeleteEvt?id="+row_id+"&eventtype="+eventtype;
	}
}
function go(qs)
{
	updateSelected(null, go)();
}
