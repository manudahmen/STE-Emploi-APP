

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
	f.firstname.isAlphaNumeric = true;
	f.lastname.isAlphaNumeric = true;
	f.age.isNumeric = true;
	f.ssnumber.isLength = [4];
	f.email.isEmail = true;
	f.phone.isPhoneNumber = true;
	f.birthday.isDate = true;
	f.password1.isLengthBetween = [4,255];
	f.password2.isMatch = f.password1.value;
	f.request.isAlphaNumeric = true;
	f.comments.optional = true;
	
	//deal with radio and check buttons must be set according to page specifics
	var preCheck = true;
	if(!f.checkbox0[0].checked && !f.checkbox0[1].checked && !f.checkbox0[2].checked){
	document.getElementById('checkbox0Lm').style.display = 'block';
	document.getElementById('checkbox0L').className = 'errorLabel';
	document.getElementById('checkbox0Lm').innerHTML = 'Required Selection';
	preCheck = false;
	}else{
	document.getElementById('checkbox0Lm').style.display = 'none';
	document.getElementById('checkbox0L').className = 'fixedLabel';
	document.getElementById('checkbox0Lm').innerHTML = '';
	};
	
	if(!f.gender[0].checked && !f.gender[1].checked){
	document.getElementById('genderLm').style.display = 'block';
	document.getElementById('genderL').className = 'errorLabel';
	document.getElementById('genderLm').innerHTML = 'Required Selection';
	preCheck = false;
	}else{
	document.getElementById('genderLm').style.display = 'none';
	document.getElementById('genderL').className = 'fixedLabel';
	document.getElementById('genderLm').innerHTML = '';
	};
	
	return validateForm(f,preCheck,'configureValidation1');
}

function configureValidation2(){
	f = null;
	f = document.forms[1]; //the form
	f.dogsname.isAlpha = true;
	f.catsname.isAlpha = true;
	precheck = true //no radio or checkbox in form

	return validateForm(f,precheck,'configureValidation2');
}

