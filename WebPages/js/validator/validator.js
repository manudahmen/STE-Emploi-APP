// returns true if the string is empty
function isEmpty(str){
	return (str == null) || (str.length == 0);
}
// returns true if the string is a valid email
function isEmail(str){
	if(isEmpty(str)) return false;
	var re = /^[^\s()<>@,;:\/]+@\w[\w\.-]+\.[a-z]{2,}$/i
	return re.test(str);
}
// returns true if the string is a valid email
function isUrl(str){// A modifier
	if(isEmpty(str)) return false;
	var re = /^[^\s()<>@,;:\/]+@\w[\w\.-]+\.[a-z]{2,}$/i
	return re.test(str);
}
// returns true if the string only contains characters A-Z or a-z
function isAlpha(str){
	var re = /[^a-zA-Z]/g
	if (re.test(str)) return false;
	return true;
}
// returns true if the string only contains characters 0-9
function isNumeric(str){
	var re = /[\D]/g
	if (re.test(str)) return false;
	return true;
}
// returns true if the string only contains characters A-Z, a-z or 0-9
function isAlphaNumeric(str){
	var re = /[^a-zA-Z0-9-\séèêëäàâîïôöûüù]/g
	if (re.test(str)) return false;
	return true;
}
// returns true if the string only contains characters A-Z, a-z or 0-9
function isStreetNumber(str){
	var re = /[a-zA-Z]+[0-9]*/g
	if (re.test(str)) return true;
	return false;
}
// returns true if the string only contains characters A-Z, a-z or 0-9
function isCodePostal(str){
	var re = /[0-9]{4}/g
	if (re.test(str)) return true;
	return true;
}isCodePostal
// returns true if the string's length equals "len"
function isLength(str, len){
	return str.length == len;
}
// returns true if the string's length is between "min" and "max"
function isLengthBetween(str, min, max){
	return (str.length >= min)&&(str.length <= max);
}
// returns true if the string is a US phone number formatted as...
// (000)000-0000, (000) 000-0000, 000-000-0000, 000.000.0000, 000 000 0000, 0000000000
function isPhoneNumber(str){
	var re = /^\[0-9\/.\s]*$/;
	return re.test(str);
}
// returns true if the string is a valid date formatted as...
// mm dd yyyy, mm/dd/yyyy, mm.dd.yyyy, mm-dd-yyyy
function isDate(str){
	var re = /^(\d{1,2})[\s\.\/-](\d{1,2})[\s\.\/-](\d{4})$/;
	if (!re.test(str)) return false;
	var result = str.match(re);
	var m = parseInt(result[2], 10);
	var d = parseInt(result[1], 10);
	var y = parseInt(result[3], 10);
	if(m < 1 || m > 12 || y < 1900 || y > 2100) return false;
	if(m == 2){
		var days = ((y % 4) == 0) ? 29 : 28;
	}else if(m == 4 || m == 6 || m == 9 || m == 11){
		var days = 30;
	}else{
		var days = 31;
	}
	return (d >= 1 && d <= days);
}
// returns true if "str1" is the same as the "str2"
function isMatch(str1, str2){
	return str1 == str2;
}
// returns true if the string contains only whitespace
// cannot check a password type input for whitespace
function isWhitespace(str){ // NOT USED IN FORM VALIDATION
	var re = /[\S]/g
	if (re.test(str)) return false;
	return true;
}
// removes any whitespace from the string and returns the result
// the value of "replacement" will be used to replace the whitespace (optional)
function stripWhitespace(str, replacement){// NOT USED IN FORM VALIDATION
	if (replacement == null) replacement = '';
	var result = str;
	var re = /\s/g
	if(str.search(re) != -1){
		result = str.replace(re, replacement);
	}
	return result;
}

