require(['jquery.lazyload'], function () {
    $(function() {
    	$("img.lazy").lazyload({effect: "fadeIn"});
	});
});
