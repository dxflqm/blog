function queryAllCategory(){
	asyncAjax("service/category/queryAll", {}, function(resultJson) {
		var result = $.parseJSON(resultJson);
		if('200' == result.code){
			bindDataToView(result.data);
		}else{
			showAlert(result.msg);
		}
	}, function(data) {
		showAlert(tip_networkError);
	});
}
function routeCategory(categoryId,categoryName){
	loadUrl(articleTimeURL + "?categoryId=" + categoryId + "&categoryName=" + escape(categoryName));
}
function bindDataToView(data){
	$("#siteContent").empty();
	var siteHtml = "";
	for (var i in data) {
		var site = data[i];
		var contentHtml = '<div class="col-md-6">';
		contentHtml = contentHtml + '<a onclick="routeCategory(\''+site.categoryId+'\',\''+site.categoryName+'\')">';
		contentHtml = contentHtml + '<div class="panel panel-success"><div class="panel-heading"><h4 class="panel-title">';
		contentHtml = contentHtml + '<span>'+site.categoryName+'</span>' +'<span class="badge pull-right"><span>'+site.categoryCount+'</span></span>';
		contentHtml = contentHtml + '</h4></div></div></a></div>';
		siteHtml = siteHtml + contentHtml;
	}
	$("#siteContent").append(siteHtml);
}