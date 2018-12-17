$(function() {
	setCategoryInfo();
	setTagInfo();
	var articleId = getUrlCode("articleId");
	if(!!articleId) {
		$("#articleId").val(articleId);
		getBaseInfo(articleId);
	}
});

function getBaseInfo(articleId){
	asyncAjax(directory + "article/queryById", {articleId:articleId}, function(dataInfo) {
		var result = JSON.parse(dataInfo);
		if(result.code == "200"){
			$('#categoryId').selectpicker('val', result.data.categoryId);
			if(!!result.data.categoryName){
				$('#categoryId').attr("disabled","disabled");
			}
			$('#tagId').selectpicker('val', result.data.tagId);
			$('#articleType').selectpicker('val', result.data.articleType);
			$("#reprintHref").val(result.data.reprintHref);
			$("#articleTitle").val(result.data.articleTitle);
			$("#articleIntroduce").val(result.data.articleIntroduce);
			
			$('.selectpicker').selectpicker('refresh');
		}else{
			showAlert(result.msg,0);
		}
	},function(data){
		showAlert(tip_networkError,0);
	});
}

function setCategoryInfo(){
	$("#categoryId").empty();
	syncAjax(directory + "category/queryCategory", {}, function(dataInfo) {
		var result = JSON.parse(dataInfo);
		if(result.code == "200"){
			var dataList = result.data;
			for(var i in dataList) {
				$("#categoryId").append("<option value='" + dataList[i].categoryId + "'>" + dataList[i].categoryName + "</option>");
			}
			$('#categoryId').selectpicker('refresh');
		}else{
			showAlert(result.msg,0);
		}
	},function(data){
		showAlert(tip_networkError,0);
	});
}

function setTagInfo(){
	$("#tagId").empty();
	syncAjax(directory + "tag/queryTag", {}, function(dataInfo) {
		var result = JSON.parse(dataInfo);
		if(result.code == "200"){
			var dataList = result.data;
			for(var i in dataList) {
				$("#tagId").append("<option value='" + dataList[i].tagId + "'>" + dataList[i].tagName + "</option>");
			}
			$('#tagId').selectpicker('refresh');
		}else{
			showAlert(result.msg,0);
		}
	},function(data){
		showAlert(tip_networkError,0);
	});
}

function comfrimInfo(){
	showLoading();
	var articleId = $("#articleId").val();
	var categoryId = $("#categoryId").val();
	var tagId = $("#tagId").val();
	var articleType = $("#articleType").val();
	var reprintHref = $("#reprintHref").val();
	var articleTitle = $("#articleTitle").val();
	var articleIntroduce = $("#articleIntroduce").val();
	if(!articleId){
		articleId = "";
	}
	if(!categoryId){
		closeLoading();
		showtip("categoryId","请选择类别！！！");
		return false; 
	}
	if(!tagId){
		closeLoading();
		showtip("tagId","请选择标签");
		return false;
	}
	if(!articleType){
		closeLoading();
		showtip("articleType","请选择文章类型");
		return false;
	}
	if(articleType == "1"){
		if(!reprintHref){
			closeLoading();
			showtip("reprintHref","请输入转载链接");
			return false;
		}
	}
	if(!articleTitle){
		closeLoading();
		showtip("articleTitle","请输入文章标题");
		return false;
	}
	if(!articleIntroduce){
		closeLoading();
		showtip("articleIntroduce","请输入文章简介");
		return false;
	}
	var param = {};param.articleId = articleId;param.categoryId = categoryId;param.tagId = tagId;param.articleType = articleType;param.reprintHref = unescape(reprintHref);param.articleTitle = articleTitle;param.articleIntroduce = articleIntroduce;
	asyncAjax(directory + "article/addOrEdit", param, function(jsondata) {
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