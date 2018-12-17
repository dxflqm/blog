$(function(){
	asyncAjax(directory + "loginMgr/checkUser", {}, function(resultJson) {
		var result = $.parseJSON(resultJson);
		if('200' != result.code){
			window.parent.location.href = "../../../login.html";
		}
	}, function(data) {
		window.parent.location.href = "../../../login.html";
	});
});