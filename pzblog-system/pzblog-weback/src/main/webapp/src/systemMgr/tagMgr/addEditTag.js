$(function() {
	var tagId = getUrlCode("tagId");
	if(!!tagId) {
		$("#tagId").val(tagId);
		getBaseInfo(tagId);
	}
});

function getBaseInfo(tagId){
	asyncAjax(directory + "tag/queryById", {tagId:tagId}, function(dataInfo) {
		var result = JSON.parse(dataInfo);
		if(result.code == "200"){
			$("#tagName").val(result.data.tagName);
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
	var tagId = $("#tagId").val();
	var tagName = $("#tagName").val();
	var sort = $("#sort").val();
	var remark = $("#remark").val();
	if(!tagId){
		tagId = "";
	}
	if(!tagName){
		closeLoading();
		showtip("tagName","标签名称不能为空！！！");
		return false; 
	}
	if(!sort){
		closeLoading();
		showtip("sort","排序不能为空");
		return false;
	}
	var param = {};param.tagId = tagId;param.tagName = tagName;param.sort = sort;param.remark = remark;
	asyncAjax(directory + "tag/addOrEdit", param, function(jsondata) {
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