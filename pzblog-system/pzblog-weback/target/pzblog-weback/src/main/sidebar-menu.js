$.sidebarMenu = function(menu) {
  var animationSpeed = 300;
  $(menu).on('click', 'li a', function(e) {
    var $this = $(this);
    var checkElement = $this.next();
    if(checkElement.is('.treeview-menu') && checkElement.is(':visible')) {
	      checkElement.slideUp(animationSpeed, function() {
		        checkElement.removeClass('menu-open');
	      });
	      checkElement.parent("li").removeClass("active");
	      $(this).find('i:last').removeClass('fa-angle-down');
				$(this).find('i:last').addClass('fa-angle-right');
    }else if((checkElement.is('.treeview-menu')) && (!checkElement.is(':visible'))) {
	      var parent = $this.parents('ul').first();
				parent.find('ul:visible').prev().find('i:last').removeClass('fa-angle-down');
				parent.find('ul:visible').prev().find('i:last').addClass('fa-angle-right');
	      var ul = parent.find('ul:visible').slideUp(animationSpeed);
	      ul.removeClass('menu-open');
	      var parent_li = $this.parent("li");
	      $(this).find('i:last').removeClass('fa-angle-right');
				$(this).find('i:last').addClass('fa-angle-down');
	      checkElement.slideDown(animationSpeed, function() {
		        checkElement.addClass('menu-open');
		        parent.find('li.active').removeClass('active');
		        parent_li.addClass('active');
	      });
    }else{
    	var parent = $(this).parents('ul').parents('ul').first();
    	parent.find('li.mactive').removeClass('mactive');
			$(this).parent("li").addClass('mactive');
    }
    if(checkElement.is('.treeview-menu')) {
		      e.preventDefault();
	    }
  });
}
$.sidebarMenu($('.sidebar-menu'));
$(".select-click a:first").click();
setTimeout('$(".select-click .treeview-menu li:first a").click()',300);