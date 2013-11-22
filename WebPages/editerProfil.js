function loadPage(page, div)
{
	Include.setPage(page);
	Include.getInclude(function(data) {
    dwr.util.setValue(div, data, { escapeHtml:false});
 	});
}

function loadLogiciels(categorie_id)
{
	
}