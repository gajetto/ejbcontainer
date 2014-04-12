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
	}
	else if (password.length > 0 && password.length < 4){
		$("#password_check").addClass("formError")
		.removeClass("formValid");
		$("#password_ok").val(false);
	}
	else if (password.length >= 4){
		$("#password_check").addClass("formValid")
		.removeClass("formError");
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
	}
	else if (password != check){
		$("#passwordRepeat_check").addClass("formError")
		.removeClass("formValid");
		$("#passwordRepeat_ok").val(false);
	}
	else if (password == check){
		$("#passwordRepeat_check").addClass("formValid")
		.removeClass("formError");
		$("#passwordRepeat_ok").val(true);
	}
	checkForm();
}

function checkForm(){
    
}