// validate form
function validateForm(f,preCheck,theformfunction){
	var valid = true;
	var i,e,t,v,g,b,spantxt,spanid,spanelement,hiddenspan,revalidate,errorwarning;
	errorwarning = document.getElementById('errorwarning');
	
	for(i=0; i < f.elements.length; i++){
		e = f.elements[i];
			
		//add event & functions to form elements based on the formfucntion string
		if (theformfunction == 'configureValidation1') revalidate = function(){configureValidation1()};
		if (theformfunction == 'configureValidation2') revalidate = function(){configureValidation2()};
		if (theformfunction == 'configureValidation3') revalidate = function(){configureValidation3()};
		if (e.type == 'text' || e.type == 'password' || e.type == 'textarea'){e.onkeyup = revalidate};
		if (e.nodeName.toLowerCase() == "select"){e.onchange = revalidate};
		if (e.type == 'radio' || e.type == 'checkbox'){e.onclick = revalidate};

		
		if(e.optional) continue;
		
		t = e.type;
		v = e.value;
		
		g = e.id + "L";
		
		if(document.getElementById(g)) b = document.getElementById(g);
		
		spanid = e.id + "m";
		spanelement = document.createElement('span');
		spanelement.id = spanid;
		spanelement.className = "errortxt"
		if (!document.getElementById(spanid)) e.parentNode.appendChild(spanelement);
		hiddenspan = document.getElementById(spanid);
		
		if(t == 'text' || t == 'password' || t == 'textarea'){
			if(isEmpty(v)){
				valid = false;
				b.className = "errorLabel";
				hiddenspan.style.display = 'block';
				hiddenspan.innerHTML = 'Required Information';
				continue;
			}else{
				hiddenspan.style.display = 'none';
				hiddenspan.innerHTML = '';
				b.className = "fixedLabel"
			}
			/*
			if(v == e.defaultValue){
				valid = false;
				hiddenspan.style.display = 'block';
				hiddenspan.innerHTML = 'Replace Default Text';
				b.className = "errorLabel";
				continue;
			}else{
				hiddenspan.style.display = 'none';
				hiddenspan.innerHTML = '';
				b.className = "fixedLabel"
			}
			*/
			if(e.isAlpha){
				if(!isAlpha(v)){
					valid = false;
					b.className = "errorLabel";
					hiddenspan.style.display = 'block';
					hiddenspan.innerHTML = 'Only Letters Allowed';
					continue;
				}else{
					hiddenspan.style.display = 'none';
					hiddenspan.innerHTML = '';
					b.className = "fixedLabel"
				}
			}
			
			if(e.isNumeric){
				if(!isNumeric(v)){
					valid = false;
					b.className = "errorLabel";
					hiddenspan.style.display = 'block';
					hiddenspan.innerHTML = 'Only Numbers Allowed';
					continue;
				}else{
					hiddenspan.style.display = 'none';
					hiddenspan.innerHTML = '';
					b.className = "fixedLabel"
				}
			}
			
			if(e.isAlphaNumeric){
				if(!isAlphaNumeric(v)){
					valid = false;
					b.className = "errorLabel";
					hiddenspan.style.display = 'block';
					hiddenspan.innerHTML = 'Only Letters & Numbers Allowed';
					continue;
				}else{
					hiddenspan.style.display = 'none';
					hiddenspan.innerHTML = '';
					b.className = "fixedLabel"
				}
			}
			
			if(e.isEmail){
				if(!isEmail(v)){
					valid = false;
					b.className = "errorLabel";
					hiddenspan.style.display = 'block';
					hiddenspan.innerHTML = 'Invalid Email Format';
					continue;
				}else{
					hiddenspan.style.display = 'none';
					hiddenspan.innerHTML = '';
					b.className = "fixedLabel"
				}
			}
			
			if(e.isLength != null){
				var len = e.isLength;
				if(!isLength(v,len)){
					valid = false;
					b.className = "errorLabel";
					hiddenspan.style.display = 'block';
					hiddenspan.innerHTML = 'Invalid Amount Characters';
					continue;
				}else{
					hiddenspan.style.display = 'none';
					hiddenspan.innerHTML = '';
					b.className = "fixedLabel"
				}
			}
			
			if(e.isLengthBetween != null){
				var min = e.isLengthBetween[0];
				var max = e.isLengthBetween[1];
				if(!isLengthBetween(v,min,max)){
					valid = false;
					b.className = "errorLabel";
					hiddenspan.style.display = 'block';
					hiddenspan.innerHTML = 'Invalid Amount Characters';
					continue;
				}else{
					hiddenspan.style.display = 'none';
					hiddenspan.innerHTML = '';
					b.className = "fixedLabel"
				}
			}
			
			if(e.isPhoneNumber){
				if(!isPhoneNumber(v)){
				 	valid = false;
					b.className = "errorLabel";
					hiddenspan.style.display = 'block';
					hiddenspan.innerHTML = 'Invalid Phone Format';
					continue;
				}else{
					hiddenspan.style.display = 'none';
					hiddenspan.innerHTML = '';
					b.className = "fixedLabel"
				}
			}
			
			if(e.isDate){
				if(!isDate(v)){
					valid = false;
					b.className = "errorLabel";
					hiddenspan.style.display = 'block';
					hiddenspan.innerHTML = 'Invalid Date Format';
					continue;
				}else{
					hiddenspan.style.display = 'none';
					hiddenspan.innerHTML = '';
					b.className = "fixedLabel"
				}
			}
			
			if(e.isMatch != null){
				if(!isMatch(v, e.isMatch)){
					valid = false;
					b.className = "errorLabel";
					hiddenspan.style.display = 'block';
					hiddenspan.innerHTML = 'Entered Values Do Not Match';
					continue;
				}else{
					hiddenspan.style.display = 'none';
					hiddenspan.innerHTML = '';
					b.className = "fixedLabel"
				}
			}
		}
		
		if(t.indexOf('select') != -1){
			if(e.options[e.selectedIndex].value == '-1'){
				valid = false;
				b.className = "errorLabel";
				hiddenspan.style.display = 'block';
				hiddenspan.innerHTML = 'Required Information';
				continue;
			}else{
				hiddenspan.style.display = 'none';
				hiddenspan.innerHTML = '';
				b.className = "fixedLabel"
			}
		}
		
		if(t == 'file'){
			if(isEmpty(v)){
				valid = false;
				b.className = "errorLabel";
				hiddenspan.style.display = 'block';
				hiddenspan.innerHTML = 'Required Information';
				continue;
			}else{
				hiddenspan.style.display = 'none';
				hiddenspan.innerHTML = '';
				b.className = "fixedLabel"
			}
		}
			
	}
	if(preCheck == false){valid = false};
	if(preCheck == false || valid == false){
			errorwarning.style.display = 'block';
			(window.location.hash == '#errorwarning') ? null : window.location.hash = 'errorwarning';
		}else{
			errorwarning.style.display = 'none'
		};
	return valid;
}
