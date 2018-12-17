
var remoteUrl = "";
var directory = "../../../service/";

/**
 * 获取Url中请求参数
 * @param {Object} name
 */
function getUrlCode(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}

/**
 * 通用消息提示
 * @param {Object} info 信息
 * @param {Object} type 0：失败，1：成功
 */
function showAlert(info,type){
	if(1 != type){
		parent.layer.alert(info,{icon:0});
	}else{
		parent.layer.alert(info,{icon:1});
	}
}

/**
 * 询问框
 */
function showConfirm(info,success){
	parent.layer.confirm(info,{btn: ['确定','取消']},success,function(data){});
}

function closeConfirm(){
	parent.layer.closeAll('dialog');
}

/**
 * 消息提示自动关闭
 * @param {Object} id
 * @param {Object} site,位置，1：上，2：右，3：下，4：左
 * @param {Object} content
 */
function showtip(id,content){
	layer.tips(content, '#' + id, { tips: [3, '#000000'], time: 4000 });
}

/**
 * popupFrame
 * 打开加载器
 */
function showLoading(){
	$('.layui-layer-btn0', parent.document).attr("disabled","ture");
	layer.load(2);
}
/**
 * popupFrame
 * 关闭加载器
 */
function closeLoading(){
	$('.layui-layer-btn0', parent.document).removeAttr("disabled");
	layer.closeAll('loading');
}

/**
 * 关闭popupFrame
 */
function closePopupFrame(){
	var pindex = parent.layer.getFrameIndex(window.name);
	parent.layer.close(pindex);
	$('#contentFrame', parent.document).contents().find("#reloadClick").click();
}

/**
 * 同步调用
 * @param {Object} dataUrl
 * @param {Object} data
 * @param {Object} success
 * @param {Object} fail
 */
function syncAjax(dataUrl, data, success, fail) {
	dataUrl = remoteUrl + dataUrl;
	$.ajax({
		type:"post",
		url:dataUrl,
		timeout : 5000,
		data:data,
		crossDomain: true,
		xhrFields:{withCredentials:true},
		async:false,
		cache:false,
		success:success,
		error:fail
	});
}

/**
 * 异步调用
 * @param {Object} dataUrl
 * @param {Object} data
 * @param {Object} success
 * @param {Object} fail
 */
function asyncAjax(dataUrl, data, success, fail) {
	dataUrl = remoteUrl + dataUrl;
	$.ajax({
		type:"post",
		url:dataUrl,
		timeout : 5000,
		data:data,
		crossDomain: true,
		xhrFields:{withCredentials:true},
		async:true,
		cache:false,
		success:success,
		error:fail
	});
}


