
function valideTout()
{
	clearErrors();
	var errors = validateEtudiantsFormations();
	//validateDate();
	errors += validateTitle();
	errors += validateCode();
	errors += validateDescription();
	
	if(errors==0)
	{
		
		return true;
	}
	else
	{
		return false;
	}
}
function couleur(errors, id, p_id)
{
	if(errors==0)
	{
		valideCouleur(id, p_id);
	}
	else
	{
		invalideCouleur(id, p_id);
	}
}
function invalideCouleur(id,p_id)
{
	elem = document.getElementById(id);
//	elem.style.backgroundColor="#FF0000";
	elem = document.getElementById(p_id);
	elem.style.color="#F55";
}
function valideCouleur(id, p_id)
{
	elem = document.getElementById(id);
	//elem.style.backgroundColor="teal";
	elem = document.getElementById(p_id);
	elem.style.color="black";
}
function bord(errors, id)
{
	if(errors==0)
	{
		valideBord(id);
	}
	else
	{
		invalideBord(id);
	}
}
function invalideBord(id)
{
	elem = document.getElementById(id);
	elem.style.borderColor="#FF0000";
	elem.style.borderWidth="2px";
}
function valideBord(id)
{
	elem = document.getElementById(id);
	elem.style.borderColor="#688";
	elem.style.borderWidth="2px";
}

function addEtudForma() {
	var sel = document.getElementById("selected");
	hidden = document.getElementById("_etudforma");
	var a = "";
	for (i = 0; i < sel.options.length; i++) 
	{
		a = a + sel.options[i].value + ";";
	}
	hidden.value = a;
	return true;
}

function validateEtudiantsFormations() {
	var errors = 0;
	addEtudForma();
	etudiantsF = document.getElementById("_etudforma");
	value = etudiantsF.value;
	errors += validateField(value, "_etudforma", "mask", /^([ef]{1}:[0-9]+;)+$/);
	couleur(errors, 'selected_div', 'selected_p');
	bord(errors, 'selected');
	return errors;
}
function validateTitle() {
	var errors = 0;
	title = document.getElementById("form_title_id");
	value = title.value;
	errors += validateField(value, "title", "minlength", 1);
	errors += validateField(value, "title", "maxlength", 255);
	errors += validateField(value, "title", "mask", /^[^<>]+$/);
	couleur(errors, 'div3', 'title_p');
	bord(errors, 'form_title_id');
	return errors;
}
function validateDescription() {
	var errors = 0;
	title = document.getElementById("form_description_id");
	value = title.value;
	errors += validateField(value, "description", "minlength", 1);
	errors += validateField(value, "description", "maxlength", 1023);
	//errors += validateField(value, "description", "mask", /^[^<>]+$/);
	couleur(errors, 'div3', 'description_p');
	bord(errors, 'form_description_id');
	return errors;
}

function validateDate() {
	var errors = 0;
	var a = document.getElementById("date_a").value;
	var m = document.getElementById("form_date_m_id").value;
	var j = document.getElementById("date_j").value;	
	value = "" + a + "/" + m + "/" + j;
	addError(value);
	errors += validateField(value, "date", "dateValid", null);
	alert (""+a+"/"+m+"/"+j);
	
	value = date_j.value;
	if(!compareDate(a, m, j))
		{
		errors++;
		addError("La date est passée");
		}
	couleur(errors, 'div3', 'date_p');
	bord(errors, 'date_div')
	return errors;
}

