/**
 * 用户登陆
 */
function login(){
	var userParam = {};
	userParam.userEmail = $("#userEmail").val().trim();
	userParam.userPwd = $("#userPwd").val().trim();
	userParam.imageCode = $('#imageCode').val().trim();
	if(!valiateEmail(userParam.userEmail) || !valiatePwd(userParam.userPwd)){
		showError(emailPwdError);
		return false;
	}else{
		hideError();
	}
	if(!userParam.imageCode){
		showError(varifiyCodeError);
		return false;
	}else{
		hideError();
	}
	$(".login-load").show();
	setTimeout(function(){
		asyncAjax("service/common/login", userParam, function(resultJson) {
			$(".login-load").hide();
			var result = $.parseJSON(resultJson);
			if(200 == result.code){
				if (typeof(Storage) !== "undefined") {
					localStorage.setItem("ssoUser", $("#userEmail").val());
			    	localStorage.setItem("ssoPwd", $("#userPwd").val());
				}
				window.location.href = "main.html";
			}else{
				showError(result.msg);
			}
		}, function(data) {
			$(".login-load").hide();
			showAlert(networkError,0);
		});
	},500);
};

$( "#loginButton" ).click(function() {
	login();
});
document.onkeydown=function(event){
    var e = event || window.event || arguments.callee.caller.arguments[0];
    if(e && e.keyCode==13){ 
    	var type = $("#pageType").val();
    	if("1" == type){
    		login();
    	}
    }
};