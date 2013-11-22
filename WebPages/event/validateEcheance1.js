/*function addEtudForma() {
	sel = document.getElementById("selected");
	hidden = document.getElementById("_etudforma");
	a = "";
	for (i = 0; i < sel.options.length; i++) {
		a += sel.options[i].value + ";";
	}
	hidden.value = a;
	return true;
}

function validateEtudiantsFormations() {
	var errors = 0;
	addEtudForma();
	etudiantsF = document.getElementById("_etudforma");
	value = etudiantsF.value;
	//alert(value);
	errors += validateField(value, "_etudforma", "mask", /^([ef]{1}:[0-9]+;)+$/);
	return errors;
}
function validateTitle() {
	var errors = 0;
	title = document.getElementById("form_title_id");
	value = title.value;
	errors += validateField(value, "title", "minlength", 1);
	errors += validateField(value, "title", "maxlength", 255);
	errors += validateField(value, "title", "mask", /^[^<>]+$/);
	return errors;
}
function validateDescription() {
	var errors = 0;
	title = document.getElementById("form_description_id");
	value = title.value;
	errors += validateField(value, "description", "minlength", 1);
	errors += validateField(value, "description", "maxlength", 1023);
	errors += validateField(value, "description", "mask", /^[^<>]+$/);
	return errors;
}

function validateDate() {
	var errors = 0;
	var date = document.getElementById("date").value;
	
	var d = parseInt(date.substring(0, 2));
	var m = parseInt(date.substring(3, 5));
	var a = parseInt(date.substring(6, 10));

	value = "" + a + "/" + m + "/" + d;
	addError(value);
	errors += validateField(value, "date", "dateValid", null);
	if(!compareDate(a, m, d))
		{
		errors++;
		addError("La date est passée");
		}
	return errors;
}

function validateCode() {
	errors = 0;
	code = document.getElementById("form_code_id");
	value = code.value;
	errors += validateField(value, "code", "minlength", 1);
	errors += validateField(value, "code", "maxlength", 3);
	errors += validateField(value, "code", "mask", /^[a-zA-Z.-\\s]+$/);
	return errors;
}
function validateEcheance(b) {
	var errors = 0;
	selectAll();
	errors += validateCode();
	errors += validateDate();
	errors += validateDescription();
	errors += validateEtudiantsFormations();
	errors += validateTitle();
	if (errors != 0) {
		// alert(errors+" erreurs");
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
		addError(date[0] + "//" + date[1] + "//" + date[2]);
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
	d.setFullYear(annee, mois-1, jour);
	var today = new Date();
	
	if(datepasseeok)
		return true;
	else if(d<today)
	{
		if(confirm("La date est passée( "+d.toDateString()+" ). Voulez-vous continuez (oui/non) ?"))
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
	} else if (i == 3) {
		errors += validateDescription();
	}
	return errors;
}
function valide(fct, i, id) {
	if (fct(i) == 0) {
		elem = document.getElementById(id);
		clazz = "listeChoixDiv";
		color = "#afa"
		if (validateEcheance(false) == 0) {
			submit = document.getElementById("submit_button");
			//submit.disabled = false;
		}
	} else {
		elem = document.getElementById(id);
		clazz = "listeChoixDivError";
		color = "#faf"
		submit = document.getElementById("submit_button");
		//submit.disabled = true;
	}
	elem.style.backgroundColor = color;
}

*/