function validateCode() {
	errors = 0;
	code = document.getElementById("form_code_id");
	value = code.value;
	errors += validateField(value, "code", "minlength", 1);
	errors += validateField(value, "code", "maxlength", 3);
	errors += validateField(value, "code", "mask", /^[a-zA-Z.-\\s]+$/);
	couleur(errors, 'div3', 'code_p');
	bord(errors, 'form_code_id')
	return errors;
}
function validateEcheance(b) {
	var errors = 0;
	errors += validateCode();
	errors += validateDate();
	errors += validateDescription();
	errors += validateEtudiantsFormations();
	errors += validateTitle();
	if (errors != 0) {
		alert("Erreurs");
		//document.location.href = "#errors";
	} else {
		//alert(errors + " erreurs");
		if (b) {
			form = document.getElementById("formid");
			form.submit();
		}
	}
	return errors;
}
function addError(msg) {
	errors = document.getElementById("errors_ul");
	newChild = document.createElement("li");
	newChild.innerHTML = msg;
	refChild = errors.firstChild;
	errors.insertBefore(newChild, refChild);
}
function clearErrors()
{
	errors = document.getElementById("errors_ul");
	errors.innerHTML="";
}
function validateField(field, fieldName, type, value) {
	var errors = 0;
	var msg = null;
	if (type == "minlength") {
		field = "" + field;
		if (field.length < value) {
			msg = "Le champ " + fieldName + " ( " + field
					+ " ) n'as pas la taille suffisante ( " + value + " )";
			errors++;
		} else {
		}
	} else if (type == "maxlength") {
		field = "" + field;
		if (field.length >= value) {
			msg = "Le champ " + fieldName + " ( " + field
					+ " )  a une taille trop important ( " + value + " )";
			errors++;
		}

	} else if (type == "mask") {
		field = "" + field;
		// Reg Exp
		regexp = new RegExp(value);
		ex = regexp.exec(field);
		if (ex == null) {
			msg = "Le champ " + fieldName + " ( " + field + " ) (" + ex
					+ ")contient des caract�res interdit ( " + value + " )";
			errors++;
		} else {
		}

	} else if (type == "min") {
		field = 0 + field;
		if (field < value) {
			msg = "Le champ " + fieldName + " ( " + field
					+ " )  est trop petit ( " + value + " )";
			errors++;
		} else {
		}

	} else if (type == "max") {
		// Reg Exp
		field = 0 + field;
		if (field > value) {
			msg = "Le champ " + fieldName + " ( " + field
					+ " )  est trop grand ( " + value + " )";
			errors++;
		} else {
		}

	} else if (type == "dateValid") {
		i1 = field.indexOf("/", 0);
		i2 = field.indexOf("/", i1 + 1);
		addError("i1=" + i1 + "i2= " + i2);
		var date = new Array();
		date[0] = field.substring(0, i1);
		date[1] = field.substring(i1 + 1, i2);
		date[2] = field.substring(i2 + 1);
		addError(date[0] + "/" + date[1] + "/" + date[2]);
		// alert(date);
		if (date[0] > 1990 & date[1] >= 0 & date[1] <= 11 & date[2] >= 0
				& date[2] <= 31) {
			if ((date[1] == 3 | date[1] == 5 | date[1] == 8 | date[1] == 10)
					& date[2] >= 30) {
				errors++;
				msg += "Mois à 30 jours";
			}

			if (date[1] == 1 & ((date[0] % 4) != 0)) {
				if (date[2] >= 28) {
					errors++;
					msg += "Février 28 jours";
			}
			}
			if (date[1] == 1 & ((date[0] % 4) == 0)) {
				if (date[2] >= 29) {
					errors++;
					msg += "Février 29 jours";
				}
			}
		} else {
			errors++;
			msg += "date non valide";
		}
	} else {
		msg = "ELSE";
		errors++;
	}
	if (msg != null)
		addError(msg)

	return errors;
}
var datepasseeok=false;
function compareDate(annee, mois, jour)
{
	var d = new Date();
	d.setFullYear(annee, mois, jour+1);
	var today = new Date();
	
	//alert(d + "--" + today);
	//alert(d+ "--" + today );
	if(datepasseeok)
		return true;
	else if(d<today)
	{
		if(confirm("La date est pass�e. Voulez-vous continuez (oui/non) ?"))
		{
			datepasseeok = true;
			return true;
		}
		else
		{
			return false;
		}
	}
	else
	{
		return true;
	}
}
function validateFrame(i) {
	var errors = 0;
	if (i == 1) {
		errors += validateEtudiantsFormations();
	} else if (i == 2) {
		check = validateDate();
		if(!check)
		{
			errors ++;
		}
		errors += validateTitle();
		errors += validateCode();
		errors += validateDescription();
	}
	return errors;
}
function valide(fct, i, id) {
	if (fct(i) == 0) {
		elem = document.getElementById(id);
		clazz = "listeChoixDiv";
		color = "688";
	} else {
		elem = document.getElementById(id);
		clazz = "listeChoixDivError";
		color = "#f00"
		submit = document.getElementById("submit_button");
		//submit.disabled = true;
	}
	//elem.style.backgroundColor = color;
}
function submit_echeance_form()
{
	return document.formid.submit();
}
