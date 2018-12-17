define(function(){
	$("#logout").click(function(){
		asyncAjax("service/common/logout", {}, function(result) {
			window.location.href = "login.html";
		}, function(data) {
			window.location.href = "login.html";
		});
	});
	
	function checkout(){
		asyncAjax("service/common/checkUser", {}, function(resultJson) {
			var result = $.parseJSON(resultJson);
			if('200' != result.code){
				window.location.href = "login.html";
			}
		}, function(data) {
			window.location.href = "login.html";
		});
	}
	checkout();
})