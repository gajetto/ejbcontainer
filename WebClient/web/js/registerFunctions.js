$(function() {
    $( "#datePicker" ).datepicker({
      changeMonth: true,
      changeYear: true,
      minDate: "-30Y",
      maxDate: "-18Y",
      dateFormat: "dd.mm.yy"
    });
  });
  
 function passwordLength() {
        var password = $("#password").val();
	if (password.length == 0){
		$("#password_ok").val(false);
                $("#Password_check").removeClass("formValid").removeClass("formError");
                $("#password_check").html("&nbsp;");
	}
	else if (password.length > 0 && password.length < 4){
		$("#password_check").addClass("formError")
		.removeClass("formValid");
                $("#password_check").html("Password must be at least 4 characters");
		$("#password_ok").val(false);
	}
	else if (password.length >= 4){
		$("#password_check").addClass("formValid")
		.removeClass("formError");
                $("#password_check").html("Password OK");
		$("#password_ok").val(true);
	}
	passwordCheck($("#passwordRepeat").val());
}

function passwordCheck() {
	var password = $("#password").val();
	var check = $("#passwordRepeat").val();
	
	if (check != null && check.length == 0){
		$("#passwordRepeat_ok").val(false);
                $("#passwordRepeat_check").removeClass("formValid").removeClass("formError");
                $("#passwordRepeat_check").html("&nbsp;");
	}
	else if (password != check){
		$("#passwordRepeat_check").addClass("formError")
		.removeClass("formValid");
                $("#passwordRepeat_check").html("Passwords are not the same");
		$("#passwordRepeat_ok").val(false);
	}
	else if (password == check){
		$("#passwordRepeat_check").addClass("formValid")
		.removeClass("formError");
                $("#passwordRepeat_check").html("Passwords are the same");
		$("#passwordRepeat_ok").val(true);
	}
	checkForm();
}

function dateCheck(){
    var re = /^\d{2}.\d{2}.\d{4}$/;
    var date = $("#datePicker").val();
    var now = new Date();
    var dateMaxi = new Date(now.setYear(now.getYear() - 18, 1));
    
    if(re.test(date)){
        var splittedDate = date.split(".");
        var day = splittedDate[0];
        var month = splittedDate[1];
        var year = splittedDate[2];
        var d2 = new Date(year,month-1,day);
        var valid = true;
        if ((isNaN(day))||(day<1)||(day>31)) {
            valid = false;
         }
         if ((isNaN(month))||(month<1)||(month>12)) {
            valid = false;
         }
         if ((isNaN(year))||(year<1900)) {
            valid = false;
         }
         
         if (valid) {
            var j2 = d2.getDate();
            var m2=d2.getMonth()+1;
            var a2=d2.getFullYear();
            if (a2<=100) {a2=1900+a2}
            if ((day!=j2)||(month!=m2)||(year!=a2) ) {
               valid = false;
            }
         }
         
         if(valid){
             if(d2>=dateMaxi){
                 valid = false;
             }
         }
        
        if (valid){
            $("#date_check").addClass("formValid")
            .removeClass("formError");
            $("#date_check").html("Date valid");
            $("#date_ok").val(true);
        }else{
            $("#date_check").addClass("formError")
            .removeClass("formValid");
            $("#date_check").html("Date not valid");
            $("#date_ok").val(false);
            checkForm();
        }
    }else{
        $("#date_check").addClass("formError")
        .removeClass("formValid");
        $("#date_check").html("Date not valid");
        $("#date_ok").val(false);
        checkForm();
    }
    checkForm();
}

function checkForm(){
    if( $("#passwordRepeat_ok").val() == "true" &&
        $("#password_ok").val() == "true" &&
        $("#date_ok").val() == "true"){
            $("#registerSignup").removeAttr('disabled');	
    }
	else{
            $("#registerSignup").attr('disabled', 'disabled');
        }
}