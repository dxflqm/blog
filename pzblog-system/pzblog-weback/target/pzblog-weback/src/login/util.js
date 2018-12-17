var emailPwdError = "用户名或密码不正确！";
var varifiyCodeError = "验证码为空！";
var networkError = "网络服务器超时,请稍后再试！";
var firstLoginInfo = "首次登陆，请重置密码！"
var emailErrorInfo = "邮箱格式不正确";
var pwdErrorInfo = "密码长度不符合要求！"
var newPwdErrorInfo = "确认密码与新密码不一致！";
var sendMailCodeInfo = "获取邮件验证码";
var sendMailResultInfo = "已发送(";
var mailResultSecondInfo = "S)";
var pwdResetSuccess = "密码重置成功！";

/**
 * 登陆模块
 * 
 */
function valiateEmail(username) {
	if(!username) {
		return false;
	}
//	var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
//	if(!reg.test(username)) {
//		return false;
//	}
	return true;
};

/**
 * pwd规则效验
 * @param {Object} password
 */
function valiatePwd(password) {
    var str = password;
    if (str == null || str.length <6) {
        return false;
    }
    var reg1 = new RegExp(/^[0-9A-Za-z]+$/);
    if (!reg1.test(str)) {
        return false;
    }
    var reg = new RegExp(/[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/);
    if (reg.test(str)) {
        return true;
    } else {
        return false;
    }
};

/**
 * 显示错误信息
 * @param {Object} info
 */
function showError(info){
	$("#errorContent").html(info);
	$(".error-info").show();
};

/**
 * 隐藏错误信息
 */
function hideError(){
	$(".error-info").hide();
};

/**
 * 显示正确信息
 * @param {Object} info
 */
function showSuccess(info){
	$("#successContent").html(info);
	$(".success-info").show();
};

/**
 * 隐藏正确信息
 */
function hideSuccess(){
	$(".success-info").hide();
};