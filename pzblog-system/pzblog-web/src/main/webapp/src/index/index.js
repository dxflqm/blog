function queryAllArticle(currentNo){
	asyncAjax("service/article/queryAll", {pageNo:currentNo}, function(resultJson) {
		var result = $.parseJSON(resultJson);
		if('200' == result.code){
			bindDataToView(result.data);
			loadPaginator(result.pageNo,result.count);
		}else{
			showAlert(result.msg);
		}
	}, function(data) {
		showAlert(tip_networkError);
	});
}

function loadPaginator(pageNo,count){
	var totalPages = (count + pageSize - 1)/pageSize;
	$('#pageLimit').bootstrapPaginator({    
	    currentPage: parseInt(pageNo),    
	    totalPages: parseInt(totalPages),    
	    size:"normal",    
	    bootstrapMajorVersion: 3,    
	    alignment:"right",
	    numberOfPages:5,  
	    itemTexts: function (type, page, current) {        
	        switch (type) {            
	        case "first": return "首页";            
	        case "prev": return "上一页";            
	        case "next": return "下一页";            
	        case "last": return "末页";            
	        case "page": return page;
	        }
	    },
	    onPageClicked: function (event, originalEvent, type, page) {
    		window.location.href = $.saveURLParam(window.location.href,"pageNo",page);
        }
	});
}

function routeArticle(articleId){
	loadUrl(articleURL + "?articleId=" + articleId);
}

function routeCategory(categoryId,categoryName){
	loadUrl(articleTimeURL + "?categoryId=" + categoryId + "&categoryName=" + escape(categoryName));
}

function bindDataToView(data){
	$("#siteContent").empty();
	var siteHtml = "";
	for (var i in data) {
		var site = data[i];
		var contentHtml = "<li>";
		//标题栏
		contentHtml = contentHtml  + '<h3><a class="article-title" onclick="routeArticle(\''+site.articleId+'\')"><span>'+site.articleTitle+'</span></a></h3>';
		//时间栏
		contentHtml = contentHtml + '<div style="margin: 10px auto;font-size: 16px;">';
		if(site.articleType == 0){
			contentHtml = contentHtml + '<div class="div-ib-r5"><a><code class="article-type-origin"><span>'+site.articleTypeName+'</span></code></a></div>';
		}else{
			contentHtml = contentHtml + '<div class="div-ib-r5"><a href="'+site.reprintHref+'" target="_blank"><code class="article-type-goal"><span>'+site.articleTypeName+'</span></code></a></div>';
		}
		contentHtml = contentHtml + '<div class="div-ib-r5"><i class="fa fa-calendar"></i> <span>'+site.updateDate+'</span></div>';
		contentHtml = contentHtml + '<div class="div-ib-r5"><i class="fa fa-folder"></i> <a onclick="routeCategory(\''+site.categoryId+'\',\''+site.categoryName+'\')" style="color: black;"><span>'+site.categoryName+'</span></a></div>';
		contentHtml = contentHtml + '<div class="div-ib-r5"><i class="fa fa-tag"></i> <span>'+site.tagName+'</span></div>';
		contentHtml = contentHtml + "</div>";
		//文章介绍区
		contentHtml = contentHtml + '<div style="color: #565a5f;padding: 10px auto;line-height: 20px;font-size: 16px;">' + '<p><span>'+site.articleIntroduce+'</span></p>' + '</div>';
		contentHtml = contentHtml + "</li>";
		siteHtml = siteHtml + contentHtml;
	}
	$("#siteContent").append(siteHtml);
}