function passwordLength() {
        var password = $("#passwordChange").val();
	if (password.length == 0){
		$("#password_ok").val(false);
                $("#password_check").removeClass("formValid").removeClass("formError");
                $("#password_check").html("Password must be at least 4 characters");
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
                $("#password_check").html("");
		$("#password_ok").val(true);
	}
	passwordCheck($("#passwordRepeat").val());
}

function passwordCheck() {
	var password = $("#passwordChange").val();
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
                $("#passwordRepeat_check").html("");
		$("#passwordRepeat_ok").val(true);
	}
	checkForm();
}

function checkForm(){
    if( $("#passwordRepeat_ok").val() == "true" &&
        $("#password_ok").val() == "true"){
            $("#changePassword").removeAttr('disabled');	
    }
	else{
            $("#changePassword").attr('disabled', 'disabled');
        }
}