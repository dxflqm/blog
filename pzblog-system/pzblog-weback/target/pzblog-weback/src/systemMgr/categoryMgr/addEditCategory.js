$(function() {
	var categoryId = getUrlCode("categoryId");
	if(!!categoryId) {
		$("#categoryId").val(categoryId);
		getBaseInfo(categoryId);
	}
});

function getBaseInfo(categoryId){
	asyncAjax(directory + "category/queryById", {categoryId:categoryId}, function(dataInfo) {
		var result = JSON.parse(dataInfo);
		if(result.code == "200"){
			$("#categoryName").val(result.data.categoryName);
			$("#sort").val(result.data.sort);
			$("#remark").val(result.data.remark);
		}else{
			showAlert(result.msg,0);
		}
	},function(data){
		showAlert(tip_networkError,0);
	});
}

function comfrimInfo(){
	showLoading();
	var categoryId = $("#categoryId").val();
	var categoryName = $("#categoryName").val();
	var sort = $("#sort").val();
	var remark = $("#remark").val();
	if(!categoryId){
		categoryId = "";
	}
	if(!categoryName){
		closeLoading();
		showtip("categoryName","类别名称不能为空！！！");
		return false; 
	}
	if(!sort){
		closeLoading();
		showtip("sort","排序不能为空");
		return false;
	}
	var param = {};param.categoryId = categoryId;param.categoryName = categoryName;param.sort = sort;param.remark = remark;
	asyncAjax(directory + "category/addOrEdit", param, function(jsondata) {
		closeLoading();
		var resultData = JSON.parse(jsondata);
		if(resultData.code == "200"){
			closePopupFrame();
			showAlert(tip_opertion_success,1);
		}else{
			showAlert(resultData.msg,0);
		}
	},function(data){
		closeLoading();
		showAlert(tip_networkError,0);
	});
}