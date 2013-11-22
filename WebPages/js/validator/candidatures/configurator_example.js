

// ||||||||||||||||||||||||||||||||||||||||||||||||||
// Instructions for Configuration
// ||||||||||||||||||||||||||||||||||||||||||||||||||

/*
All elements are assumed required and will only be validated for an
empty value or defaultValue unless specified by the following properties.

isEmail = true;          // valid email address
isAlpha = true;          // A-Z a-z characters only
isNumeric = true;        // 0-9 characters only
isAlphaNumeric = true;   // A-Z a-z 0-9 characters only
isLength = number;       // must be exact length
isLengthBetween = array; // [lowNumber, highNumber] must be between lowNumber and highNumber
isPhoneNumber = true;    // valid US phone number. See "isPhoneNumber()" comments for the formatting rules
isDate = true;           // valid date. See "isDate()" comments for the formatting rules
isMatch = string;        // must match string
optional = true;         // element will not be validated
*/

// ||||||||||||||||||||||||||||||||||||||||||||||||||
// --------------------------------------------------
// ||||||||||||||||||||||||||||||||||||||||||||||||||

// configures form[0] or the first form in the document
function configureValidation1(){
    f = null;
	f = document.forms[0]; //the form must be set here
	f.tache.isAlphaNumeric = true;
	f.date_.isDate= true;
	f.entreprise.isAlphaNumeric = true;
	f.fonction.isAlphaNumeric  = true;
	f.reponse.optional  = true;
	f.suite.optional= true;
	f.infos.optional= true;
	f.contact.isRequired= true;

	//deal with radio and check buttons must be set according to page specifics
	var preCheck = true;
	return validateForm(f,preCheck,'configureValidation1');
}

